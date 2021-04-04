package com.ac101m.seedgrind.util;

/**
 * Uses x and z rather than z and y
 * A little strange I know, but that's how the game does it
 */
public class Vec2 {
    public int x = 0;
    public int z = 0;

    /**
     * Void constructor, initializes to (0, 0)
     */
    public Vec2() {}

    /**
     * @param x x component
     * @param z z component
     */
    public Vec2(int x, int z) {
        this.x = x;
        this.z = z;
    }

    /**
     * @param box the area to check within
     * @return true if this point within the box or on the edge of the box
     */
    public boolean withinBoxInclusive(Area box) {
        boolean xWithin = this.x >= box.min.x && this.x <= box.max.x;
        boolean zWithin = this.z >= box.min.z && this.z <= box.max.z;
        return xWithin && zWithin;
    }

    /**
     * @param box the area to check within
     * @return true if this point within the box
     */
    public boolean withinBoxExclusive(Area box) {
        boolean xWithin = this.x > box.min.x && this.x < box.max.x;
        boolean zWithin = this.z > box.min.z && this.z < box.max.z;
        return xWithin && zWithin;
    }

    /**
     * @param v1 A Vec2 object
     * @param v2 Another Vec2 object
     * @return True if v1 and v2 are the same
     */
    public static boolean equals(Vec2 v1, Vec2 v2) {
        return v1.x == v2.x && v1.z == v2.z;
    }

    /**
     * Scale a vector by a constant factor.
     * @param scale Factor to scale vector by.
     */
    public void scale(double scale) {
        this.x *= scale;
        this.z *= scale;
    }

    public String toString() {
        return "(" + this.x + ", " + this.z + ")";
    }
}
