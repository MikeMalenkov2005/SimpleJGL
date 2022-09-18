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
    private final Vector2i resolution;
    private final Runnable close;
    private final float d;

    private final Vector3f coord = new Vector3f(0, 0, 1);
    private final Vector2f mousePos = new Vector2f(0);
    private boolean drag = false;
    private int iterations;

    public MandelbrotScene(Renderer renderer, int iterations) {
        resolution = renderer.getWindowResolution();
        d = Math.max(resolution.x, resolution.y);
        this.iterations = iterations;
        this.display = new ColorTexture(resolution, new Vector4f(1));
        Shader.deleteShader(renderer.setPostprocessing(Shader.loadFragmentShader(Utils.getInternalFileInputStream(getClass(), "draw.frag")), (shader, display) -> {
            shader.loadUniform("u_resolution", new Vector2f(display.width, display.height));
            shader.loadUniform("u_time", (float) renderer.getTime());
            shader.loadUniform("coord", coord);
            shader.loadUniform("iterations", this.iterations);
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
    public void destroy() {
        display.destroy();
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
        float scale = (float) Math.pow(1.1, -yScroll);
        coord.x = mousePos.x + (coord.x - mousePos.x) * scale;
        coord.y = mousePos.y + (coord.y - mousePos.y) * scale;
        coord.z *= scale;
    }

    @Override
    public void mouseMoveEvent(double x, double y, double dx, double dy) {
        if (drag) {
            coord.x -= dx * 4 * coord.z / d;
            coord.y += dy * 4 * coord.z / d;
        } else {
            mousePos.x = (float) ((x - resolution.x / 2.0) / d * 4 * coord.z + coord.x);
            mousePos.y = (float) (-(y - resolution.y / 2.0) / d * 4 * coord.z + coord.y);
        }
    }
}
