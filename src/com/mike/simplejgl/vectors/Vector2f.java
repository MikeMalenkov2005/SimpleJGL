package com.mike.simplejgl.vectors;

public class Vector2f {
    public float x, y;

    public Vector2f(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public Vector2f(Vector2f source) {
        this(source.x, source.y);
    }

    public Vector2f(float d) {
        this(d, d);
    }

    public float[] get() {
        return new float[] {x, y};
    }

    public Vector2f add(Vector2f other) {
        x += other.x;
        y += other.y;
        return this;
    }

    public Vector2f sub(Vector2f other) {
        x -= other.x;
        y -= other.y;
        return this;
    }

    public Vector2f mul(Vector2f other) {
        x *= other.x;
        y *= other.y;
        return this;
    }

    public Vector2f div(Vector2f other) {
        x /= other.x;
        y /= other.y;
        return this;
    }

    public Vector2f mul(float n) {
        x *= n;
        y *= n;
        return this;
    }

    public Vector2f div(float n) {
        x /= n;
        y /= n;
        return this;
    }

    public float dot(Vector2f other) {
        return x * other.x + y * other.y;
    }

    public float lengthSquared() {
        return x * x + y * y;
    }

    public double length() {
        return Math.sqrt(lengthSquared());
    }

    public Vector2f normalize() {
        double l = length();
        x = (float) (x / l);
        y = (float) (x / l);
        return this;
    }

    @Override
    public String toString() {
        return "Vector2f(" + x + ", " + y + ')';
    }
}
