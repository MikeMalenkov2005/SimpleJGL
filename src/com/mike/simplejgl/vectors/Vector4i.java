package com.mike.simplejgl.vectors;

public class Vector4i {
    public int x, y, z, w;

    public Vector4i(int x, int y, int z, int w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }

    public Vector4i(Vector4i source) {
        this(source.x, source.y, source.z, source.w);
    }

    public Vector4i(int d) {
        this(d, d, d, d);
    }

    public int[] get() {
        return new int[] {x, y, z, w};
    }

    public Vector4i add(Vector4i other) {
        x += other.x;
        y += other.y;
        z += other.z;
        w += other.w;
        return this;
    }

    public Vector4i sub(Vector4i other) {
        x -= other.x;
        y -= other.y;
        z -= other.z;
        w -= other.w;
        return this;
    }

    public Vector4i mul(Vector4i other) {
        x *= other.x;
        y *= other.y;
        z *= other.z;
        w *= other.w;
        return this;
    }

    public Vector4i div(Vector4i other) {
        x /= other.x;
        y /= other.y;
        z /= other.z;
        w /= other.w;
        return this;
    }

    public Vector4i mul(int n) {
        x *= n;
        y *= n;
        z *= n;
        w *= n;
        return this;
    }

    public Vector4i div(int n) {
        x /= n;
        y /= n;
        z /= n;
        w /= n;
        return this;
    }

    public int dot(Vector4i other) {
        return x * other.x + y * other.y + z * other.z + w * other.w;
    }

    public int lengthSquared() {
        return x * x + y * y + z * z + w * w;
    }

    public double length() {
        return Math.sqrt(lengthSquared());
    }

    public Vector4i normalize() {
        double l = length();
        x = (int) (x / l);
        y = (int) (y / l);
        z = (int) (z / l);
        w = (int) (w / l);
        return this;
    }

    @Override
    public String toString() {
        return "Vector2f(" + x + ", " + y + ", " + z + ", " + w + ')';
    }
}
