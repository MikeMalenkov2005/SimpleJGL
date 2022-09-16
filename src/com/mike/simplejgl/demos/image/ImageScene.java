package com.mike.simplejgl.demos.image;

import com.mike.simplejgl.Renderer;
import com.mike.simplejgl.io.InputListener;
import com.mike.simplejgl.rendering.Scene;
import com.mike.simplejgl.rendering.textures.Texture;
import com.mike.simplejgl.vectors.Vector2i;

import java.io.File;
import java.util.Objects;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL45.*;

public class ImageScene implements Scene, InputListener {
    private final Texture image;
    private final Runnable close;

    public ImageScene(Renderer renderer, File imageFile) {
        image = Texture.loadTexture(Objects.requireNonNull(Texture.loadImage(imageFile)), GL_REPEAT, GL_LINEAR, 1);
        close = renderer::close;
        renderer.setScene(this);
        renderer.addInputListener(this);
    }

    @Override
    public void render(Vector2i windowResolution, int depth) {

    }

    @Override
    public Texture getDisplay() {
        return image;
    }

    @Override
    public void destroy() {
        image.destroy();
    }

    @Override
    public void keyEvent(int key, int action, int mods) {
        if (key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE) {
            close.run();
        }
    }

    @Override
    public void mouseButtonEvent(double x, double y, int button, int action, int mods) {

    }

    @Override
    public void mouseScrollEvent(double xScroll, double yScroll) {

    }

    @Override
    public void mouseMoveEvent(double x, double y, double dx, double dy) {

    }
}
