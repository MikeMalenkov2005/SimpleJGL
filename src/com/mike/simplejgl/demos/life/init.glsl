#version 450 core
layout(local_size_x = 8, local_size_y = 8, local_size_z = 1) in;
layout(rgba32f, binding = 0) uniform image2D grid;
uniform vec3 deadColor;
uniform vec3 aliveColor;
uniform ivec2 resolution;

float random(vec2 st) {
    return fract(sin(dot(st.xy, vec2(12.9898, 78.233))) * 43758.5453123);
}

void main() {
    ivec2 pos = ivec2(gl_GlobalInvocationID.xy);
//    vec2 st = vec2(vec2(pos.xy) / vec2(resolution.xy) * 5.0);
//    imageStore(grid, pos, vec4(random(st)));
    ivec2 center = ivec2(resolution / 2);
    if (pos == (center + ivec2(1, 0)) || pos == (center + ivec2(2, 0)) || pos == (center + ivec2(0, 1)) || pos == (center + ivec2(1, 1)) || pos == (center + ivec2(1, 2))) {
        imageStore(grid, pos, vec4(aliveColor, 1.0));
    }
    else {
        imageStore(grid, pos, vec4(deadColor, 0.1));
    }
}