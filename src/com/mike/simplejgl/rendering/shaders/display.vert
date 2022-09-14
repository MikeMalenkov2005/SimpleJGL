#version 450 core

layout(location = 0) in vec3 in_pos;
layout(location = 1) in vec2 in_uv;

out DATA {
    vec2 uv;
} data_out;

uniform float wdr;

void main() {
    gl_Position = vec4(in_pos.x * min(1., 1 / wdr), in_pos.y * min(1., wdr), in_pos.z, 1.0);
    data_out.uv = in_uv;
}