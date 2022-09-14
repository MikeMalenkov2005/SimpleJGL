package com.mike.simplejgl.vectors;

public class Vector4f {
    public float x, y, z, w;

    public Vector4f(float x, float y, float z, float w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }

    public Vector4f(Vector4f source) {
        this(source.x, source.y, source.z, source.w);
    }

    public Vector4f(float d) {
        this(d, d, d, d);
    }

    public float[] get() {
        return new float[] {x, y, z, w};
    }

    public Vector4f add(Vector4f other) {
        x += other.x;
        y += other.y;
        z += other.z;
        w += other.w;
        return this;
    }

    public Vector4f sub(Vector4f other) {
        x -= other.x;
        y -= other.y;
        z -= other.z;
        w -= other.w;
        return this;
    }

    public Vector4f mul(Vector4f other) {
        x *= other.x;
        y *= other.y;
        z *= other.z;
        w *= other.w;
        return this;
    }

    public Vector4f div(Vector4f other) {
        x /= other.x;
        y /= other.y;
        z /= other.z;
        w /= other.w;
        return this;
    }

    public Vector4f mul(float n) {
        x *= n;
        y *= n;
        z *= n;
        w *= n;
        return this;
    }

    public Vector4f div(float n) {
        x /= n;
        y /= n;
        z /= n;
        w /= n;
        return this;
    }

    public float dot(Vector4f other) {
        return x * other.x + y * other.y + z * other.z + w * other.w;
    }

    public float lengthSquared() {
        return x * x + y * y + z * z + w * w;
    }

    public double length() {
        return Math.sqrt(lengthSquared());
    }

    public Vector4f normalize() {
        double l = length();
        x = (float) (x / l);
        y = (float) (y / l);
        z = (float) (z / l);
        w = (float) (w / l);
        return this;
    }

    @Override
    public String toString() {
        return "Vector2f(" + x + ", " + y + ", " + z + ", " + w + ')';
    }
}
