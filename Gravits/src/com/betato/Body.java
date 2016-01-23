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

	// Creates a new body with all available parameters
	public Body(double mass, double radius, Vec2d position, Vec2d acceleration,
			Vec2d velocity) {
		this.mass = mass;
		this.radius = radius;
		this.position = position;
		this.acceleration = acceleration;
		this.velocity = velocity;
	}

	// Creates a new body with all necessary parameters and velocity
	public Body(double mass, double radius, Vec2d position, Vec2d velocity) {
		this.mass = mass;
		this.radius = radius;
		this.position = position;
		this.velocity = velocity;
	}
	
	// Creates a new body with only necessary parameters
	public Body(double mass, double radius, Vec2d position) {
		this.mass = mass;
		this.radius = radius;
		this.position = position;
	}

	public boolean isValid() {
		return (mass > 0 && radius > 0 && position != null
				&& acceleration != null && position != null && velocity != null);
	}
}
