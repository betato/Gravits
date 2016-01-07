package com.betato;

import java.util.ArrayList;


public class Simulator {
	public ArrayList<Body> bodies = new ArrayList<Body>();
    public double GRAV_CONST = 6.67384E-11;
    public int DEGREES_OF_FREEDOM = 2;
    public int interval = 50;

    public Simulator(double GRAV_CONST, int DEGREES_OF_FREEDOM, int TIMESTEP) {
        this.GRAV_CONST = GRAV_CONST;
        this.DEGREES_OF_FREEDOM = DEGREES_OF_FREEDOM;
        this.interval = TIMESTEP;
    }

    public void step() {
        accelerate();
        move();
        collide();
    }

    public void accelerate() {
        //loop for each body
        for (int i = 0; i < bodies.size(); i++) {
            //loop for each body acting upon the body (except for bodies that will be calculated in the main loop)
            for (int j = 0; j < i; j++) {
                //find distance
            	double distance;
                double[] directionalDistance = dirDistance(bodies.get(i), bodies.get(j));
                distance = calcDistance(directionalDistance);

                //calculate gravitational force on body (F = G m1*m2 / r2)
                double force = GRAV_CONST * bodies.get(i).mass * bodies.get(j).mass / Math.pow(distance, 2);

                //add acceleration (a = F / m)
                for (int k = 0; k < DEGREES_OF_FREEDOM; k++) {
                    //subtract
                    bodies.get(i).acceleration[k] -= (directionalDistance[k] / distance) * (force / bodies.get(i).mass);
                    //add
                    bodies.get(j).acceleration[k] += (directionalDistance[k] / distance) * (force / bodies.get(i).mass);
                }
            }
        }
    }

    public void move()  {
        //loop for each body
        for (int i = 0; i < bodies.size(); i++) {
            //loop for each axis
            for (int j = 0; j < DEGREES_OF_FREEDOM; j++) {
                //calculate velocity (v = a * t)
                bodies.get(i).velocity[j] = bodies.get(i).acceleration[j] * interval;
                //calculate position (metres = metres/second * seconds)
                bodies.get(i).position[j] += bodies.get(i).velocity[j] * interval;
            }
        }
    }

    public void collide() {
        //loop for each body
        for (int i = 0; i < bodies.size(); i++) {
            //loop for each body that could be colliding
            for (int j = 0; j < i; j++) {
                //calculate distance
                double distance;
                double[] directionalDistance = dirDistance(bodies.get(i), bodies.get(j));
                distance = calcDistance(directionalDistance);

                //check for collisions
                if (bodies.get(i).radius + bodies.get(j).radius >= distance){
                	
                    //System.out.println("Hit!");
                }
            }
        }
    }

    private double calcDistance(double[] directionalDistance){
        double distance = 0;
        for (int i = 0; i < DEGREES_OF_FREEDOM; i++) {
            distance += Math.pow(directionalDistance[i], 2);
        }
        return Math.sqrt(distance); //aPow+bPow=cPow
    }
    
    private double[] dirDistance(Body body1, Body body2) {
    	double[] directionalDistance = new double[DEGREES_OF_FREEDOM];
        //find distance
        for (int k = 0; k < DEGREES_OF_FREEDOM; k++) {
            //subtract to find relative distance
            directionalDistance[k] = body1.position[k] - body2.position[k];
        }
        return directionalDistance;
    }
}
