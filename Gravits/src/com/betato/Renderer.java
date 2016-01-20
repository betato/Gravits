package com.betato;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;

public class Renderer {
	Dimension size = new Dimension(0, 0);
	double scale;

	public Renderer(Dimension size, double scale) {
		this.size = size;
		this.scale = scale;
	}

	int camX;
	int camY;
	Vec2d cam;

	public void createTextBox() {

	}

	public BufferedImage frame(Simulator simulator, String message, Point center, int camera) {
		BufferedImage frame = new BufferedImage(size.width, size.height, BufferedImage.TYPE_INT_ARGB);

		Graphics g = frame.getGraphics();

		g.setColor(Color.white);
		g.fill3DRect(0, 0, size.width + 1, size.height + 1, false);
		g.setColor(Color.black);

		for (int i = 0; i < simulator.bodies.size(); i++) {
			int scaleX = (int) (simulator.bodies.get(i).position.x / scale);
			int scaleY = (int) (simulator.bodies.get(i).position.y / scale);
			int scaleR = (int) (simulator.bodies.get(i).radius / scale);
			int scaleW = (size.width / 2);
			int scaleH = (size.height / 2);
			cam = center(simulator);

			switch (camera) {
			case 0:
				// Static
				break;
			case 1:
				// Pan
				camX += center.x;
				camY += center.y;
				break;
			case 2:
				// Tracking
				camX = (int) (-cam.x / scale);
				camY = (int) (-cam.y / scale);
				break;
			}

			g.setColor(Color.black);
			g.fillOval((scaleX - scaleR) + scaleW + camX, (scaleY - scaleR) + scaleH + camY, scaleR * 2, scaleR * 2);

			g.setColor(new Color(0, 255, 0, 128));
			g.fillOval((scaleX - 5) + scaleW + camX, (scaleY - 5) + scaleH + camY, 10, 10);
		}

		drawMessage(g, message);

		return frame;
	}

	private void drawMessage(Graphics g, String message) {
		if (message != null) {
			g.setColor(Color.white);
			g.setFont(new Font("SansSerif", Font.BOLD, 24));
			String[] lines = message.split(",");
			for (int i = 0; i < lines.length; i++) {
				g.drawString(lines[i], 50, 50 * (i + 1));
			}
		}
	}

	public Vec2d pointToSim(Point p) {
		return new Vec2d(p.x * scale, p.y * scale);
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
