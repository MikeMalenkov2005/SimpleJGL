package com.mike.simplejgl.matricies;

import com.mike.simplejgl.vectors.Vector2f;

public class Matrix2f {
    public float m00, m01, m10, m11;

    public Matrix2f(float m00, float m01,
                    float m10, float m11) {
        this.m00 = m00;
        this.m01 = m01;
        this.m10 = m10;
        this.m11 = m11;
    }

    public Matrix2f(Matrix2f m) {
        this(m.m00, m.m01, m.m10, m.m11);
    }

    public Matrix2f(float[] m) {
        this(m[0], m[1], m[2], m[3]);
    }

    public Matrix2f() {
        this(1, 0, 0, 1);
    }

    public float[] get() {
        return new float[] {m00, m01, m10, m11};
    }

    public Matrix2f add(Matrix2f m) {
        m00 += m.m00;
        m01 += m.m01;
        m10 += m.m10;
        m11 += m.m11;
        return this;
    }

    public Matrix2f sub(Matrix2f m) {
        m00 -= m.m00;
        m01 -= m.m01;
        m10 -= m.m10;
        m11 -= m.m11;
        return this;
    }

    public Matrix2f mul(Matrix2f m) {
        float r00 = m00 * m.m00 + m01 * m.m10;
        float r01 = m00 * m.m01 + m01 * m.m11;
        float r10 = m10 * m.m00 + m11 * m.m10;
        float r11 = m10 * m.m01 + m11 * m.m11;
        m00 = r00;
        m01 = r01;
        m10 = r10;
        m11 = r11;
        return this;
    }

    public Matrix2f div(Matrix2f other) {
        return mul(new Matrix2f(other).invert());
    }

    public Matrix2f mul(float n) {
        m00 *= n;
        m01 *= n;
        m10 *= n;
        m11 *= n;
        return this;
    }

    public Matrix2f div(float n) {
        m00 /= n;
        m01 /= n;
        m10 /= n;
        m11 /= n;
        return this;
    }

    public Vector2f mul(Vector2f v) {
        return new Vector2f(m00 * v.x + m01 * v.y, m10 * v.x + m11 * v.y);
    }

    public Matrix2f transpose() {
        float tmp = m01;
        m01 = m10;
        m10 = tmp;
        return this;
    }

    public Matrix2f invert() {
        float det = m00 * m11 - m01 * m10;
        float tmp = m00;
        m00 = m11 / det;
        m11 = tmp / det;
        m01 = -m01 / det;
        m10 = -m10 / det;
        return this;
    }

    public Matrix2f scale(float scale) {
        return mul(scale);
    }

    public Matrix2f rotate(float angle) {
        float sin = (float) Math.sin(angle);
        float cos = (float) Math.cos(angle);
        float r00 = cos * m00 - sin * m10;
        float r01 = cos * m01 - sin * m11;
        float r10 = sin * m00 + cos * m10;
        float r11 = sin * m01 + cos * m11;
        m00 = r00;
        m01 = r01;
        m10 = r10;
        m11 = r11;
        return this;
    }
}
