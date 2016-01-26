package com.betato;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;

/**
 * The {@code Renderer} class is used to draw a simulation as a BufferedImage
 */
public class SimulatorRenderer {

	/**
	 * Initializes a renderer with a specified height width and scale
	 */
	public SimulatorRenderer(int height, int width, double scale) {
		this.width = width;
		this.height = height;
		this.scale = scale;
		halfHeight = height / 2;
		halfWidth = width / 2;
	}

	public double scale;
	private int height;
	private int width;

	private int halfHeight;
	private int halfWidth;
	private Vec2d center;

	int camX;
	int camY;

	/**
	 * Sets the renderer to the specified scale.
	 * 
	 * <pre>
	 * public void reScale(double scale)
	 * </pre>
	 *
	 * @param scale
	 *            - the scale to set the renderer to
	 */
	//public void reScale(double scale) {
	//	this.scale = scale;
	//}

	/**
	 * Re-sizes the renderer image.
	 * 
	 * <pre>
	 * public void reSize(int height, int width)
	 * </pre>
	 *
	 * @param height
	 *            - the height to set the renderer to
	 * @param width
	 *            - the width to set the renderer to
	 */
	public void reSize(int height, int width) {
		this.width = width;
		this.height = height;
		halfHeight = height / 2;
		halfWidth = width / 2;
	}

	/**
	 * Draws a simulator
	 * 
	 * <pre>
	 * public BufferedImage frame(Simulator simulator, Point position, int camera, boolean drawPoints)
	 * </pre>
	 *
	 * @param simulator
	 *            - the simulator to draw
	 * 
	 * @param position
	 *            - the point to draw the simulator from
	 *
	 * @param camera
	 *            - the simulator camera mode
	 * 
	 * @param drawPoints
	 *            - whether or not bodies will be highlighted by a green dot
	 */
	public BufferedImage frame(Simulator simulator, Point position, int camera, boolean drawPoints) {

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
		BufferedImage frame = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		Graphics g = frame.getGraphics();

		// Draw background
		g.setColor(Color.white);
		g.fill3DRect(0, 0, width + 1, height + 1, false);

		// Draw planets
		g.setColor(Color.black);
		for (int i = 0; i < simulator.bodies.size(); i++) {
			drawBody(g, simulator.bodies.get(i).position.x, simulator.bodies.get(i).position.y,
					simulator.bodies.get(i).radius);
		}

		if (drawPoints) {
			// Draw planet location overlay
			g.setColor(new Color(0, 255, 0, 128));
			for (int i = 0; i < simulator.bodies.size(); i++) {
				drawCircle(g, simulator.bodies.get(i).position.x, simulator.bodies.get(i).position.y, 5);
			}
		}
		return frame;
	}

	/**
	 * Scales and draws a body.
	 * 
	 * <pre>
	 * public void drawBody(Graphics g, double x, double y, double radius)
	 * </pre>
	 *
	 * @param g
	 *            - the graphics to draw with
	 * @param x
	 *            - the x position of the circle
	 * @param y
	 *            - the y position of the circle
	 * @param radius
	 *            - the radius of the circle
	 */
	public void drawBody(Graphics g, double x, double y, double radius) {
		int scaledRadius = (int) (radius / scale);
		g.fillOval((int) ((x / scale) + halfWidth - scaledRadius + camX),
				(int) ((y / scale) + halfHeight - scaledRadius + camY), scaledRadius * 2, scaledRadius * 2);
	}

	/**
	 * Draws a centered circle.
	 * 
	 * <pre>
	 * public void drawCircle(Graphics g, double x, double y, int radius)
	 * </pre>
	 *
	 * @param g
	 *            - the graphics to draw with
	 * @param x
	 *            - the x position of the circle
	 * @param y
	 *            - the y position of the circle
	 * @param radius
	 *            - the radius of the circle
	 */
	public void drawCircle(Graphics g, double x, double y, int radius) {
		int drawDi = radius * 2;
		g.fillOval((int) ((x / scale) - radius + halfWidth + camX), (int) ((y / scale) - radius + halfHeight + camY),
				drawDi, drawDi);
	}

	/**
	 * Gets the simulator coordinate of a position on screen
	 * 
	 * <pre>
	 * public Vec2d pointToSim(Point p)
	 * </pre>
	 *
	 * @param p
	 *            - the point on screen to get the simulator coordinate of
	 * 
	 * @return The vector representing the given point
	 */
	public Vec2d pointToSim(Point p) {
		// Scale up point to scale
		return new Vec2d((p.x - halfWidth) * scale, (p.y - halfHeight) * scale);
	}

	/**
	 * Gets the on screen coordinate of a position in the simulator
	 * 
	 * <pre>
	 * public Point simToPoint(Vec2d vec)
	 * </pre>
	 *
	 * @param vec
	 *            - the point in the simulator to get the on screen coordinate of
	 * 
	 * @return The point representing the given vector
	 */
	public Point simToPoint(Vec2d vec) {
		// Scale down simulator points to display
		return new Point((int) ((vec.x + halfWidth) / scale), (int) ((vec.y + halfHeight) / scale));
	}

	/**
	 * Gets the centerpoint of the specified simulator.
	 * 
	 * <pre>
	 * private Vec2d center(Simulator simulator)
	 * </pre>
	 *
	 * @param simulator
	 *            - the simulator to find the center of
	 * 
	 * @return The vector representing the center of all simulator bodies
	 */
	private Vec2d center(Simulator simulator) {
		Vec2d v = new Vec2d();
		for (Body body : simulator.bodies) {
			v.add(body.position);
		}
		v.div(simulator.bodies.size());
		return v;
	}
}
