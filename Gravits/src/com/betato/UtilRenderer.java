package com.betato;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class UtilRenderer {
	public static void drawText(Graphics g, String message, int posX, int posY) {
		if (message != null) {
			g.setColor(Color.white);
			g.setFont(new Font("SansSerif", Font.BOLD, 24));
			String[] lines = message.split(",");
			for (int i = 0; i < lines.length; i++) {
				g.drawString(lines[i], posX, posY * (i + 1));
			}
		}
	}

	public static void drawArrow(Graphics g, int x1, int y1, int x2, int y2,
			int headLength, int headWidth, int lineThickness) {
		// Only draw if two points are not identical
		if (x1 != x2 || y1 != y2) {
			// Get line length
			int dx = x2 - x1;
			int dy = y2 - y1;
			double l = Math.sqrt(dx * dx + dy * dy);

			// Sine and cosine of line to get arrow head points
			double sin = dy / l;
			double cos = dx / l;

			// Get locations of arrow head endpoints
			double xm = headLength * cos - headWidth * sin + x1;
			double ym = headLength * sin + headWidth * cos + y1;

			double xn = headLength * cos + headWidth * sin + x1;
			double yn = headLength * sin - headWidth * cos + y1;

			// Get graphics 2d from graphics for line
			Graphics2D g2d = (Graphics2D) g;
			g2d.setStroke(new BasicStroke(lineThickness));
			// Draw line
			g2d.drawLine(x1, y1, x2, y2);
			// Draw arrow sections
			g2d.drawLine(x1, y1, (int) xm, (int) ym);
			g2d.drawLine(x1, y1, (int) xn, (int) yn);
		}
	}
}
