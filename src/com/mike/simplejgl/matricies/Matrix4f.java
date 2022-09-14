package com.mike.simplejgl.matricies;

import com.mike.simplejgl.vectors.Vector4f;

public class Matrix4f {
    public float m00, m01, m02, m03, m10, m11, m12, m13, m20, m21, m22, m23, m30, m31, m32, m33;

    public Matrix4f(float m00, float m01, float m02, float m03,
                    float m10, float m11, float m12, float m13,
                    float m20, float m21, float m22, float m23,
                    float m30, float m31, float m32, float m33) {
        this.m00 = m00;
        this.m01 = m01;
        this.m02 = m02;
        this.m03 = m03;
        this.m10 = m10;
        this.m11 = m11;
        this.m12 = m12;
        this.m13 = m13;
        this.m20 = m20;
        this.m21 = m21;
        this.m22 = m22;
        this.m23 = m23;
        this.m30 = m30;
        this.m31 = m31;
        this.m32 = m32;
        this.m33 = m33;
    }

    public Matrix4f(Matrix4f m) {
        this(m.m00, m.m01, m.m02, m.m03, m.m10, m.m11, m.m12, m.m13, m.m20, m.m21, m.m22, m.m23, m.m30, m.m31, m.m32, m.m33);
    }

    public Matrix4f(float[] m) {
        this(m[0], m[1], m[2], m[3], m[4], m[5], m[6], m[7], m[8], m[9], m[10], m[11], m[12], m[13], m[14], m[15]);
    }

    public Matrix4f() {
        this(1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1);
    }

    public float[] get() {
        return new float[] {m00, m01, m02, m03, m10, m11, m12, m13, m20, m21, m22, m23, m30, m31, m32, m33};
    }

    public Matrix4f add(Matrix4f m) {
        m00 += m.m00;
        m01 += m.m01;
        m02 += m.m02;
        m03 += m.m03;
        m10 += m.m10;
        m11 += m.m11;
        m12 += m.m12;
        m13 += m.m13;
        m20 += m.m20;
        m21 += m.m21;
        m22 += m.m22;
        m23 += m.m23;
        m30 += m.m30;
        m31 += m.m31;
        m32 += m.m32;
        m33 += m.m33;
        return this;
    }

    public Matrix4f sub(Matrix4f m) {
        m00 -= m.m00;
        m01 -= m.m01;
        m02 -= m.m02;
        m03 -= m.m03;
        m10 -= m.m10;
        m11 -= m.m11;
        m12 -= m.m12;
        m13 -= m.m13;
        m20 -= m.m20;
        m21 -= m.m21;
        m22 -= m.m22;
        m23 -= m.m23;
        m30 -= m.m30;
        m31 -= m.m31;
        m32 -= m.m32;
        m33 -= m.m33;
        return this;
    }

    public Matrix4f mul(Matrix4f m) {
        float r00 = m00 * m.m00 + m01 * m.m10 + m02 * m.m20 + m03 * m.m30;
        float r01 = m00 * m.m01 + m01 * m.m11 + m02 * m.m21 + m03 * m.m31;
        float r02 = m00 * m.m02 + m01 * m.m12 + m02 * m.m22 + m03 * m.m32;
        float r03 = m00 * m.m03 + m01 * m.m13 + m02 * m.m23 + m03 * m.m33;
        float r10 = m10 * m.m00 + m11 * m.m10 + m12 * m.m20 + m13 * m.m30;
        float r11 = m10 * m.m01 + m11 * m.m11 + m12 * m.m21 + m13 * m.m31;
        float r12 = m10 * m.m02 + m11 * m.m12 + m12 * m.m22 + m13 * m.m32;
        float r13 = m10 * m.m03 + m11 * m.m13 + m12 * m.m23 + m13 * m.m33;
        float r20 = m20 * m.m00 + m21 * m.m10 + m22 * m.m20 + m23 * m.m30;
        float r21 = m20 * m.m01 + m21 * m.m11 + m22 * m.m21 + m23 * m.m31;
        float r22 = m20 * m.m02 + m21 * m.m12 + m22 * m.m22 + m23 * m.m32;
        float r23 = m20 * m.m03 + m21 * m.m13 + m22 * m.m23 + m23 * m.m33;
        float r30 = m30 * m.m00 + m31 * m.m10 + m32 * m.m20 + m33 * m.m30;
        float r31 = m30 * m.m01 + m31 * m.m11 + m32 * m.m21 + m33 * m.m31;
        float r32 = m30 * m.m02 + m31 * m.m12 + m32 * m.m22 + m33 * m.m32;
        float r33 = m30 * m.m03 + m31 * m.m13 + m32 * m.m23 + m33 * m.m33;
        m00 = r00;
        m01 = r01;
        m02 = r02;
        m03 = r03;
        m10 = r10;
        m11 = r11;
        m12 = r12;
        m13 = r13;
        m20 = r20;
        m21 = r21;
        m22 = r22;
        m23 = r23;
        m30 = r30;
        m31 = r31;
        m32 = r32;
        m33 = r33;
        return this;
    }

    public Matrix4f div(Matrix4f other) {
        return mul(new Matrix4f(other).invert());
    }

    public Matrix4f mul(float n) {
        m00 *= n;
        m01 *= n;
        m02 *= n;
        m03 *= n;
        m10 *= n;
        m11 *= n;
        m12 *= n;
        m13 *= n;
        m20 *= n;
        m21 *= n;
        m22 *= n;
        m23 *= n;
        m30 *= n;
        m31 *= n;
        m32 *= n;
        m33 *= n;
        return this;
    }

    public Matrix4f div(float n) {
        m00 /= n;
        m01 /= n;
        m02 /= n;
        m03 /= n;
        m10 /= n;
        m11 /= n;
        m12 /= n;
        m13 /= n;
        m20 /= n;
        m21 /= n;
        m22 /= n;
        m23 /= n;
        m30 /= n;
        m31 /= n;
        m32 /= n;
        m33 /= n;
        return this;
    }

    public Vector4f mul(Vector4f v) {
        return new Vector4f(
                m00 * v.x + m01 * v.y + m02 * v.z + m03 * v.w,
                m10 * v.x + m11 * v.y + m12 * v.z + m13 * v.w,
                m20 * v.x + m21 * v.y + m22 * v.z + m23 * v.w,
                m30 * v.x + m31 * v.y + m32 * v.z + m33 * v.w
        );
    }

    public Matrix4f transpose() {
        float tmp = m01;
        m01 = m10;
        m10 = tmp;
        tmp = m02;
        m02 = m20;
        m20 = tmp;
        tmp = m03;
        m03 = m30;
        m30 = tmp;
        tmp = m12;
        m12 = m21;
        m21 = tmp;
        tmp = m13;
        m13 = m31;
        m31 = tmp;
        tmp = m23;
        m23 = m32;
        m32 = tmp;
        return this;
    }

    public Matrix4f invert() {
        float det = m00 * m11 * m22 + m01 * m12 * m20 - m01 * m10 * m22 - m02 * m11 * m20;
        float r00 = (m00 * m11 * m22 + m01 * m12 * m20 - m01 * m10 * m22 - m02 * m11 * m20) / det;
        float r01 = -(m00 * m11 * m22 + m01 * m12 * m20 - m01 * m10 * m22 - m02 * m11 * m20) / det;
        float r02 = (m00 * m11 * m22 + m01 * m12 * m20 - m01 * m10 * m22 - m02 * m11 * m20) / det;
        float r03 = -(m00 * m11 * m22 + m01 * m12 * m20 - m01 * m10 * m22 - m02 * m11 * m20) / det;
        float r10 = -(m00 * m11 * m22 + m01 * m12 * m20 - m01 * m10 * m22 - m02 * m11 * m20) / det;
        float r11 = (m00 * m11 * m22 + m01 * m12 * m20 - m01 * m10 * m22 - m02 * m11 * m20) / det;
        float r12 = -(m00 * m11 * m22 + m01 * m12 * m20 - m01 * m10 * m22 - m02 * m11 * m20) / det;
        float r13 = (m00 * m11 * m22 + m01 * m12 * m20 - m01 * m10 * m22 - m02 * m11 * m20) / det;
        float r20 = (m00 * m11 * m22 + m01 * m12 * m20 - m01 * m10 * m22 - m02 * m11 * m20) / det;
        float r21 = -(m00 * m11 * m22 + m01 * m12 * m20 - m01 * m10 * m22 - m02 * m11 * m20) / det;
        float r22 = (m00 * m11 * m22 + m01 * m12 * m20 - m01 * m10 * m22 - m02 * m11 * m20) / det;
        float r23 = -(m00 * m11 * m22 + m01 * m12 * m20 - m01 * m10 * m22 - m02 * m11 * m20) / det;
        float r30 = -(m00 * m11 * m22 + m01 * m12 * m20 - m01 * m10 * m22 - m02 * m11 * m20) / det;
        float r31 = (m00 * m11 * m22 + m01 * m12 * m20 - m01 * m10 * m22 - m02 * m11 * m20) / det;
        float r32 = -(m00 * m11 * m22 + m01 * m12 * m20 - m01 * m10 * m22 - m02 * m11 * m20) / det;
        float r33 = (m00 * m11 * m22 + m01 * m12 * m20 - m01 * m10 * m22 - m02 * m11 * m20) / det;
        m00 = r00;
        m01 = r01;
        m02 = r02;
        m03 = r03;
        m10 = r10;
        m11 = r11;
        m12 = r12;
        m13 = r13;
        m20 = r20;
        m21 = r21;
        m22 = r22;
        m23 = r23;
        m30 = r30;
        m31 = r31;
        m32 = r32;
        m33 = r33;
        return this;
    }

    public Matrix4f scale(float scale) {
        m00 *= scale;
        m01 *= scale;
        m02 *= scale;
        m10 *= scale;
        m11 *= scale;
        m12 *= scale;
        m20 *= scale;
        m21 *= scale;
        m22 *= scale;
        return this;
    }

    public Matrix4f rotateX(float angle) {
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

    public Matrix4f rotateY(float angle) {
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

    public Matrix4f rotateZ(float angle) {
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

    public Matrix4f translate(float x, float y, float z) {
        float r00 = m00 + m30 * x;
        float r01 = m01 + m31 * x;
        float r02 = m02 + m32 * x;
        float r03 = m03 + m33 * x;
        float r10 = m10 + m30 * y;
        float r11 = m11 + m31 * y;
        float r12 = m12 + m32 * y;
        float r13 = m13 + m33 * y;
        float r20 = m20 + m30 * z;
        float r21 = m21 + m31 * z;
        float r22 = m22 + m32 * z;
        float r23 = m23 + m33 * z;
        m00 = r00;
        m01 = r01;
        m02 = r02;
        m03 = r03;
        m10 = r10;
        m11 = r11;
        m12 = r12;
        m13 = r13;
        m20 = r20;
        m21 = r21;
        m22 = r22;
        m23 = r23;
        return this;
    }
}
