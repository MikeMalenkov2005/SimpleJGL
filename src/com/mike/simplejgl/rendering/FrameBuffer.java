package com.mike.simplejgl.rendering;

import com.mike.simplejgl.rendering.textures.Texture;

import static org.lwjgl.opengl.GL45.*;

public class FrameBuffer {
    private static int boundFrameBufferObject;

    public final int id;

    public FrameBuffer() {
        id = glGenFramebuffers();
    }

    public void bind() {
        if (boundFrameBufferObject != id) {
            glBindFramebuffer(GL_FRAMEBUFFER, id);
            boundFrameBufferObject = id;
        }
    }

    public void unbind() {
        if (boundFrameBufferObject == id) {
            glBindFramebuffer(GL_FRAMEBUFFER, 0);
            boundFrameBufferObject = 0;
        }
    }

    /**
     * Attaches a texture to the frame buffer.
     * @param texture represents the texture to attach.
     * @param attachment represents an opengl attachment for the frame buffer (GL_DEPTH_ATTACHMENT, GL_STENCIL_ATTACHMENT, GL_DEPTH_STENCIL_ATTACHMENT, GL_COLOR_ATTACHMENT0-31).
     */
    public void attach(Texture texture, int attachment) {
        bind();
        glFramebufferTexture(GL_FRAMEBUFFER, attachment, texture.id, 0);
        unbind();
    }

    public void destroy() {
        glDeleteFramebuffers(id);
    }
}
