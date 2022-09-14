#version 450 core
uniform vec2 u_resolution;
uniform float u_time;
uniform vec3 coord;
uniform int iterations;

vec2 complexSquare(vec2 z) {
    return vec2(z.x * z.x - z.y * z.y, 2 * z.x * z.y);
}

vec4 mandelbrot(vec2 c, int maxIter)
{
    int iterations = 0;
    vec2 z = vec2(0.0);
    while (iterations < maxIter)
    {
        z = complexSquare(z) + c;
        float dist = z.x * z.x + z.y * z.y;
        if (dist > 4.0) break;
        ++iterations;
    }
    if (iterations == maxIter) return vec4(0, 0, 0, 1);
    int i = int(mod(iterations, 16));
    switch (i) {
        case 0: return vec4(vec3(66, 30, 15) / 255.0, 1);
        case 1: return vec4(vec3(25, 7, 26) / 255.0, 1);
        case 2: return vec4(vec3(9, 1, 47) / 255.0, 1);
        case 3: return vec4(vec3(4, 4, 73) / 255.0, 1);
        case 4: return vec4(vec3(0, 7, 100) / 255.0, 1);
        case 5: return vec4(vec3(12, 44, 138) / 255.0, 1);
        case 6: return vec4(vec3(24, 82, 177) / 255.0, 1);
        case 7: return vec4(vec3(57, 125, 209) / 255.0, 1);
        case 8: return vec4(vec3(134, 181, 229) / 255.0, 1);
        case 9: return vec4(vec3(211, 236, 248) / 255.0, 1);
        case 10: return vec4(vec3(241, 233, 191) / 255.0, 1);
        case 11: return vec4(vec3(248, 201, 95) / 255.0, 1);
        case 12: return vec4(vec3(255, 170, 0) / 255.0, 1);
        case 13: return vec4(vec3(204, 128, 0) / 255.0, 1);
        case 14: return vec4(vec3(153, 87, 0) / 255.0, 1);
        case 15: return vec4(vec3(106, 52, 3) / 255.0, 1);
        default: return vec4(0, 0, 0, 1);
    }
}

void main()
{
    vec2 c = (gl_FragCoord.xy - u_resolution / 2) / max(u_resolution.x, u_resolution.y) * 4 * coord.z + coord.xy;
    gl_FragColor = mandelbrot(c, iterations);
}