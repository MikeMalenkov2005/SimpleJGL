#version 450 core
layout(local_size_x = 1, local_size_y = 1, local_size_z = 1) in;
layout(rgba32f, binding = 0) uniform image2D grid;
uniform vec2 pos;
uniform vec4 data;


void main() {
    imageStore(grid, ivec2(pos.xy), data);
}