package com.mike.simplejgl.vectors;

public class Vector3i {
    public int x, y, z;

    public Vector3i(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vector3i(Vector3i source) {
        this(source.x, source.y, source.z);
    }

    public Vector3i(int d) {
        this(d, d, d);
    }

    public int[] get() {
        return new int[] {x, y, z};
    }

    public Vector3i add(Vector3i other) {
        x += other.x;
        y += other.y;
        z += other.z;
        return this;
    }

    public Vector3i sub(Vector3i other) {
        x -= other.x;
        y -= other.y;
        z -= other.z;
        return this;
    }

    public Vector3i mul(Vector3i other) {
        x *= other.x;
        y *= other.y;
        z *= other.z;
        return this;
    }

    public Vector3i div(Vector3i other) {
        x /= other.x;
        y /= other.y;
        z /= other.z;
        return this;
    }

    public Vector3i mul(int n) {
        x *= n;
        y *= n;
        z *= n;
        return this;
    }

    public Vector3i div(int n) {
        x /= n;
        y /= n;
        z /= n;
        return this;
    }

    public int dot(Vector3i other) {
        return x * other.x + y * other.y + z * other.z;
    }

    public Vector3i cross(Vector3i other) {
        return new Vector3i(y * other.z - z * other.y, z * other.x - x * other.z, x * other.y - y * other.x);
    }

    public int lengthSquared() {
        return x * x + y * y + z * z;
    }

    public double length() {
        return Math.sqrt(lengthSquared());
    }

    public Vector3i normalize() {
        double l = length();
        x = (int) (x / l);
        y = (int) (y / l);
        z = (int) (z / l);
        return this;
    }

    @Override
    public String toString() {
        return "Vector2f(" + x + ", " + y + ", " + z + ')';
    }
}
