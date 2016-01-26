package com.betato;

import java.util.ArrayList;
import java.util.Collections;

public class SimulatorUtils {

	/**
	 * Sorts the specified body list by x position.
	 * 
	 * <pre>
	 * public static ArrayList<Body> sortBodies(ArrayList<Body> bodies)
	 * </pre>
	 *
	 * @param bodies
	 *            - the ArrayList to sort
	 * 
	 * @return - the sorted body list
	 */
	public static ArrayList<Body> sortBodies(ArrayList<Body> bodies) {
		// Loop for array length
		for (int i = 0; i < bodies.size(); i++) {
			// Move each element
			for (int j = 1; j < (bodies.size() - i); j++) {
				if (bodies.get(j - 1).position.x > bodies.get(j).position.x) {
					// Swap elements
					// No need for a temporary variable, Collections.swap takes
					// care of that
					Collections.swap(bodies, j - 1, j);
				}
				// Otherwise, don't swap
			}
		}
		return bodies;
	}

	/**
	 * Finds a body overlapping the specified point.
	 * 
	 * <pre>
	 * public static int searchBodies(ArrayList<Body> bodies)
	 * </pre>
	 *
	 * @param bodies
	 *            - the ArrayList to search
	 * @param pos
	 *            - the point to search for intersecting bodies
	 * 
	 * @return - the index of the overlapping body
	 */
	public static int searchBodies(ArrayList<Body> bodies, Vec2d pos) {
		int low = 0;
		int high = bodies.size() - 1;
		int mid;

		while (high >= low) {
			mid = (low + high) / 2;
			if (bodyAligned(bodies.get(mid), pos)) {
				// Point overlaps body on x axis, check y and x
				if (bodyIntersecting(bodies.get(mid), pos)) {
					// Body found
					return mid;
				}
				// Otherwise, keep searching
			} else if (bodies.get(mid).position.x < pos.x) {
				// Too low
				low = mid + 1;
			} else if (bodies.get(mid).position.x > pos.x) {
				// Too high
				high = mid - 1;
			}
		}
		// Nothing found
		return -1;
	}

	// Checks for overlap in the x axis
	private static boolean bodyAligned(Body body, Vec2d pos) {
		// Check if body is within bounds
		return (pos.x > body.position.x - body.radius &&
				pos.x < body.position.x + body.radius);
	}

	// Checks for overlap in both axis
	private static boolean bodyIntersecting(Body body, Vec2d pos) {
		return Math.pow(body.position.x - pos.x, 2) + Math.pow(body.position.y - pos.y, 2) < Math.pow(body.radius, 2);
	}
}
