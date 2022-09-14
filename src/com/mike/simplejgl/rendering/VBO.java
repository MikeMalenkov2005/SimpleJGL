package com.mike.simplejgl.rendering;

import java.util.HashMap;
import java.util.Map;

import static org.lwjgl.opengl.GL45.*;

public class VBO {
    private final int id;
    private int size;

    private static final Map<Integer, Integer> boundBuffers = new HashMap<>();

    public VBO(int usage, float... data) {
        id = glCreateBuffers();
        glNamedBufferData(id, data, usage);
        size = data.length * 4;
    }

    public VBO(int usage, int... data) {
        id = glCreateBuffers();
        glNamedBufferData(id, data, usage);
        size = data.length * 4;
    }

    public void setData(int usage, float... data) {
        glNamedBufferData(id, data, usage);
        size = data.length * 4;
    }

    public void setData(int usage, int... data) {
        glNamedBufferData(id, data, usage);
        size = data.length * 4;
    }

    /**
     * copies data from the opengl buffer to the target array.
     * @param offset an offset as a number of elements from the beginning of the buffer.
     * @param target an array to hold the copied data.
     */
    public void getData(long offset, float[] target) {
        glGetNamedBufferSubData(id, offset * 4, target);
    }

    /**
     * copies data from the opengl buffer to the target array.
     * @param offset an offset as a number of elements from the beginning of the buffer.
     * @param target an array to hold the copied data.
     */
    public void getData(long offset, int[] target) {
        glGetNamedBufferSubData(id, offset * 4, target);
    }

    public void bind(int target) {
        if (boundBuffers.computeIfAbsent(target, k -> 0) != id) {
            boundBuffers.put(target, id);
            glBindBuffer(target, id);
        }
    }

    public void unbind(int target) {
        if (boundBuffers.computeIfAbsent(target, k -> 0) == id) {
            boundBuffers.put(target, 0);
            glBindBuffer(target, 0);
        }
    }

    public void bindIndexed(int target, int index) {
        glBindBufferBase(target, index, id);
    }

    public void destroy() {
        glDeleteBuffers(id);
    }

    /**
     * gets the size of the opengl buffer.
     * @return size in bytes.
     */
    public int getSize() {
        return size;
    }
}
