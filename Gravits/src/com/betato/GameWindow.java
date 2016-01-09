package com.betato;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JFrame;
import javax.swing.JPanel;

public abstract class GameWindow extends GameLoop{
	
	private JFrame window;
	private JPanel display;
	public Dimension size;
	
	public void init(int fps, int ups, String title, Dimension size, boolean resizable, boolean fullscreen){
		set(fps, ups);
		window = new JFrame(title);
		window.setSize(size);
		display = new JPanel() {
			private static final long serialVersionUID = 1L;
			@Override
	        public void paintComponent(Graphics g) {
	            render(g);
	        }
	    };
		window.add(display);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(resizable);
		window.setVisible(true);
		if (fullscreen) {
			
		}
		
		initListeners();
	}
	
	public void setFullscreen(boolean fullscreen) {
		window.setUndecorated(fullscreen);
		window.setExtendedState(JFrame.MAXIMIZED_BOTH); 
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
	
	private void initListeners() {
		window.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// Something to get simulator position here
			}

			@Override
			public void mouseEntered(MouseEvent e) { }

			@Override
			public void mouseExited(MouseEvent e) { }

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
			public void keyTyped(KeyEvent e) { }
		});
		
		window.addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e) {
            	
            }
        });
	}

	@Override
	public void onUpdate() {
		
	}

	@Override
	public void onRender() {
		display.repaint();
	}

	@Override
	public void onDisplayFps(int fps, int ups) {
		System.out.println("Fps: " + fps );
		System.out.println("Ups: " + ups);
	}
	
	abstract public void update();

	abstract public void render(Graphics g);
	
	abstract public void mouseClicked(MouseEvent e);
	
	abstract public void mousePressed(MouseEvent e);
	
	abstract public void mouseReleased(MouseEvent e);
	
	abstract public void mouseClicked(KeyEvent e);
	
	abstract public void keyPressed(KeyEvent e);
	
	abstract public void keyReleased(KeyEvent e);
	
	abstract public void windowResized(KeyEvent e);
}
