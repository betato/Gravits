package com.betato;

/**
 * The {@code Vec2d} class stores a two-dimensional vector as two doubles and
 * provides methods for manipulating them.
 */
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
	}

	/**
	 * New Vec2d initialized to specified doubles
	 */
	public Vec2d(double x, double y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * New Vec2d initialized to specified Vec2d
	 */
	public Vec2d(Vec2d vec) {
		x = vec.x;
		y = vec.y;
	}

	/**
	 * Sets the vector to zero.
	 * 
	 * <pre>
	 * public Vec2d zero()
	 * </pre>
	 *
	 * @return The vector itself
	 */
	public Vec2d zero() {
		this.x = 0.0d;
		this.y = 0.0d;
		return this;
	}

	/**
	 * Sets the vector to the specified doubles.
	 * 
	 * <pre>
	 * public Vec2d set(double x, double y)
	 * </pre>
	 *
	 * @param x
	 *            - the x value to set the vector to
	 * @param y
	 *            - the y value to set the vector to
	 * 
	 * @return The vector itself
	 */
	public Vec2d set(double x, double y) {
		this.x = x;
		this.y = y;
		return this;
	}

	/**
	 * Sets the vector to the specified {@code Vec2d}.
	 * 
	 * <pre>
	 * public Vec2d set(Vec2d vec)
	 * </pre>
	 *
	 * @param vec
	 *            - the {@code Vec2d} to set the vector to
	 * 
	 * @return The vector itself
	 */
	public Vec2d set(Vec2d vec) {
		x = vec.x;
		y = vec.y;
		return this;
	}

	/**
	 * Adds the specified doubles to the vector.
	 * 
	 * <pre>
	 * public Vec2d add(double x, double y)
	 * </pre>
	 *
	 * @param x
	 *            - the x value to add to the vector
	 * @param y
	 *            - the y value to add to the vector
	 * 
	 * @return The vector itself
	 */
	public Vec2d add(double x, double y) {
		this.x += x;
		this.y += y;
		return this;
	}

	/**
	 * Adds the specified {@code Vec2d} to the vector.
	 * 
	 * <pre>
	 * public Vec2d set(Vec2d vec)
	 * </pre>
	 *
	 * @param vec
	 *            - the {@code Vec2d} to add to the vector
	 * 
	 * @return The vector itself
	 */
	public Vec2d add(Vec2d vec) {
		x += vec.x;
		y += vec.y;
		return this;
	}

	/**
	 * Subtracts the specified doubles from the vector.
	 * 
	 * <pre>
	 * public Vec2d sub(double x, double y)
	 * </pre>
	 *
	 * @param x
	 *            - the x value to subtract from the vector
	 * @param y
	 *            - the y value to subtract from the vector
	 * 
	 * @return The vector itself
	 */
	public Vec2d sub(double x, double y) {
		this.x -= x;
		this.y -= y;
		return this;
	}

	/**
	 * Subtracts the specified {@code Vec2d} from the vector.
	 * 
	 * <pre>
	 * public Vec2d sub(Vec2d vec)
	 * </pre>
	 *
	 * @param vec
	 *            - the {@code Vec2d} to subtract from the vector
	 * 
	 * @return The vector itself
	 */
	public Vec2d sub(Vec2d vec) {
		x -= vec.x;
		y -= vec.y;
		return this;
	}

	/**
	 * Multiplies vector by the a scalar.
	 * 
	 * <pre>
	 * public Vec2d mul(double scalar)
	 * </pre>
	 *
	 * @param scalar
	 *            - the scalar value to multiply the vector by
	 * 
	 * @return The vector itself
	 */
	public Vec2d mul(double scalar) {
		this.x *= scalar;
		this.y *= scalar;
		return this;
	}

	/**
	 * Multiplies the vector by a {@code Vec2d}.
	 * 
	 * <pre>
	 * public Vec2d mul(Vec2d vec)
	 * </pre>
	 *
	 * @param vec
	 *            - the {@code Vec2d} to multiply the vector by
	 * 
	 * @return The vector itself
	 */
	public Vec2d mul(Vec2d vec) {
		this.x *= vec.x;
		this.y *= vec.y;
		return this;
	}

	/**
	 * Divides the vector by a scalar.
	 * 
	 * <pre>
	 * public Vec2d div(double scalar)
	 * </pre>
	 *
	 * @param scalar
	 *            - the scalar value to divide the vector by
	 * 
	 * @return The vector itself
	 */
	public Vec2d div(double scalar) {
		this.x /= scalar;
		this.y /= scalar;
		return this;
	}

	/**
	 * Divides the vector by a {@code Vec2d}.
	 * 
	 * <pre>
	 * public Vec2d div(Vec2d vec)
	 * </pre>
	 *
	 * @param vec
	 *            - the {@code Vec2d} to divide the vector by
	 * 
	 * @return The vector itself
	 */
	public Vec2d div(Vec2d vec) {
		this.x /= vec.x;
		this.y /= vec.y;
		return this;
	}

	/**
	 * Calculates the dot product of the vector.
	 * 
	 * <pre>
	 * public Vec2d dot()
	 * </pre>
	 *
	 * @param x
	 *            - the x of the vector to calculate the dot product against
	 * @param y
	 *            - the y of the vector to calculate the dot product against
	 * 
	 * @return The vector itself
	 */
	public double dot(double x, double y) {
		return this.x * x + this.y * y;
	}

	/**
	 * Calculates the dot product of the vector.
	 * 
	 * <pre>
	 * public Vec2d dot(Vec2d vec)
	 * </pre>
	 *
	 * @param vec
	 *            - the {@code Vec2d} to calculate the dot product against
	 * 
	 * @return The vector itself
	 */
	public double dot(Vec2d vec) {
		return x * vec.x + y * vec.y;
	}

	/**
	 * Calculates the determinant of the vector.
	 * 
	 * <pre>
	 * public Vec2d dot()
	 * </pre>
	 *
	 * @param x
	 *            - the x value of the the vector to calculate the determinant
	 *            against
	 * @param y
	 *            - the y value of the the vector to calculate the determinant
	 *            against
	 * 
	 * @return The vector itself
	 */
	public double det(double x, double y) {
		return this.x * y + this.y * x;
	}

	/**
	 * Calculates the determinant of the vector.
	 * 
	 * <pre>
	 * public Vec2d div(Vec2d vec)
	 * </pre>
	 *
	 * @param vec
	 *            - the {@code Vec2d} to calculate the determinant against
	 * 
	 * @return The vector itself
	 */
	public double det(Vec2d vec) {
		return x * vec.y + y * vec.x;
	}

	/**
	 * Calculates the angle of the vector.
	 * 
	 * <pre>
	 * public Vec2d ang()
	 * </pre>
	 * 
	 * @return The angle of the vector
	 */
	public double ang(Vec2d vec) {
		return Math.atan2(det(vec), dot(vec));
	}

	/**
	 * Calculates the length of the vector.
	 * 
	 * <pre>
	 * public Vec2d len()
	 * </pre>
	 * 
	 * @return The length of the vector
	 */
	public double len() {
		return Math.sqrt((x * x) + (y * y));
	}

	/**
	 * Calculates the squared length of the vector.
	 * 
	 * <pre>
	 * public Vec2d lenSqr()
	 * </pre>
	 * 
	 * @return The square length of the vector
	 */
	public double lenSqr() {
		return (x * x) + (y * y);
	}

	/**
	 * Calculates the distance between the vector and another {@code Vec2d}.
	 * 
	 * <pre>
	 * public Vec2d dst(Vec2d vec)
	 * </pre>
	 *
	 * @param vec
	 *            - the {@code Vec2d} to calculate the distance between
	 * 
	 * @return The distance between the vectors;
	 */
	public double dst(Vec2d vec) {
		double dstx = x - vec.x;
		double dsty = y - vec.y;
		return Math.sqrt((dstx * dstx) + (dsty * dsty));
	}

	/**
	 * Calculates the squared distance between the vector and another
	 * {@code Vec2d}.
	 * 
	 * <pre>
	 * public Vec2d dstSqr(Vec2d vec)
	 * </pre>
	 * 
	 * @param vec
	 *            - the {@code Vec2d} to calculate the distance between
	 * 
	 * @return The square distance between the vectors;
	 */
	public double dstSqr(Vec2d vec) {
		double dstx = x - vec.x;
		double dsty = y - vec.y;
		return (dstx * dstx) + (dsty * dsty);
	}

	/**
	 * Sets the vector to it's perpendicular counterpart.
	 * 
	 * <pre>
	 * public Vec2d perp()
	 * </pre>
	 * 
	 * @return The vector itself
	 */
	public Vec2d perp() {
		this.x = y;
		this.y = -x;
		return this;
	}

	/**
	 * Negates the vector.
	 * 
	 * <pre>
	 * public Vec2d neg()
	 * </pre>
	 * 
	 * @return The vector itself
	 */
	public Vec2d neg() {
		x = -x;
		y = -y;
		return this;
	}

	/**
	 * Normalizes the vector.
	 * 
	 * <pre>
	 * public Vec2d norm()
	 * </pre>
	 * 
	 * @return The vector itself
	 */
	public Vec2d norm() {
		double len = Math.sqrt(x * x + y * y);
		x /= len;
		y /= len;
		return this;
	}

	/**
	 * Adds two vectors.
	 * 
	 * <pre>
	 * public Vec2d add(Vec2d vec1, Vec2d vec2)
	 * </pre>
	 *
	 * @param vec1
	 *            - the first {@code Vec2d} to add
	 * @param vec2
	 *            - the second {@code Vec2d} to add
	 * 
	 * @return The sum of the vectors
	 */
	public static Vec2d add(Vec2d vec1, Vec2d vec2) {
		return new Vec2d(vec1.x + vec2.x, vec1.y + vec2.y);
	}

	/**
	 * Subtracts two vectors.
	 * 
	 * <pre>
	 * public Vec2d sub(Vec2d vec1, Vec2d vec2)
	 * </pre>
	 *
	 * @param vec1
	 *            - the minuend {@code Vec2d}
	 * @param vec2
	 *            - the subtrahend {@code Vec2d}
	 * 
	 * @return The difference of the vectors
	 */
	public static Vec2d sub(Vec2d vec1, Vec2d vec2) {
		return new Vec2d(vec1.x - vec2.x, vec1.y - vec2.y);
	}

	/**
	 * Multiplies two vectors.
	 * 
	 * <pre>
	 * public Vec2d mul(Vec2d vec1, Vec2d vec2)
	 * </pre>
	 *
	 * @param vec1
	 *            - the multiplicand {@code Vec2d}
	 * @param vec2
	 *            - the multiplier {@code Vec2d}
	 * 
	 * @return The product of the vectors
	 */
	public static Vec2d mul(Vec2d vec1, Vec2d vec2) {
		return new Vec2d(vec1.x * vec2.x, vec1.y * vec2.y);
	}

	/**
	 * Divides two vectors.
	 * 
	 * <pre>
	 * public Vec2d div(Vec2d vec1, Vec2d vec2)
	 * </pre>
	 *
	 * @param vec1
	 *            - the divined {@code Vec2d}
	 * @param vec2
	 *            - the divisor {@code Vec2d}
	 * 
	 * @return The quotient of the vectors
	 */
	public static Vec2d div(Vec2d vec1, Vec2d vec2) {
		return new Vec2d(vec1.x / vec2.x, vec1.y / vec2.y);
	}

	/**
	 * Calculates the dot product of two vectors.
	 * 
	 * <pre>
	 * public Vec2d dot(Vec2d vec1, Vec2d vec2)
	 * </pre>
	 *
	 * @param vec1
	 *            - the first {@code Vec2d} to calculate the dot product
	 * @param vec2
	 *            - the second {@code Vec2d} to calculate the dot product
	 * 
	 * @return The dot product of the vectors
	 */
	public static double dot(Vec2d vec1, Vec2d vec2) {
		return vec1.x * vec2.x + vec1.y * vec2.y;
	}

	/**
	 * Calculates the determinant of two vectors.
	 * 
	 * <pre>
	 * public Vec2d det(Vec2d vec1, Vec2d vec2)
	 * </pre>
	 *
	 * @param vec1
	 *            - the first {@code Vec2d} to calculate the determinant
	 * @param vec2
	 *            - the second {@code Vec2d} to calculate the determinant
	 * 
	 * @return The determinant of the vectors
	 */
	public static double det(Vec2d vec1, Vec2d vec2) {
		return vec1.x * vec2.y + vec1.y * vec2.x;
	}

	/**
	 * Calculates the distance between two vectors.
	 * 
	 * <pre>
	 * public Vec2d dst(Vec2d vec)
	 * </pre>
	 * 
	 * @param vec1
	 *            - the first {@code Vec2d} to calculate the distance between
	 * @param vec2
	 *            - the second {@code Vec2d} to calculate the distance between
	 * 
	 * @return The distance between the vectors;
	 */
	public static double dst(Vec2d vec1, Vec2d vec2) {
		double dstx = vec1.x - vec2.x;
		double dsty = vec1.y - vec2.y;
		return Math.sqrt((dstx * dstx) + (dsty * dsty));
	}

	/**
	 * Calculates the squared distance two vectors.
	 * 
	 * <pre>
	 * public Vec2d dstSqr(Vec2d vec)
	 * </pre>
	 * 
	 * @param vec1
	 *            - the first {@code Vec2d} to calculate the distance between
	 * @param vec2
	 *            - the second {@code Vec2d} to calculate the distance between
	 * 
	 * @return The square distance between the vectors;
	 */
	public static double dstSqr(Vec2d vec1, Vec2d vec2) {
		double dstx = vec1.x - vec2.x;
		double dsty = vec1.y - vec2.y;
		return (dstx * dstx) + (dsty * dsty);
	}
}
