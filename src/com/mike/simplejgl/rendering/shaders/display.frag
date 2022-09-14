#version 450 core

in vec2 uv;

out vec4 fragColor;

uniform sampler2D display_texture;

void main() {
    fragColor = texture(display_texture, uv);
}