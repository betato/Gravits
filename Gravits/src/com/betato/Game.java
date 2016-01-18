package com.betato;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;

import com.betato.gameDisplay.GameWindow;
import com.betato.gameDisplay.KeyStates;
import com.betato.gameDisplay.MouseStates;

public class Game extends GameWindow {

	boolean[] lastKeys = new boolean[KeyStates.NUM_KEYS];
	Simulator sim = new Simulator(6.67384E-11, 60);
	Renderer rn;
	boolean running = false;
	boolean fullscreen;
	Point center = new Point(0, 0);
	Point lastCenter;
	int camMode = 1;
	
	public Game() {
		init(60, 120, "Gravits", new Dimension(800, 800), false, false);
	}

	@Override
	public void onInit() {
		//sim.bodies.add(new Body(5.972E24, 6.371E6, new Vec2d(7E7, 1E7), new Vec2d(0, 0), new Vec2d(0, 0)));
		//sim.bodies.add(new Body(6.39E23, 3.36E6, new Vec2d(-1E7, -1E7), new Vec2d(50, 50), new Vec2d(0, 0)));
		//sim.bodies.add(new Body(3.64E24, 4.35E6, new Vec2d(2E7, 0), new Vec2d(100, -20), new Vec2d(0, 0)));
		//sim.bodies.add(new Body(6.97E24, 6.371E6, new Vec2d(2E7, 1E7), new Vec2d(200, 0), new Vec2d(0, 0)));
		//sim.bodies.add(new Body(5.97E24, 6371000, new Vec2d(0, 0), new Vec2d(0, 0), new Vec2d(0, 0))); // Earth
		//sim.bodies.add(new Body(1E29, 40.2E6, new Vec2d(102039239, 5), new Vec2d(0, 0), new Vec2d(0, 0)));
		//sim.bodies.add(new Body(7.35E22, 137000, new Vec2d(3740000, 0), new Vec2d(0, 0), new Vec2d(0, 368600))); // Moon
		
		sim.bodies.add(new Body(7.97E25, 6371000, new Vec2d(0, 0), new Vec2d(40, 0), new Vec2d(0, 0))); // Earth
		sim.bodies.add(new Body(7.35E22, 937000, new Vec2d(37400000, 0), new Vec2d(40, -200), new Vec2d(0, 0))); // Moon

		rn = new Renderer(getContentSize().getSize(), 6E6);
	}
	
	TextBox tb = new TextBox("New Body", 180, new String[] { "1", "aa", "poi" }, TextBox.OK_CANCEL_BUTTONS);
	
	@Override
	public void onUpdate(KeyStates keys, MouseStates mouse, boolean resized) {
		// Exit on escape
		if (keys.keyReleases[KeyStates.ESCAPE]) {
			exit();
		}
		
		tb.update(keys, mouse, new Point(10, 10));
		
		// Pause on space
		if (keys.keyReleases[KeyStates.SPACE]) {
			running = !running;
		}
		
		// Create body on right click
		if (mouse.buttonReleases[MouseStates.BUTTON_3]) {
			// This in next on to-do
			running = !running;
		}
		
		// Switch camera on c
		if (keys.keyReleases[KeyStates.C]) {
			if (camMode >= 2) {
				camMode = 0;  
			}
			else { 
				camMode++;
			}
		}
		
		// Go fullscreen on f11
		if (keys.keyReleases[KeyStates.F11]) {
			//Space released
			fullscreen = !fullscreen;
			setFullscreen(fullscreen);
		}
		
		//Pan when clicked
		if (mouse.buttonStates[MouseStates.BUTTON_1]) {
			center.x += mouse.pos.x - lastCenter.x;
			center.y += mouse.pos.y - lastCenter.y;
		}
		
		if (running) {
			sim.step();
		}		
		
		if (resized) {
			rn.size = getContentSize().getSize();
		}
		
		lastCenter = mouse.pos;
		rn.scale = 7E5 + mouse.wheel * 10000;
		//System.out.println(rn.pointToSim(mouse.pos).toString());
	}

	@Override
	public void onRender(Graphics g) {
		String message = "";
		
		switch (camMode) {
		case 0:
			message += "Camera: Static";
			break;
		case 1:
			message += "Camera: Pan";
			break;
		case 2:
			message += "Camera: Chase";
			break;
		}
		
		message += ",";
		
		if (!running) {
			message += "Paused";
		}
		
		g.drawImage(rn.frame(sim, message, center, camMode), 0, 0, null);
		tb.drawTextBox(g, new Point(10, 10));

		center.x = 0;
		center.y = 0;
	}

	@Override
	public void onExit() {
		
	}
}
