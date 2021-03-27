package com.ac101m.seedgrind.util;

public class Vec3 {
    public int x = 0;
    public int y = 0;
    public int z = 0;

    public Vec3() {}
    
    public Vec3(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public String toString() {
        return "(" + this.x + ", " + this.y + ", " + this.z + ")";
    }
}
