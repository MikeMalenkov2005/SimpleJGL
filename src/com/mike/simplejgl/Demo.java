package com.mike.simplejgl;

import com.mike.simplejgl.demos.mandelbrot.MandelbrotScene;

public class Demo {
    public static void main(String[] args) {
        Renderer renderer = new Renderer("TEST", 0, System.err);
        MandelbrotScene scene = new MandelbrotScene(renderer, 1000);
        renderer.run();
        scene.destroy();
        renderer.destroy();
    }
}
