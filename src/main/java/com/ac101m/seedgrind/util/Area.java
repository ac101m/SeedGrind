package com.ac101m.seedgrind.util;

import java.lang.Math;
import java.util.Optional;

/**
 * Class represents a 2D area in the minecraft world
 */
public class Area {
    public final Vec2 min = new Vec2();     // Bottom left
    public final Vec2 max = new Vec2();     // Top right

    /**
     * Void constructor, initializes to (0, 0), (0, 0)
     */
    public Area() {}

    /**
     * Initialise a 2D area from an existing area
     * @param area the area to make a copy of
     */
    public Area(Area area) {
        this.min.x = area.min.x;
        this.min.z = area.min.z;
        this.max.x = area.max.x;
        this.max.z = area.max.z;
    }

    /**
     * Define a 2D area in terms of two pairs of two integers
     * @param x1 First coordinate, x component
     * @param z1 First coordinate, z component
     * @param x2 Second coordinate, x component
     * @param z2 Second coordinate, z component
     */
    public Area(int x1, int z1, int x2, int z2) {
        this.min.x = Math.min(x1, x2);
        this.min.z = Math.min(z1, z2);
        this.max.x = Math.max(x1, x2);
        this.max.z = Math.max(z1, z2);
    }

    /**
     * Define a 2D area in terms of two 2D vectors
     * @param p1 A 2D vector
     * @param p2 Another 2D vector
     */
    public Area(Vec2 p1, Vec2 p2) {
        this.min.x = Math.min(p1.x, p2.x);
        this.min.z = Math.min(p1.z, p2.z);
        this.max.x = Math.max(p1.x, p2.x);
        this.max.z = Math.max(p1.z, p2.z);
    }

    /**
     * Construct from centre point and radius
     * @param center A 2D vector specifying the center
     * @param radius Integer radius
     */
    public Area(Vec2 center, int radius) {
        this.min.x = center.x - radius;
        this.min.z = center.z - radius;
        this.max.x = center.x + radius;
        this.max.z = center.z + radius;
    }

    /**
     * @return Long containing width of the area (x)
     */
    public long width() {
        return this.max.x - this.min.x;
    }

    /**
     * @return Long containing the height of the area (z)
     */
    public long height() {
        return this.max.z - this.min.z;
    }

    /**
     * @return Long containing the size of this area (width * height)
     */
    public long size() {
        return this.width() * this.height();
    }

    /**
     * Get the center of the tile (to the nearest block)
     * @return Vec2 containing the center of the area
     */
    public Vec2 center() {
        int x = (this.min.x + this.max.x) / 2;
        int z = (this.min.z + this.max.z) / 2;
        return new Vec2(x, z);
    }

    /**
     * @param a1 An area
     * @param a2 Another area
     * @return True if the areas overlap, otherwise false
     */
    public static boolean areOverlapping(Area a1, Area a2) {
        return a1.min.withinBoxExclusive(a2) || a1.max.withinBoxExclusive(a2) || Area.equals(a1, a2);
    }

    /**
     * Compute the overlap between two areas
     * @param a1 An area
     * @param a2 Another area
     * @return Optional overlapping area, present if an overlap exists
     */
    public static Optional<Area> overlap(Area a1, Area a2) {
        if (!areOverlapping(a1, a2)) {
            return Optional.empty();
        }

        Vec2 p1 = new Vec2(Math.max(a1.min.x, a2.min.x), Math.max(a1.min.z, a2.min.z));
        Vec2 p2 = new Vec2(Math.min(a1.max.x, a2.max.x), Math.min(a1.max.z, a2.max.z));

        return Optional.of(new Area(p1, p2));
    }

    /**
     * Equality check for two areas
     * @param a1 An area
     * @param a2 Another area
     * @return True if the two areas are identical, false otherwise
     */
    public static boolean equals(Area a1, Area a2) {
        return Vec2.equals(a1.min, a2.min) && Vec2.equals(a1.max, a2.max);
    }

    /**
     * Expand the area to the nearest power of two aligned size
     * @param pow the power of two to which edges should be expanded
     */
    public void expandPower2(int pow) {
        int offset = 1 << pow;
        int mask = (1 << pow) - 1;

        this.min.x = (this.min.x & mask) == 0 ? this.min.x : (this.min.x & ~mask);
        this.min.z = (this.min.z & mask) == 0 ? this.min.z : (this.min.z & ~mask);
        this.max.x = (this.max.x & mask) == 0 ? this.max.x : (this.max.x & ~mask) + offset;
        this.max.z = (this.max.z & mask) == 0 ? this.max.z : (this.max.z & ~mask) + offset;
    }

    /**
     * @param scale Ratio to scale by
     */
    public void scale(double scale) {
        this.min.scale(scale);
        this.max.scale(scale);
    }

    public String toString() {
        return "[" + this.min.toString() + ", " + this.max.toString() + "]";
    }
}
