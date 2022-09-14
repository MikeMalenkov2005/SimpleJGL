package com.mike.simplejgl;

import com.mike.simplejgl.demos.life.LifeScene;
import com.mike.simplejgl.vectors.Vector2i;
import com.mike.simplejgl.vectors.Vector3f;

public class Demo {
    public static void main(String[] args) {
        Renderer renderer = new Renderer("TEST", 0, System.err);
        LifeScene scene = new LifeScene(renderer, new Vector2i(100), new Vector3f(0.125f), new Vector3f(1f));
        renderer.run();
        scene.destroy();
        renderer.destroy();
    }
}
