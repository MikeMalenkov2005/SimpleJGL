package com.mike.simplejgl.rendering.shaders;

import org.joml.Vector3i;

import java.io.InputStream;

import static org.lwjgl.opengl.GL45.*;

public class ComputeShader extends Shader {
    private final Vector3i local_size = new Vector3i(1, 1, 1);

    public ComputeShader(int shader, Vector3i local_size) {
        super(shader);
        this.local_size.x = Math.max(1, local_size.x);
        this.local_size.y = Math.max(1, local_size.y);
        this.local_size.z = Math.max(1, local_size.z);
    }

    public ComputeShader(InputStream source, Vector3i local_size) {
        this(loadComputeShader(source), local_size);
    }

    /**
     * Executes compute shader on the GPU.
     * @param memory_barrier represents an opengl memory barrier for the GPU program.
     */
    public void compute(Vector3i total_size, int memory_barrier) {
        Vector3i g = new Vector3i(1);
        g.x = Math.max(g.x, (int) Math.ceil(total_size.x / (float) local_size.x));
        g.y = Math.max(g.y, (int) Math.ceil(total_size.y / (float) local_size.y));
        g.z = Math.max(g.z, (int) Math.ceil(total_size.z / (float) local_size.z));

        bind();
        glDispatchCompute(g.x, g.y, g.z);
        glMemoryBarrier(memory_barrier);
        unbind();
    }

    public Vector3i getLocalSize() {
        return new Vector3i(local_size);
    }

    public void setLocalSize(Vector3i local_size) {
        this.local_size.x = Math.max(1, local_size.x);
        this.local_size.y = Math.max(1, local_size.y);
        this.local_size.z = Math.max(1, local_size.z);
    }
}
