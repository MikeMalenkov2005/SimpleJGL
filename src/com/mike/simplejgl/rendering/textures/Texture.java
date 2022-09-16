package com.mike.simplejgl.rendering.textures;

import com.mike.simplejgl.Utils;
import com.mike.simplejgl.vectors.Vector2i;
import com.mike.simplejgl.vectors.Vector3i;
import org.lwjgl.BufferUtils;
import org.lwjgl.system.MemoryUtil;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.lwjgl.opengl.GL45.*;

public class Texture {
    public static final int ALBEDO_MAP = 0;
    public static final int NORMAL_MAP = 1;
    public static final int SHADOW_MAP = 2;

    public static final int MAIN_TEXTURE = ALBEDO_MAP;

    private static final Map<Integer, int[]> boundTextures = new HashMap<>();

    public final int id, type, format, levels, width, height, depth;
    private int filter, wrapMode;

    public Texture(int size, int wrapMode, int filter, int levels, int internalFormat) {
        this.width = size;
        this.height = 1;
        this.depth = 1;

        this.format = internalFormat;
        this.levels = levels;
        this.wrapMode = wrapMode;
        this.filter = filter;

        type = GL_TEXTURE_1D;
        id = glCreateTextures(type);
        glTextureStorage1D(id, levels, internalFormat, width);

        glTextureParameteri(id, GL_TEXTURE_MIN_FILTER, filter);
        glTextureParameteri(id, GL_TEXTURE_MAG_FILTER, filter);
        glTextureParameteri(id, GL_TEXTURE_WRAP_S, wrapMode);
    }

    public Texture(int size, int wrapMode, int filter, int levels, int internalFormat, int format, int data_type, long address) {
        this(size, wrapMode, filter, levels, internalFormat);
        glTextureSubImage1D(id, 0, 0, width, format, data_type, address);
    }

    public Texture(int size, int wrapMode, int filter, int levels, int internalFormat, int format, int data_type, Buffer buffer) {
        this(size, wrapMode, filter, levels, internalFormat, format, data_type, MemoryUtil.memAddress(buffer));
    }

    public Texture(Vector2i size, int wrapMode, int filter, int levels, int internalFormat) {
        this.width = size.x;
        this.height = size.y;
        this.depth = 1;

        this.format = internalFormat;
        this.levels = levels;
        this.wrapMode = wrapMode;
        this.filter = filter;

        type = GL_TEXTURE_2D;
        id = glCreateTextures(type);
        glTextureStorage2D(id, levels, internalFormat, width, height);

        glTextureParameteri(id, GL_TEXTURE_MIN_FILTER, filter);
        glTextureParameteri(id, GL_TEXTURE_MAG_FILTER, filter);
        glTextureParameteri(id, GL_TEXTURE_WRAP_S, wrapMode);
        glTextureParameteri(id, GL_TEXTURE_WRAP_T, wrapMode);
    }

    public Texture(Vector2i size, int wrapMode, int filter, int levels, int internalFormat, int format, int data_type, long address) {
        this(size, wrapMode, filter, levels, internalFormat);
        glTextureSubImage2D(id, 0, 0, 0, width, height, format, data_type, address);
    }

    public Texture(Vector2i size, int wrapMode, int filter, int levels, int internalFormat, int format, int data_type, Buffer buffer) {
        this(size, wrapMode, filter, internalFormat, levels, format, data_type, MemoryUtil.memAddress(buffer));
    }

    public Texture(Vector3i size, int wrapMode, int filter, int levels, int internalFormat) {
        this.width = size.x;
        this.height = size.y;
        this.depth = size.z;

        this.format = internalFormat;
        this.levels = levels;
        this.wrapMode = wrapMode;
        this.filter = filter;

        type = GL_TEXTURE_3D;
        id = glCreateTextures(type);

        glTextureParameteri(id, GL_TEXTURE_MIN_FILTER, filter);
        glTextureParameteri(id, GL_TEXTURE_MAG_FILTER, filter);
        glTextureParameteri(id, GL_TEXTURE_WRAP_S, wrapMode);
        glTextureParameteri(id, GL_TEXTURE_WRAP_T, wrapMode);
        glTextureParameteri(id, GL_TEXTURE_WRAP_R, wrapMode);

        glTextureStorage3D(id, levels, internalFormat, width, height, depth);
    }

    public Texture(Vector3i size, int wrapMode, int filter, int levels, int internalFormat, int format, int data_type, long address) {
        this(size, wrapMode, filter, levels, internalFormat);
        glTextureSubImage3D(id, 0, 0, 0, 0, width, height, depth, format, data_type, address);
    }

    public Texture(Vector3i size, int wrapMode, int filter, int levels, int internalFormat, int format, int data_type, Buffer buffer) {
        this(size, wrapMode, filter, levels, internalFormat, format, data_type, MemoryUtil.memAddress(buffer));
    }

    public void bind(int index) {
        int i = Utils.clamp(index, 0, 31);
        if (boundTextures.computeIfAbsent(type, k -> new int[32])[i] != id) {
            glActiveTexture(GL_TEXTURE0 + i);
            glBindTexture(type, id);
            boundTextures.get(type)[i] = id;
        }
    }

    public void unbind(int index) {
        int i = Utils.clamp(index, 0, 31);
        if (boundTextures.containsKey(type) && boundTextures.get(type)[i] == id) {
            glActiveTexture(GL_TEXTURE0 + i);
            glBindTexture(type, 0);
            boundTextures.get(type)[i] = 0;
        }
    }

    public void bindImage(int index, int access) {
        glBindImageTexture(index, id, 0, false, 0, access, format);
    }

    public void destroy() {
        glDeleteTextures(id);
    }

    public int getFilter() {
        return filter;
    }

    public void setFilter(int filter) {
        this.filter = filter;
        glTextureParameteri(id, GL_TEXTURE_MIN_FILTER, filter);
        glTextureParameteri(id, GL_TEXTURE_MAG_FILTER, filter);
    }

    public int getWrapMode() {
        return wrapMode;
    }

    public void setWrapMode(int wrapMode) {
        this.wrapMode = wrapMode;
        glTextureParameteri(id, GL_TEXTURE_WRAP_S, wrapMode);
        glTextureParameteri(id, GL_TEXTURE_WRAP_T, wrapMode);
        glTextureParameteri(id, GL_TEXTURE_WRAP_R, wrapMode);
    }

    /**
     * loads a 2D texture from a buffered image in RGBA format with 4 bytes per pixel.
     *
     * @param image represents an image loaded to the texture.
     * @param wrapMode represents an opengl texture wrap mode (GL_REPEAT, GL_CLAMP_TO_BORDER, GL_CLAMP_TO_EDGE).
     * @param filter represents an opengl texture filtering mode (GL_NEAREST, GL_LINEAR).
     * @param levels represents an amount of mipmap levels.
     * @return a texture, generated from an image.
     */
    public static Texture loadTexture(BufferedImage image, int wrapMode, int filter, int levels) {
        int[] pixels = new int[image.getWidth() * image.getHeight()];
        image.getRGB(0, 0, image.getWidth(), image.getHeight(), pixels, 0, image.getWidth());
        ByteBuffer buffer = BufferUtils.createByteBuffer(image.getWidth() * image.getHeight() * 4);
        for (int y = image.getHeight() - 1; y >= 0; y--) {
            for (int x = 0; x < image.getWidth(); x++) {
                int pixel = pixels[y * image.getWidth() + x];
                buffer.put((byte) ((pixel >> 16) & 0xFF));
                buffer.put((byte) ((pixel >> 8) & 0xFF));
                buffer.put((byte) (pixel & 0xFF));
                buffer.put((byte) ((pixel >> 24) & 0xFF));
            }
        }
        buffer.flip();
        return new Texture(new Vector2i(image.getWidth(), image.getHeight()), wrapMode, filter, levels, GL_RGBA8, GL_RGBA, GL_UNSIGNED_BYTE, MemoryUtil.memAddress(buffer));
    }

    /**
     * Loads a buffered image from a file.
     *
     * @param file represents a file you want to load an image from.
     * @return a buffered image, read from the file.
     */
    public static BufferedImage loadImage(File file) {
        try {
            return ImageIO.read(file);
        } catch (IOException ignored) {

        }
        return null;
    }
}
