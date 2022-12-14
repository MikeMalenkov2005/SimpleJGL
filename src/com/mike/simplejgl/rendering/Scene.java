package com.mike.simplejgl.rendering;

import com.mike.simplejgl.rendering.textures.Texture;
import org.joml.Vector2i;

public interface Scene {
    void render(Vector2i windowResolution, int depth);
    Texture getDisplay();
    void destroy();
}
