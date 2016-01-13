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

	public BufferedImage frame(Simulator simulator, String message, Point center) {
		BufferedImage frame = new BufferedImage(size.width, size.height,
				BufferedImage.TYPE_INT_ARGB);
		
		Graphics graphics = frame.getGraphics();
		
		graphics.setColor(Color.white);
		graphics.fill3DRect(0, 0, size.width + 1, size.height + 1, false);
		graphics.setColor(Color.black);
		
		for (int i = 0; i < simulator.bodies.size(); i++) {
			int scaleX = (int) (simulator.bodies.get(i).position.x / scale);
			int scaleY = (int) (simulator.bodies.get(i).position.y / scale);
			int scaleR = (int) (simulator.bodies.get(i).radius / scale);
			int scaleW = (size.width / 2);
			int scaleH = (size.height / 2);
			//Vec2d cam = center(simulator);
			//int camX = (int) (cam.x / scale);
			//int camY = (int) (cam.y / scale);
			
			int camX = center.x;
			int camY = center.y;
			
			graphics.setColor(Color.black);
			graphics.fillOval((scaleX - scaleR) + scaleW + camX, (scaleY - scaleR) + scaleH + camY, scaleR * 2, scaleR * 2);
			
			graphics.setColor(new Color(0, 255, 0, 128));
			graphics.fillOval((scaleX - 5) + scaleW + camX, (scaleY - 5) + scaleH + camY, 10, 10);
		}
		
		if (message != null) {
			graphics.setColor(Color.white);
			graphics.setFont(new Font("SansSerif", Font.BOLD, 24));
			String[] lines = message.split(",");
			for (String line : lines) {
				graphics.drawString(line, 100, 100);
			}
		}
		
		return frame;
	}

	public Vec2d pointToSim(Point p) {
		return new Vec2d(p.x * scale, p.y * scale);
	}
	
	private Vec2d center(Simulator simulator) {
		Vec2d v = new Vec2d();
		for (Body body : simulator.bodies) {
			v.add(body.position);
		}
		v.mul(1 / simulator.bodies.size());
		return v;
	}
	
}
