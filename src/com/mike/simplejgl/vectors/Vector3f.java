package com.mike.simplejgl.vectors;

public class Vector3f {
    public float x, y, z;

    public Vector3f(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vector3f(Vector3f source) {
        this(source.x, source.y, source.z);
    }

    public Vector3f(float d) {
        this(d, d, d);
    }

    public float[] get() {
        return new float[] {x, y, z};
    }

    public Vector3f add(Vector3f other) {
        x += other.x;
        y += other.y;
        z += other.z;
        return this;
    }

    public Vector3f sub(Vector3f other) {
        x -= other.x;
        y -= other.y;
        z -= other.z;
        return this;
    }

    public Vector3f mul(Vector3f other) {
        x *= other.x;
        y *= other.y;
        z *= other.z;
        return this;
    }

    public Vector3f div(Vector3f other) {
        x /= other.x;
        y /= other.y;
        z /= other.z;
        return this;
    }

    public Vector3f mul(float n) {
        x *= n;
        y *= n;
        z *= n;
        return this;
    }

    public Vector3f div(float n) {
        x /= n;
        y /= n;
        z /= n;
        return this;
    }

    public float dot(Vector3f other) {
        return x * other.x + y * other.y + z * other.z;
    }

    public Vector3f cross(Vector3f other) {
        return new Vector3f(y * other.z - z * other.y, z * other.x - x * other.z, x * other.y - y * other.x);
    }

    public float lengthSquared() {
        return x * x + y * y + z * z;
    }

    public double length() {
        return Math.sqrt(lengthSquared());
    }

    public Vector3f normalize() {
        double l = length();
        x = (float) (x / l);
        y = (float) (y / l);
        z = (float) (z / l);
        return this;
    }

    @Override
    public String toString() {
        return "Vector2f(" + x + ", " + y + ", " + z + ')';
    }
}
