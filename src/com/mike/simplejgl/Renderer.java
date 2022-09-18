package com.mike.simplejgl;

import com.mike.simplejgl.rendering.Scene;
import com.mike.simplejgl.rendering.VAO;
import com.mike.simplejgl.rendering.VBO;
import com.mike.simplejgl.rendering.shaders.Shader;
import com.mike.simplejgl.rendering.textures.ColorTexture;
import com.mike.simplejgl.rendering.textures.Texture;
import com.mike.simplejgl.vectors.Vector2i;
import com.mike.simplejgl.vectors.Vector4f;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL45.*;
import static org.lwjgl.system.MemoryUtil.NULL;

public class Renderer implements Runnable {
    public interface PostprocessingCallback {
        void bindUniforms(Shader shader, Texture display);

    }

    public int renderDepth;
    private Scene scene;

    private final long window;
    private long monitor;

    private final int display_vert, display_geom;
    private int display_frag;
    private Shader displayShader;
    private PostprocessingCallback postProcessingCallback;

    public final Texture placeholder;
    private final VAO displayPlane;

    private final List<InputListener> listeners = new ArrayList<>();
    private double cursorX, cursorY, time;

    /**
     * Implements a glfw and opengl initialization.
     *
     * @param title represents a title for a glfw window.
     */
    public Renderer(String title, int renderDepth, PrintStream log) {
        GLFWErrorCallback.createPrint(log).set();

        if (!glfwInit())
            throw new IllegalStateException("Unable to initialize GLFW");

        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_FALSE);
        glfwWindowHint(GLFW_MAXIMIZED, GLFW_TRUE);

        monitor = glfwGetPrimaryMonitor();
        window = glfwCreateWindow(Objects.requireNonNull(glfwGetVideoMode(monitor)).width(), Objects.requireNonNull(glfwGetVideoMode(monitor)).height(), title, monitor, NULL);
        if (window == NULL)
            throw new IllegalStateException("Could not create a GLFW window");

        glfwSetCursorPosCallback(window, this::mousePosCallback);
        glfwSetMouseButtonCallback(window, this::mouseButtonCallback);
        glfwSetScrollCallback(window, this::mouseScrollCallback);
        glfwSetKeyCallback(window, this::keyCallback);

        glfwMakeContextCurrent(window);
        glfwSwapInterval(1);
        glfwShowWindow(window);

        // IS REQUIRED FOR SOME REASON
        GL.createCapabilities();

        glEnable(GL_DEPTH_TEST);
        glEnable(GL_CULL_FACE);

        glFrontFace(GL_CW);
        glCullFace(GL_BACK);

        displayPlane = new VAO(0, 1, 2, 0, 2, 3);
        displayPlane.setVBO(0, new VBO(GL_STATIC_DRAW, -1f, -1f, 0f, -1f, 1f, 0f, 1f, 1f, 0f, 1f, -1f, 0f), GL_FLOAT, 3, false);
        displayPlane.setVBO(1, new VBO(GL_STATIC_DRAW, 0f, 0f, 0f, 1f, 1f, 1f, 1f, 0f), GL_FLOAT, 2, false);

        display_vert = Shader.loadVertexShader(Utils.getInternalFileInputStream(getClass(), "/com/mike/simplejgl/rendering/shaders/display.vert"));
        display_geom = Shader.loadGeometryShader(Utils.getInternalFileInputStream(getClass(), "/com/mike/simplejgl/rendering/shaders/display.geom"));
        display_frag = Shader.loadFragmentShader(Utils.getInternalFileInputStream(getClass(), "/com/mike/simplejgl/rendering/shaders/display.frag"));
        displayShader = new Shader(display_vert, display_geom, display_frag);
        placeholder = new ColorTexture(getWindowResolution(), new Vector4f(1, 1, 1, 1));
        this.renderDepth = renderDepth;
    }

    /**
     * Implements a main rendering loop.
     */
    @Override
    public void run() {
        glMatrixMode(GL_MODELVIEW);
        long t0 = System.nanoTime();
        while (!glfwWindowShouldClose(window)) {
            long t1 = System.nanoTime();
            time += (t1 - t0) / 1000000000.0;
            t0 = t1;
            glClearColor(0, 0, 0, 1);
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

            if (scene != null) scene.render(getWindowResolution(), renderDepth);

            (scene != null ? scene.getDisplay() : placeholder).bind(Texture.ALBEDO_MAP);

            if (displayShader != null) {
                float wdr = ((float) getWindowResolution().x * (scene != null ? scene.getDisplay() : placeholder).height) / (getWindowResolution().y * (scene != null ? scene.getDisplay() : placeholder).width);
                displayShader.loadUniform("wdr", wdr);
                if (postProcessingCallback != null) postProcessingCallback.bindUniforms(displayShader, scene != null ? scene.getDisplay() : placeholder);
                displayShader.bind();
            } else {
                glUseProgram(0);
            }
            displayPlane.draw(GL_TRIANGLES, 0, 1);

            glfwSwapBuffers(window);
            glfwPollEvents();
        }
    }

    /**
     * Closes the glfw window and safely ends the rendering loop.
     */
    public void close() {
        glfwSetWindowShouldClose(window, true);
    }

    public void destroy() {
        displayShader.destroy().forEach(Shader::deleteShader);
        placeholder.destroy();
        System.out.println(displayPlane.destroy(0, 1).size());
        glfwFreeCallbacks(window);
        glfwDestroyWindow(window);
        glfwTerminate();
        glfwSetErrorCallback(null);
    }

    public Vector2i getWindowResolution() {
        int[] w = new int[1];
        int[] h = new int[1];
        glfwGetWindowSize(window, w, h);
        return new Vector2i(w[0], h[0]);
    }

    /**
     * gets a time passed from the start of the render loop.
     * @return time in seconds.
     */
    public double getTime() {
        return time;
    }

    /**
     * Sets a postprocessing shader.
     *
     * @param postprocessingFragmentShader an opengl fragment shader for postprocessing.
     * @param postprocessingCallback       is called, to set all uniforms (can be null).
     */
    public int setPostprocessing(int postprocessingFragmentShader, PostprocessingCallback postprocessingCallback) {
        int p = display_frag;
        if (postprocessingFragmentShader == 0) {
            display_frag = Shader.loadFragmentShader(Utils.getInternalFileInputStream(getClass(), "/com/mike/simplejgl/rendering/shaders/display.frag"));
            displayShader.destroy();
            displayShader = new Shader(display_vert, display_geom, display_frag);
            this.postProcessingCallback = postprocessingCallback;
        } else {
            display_frag = postprocessingFragmentShader;
            displayShader.destroy();
            displayShader = new Shader(display_vert, display_geom, display_frag);
            this.postProcessingCallback = postprocessingCallback;
        }
        return p;
    }

    public void addInputListener(InputListener listener) {
        listeners.add(listener);
    }

    public void removeInputListener(InputListener listener) {
        listeners.remove(listener);
    }

    public Scene getScene() {
        return scene;
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }

    private void keyCallback(long window, int key, int scancode, int action, int mods) {
        listeners.forEach(l -> l.keyEvent(key, action, mods));
    }

    private void mouseButtonCallback(long window, int button, int action, int mods) {
        listeners.forEach(l -> l.mouseButtonEvent(cursorX, cursorY, button, action, mods));
    }

    private void mouseScrollCallback(long window, double xScroll, double yScroll) {
        listeners.forEach(l -> l.mouseScrollEvent(xScroll, yScroll));
    }

    private void mousePosCallback(long window, double x, double y) {
        listeners.forEach(l -> l.mouseMoveEvent(x, y, x - cursorX, y - cursorY));
        cursorX = x;
        cursorY = y;
    }
}
