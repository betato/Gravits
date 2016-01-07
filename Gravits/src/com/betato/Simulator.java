package com.betato;

import java.util.ArrayList;

public class Simulator {
	public ArrayList<Body> bodies = new ArrayList<Body>();
	public double GRAV_CONST = 6.67384E-11;
	public int DEGREES_OF_FREEDOM = 2;
	public int interval = 50;

	public Simulator(double GRAV_CONST, int DEGREES_OF_FREEDOM, int TIMESTEP) {
		this.GRAV_CONST = GRAV_CONST;
		//this.DEGREES_OF_FREEDOM = DEGREES_OF_FREEDOM;
		this.interval = TIMESTEP;
	}

	public void step() {
		accelerate();
		move();
		collide();
	}

	public void accelerate() {
		// loop for each body
		for (int i = 0; i < bodies.size(); i++) {
			// loop for each body acting upon the body (except for bodies that
			// will be calculated in the main loop)
			for (int j = 0; j < i; j++) {
				// find distance
				double distance;
				double[] directionalDistance = dirDistance(bodies.get(i), bodies.get(j));
				distance = calcDistance(directionalDistance);

				// calculate gravitational force on body (F = G m1*m2 / r2)
				double force = GRAV_CONST * bodies.get(i).mass * bodies.get(j).mass / Math.pow(distance, 2);

				// add acceleration (a = F / m)
				// subtract
				bodies.get(i).acceleration.x -= (directionalDistance[0] / distance) * (force / bodies.get(i).mass);
				bodies.get(i).acceleration.y -= (directionalDistance[1] / distance) * (force / bodies.get(i).mass);
				// add
				bodies.get(j).acceleration.x += (directionalDistance[0] / distance) * (force / bodies.get(i).mass);
				bodies.get(j).acceleration.y += (directionalDistance[1] / distance) * (force / bodies.get(i).mass);
			}
		}
	}

	public void move() {
		// loop for each body
		for (int i = 0; i < bodies.size(); i++) {
			// loop for each axis
			for (int j = 0; j < DEGREES_OF_FREEDOM; j++) {
				// calculate velocity (v = a * t)
				bodies.get(i).velocity.x = bodies.get(i).acceleration.x * interval;
				bodies.get(i).velocity.y = bodies.get(i).acceleration.y * interval;
				// calculate position (metres = metres/second * seconds)
				bodies.get(i).position.x += bodies.get(i).velocity.x * interval;
				bodies.get(i).position.y += bodies.get(i).velocity.y * interval;
			}
		}
	}

	public void collide() {
		// loop for each body
		for (int i = 0; i < bodies.size(); i++) {
			// loop for each body that could be colliding
			for (int j = 0; j < i; j++) {
				// calculate distance
				double distance;
				distance = Vec2d.dst(bodies.get(i).position, bodies.get(j).position);
				// check for collisions
				if (bodies.get(i).radius + bodies.get(j).radius >= distance) {

					// System.out.println("Hit!");
				}
			}
		}
	}
	private double calcDistance(double[] directionalDistance) {
		double distance = 0;
		for (int i = 0; i < DEGREES_OF_FREEDOM; i++) {
			distance += Math.pow(directionalDistance[i], 2);
		}
		return Math.sqrt(distance); // aPow+bPow=cPow
	}

	private double[] dirDistance(Body body1, Body body2) {
		double[] directionalDistance = new double[DEGREES_OF_FREEDOM];
		// subtract to find relative distance
		directionalDistance[0] = body1.position.x - body2.position.x;
		directionalDistance[1] = body1.position.y - body2.position.y;
		return directionalDistance;
	}
}
