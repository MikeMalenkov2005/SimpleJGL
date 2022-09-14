package com.mike.simplejgl.demos.mandelbrot;

import com.mike.simplejgl.Renderer;
import com.mike.simplejgl.Utils;
import com.mike.simplejgl.io.InputListener;
import com.mike.simplejgl.rendering.Scene;
import com.mike.simplejgl.rendering.shaders.Shader;
import com.mike.simplejgl.rendering.textures.ColorTexture;
import com.mike.simplejgl.rendering.textures.Texture;
import com.mike.simplejgl.vectors.Vector2f;
import com.mike.simplejgl.vectors.Vector2i;
import com.mike.simplejgl.vectors.Vector3f;
import com.mike.simplejgl.vectors.Vector4f;

import static org.lwjgl.glfw.GLFW.*;

public class MandelbrotScene implements Scene, InputListener {
    private final Texture display;
    private final Vector2i windowResolution;
    private final Runnable close;
    private final Vector3f coord = new Vector3f(-0.16f, 1.0405f, 0.026f);
    private boolean drag = false;
    private int iterations = 500;

    public MandelbrotScene(Renderer renderer, Vector2i resolution) {
        this.display = new ColorTexture(resolution, new Vector4f(1));
        windowResolution = renderer.getWindowResolution();
        Shader.deleteShader(renderer.setPostprocessing(Shader.loadFragmentShader(Utils.getInternalFile("/com/mike/simplejgl/demos/mandelbrot/draw.frag")), (shader, display) -> {
            shader.loadUniform("u_resolution", new Vector2f(display.width, display.height));
            shader.loadUniform("u_time", (float) renderer.getTime());
            shader.loadUniform("coord", coord);
            shader.loadUniform("iterations", iterations);
        }));
        renderer.setScene(this);
        renderer.addInputListener(this);
        close = renderer::close;
    }

    @Override
    public void render(Vector2i windowResolution, int depth) {

    }

    @Override
    public Texture getDisplay() {
        return display;
    }

    @Override
    public void keyEvent(int key, int action, int mods) {
        if (action == GLFW_RELEASE) {
            switch (key) {
                case GLFW_KEY_ESCAPE -> close.run();
                case GLFW_KEY_SPACE -> System.out.println(coord);
                case GLFW_KEY_LEFT -> iterations--;
                case GLFW_KEY_RIGHT -> iterations++;
            }
        }
    }

    @Override
    public void mouseButtonEvent(double x, double y, int button, int action, int mods) {
        if (button == GLFW_MOUSE_BUTTON_1) {
            switch (action) {
                case GLFW_RELEASE -> drag = false;
                case GLFW_PRESS -> drag = true;
            }
        }
    }

    @Override
    public void mouseScrollEvent(double xScroll, double yScroll) {
        coord.z *= Math.pow(1.1, -yScroll);
    }

    @Override
    public void mouseMoveEvent(double x, double y, double dx, double dy) {
        if (drag) {
            float d = Math.max(windowResolution.x, windowResolution.y);
            coord.x -= dx * 4 * coord.z / d;
            coord.y += dy * 4 * coord.z / d;
        }
    }
}
