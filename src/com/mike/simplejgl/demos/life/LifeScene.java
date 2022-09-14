package com.mike.simplejgl.demos.life;

import com.mike.simplejgl.Renderer;
import com.mike.simplejgl.Utils;
import com.mike.simplejgl.io.InputListener;
import com.mike.simplejgl.rendering.Scene;
import com.mike.simplejgl.rendering.shaders.ComputeShader;
import com.mike.simplejgl.rendering.textures.Texture;
import com.mike.simplejgl.vectors.*;
import org.lwjgl.system.MemoryUtil;

import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL45.*;

public class LifeScene implements Scene, InputListener {
    public long waitTime = 250000000L;
    public boolean paused = true;

    private final Runnable close;
    private final ComputeShader shader, init, set;
    private Vector3f deadColor, aliveColor;
    private Texture display, buffer;

    private long waited, t0 = System.nanoTime();
    private List<Vector3f> toSet = new ArrayList<>();

    public LifeScene(Renderer renderer, Vector2i gridSize, Vector3f deadColor, Vector3f aliveColor) {
        set = new ComputeShader(Utils.getInternalFile("/com/mike/simplejgl/demos/life/set.glsl"), new Vector3i(1, 1, 1));
        shader = new ComputeShader(Utils.getInternalFile("/com/mike/simplejgl/demos/life/life.glsl"), new Vector3i(8, 8, 1));
        display = new Texture(gridSize, GL_REPEAT, GL_NEAREST, 1, GL_RGBA32F);
        buffer = new Texture(gridSize, GL_REPEAT, GL_NEAREST, 1, GL_RGBA32F);

        init = new ComputeShader(Utils.getInternalFile("/com/mike/simplejgl/demos/life/init.glsl"), new Vector3i(8, 8, 1));
        display.bindImage(0, GL_READ_WRITE);
        init.loadUniform("resolution", gridSize);
        init.compute(new Vector3i(gridSize.x, gridSize.y, 1), GL_ALL_BARRIER_BITS);
        init.unbind();

        this.deadColor = new Vector3f(deadColor);
        this.aliveColor = new Vector3f(aliveColor);

        close = renderer::close;
        renderer.setScene(this);
        renderer.addInputListener(this);
    }

    public void restart() {
        display.bindImage(0, GL_READ_WRITE);
        init.loadUniform("resolution", new Vector2i(display.width, display.height));
        init.compute(new Vector3i(display.width, display.height, 1), GL_ALL_BARRIER_BITS);
        init.unbind();
    }

    public void update() {
        if (!paused) {
            shader.loadUniform("deadColor", deadColor);
            shader.loadUniform("aliveColor", aliveColor);
            shader.loadUniform("resolution", new Vector2i(display.width, display.height));
            display.bindImage(0, GL_READ_ONLY);
            buffer.bindImage(1, GL_WRITE_ONLY);
            shader.compute(new Vector3i(display.width, display.height, 1), GL_ALL_BARRIER_BITS);
            Texture tmp = buffer;
            buffer = display;
            display = tmp;
        }
    }

    @Override
    public void render(Vector2i windowResolution, int depth) {
        long t1 = System.nanoTime();
        waited += t1 - t0;
        if (!toSet.isEmpty()) {
            display.bindImage(0, GL_WRITE_ONLY);
            toSet.forEach(v -> {
                float wdr = ((float) windowResolution.x * display.height) / (windowResolution.y * display.width);
                Vector2f center = new Vector2f(windowResolution.x, windowResolution.y).div(2);
                Vector2f shifter = new Vector2f((float) Math.min(1., 1 / wdr), (float) Math.min(1., wdr));
                Vector2f pos = new Vector2f(v.x, v.y).div(center).sub(new Vector2f(1)).div(shifter);
                pos.add(new Vector2f(1)).div(2).mul(new Vector2f(display.width, display.height));
                if (pos.x > 0 && pos.x < display.width && pos.y > 0 && pos.y < display.height) {
                    Vector3f color = v.z > .5f ? aliveColor : deadColor;
                    set.loadUniform("pos", pos);
                    set.loadUniform("data", new Vector4f(color.x, color.y, color.z, v.z));
                    set.compute(new Vector3i(1), GL_ALL_BARRIER_BITS);
                }
            });
            toSet = new ArrayList<>();
        }
        if (waited >= waitTime) {
            update();
            waited = 0;
        }
        t0 = t1;
    }

    @Override
    public Texture getDisplay() {
        return display;
    }

    public Vector3f getDeadColor() {
        return deadColor;
    }

    public void setDeadColor(Vector3f deadColor) {
        this.deadColor = new Vector3f(deadColor);
    }

    public Vector3f getAliveColor() {
        return aliveColor;
    }

    public void setAliveColor(Vector3f aliveColor) {
        this.aliveColor = new Vector3f(aliveColor);
    }

    public Vector2i getGridSize() {
        return new Vector2i(display.width, display.height);
    }

    public void setGridSize(Vector2i gridSize) {
        display.destroy();
        display = new Texture(gridSize, GL_REPEAT, GL_NEAREST, 1, GL_RGBA32F);
        buffer = new Texture(gridSize, GL_REPEAT, GL_NEAREST, 1, GL_RGBA32F);
    }

    @Override
    public void keyEvent(int key, int action, int mods) {
        switch (action) {
            case GLFW_RELEASE -> {
                switch (key) {
                    case GLFW_KEY_ESCAPE -> close.run();
                    case GLFW_KEY_LEFT -> waitTime = waitTime << 1;
                    case GLFW_KEY_RIGHT -> waitTime = waitTime >> 1;
                    case GLFW_KEY_R -> restart();
                    case GLFW_KEY_SPACE -> paused = !paused;
                }
            }
            case GLFW_PRESS -> {

            }
        }
    }

    @Override
    public void mouseButtonEvent(double x, double y, int button, int action, int mods) {

        switch (action) {
            case GLFW_RELEASE -> {
                switch (button) {
                    case GLFW_MOUSE_BUTTON_1 -> toSet.add(new Vector3f((float) x, (float) y, 1f));
                    case GLFW_MOUSE_BUTTON_2 -> toSet.add(new Vector3f((float) x, (float) y, 0f));
                }
            }
            case GLFW_PRESS -> {

            }
        }
    }

    @Override
    public void mouseScrollEvent(double xScroll, double yScroll) {

    }

    @Override
    public void mouseMoveEvent(double x, double y, double dx, double dy) {

    }
}
