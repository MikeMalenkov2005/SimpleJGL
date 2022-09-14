package com.mike.simplejgl.vectors;

public class Vector2i {
    public int x, y;

    public Vector2i(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Vector2i(Vector2i source) {
        this(source.x, source.y);
    }

    public Vector2i(int d) {
        this(d, d);
    }

    public int[] get() {
        return new int[] {x, y};
    }

    public Vector2i add(Vector2i other) {
        x += other.x;
        y += other.y;
        return this;
    }

    public Vector2i sub(Vector2i other) {
        x -= other.x;
        y -= other.y;
        return this;
    }

    public Vector2i mul(Vector2i other) {
        x *= other.x;
        y *= other.y;
        return this;
    }

    public Vector2i div(Vector2i other) {
        x /= other.x;
        y /= other.y;
        return this;
    }

    public Vector2i mul(int n) {
        x *= n;
        y *= n;
        return this;
    }

    public Vector2i div(int n) {
        x /= n;
        y /= n;
        return this;
    }

    public int dot(Vector2i other) {
        return x * other.x + y * other.y;
    }

    public int lengthSquared() {
        return x * x + y * y;
    }

    public double length() {
        return Math.sqrt(lengthSquared());
    }

    public Vector2i normalize() {
        double l = length();
        x = (int) (x / l);
        y = (int) (x / l);
        return this;
    }

    @Override
    public String toString() {
        return "Vector2f(" + x + ", " + y + ')';
    }
}
