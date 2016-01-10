package com.betato;

import java.awt.Dimension;
import java.awt.Graphics;

public class Game extends GameWindow {

	Simulator sim = new Simulator(6.67384E-11, 50);
	Renderer rn;

	public Game() {
		init(60, 120, "Gravits", new Dimension(800, 800), false, false);
	}

	@Override
	public void onInit() {
		sim.bodies.add(new Body(5.972E24, 6.371E6, new Vec2d(7E7, 1E7), new Vec2d(0, 0), new Vec2d(0, 0)));
		sim.bodies.add(new Body(6.39E23, 3.36E6, new Vec2d(-1E7, -1E7), new Vec2d(50, 50), new Vec2d(0, 0)));
		sim.bodies.add(new Body(3.64E24, 4.35E6, new Vec2d(2E7, 0), new Vec2d(100, -20), new Vec2d(0, 0)));
		sim.bodies.add(new Body(6.97E24, 6.371E6, new Vec2d(2E7, 1E7), new Vec2d(200, 0), new Vec2d(0, 0)));

		rn = new Renderer(getContentSize(), 6E5);
	}
	boolean k;
	@Override
	public void onUpdate(KeyStates keys, MouseStates mouse) {
		if (keys.keystates[KeyStates.ESCAPE] != k && keys.keystates[KeyStates.ESCAPE] == false) {
			//Escape released
			exit();
		}
		//System.out.println(mouse.wheel);
		k = keys.keystates[KeyStates.ESCAPE];
		sim.step();
	}

	@Override
	public void onRender(Graphics g) {
		g.drawImage(rn.frame(sim, true), 0, 0, null);
		rn.frame(sim, true);
	}

	@Override
	public void onExit() {

	}
}
