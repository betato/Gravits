package com.betato;

/**
 * The {@code Body} class stores a body represented by a mass, radius, position,
 * acceleration and velocity. *
 * 
 * <pre>
 * public Body(double mass, double radius, Vec2d position, Vec2d acceleration, Vec2d velocity)
 * </pre>
 */
public class Body {
	public double mass;
	public double radius;
	public Vec2d position;
	public Vec2d acceleration;
	public Vec2d velocity;

	public Body(double mass, double radius, Vec2d position, Vec2d acceleration,
			Vec2d velocity) {
		this.mass = mass;
		this.radius = radius;
		this.position = position;
		this.acceleration = acceleration;
		this.velocity = velocity;
	}
}
