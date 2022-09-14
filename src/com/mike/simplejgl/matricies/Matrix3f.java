package com.mike.simplejgl.matricies;

import com.mike.simplejgl.vectors.Vector3f;

public class Matrix3f {
    public float m00, m01, m02, m10, m11, m12, m20, m21, m22;

    public Matrix3f(float m00, float m01, float m02,
                    float m10, float m11, float m12,
                    float m20, float m21, float m22) {
        this.m00 = m00;
        this.m01 = m01;
        this.m02 = m02;
        this.m10 = m10;
        this.m11 = m11;
        this.m12 = m12;
        this.m20 = m20;
        this.m21 = m21;
        this.m22 = m22;
    }

    public Matrix3f(Matrix3f m) {
        this(m.m00, m.m01, m.m02, m.m10, m.m11, m.m12, m.m20, m.m21, m.m22);
    }

    public Matrix3f(float[] m) {
        this(m[0], m[1], m[2], m[3], m[4], m[5], m[6], m[7], m[8]);
    }

    public Matrix3f() {
        this(1, 0, 0, 0, 1, 0, 0, 0, 1);
    }

    public float[] get() {
        return new float[] {m00, m01, m02, m10, m11, m12, m20, m21, m22};
    }

    public Matrix3f add(Matrix3f m) {
        m00 += m.m00;
        m01 += m.m01;
        m02 += m.m02;
        m10 += m.m10;
        m11 += m.m11;
        m12 += m.m12;
        m20 += m.m20;
        m21 += m.m21;
        m22 += m.m22;
        return this;
    }

    public Matrix3f sub(Matrix3f m) {
        m00 -= m.m00;
        m01 -= m.m01;
        m02 -= m.m02;
        m10 -= m.m10;
        m11 -= m.m11;
        m12 -= m.m12;
        m20 -= m.m20;
        m21 -= m.m21;
        m22 -= m.m22;
        return this;
    }

    public Matrix3f mul(Matrix3f m) {
        float r00 = m00 * m.m00 + m01 * m.m10 + m02 * m.m20;
        float r01 = m00 * m.m01 + m01 * m.m11 + m02 * m.m21;
        float r02 = m00 * m.m02 + m01 * m.m12 + m02 * m.m22;
        float r10 = m10 * m.m00 + m11 * m.m10 + m12 * m.m20;
        float r11 = m10 * m.m01 + m11 * m.m11 + m12 * m.m21;
        float r12 = m10 * m.m02 + m11 * m.m12 + m12 * m.m22;
        float r20 = m20 * m.m00 + m21 * m.m10 + m22 * m.m20;
        float r21 = m20 * m.m01 + m21 * m.m11 + m22 * m.m21;
        float r22 = m20 * m.m02 + m21 * m.m12 + m22 * m.m22;
        m00 = r00;
        m01 = r01;
        m02 = r02;
        m10 = r10;
        m11 = r11;
        m12 = r12;
        m20 = r20;
        m21 = r21;
        m22 = r22;
        return this;
    }

    public Matrix3f div(Matrix3f other) {
        return mul(new Matrix3f(other).invert());
    }

    public Matrix3f mul(float n) {
        m00 *= n;
        m01 *= n;
        m02 *= n;
        m10 *= n;
        m11 *= n;
        m12 *= n;
        m20 *= n;
        m21 *= n;
        m22 *= n;
        return this;
    }

    public Matrix3f div(float n) {
        m00 /= n;
        m01 /= n;
        m02 /= n;
        m10 /= n;
        m11 /= n;
        m12 /= n;
        m20 /= n;
        m21 /= n;
        m22 /= n;
        return this;
    }

    public Vector3f mul(Vector3f v) {
        return new Vector3f(m00 * v.x + m01 * v.y + m02 * v.z, m10 * v.x + m11 * v.y + m12 * v.z, m20 * v.x + m21 * v.y + m22 * v.z);
    }

    public Matrix3f transpose() {
        float tmp = m01;
        m01 = m10;
        m10 = tmp;
        tmp = m02;
        m02 = m20;
        m20 = tmp;
        tmp = m12;
        m12 = m21;
        m21 = tmp;
        return this;
    }

    public Matrix3f invert() {
        float det = m00 * m11 * m22 + m01 * m12 * m20 - m01 * m10 * m22 - m02 * m11 * m20;
        float r00 = (m11 * m22 - m12 * m21) / det;
        float r01 = -(m01 * m22 - m02 * m21) / det;
        float r02 = (m01 * m12 - m02 * m11) / det;
        float r10 = -(m10 * m22 - m12 * m20) / det;
        float r11 = (m00 * m22 - m02 * m20) / det;
        float r12 = -(m00 * m12 - m02 * m10) / det;
        float r20 = (m10 * m21 - m11 * m20) / det;
        float r21 = -(m00 * m21 - m01 * m20) / det;
        float r22 = (m00 * m11 - m01 * m10) / det;
        m00 = r00;
        m01 = r01;
        m02 = r02;
        m10 = r10;
        m11 = r11;
        m12 = r12;
        m20 = r20;
        m21 = r21;
        m22 = r22;
        return this;
    }

    public Matrix3f scale(float scale) {
        return mul(scale);
    }

    public Matrix3f rotateX(float angle) {
        float sin = (float) Math.sin(angle);
        float cos = (float) Math.cos(angle);
        float r10 = cos * m10 - sin * m20;
        float r11 = cos * m11 - sin * m21;
        float r12 = cos * m12 - sin * m22;
        float r20 = sin * m10 + cos * m20;
        float r21 = sin * m11 + cos * m21;
        float r22 = sin * m12 + cos * m22;
        m10 = r10;
        m11 = r11;
        m12 = r12;
        m20 = r20;
        m21 = r21;
        m22 = r22;
        return this;
    }

    public Matrix3f rotateY(float angle) {
        float sin = (float) Math.sin(angle);
        float cos = (float) Math.cos(angle);
        float r00 = cos * m00 - sin * m20;
        float r01 = cos * m01 - sin * m21;
        float r02 = cos * m02 - sin * m22;
        float r20 = sin * m00 + cos * m20;
        float r21 = sin * m01 + cos * m21;
        float r22 = sin * m02 + cos * m22;
        m00 = r00;
        m01 = r01;
        m02 = r02;
        m20 = r20;
        m21 = r21;
        m22 = r22;
        return this;
    }

    public Matrix3f rotateZ(float angle) {
        float sin = (float) Math.sin(angle);
        float cos = (float) Math.cos(angle);
        float r00 = cos * m00 - sin * m10;
        float r01 = cos * m01 - sin * m11;
        float r02 = cos * m02 - sin * m12;
        float r10 = sin * m00 + cos * m10;
        float r11 = sin * m01 + cos * m11;
        float r12 = sin * m02 + cos * m12;
        m00 = r00;
        m01 = r01;
        m02 = r02;
        m10 = r10;
        m11 = r11;
        m12 = r12;
        return this;
    }
}
