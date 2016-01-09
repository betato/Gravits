package com.betato;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;

public abstract class GameWindow extends GameLoop {

	private JFrame window;
	private JPanel display;
	private final Dimension DEFAULT_SIZE = new Dimension(400, 400);

	public void init(int fps, int ups, String title, Dimension size, boolean resizable, boolean fullscreen) {
		set(fps, ups);
		window = new JFrame(title);
		display = new JPanel() {
			private static final long serialVersionUID = 1L;

			@Override
			public void paintComponent(Graphics g) {
				onRender(g);
			}
		};
		window.add(display);
		window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		if (fullscreen) {
			window.setUndecorated(fullscreen);
			window.setExtendedState(window.getExtendedState() | JFrame.MAXIMIZED_BOTH);
		} else {
			window.setSize(size);
		}
		setResizable(resizable);
		initListeners();
		window.setVisible(true);
		run();
	}

	public void setFullscreen(boolean fullscreen) {
		window.dispose();
		window.setUndecorated(fullscreen);
		if (fullscreen) {
			window.setExtendedState(window.getExtendedState() | JFrame.MAXIMIZED_BOTH);
		} else {
			window.setSize(DEFAULT_SIZE);
		}
		window.setVisible(true);
	}

	public void setResizable(boolean resizable) {
		window.setResizable(resizable);
	}

	public void setFrameSize(Dimension size) {
		window.setSize(size);
	}

	public void setContentSize(Dimension size) {
		window.getContentPane().setPreferredSize(size);
		window.pack();
	}

	public Rectangle getContentSize() {
		return window.getContentPane().getBounds();
	}

	public Rectangle getFrameSize() {
		return window.getBounds();
	}

	private void initListeners() {
		window.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// Something to get simulator position here
			}

			@Override
			public void mouseEntered(MouseEvent e) {
			}

			@Override
			public void mouseExited(MouseEvent e) {
			}

			@Override
			public void mousePressed(MouseEvent e) {
				
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				
			}
		});

		window.addKeyListener(new KeyListener() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyChar() == ' ') {

				}
			}

			@Override
			public void keyReleased(KeyEvent e) {

			}

			@Override
			public void keyTyped(KeyEvent e) {
			}
		});

		window.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {

			}
		});

		window.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent windowEvent) {
				// Halt gameloop
				stop();
				// Call the exit stuff
				onExit();
				// Shutdown everything
				System.exit(0);
			}
		});
	}

	@Override
	public void init() {
		onInit();
	}

	@Override
	public void update() {
		onUpdate();
	}

	@Override
	public void render() {
		display.repaint();
	}

	@Override
	public void displayFps(int fps, int ups) {
		System.out.println("Fps: " + fps);
		System.out.println("Ups: " + ups);
	}

	abstract public void onInit();

	abstract public void onUpdate();

	abstract public void onRender(Graphics g);

	abstract public void onExit();

	abstract public void mouseClicked(MouseEvent e);

	abstract public void mousePressed(MouseEvent e);

	abstract public void mouseReleased(MouseEvent e);

	abstract public void mouseClicked(KeyEvent e);

	abstract public void keyPressed(KeyEvent e);

	abstract public void keyReleased(KeyEvent e);

	abstract public void windowResized(KeyEvent e);
}
