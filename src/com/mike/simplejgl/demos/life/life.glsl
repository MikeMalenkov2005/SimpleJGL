#version 450 core
layout(local_size_x = 8, local_size_y = 8, local_size_z = 1) in;
layout(rgba32f, binding = 0) uniform image2D in_grid;
layout(rgba32f, binding = 1) uniform image2D out_grid;
uniform vec3 deadColor;
uniform vec3 aliveColor;
uniform ivec2 resolution;

bool alive(ivec2 pos) {
    return imageLoad(in_grid, pos).w > 0.5;
}

void main() {
    ivec2 pos = ivec2(gl_GlobalInvocationID.xy);
    int aliveAround = 0;
    for (int x = -1; x <= 1; x++) {
        for (int y = -1; y <= 1; y++) {
            if (x != 0 || y != 0) {
                ivec2 npos = pos + ivec2(x, y);
                if (npos.x < 0) npos += ivec2(resolution.x, 0);
                if (npos.y < 0) npos += ivec2(0, resolution.y);
                if (npos.x >= resolution.x) npos -= ivec2(resolution.x, 0);
                if (npos.y >= resolution.y) npos -= ivec2(0, resolution.y);
                if (alive(npos)) aliveAround++;
            }
        }
    }
    if (aliveAround == 3 || (aliveAround == 2 && alive(pos))) {
        imageStore(out_grid, pos, vec4(aliveColor, 1.0));
    }
    else {
        imageStore(out_grid, pos, vec4(deadColor, 0.1));
    }
}