package com.mike.simplejgl;

import com.mike.simplejgl.demos.mandelbrot.MandelbrotScene;
import com.mike.simplejgl.io.InputListener;

import static org.lwjgl.glfw.GLFW.*;

public class Client {
    public static void main(String[] args) {
        Renderer renderer = new Renderer("TEST", 0, System.err);
        MandelbrotScene scene = new MandelbrotScene(renderer, renderer.getWindowResolution());

        renderer.addInputListener(new InputListener() {
            @Override
            public void keyEvent(int key, int action, int mods) {
                switch (action) {
                    case GLFW_RELEASE -> {
                        switch (key) {
                            case GLFW_KEY_ESCAPE -> renderer.close();
                        }
                    }
                    case GLFW_PRESS -> {

                    }
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
        });

        renderer.run();
        renderer.destroy();
    }
}
