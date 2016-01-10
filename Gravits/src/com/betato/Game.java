package com.betato;

import java.awt.Dimension;
import java.awt.Graphics;

import com.betato.gameDisplay.GameWindow;
import com.betato.gameDisplay.KeyStates;
import com.betato.gameDisplay.MouseStates;

public class Game extends GameWindow {

	boolean[] lastKeys = new boolean[KeyStates.NUM_KEYS];
	Simulator sim = new Simulator(6.67384E-11, 50);
	Renderer rn;
	boolean running = true;
	boolean fullscreen;

	public Game() {
		init(60, 120, "Gravits", new Dimension(800, 800), false, false);
	}

	@Override
	public void onInit() {
		sim.bodies.add(new Body(5.972E24, 6.371E6, new Vec2d(7E7, 1E7), new Vec2d(0, 0), new Vec2d(0, 0)));
		sim.bodies.add(new Body(6.39E23, 3.36E6, new Vec2d(-1E7, -1E7), new Vec2d(50, 50), new Vec2d(0, 0)));
		sim.bodies.add(new Body(3.64E24, 4.35E6, new Vec2d(2E7, 0), new Vec2d(100, -20), new Vec2d(0, 0)));
		sim.bodies.add(new Body(6.97E24, 6.371E6, new Vec2d(2E7, 1E7), new Vec2d(200, 0), new Vec2d(0, 0)));

		rn = new Renderer(getContentSize().getSize(), 6E5);
	}
	
	@Override
	public void onUpdate(KeyStates keys, MouseStates mouse, boolean resized) {
		if (keys.keystates[KeyStates.ESCAPE] != lastKeys[KeyStates.ESCAPE] && keys.keystates[KeyStates.ESCAPE] == false) {
			//Escape released
			exit();
		}
		
		if (keys.keystates[KeyStates.SPACE] != lastKeys[KeyStates.SPACE] && keys.keystates[KeyStates.SPACE] == false) {
			//Space released
			running = !running;
		}
		
		if (keys.keystates[KeyStates.F11] != lastKeys[KeyStates.F11] && keys.keystates[KeyStates.F11] == false) {
			//Space released
			fullscreen = !fullscreen;
			setFullscreen(fullscreen);
		}
		
		if (running) {
			sim.step();
		}		
		
		if (resized) {
			rn.size = getContentSize().getSize();
		}
		
		rn.scale = 6E5 + mouse.wheel * 10000;
		lastKeys[KeyStates.ESCAPE] = keys.keystates[KeyStates.ESCAPE];
		lastKeys[KeyStates.SPACE] = keys.keystates[KeyStates.SPACE];
		lastKeys[KeyStates.F11] = keys.keystates[KeyStates.F11];
		//System.out.println(rn.pointToSim(mouse.pos).toString());
	}

	@Override
	public void onRender(Graphics g) {
		g.drawImage(rn.frame(sim, (running ? null : "Paused")), 0, 0, null);;
	}

	@Override
	public void onExit() {
		
	}
}
