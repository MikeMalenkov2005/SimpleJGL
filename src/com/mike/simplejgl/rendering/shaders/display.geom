#version 450 core

layout (triangles) in;
layout (triangle_strip, max_vertices = 3) out;

out vec2 uv;

in DATA {
    vec2 uv;
} data_in[];

void main() {
    for (int i = 0; i < 3; i++) {
        gl_Position = gl_in[i].gl_Position;
        uv = data_in[i].uv;
        EmitVertex();
    }
    EndPrimitive();
}