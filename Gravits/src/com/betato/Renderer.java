package com.betato;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Renderer {
	Dimension size = new Dimension (0, 0);
	double scale;
	
	public Renderer(Rectangle size, double scale) {
		this.size.width = size.width;
		this.size.height = size.height;
		this.scale = scale;
	}
	
	public BufferedImage frame(Simulator simulator, boolean running) {
		BufferedImage frame = new BufferedImage(size.width, size.height,
				BufferedImage.TYPE_INT_ARGB);
		
		Graphics graphics = frame.getGraphics();
		
		graphics.setColor(Color.white);
		graphics.fill3DRect(0, 0, size.width, size.height, false);
		graphics.setColor(Color.black);
		
		for (int i = 0; i < simulator.bodies.size(); i++) {
			int scaleX = (int) (simulator.bodies.get(i).position.x / scale);
			int scaleY = (int) (simulator.bodies.get(i).position.y / scale);
			int scaleR = (int) (simulator.bodies.get(i).radius / scale);

			graphics.fillOval((scaleX - scaleR) + (size.width / 2), (scaleY - scaleR)
					+ (size.height / 2), scaleR * 2, scaleR * 2);
		}
		
		/*if (!running) {
			graphics.setColor(Color.white);
			graphics.setFont(new Font("SansSerif", Font.BOLD, 24));
			graphics.drawString("Paused", 100, 100);
		}*/
		
		//graphics.fillOval(10,10,10,10);
		
		return frame;
	}
	
}
