package com.betato;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;

@SuppressWarnings("serial")
public class Display extends JFrame {
	public boolean running = false;
	int fps;
	Rectangle innerFrame = new Rectangle(0, 0, 0, 0);

	Simulator sim = new Simulator(6.67384E-11, 2, 50);
	Renderer rn;

	public Display() {
		// Create frame
		super("Gravits");
		this.fps = fps;
		setSize(600, 600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		
		// Get animation size
		scale();

		// Init renderer
		rn = new Renderer(innerFrame, 6E5);
		
		// Init listeners
		initListeners();

		// Add bodies
		sim.bodies.add(new Body(5.972E24, 6.371E6, new double[] { 7E7, 1E7 },
				new double[] { 0, 0 }, new double[] { 0, 0 }));
		sim.bodies.add(new Body(6.39E23, 3.36E6, new double[] { -1E7, -1E7 },
				new double[] { 50, 50 }, new double[] { 0, 0 }));
		sim.bodies.add(new Body(3.64E24, 4.35E6, new double[] { 2E7, 0 },
				new double[] { 100, -20 }, new double[] { 0, 0 }));
		sim.bodies.add(new Body(6.97E24, 6.371E6, new double[] { 2E7, 1E7 },
				new double[] { 200, 0 }, new double[] { 0, 0 }));
	}

	private void scale() {
		innerFrame.x = this.getInsets().left;
		innerFrame.y = this.getInsets().top;
		innerFrame.width = this.getContentPane().getWidth();
		innerFrame.height =  this.getContentPane().getHeight();
	}
	
	private void initListeners() {
		this.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// Something to get simulator position here
			}

			@Override
			public void mouseEntered(MouseEvent e) { }

			@Override
			public void mouseExited(MouseEvent e) { }

			@Override
			public void mousePressed(MouseEvent e) { }

			@Override
			public void mouseReleased(MouseEvent e) { }
		});
		
		this.addKeyListener(new KeyListener() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyChar() == ' ') {
					running = !running;
				}
			}

			@Override
			public void keyReleased(KeyEvent arg0) { }

			@Override
			public void keyTyped(KeyEvent arg0) { }
		});
		
		super.addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e) {
            	scale();
            }
        });
	}

	public void update() {
		if (running) {
			sim.step();
		}
	}

	public void paint(Graphics g) {
		g.drawImage(rn.frame(sim, running), innerFrame.x, innerFrame.y,
				null);
	}
}
