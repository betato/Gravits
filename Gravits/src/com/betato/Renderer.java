package com.betato;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;

public class Renderer {

	public Renderer(int height, int width, double scale) {
		this.width = width;
		this.height = height;
		this.scale = scale;
		halfHeight = height / 2;
		halfWidth = width / 2;
	}

	double scale;
	int height;
	int width;

	int halfHeight;
	int halfWidth;
	Vec2d center;

	int camX;
	int camY;

	public void reScale(double scale) {
		this.scale = scale;
	}

	public void reSize(int height, int width) {
		this.width = width;
		this.height = height;
		halfHeight = height / 2;
		halfWidth = width / 2;
	}

	public BufferedImage frame(Simulator simulator, Point position, int camera,
			boolean drawPoints) {
		
		// Get camera position
		switch (camera) {
		case 0:
			// Static
			break;
		case 1:
			// Pan
			camX += position.x;
			camY += position.y;
			break;
		case 2:
			// Tracking
			center = center(simulator);
			camX = (int) (-center.x / scale);
			camY = (int) (-center.y / scale);
			break;
		}
		
		// Create image
		BufferedImage frame = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_ARGB);
		Graphics g = frame.getGraphics();

		// Draw background
		g.setColor(Color.white);
		g.fill3DRect(0, 0, width + 1, height + 1, false);
		
		// Draw planets
		g.setColor(Color.black);
		for (int i = 0; i < simulator.bodies.size(); i++) {
			drawBody(g, simulator.bodies.get(i).position.x,
					simulator.bodies.get(i).position.y,
					simulator.bodies.get(i).radius);
		}

		if (drawPoints) {
			// Draw planet location overlay
			g.setColor(new Color(0, 255, 0, 128));
			for (int i = 0; i < simulator.bodies.size(); i++) {
				drawCircle(g, simulator.bodies.get(i).position.x,
						simulator.bodies.get(i).position.y, 5);
			}
		}
		return frame;
	}

	public void drawBody(Graphics g, double x, double y, double radius) {
		int scaledRadius = (int) (radius / scale);
		g.fillOval((int) ((x / scale) + halfWidth - scaledRadius + camX),
				(int) ((y / scale) + halfHeight - scaledRadius + camY),
				scaledRadius * 2, scaledRadius * 2);
	}

	public void drawCircle(Graphics g, double x, double y, int radius) {
		int drawDi = radius * 2;
		g.fillOval((int) ((x / scale) - radius + halfWidth + camX),
				(int) ((y / scale) - radius + halfHeight + camY), drawDi,
				drawDi);
	}

	public Vec2d pointToSim(Point p) {
		// Scale up point to scale
		return new Vec2d((p.x - halfWidth) * scale, (p.y - halfHeight) * scale);
	}

	public Point simToPoint(Vec2d vec) {
		// Scale down simulator points to display
		return new Point((int)((vec.x + halfWidth) / scale), (int)((vec.y + halfHeight) / scale));
	}
	
	private Vec2d center(Simulator simulator) {
		Vec2d v = new Vec2d();
		for (Body body : simulator.bodies) {
			v.add(body.position);
		}
		v.div(simulator.bodies.size());
		return v;
	}
}
