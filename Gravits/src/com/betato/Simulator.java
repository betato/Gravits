package com.betato;

import java.util.ArrayList;

public class Simulator {
	public ArrayList<Body> bodies = new ArrayList<Body>();
	public double gravConst = 6.67384E-11;
	public int interval = 50;

	// Initialize the simulator with specified gravitational constant and timestep
	public Simulator(double gravConst, int timestep) {
		this.gravConst = gravConst;
		this.interval = timestep;
	}

	// Advance the simulation
	public void step() {
		// Apply gravitational forces on the bodies
		accelerate();
		// Move the bodies by the applied force and timestep
		move();
	}

	// Calculate acceleration of each body by calculating force between every body
	public void accelerate() {
		// loop for each body
		for (int i = 0; i < bodies.size(); i++) {
			// loop for each body acting upon the body (except for bodies that
			// will be calculated in the main loop)
			for (int j = 0; j < i; j++) {
				// find distance
				double distance;
				Vec2d directionalDistance = Vec2d.sub(bodies.get(i).position, bodies.get(j).position);
				distance = Vec2d.dst(bodies.get(i).position, bodies.get(j).position);

				// calculate gravitational force on body (F = G m1*m2 / r2)
				double force = gravConst * bodies.get(i).mass * bodies.get(j).mass / Math.pow(distance, 2);

				// add acceleration (a = F / m)
				// along x axis
				bodies.get(i).acceleration.x -= (directionalDistance.x / distance) * (force / bodies.get(i).mass);
				bodies.get(j).acceleration.x += (directionalDistance.x / distance) * (force / bodies.get(j).mass);
				// along y axis
				bodies.get(i).acceleration.y -= (directionalDistance.y / distance) * (force / bodies.get(i).mass);
				bodies.get(j).acceleration.y += (directionalDistance.y / distance) * (force / bodies.get(j).mass);
			}
		}
	}

	// Move each body based on their acceleration
	public void move() {
		// loop for each body
		for (int i = 0; i < bodies.size(); i++) {
			// loop for each axis
			// calculate velocity (v = a * t)
			bodies.get(i).velocity.x += bodies.get(i).acceleration.x * interval;
			bodies.get(i).velocity.y += bodies.get(i).acceleration.y * interval;
			// calculate position (metres = metres/second * seconds)
			bodies.get(i).position.x += bodies.get(i).velocity.x * interval;
			bodies.get(i).position.y += bodies.get(i).velocity.y * interval;
			// Reset acceleration
			bodies.get(i).acceleration.x = 0;
			bodies.get(i).acceleration.y = 0;
		}
	}
}