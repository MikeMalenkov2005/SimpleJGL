package com.mike.simplejgl.rendering;

import java.util.*;

import static org.lwjgl.opengl.GL45.*;

public class VAO {
    private final Map<Integer, VBO> vbos = new HashMap<>();
    private final VBO indexBuffer;
    private final int id, vc;

    public VAO(int... indices) {
        vc = indices.length;
        id = glGenVertexArrays();
        glBindVertexArray(id);
        indexBuffer = new VBO(GL_STATIC_DRAW, indices);
        glBindVertexArray(0);
    }

    public VBO setVBO(int attribute, VBO vbo, int type, int vertexSize, boolean normalize) {
        glBindVertexArray(id);
        if (vbo != null) vbo.bind(GL_ARRAY_BUFFER);
        else glBindBuffer(GL_ARRAY_BUFFER, 0);
        glEnableVertexAttribArray(attribute);
        glVertexAttribPointer(attribute, vertexSize, vbo == null ? GL_BYTE : type, normalize, 0, 0);
        glDisableVertexAttribArray(attribute);
        glBindVertexArray(0);
        if (vbo != null) vbo.unbind(GL_ARRAY_BUFFER);
        return vbo == null ? vbos.remove(attribute) : vbos.put(attribute, vbo);
    }

    public void draw(int mode, int... attributes) {
        glBindVertexArray(id);
        indexBuffer.bind(GL_ELEMENT_ARRAY_BUFFER);
        for (int index : attributes) {
            glEnableVertexAttribArray(index);
        }
        glDrawElements(mode, vc, GL_UNSIGNED_INT, 0);
        for (int index : attributes) {
            glDisableVertexAttribArray(index);
        }
        indexBuffer.unbind(GL_ELEMENT_ARRAY_BUFFER);
        glBindVertexArray(0);
    }

    /**
     * Destroys a vertex array object and some of its attributes.
     * @param attributes specifies, which attributes to destroy.
     * @return a collections of vertex buffer objects, left after destruction of specified attributes.
     */
    public Collection<VBO> destroy(int... attributes) {
        glDeleteVertexArrays(id);
        indexBuffer.destroy();
        for (int attribute : attributes) {
            VBO vbo = vbos.remove(attribute);
            if (vbo != null) vbo.destroy();
        }
        return vbos.values();
    }
}
