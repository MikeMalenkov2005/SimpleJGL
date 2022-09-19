package com.mike.simplejgl;

import org.lwjgl.BufferUtils;

import java.nio.*;

public class Utils {
    public static ByteBuffer createNativeBuffer(byte... arr) {
        ByteBuffer buffer = BufferUtils.createByteBuffer(arr.length);
        for (byte e : arr) {
            buffer.put(e);
        }
        return buffer.flip();
    }

    public static ShortBuffer createNativeBuffer(short... arr) {
        ShortBuffer buffer = BufferUtils.createShortBuffer(arr.length);
        for (short e : arr) {
            buffer.put(e);
        }
        return buffer.flip();
    }

    public static IntBuffer createNativeBuffer(int... arr) {
        IntBuffer buffer = BufferUtils.createIntBuffer(arr.length);
        for (int e : arr) {
            buffer.put(e);
        }
        return buffer.flip();
    }

    public static LongBuffer createNativeBuffer(long... arr) {
        LongBuffer buffer = BufferUtils.createLongBuffer(arr.length);
        for (long e : arr) {
            buffer.put(e);
        }
        return buffer.flip();
    }

    public static FloatBuffer createNativeBuffer(float... arr) {
        FloatBuffer buffer = BufferUtils.createFloatBuffer(arr.length);
        for (float e : arr) {
            buffer.put(e);
        }
        return buffer.flip();
    }

    public static double clamp(double v, double min, double max) {
        return Math.min(Math.max(v, min), max);
    }

    public static float clamp(float v, float min, float max) {
        return Math.min(Math.max(v, min), max);
    }

    public static int clamp(int v, int min, int max) {
        return Math.min(Math.max(v, min), max);
    }

    public static double roll(double v, double min, double max) {
        return min + ((v - min) % (max - min));
    }

    public static float roll(float v, float min, float max) {
        return min + ((v - min) % (max - min));
    }

    public static int roll(int v, int min, int max) {
        return min + ((v - min) % (max - min));
    }

    public static double pong(double v, double min, double max) {
        if (max <= 0) return 0;
        return Math.abs(Math.abs(roll(v - min - max, 0, 4 * max) - 2 * max) - max) + min;
    }

    public static float pong(float v, float min, float max) {
        if (max <= 0) return 0;
        return Math.abs(Math.abs(roll(v - min - max, 0, 4 * max) - 2 * max) - max) + min;
    }

    public static int pong(int v, int min, int max) {
        if (max <= 0) return 0;
        return Math.abs(Math.abs(roll(v - min - max, 0, 4 * max) - 2 * max) - max) + min;
    }
}
