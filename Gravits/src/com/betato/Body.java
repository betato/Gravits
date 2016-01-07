package com.betato;

public class Body {
	public double mass;
    public double radius;
    public double[] position;
    public double[] acceleration;
    public double[] velocity;

    public Body(double mass, double radius, double[] position, double[] acceleration, double[] velocity)
    {
        this.mass = mass;
        this.radius = radius;
        this.position = position;
        this.acceleration = acceleration;
        this.velocity = velocity;
    }
}
