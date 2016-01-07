package com.betato;

public class Vec2d {
	
	/**
     * Vector x component
     */
    public double x;
    
    /**
     * Vector y component
     */
    public double y;

    /**
     * New Vec2d initialized to zero
     */
    public Vec2d() {
		this(0.0f, 0.0f);
    }

    /**
     * New Vec2d initialized to specified doubles
     */
    public Vec2d(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * New Vec2d initialized to zero
     */
    public Vec2d(Vec2d vec) {
        x = vec.x;
        y = vec.y;
    }
    
    public Vec2d zero() {
        this.x = 0.0f;
        this.y = 0.0f;
        return this;
    }
	
    public Vec2d set(double x, double y) {
        this.x = x;
        this.y = y;
        return this;
    }

    public Vec2d set(Vec2d vec) {
        x = vec.x;
        y = vec.y;
        return this;
    }	

	public Vec2d add(double x, double y) {
        this.x += x;
        this.y += y;
        return this;
    }
	
    public Vec2d add(Vec2d vec) {
        x += vec.x;
        y += vec.y;
        return this;
    }
	
    public Vec2d sub(double x, double y) {
        this.x -= x;
        this.y -= y;
        return this;
    }
	
    public Vec2d sub(Vec2d vec) {
        x -= vec.x;
        y -= vec.y;
        return this;
    }

	public Vec2d mul(Vec2d vec) {
        this.x *= vec.x;
        this.y *= vec.y;
        return this;
    }
	
    public Vec2d mul(double scalar) {
        this.x *= scalar;
        this.y *= scalar;
        return this;
    }

	public Vec2d div(Vec2d vec) {
        this.x /= vec.x;
        this.y /= vec.y;
        return this;
    }
	
    public Vec2d div(double scalar) {
        this.x /= scalar;
        this.y /= scalar;
        return this;
    }

	public double dot(double x, double y) {
        return this.x * x + this.y * y;
    }
	
    public double dot(Vec2d vec) {
        return x * vec.x + y * vec.y;
    }

	public double det(double x, double y) {
        return this.x * y + this.y * x;
    }
	
    public double det(Vec2d vec) {
        return x * vec.y + y * vec.x;
    }
	
    public double ang(Vec2d vec) {
        return (double) Math.atan2(det(vec), dot(vec));
    }
	
    public Vec2d perp() {
        return set(y, x * -1);
    }

    public double len() {
        return (double) Math.sqrt((x * x) + (y * y));
    }
	
    public Vec2d neg() {
        x = -x;
        y = -y;
        return this;
    }
	
    public Vec2d norm() {
        double len = Math.sqrt(x * x + y * y);
        x /= len;
        y /= len;
        return this;
    }
}
