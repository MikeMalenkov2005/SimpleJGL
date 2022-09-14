package com.mike.simplejgl.rendering.textures;

import com.mike.simplejgl.vectors.Vector2i;
import com.mike.simplejgl.vectors.Vector3i;
import com.mike.simplejgl.vectors.Vector4f;
import org.lwjgl.BufferUtils;
import org.lwjgl.system.MemoryUtil;

import java.nio.FloatBuffer;

import static org.lwjgl.opengl.GL45.*;

public class ColorTexture extends Texture {
    public ColorTexture(int size, Vector4f color) {
        super(size, GL_REPEAT, GL_NEAREST, 1, GL_RGBA32F, GL_RGBA, GL_FLOAT, colorToPixelBuffer(color, size));
    }

    public ColorTexture(Vector2i size, Vector4f color) {
        super(size, GL_REPEAT, GL_NEAREST, 1, GL_RGBA32F, GL_RGBA, GL_FLOAT, colorToPixelBuffer(color, size.x * size.y));
    }

    public ColorTexture(Vector3i size, Vector4f color) {
        super(size, GL_REPEAT, GL_NEAREST, 1, GL_RGBA32F, GL_RGBA, GL_FLOAT, colorToPixelBuffer(color, size.x * size.y * size.z));
    }

    private static long colorToPixelBuffer(Vector4f color, int size) {
        FloatBuffer buffer = BufferUtils.createFloatBuffer(size * 4);
        for (int i = 0; i < size; i++) {
            buffer.put(color.x);
            buffer.put(color.y);
            buffer.put(color.z);
            buffer.put(color.w);
        }
        return MemoryUtil.memAddress(buffer.flip());
    }
}
