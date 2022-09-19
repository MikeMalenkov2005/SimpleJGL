package com.mike.simplejgl.rendering.shaders;

import org.joml.*;
import org.lwjgl.opengl.GL20;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

import static org.lwjgl.opengl.GL45.*;

public class Shader {
    private static int boundProgram;

    private final int program;
    private final int[] attachedShaders;

    public Shader(int... attachedShaders) {
        this.attachedShaders = attachedShaders;
        program = glCreateProgram();
        for (int shader : attachedShaders) {
            glAttachShader(program, shader);
        }
        glLinkProgram(program);
        if (glGetProgrami(program, GL_LINK_STATUS) == GL_FALSE) {
            System.err.println("Error: Linking Program - " + glGetProgramInfoLog(program));
        }
        glValidateProgram(program);
        if (glGetProgrami(program, GL_VALIDATE_STATUS) == GL_FALSE) {
            System.err.println("Error: Validating Program - " + glGetProgramInfoLog(program));
        }
    }

    /**
     * Destroys the shader program.
     */
    public List<Integer> destroy() {
        for (int attachedShader : attachedShaders) {
            glDetachShader(program, attachedShader);
        }
        glDeleteProgram(program);
        return Arrays.stream(attachedShaders).boxed().toList();
    }

    public void bind() {
        if (boundProgram != program) {
            glUseProgram(program);
            boundProgram = program;
        }
    }

    public void unbind() {
        if (boundProgram == program) {
            glUseProgram(0);
            boundProgram = 0;
        }
    }

    public boolean hasUniform(String name) {
        return glGetUniformLocation(program, name) != -1;
    }

    public void loadUniform(String uniformName, float value) {
        bind();
        GL20.glUniform1f(GL20.glGetUniformLocation(program, uniformName), value);
    }

    public void loadUniform(String uniformName, float[] values) {
        bind();
        GL20.glUniform1fv(GL20.glGetUniformLocation(program, uniformName), values);
    }

    public void loadUniform(String uniformName, Vector2f value) {
        bind();
        GL20.glUniform2f(GL20.glGetUniformLocation(program, uniformName), value.x, value.y);
    }

    public void loadUniform(String uniformName, Vector2f[] values) {
        bind();
        float[] arr = new float[values.length * 2];
        for (int i = 0; i < values.length; i++) {
            arr[i * 2] = values[i].x;
            arr[i * 2 + 1] = values[i].y;
        }
        GL20.glUniform2fv(GL20.glGetUniformLocation(program, uniformName), arr);
    }

    public void loadUniform(String uniformName, Vector3f value) {
        bind();
        GL20.glUniform3f(GL20.glGetUniformLocation(program, uniformName), value.x, value.y, value.z);
    }

    public void loadUniform(String uniformName, Vector3f[] values) {
        bind();
        float[] arr = new float[values.length * 3];
        for (int i = 0; i < values.length; i++) {
            arr[i * 3] = values[i].x;
            arr[i * 3 + 1] = values[i].y;
            arr[i * 3 + 2] = values[i].z;
        }
        GL20.glUniform3fv(GL20.glGetUniformLocation(program, uniformName), arr);
    }

    public void loadUniform(String uniformName, Vector4f value) {
        bind();
        GL20.glUniform4f(GL20.glGetUniformLocation(program, uniformName), value.x, value.y, value.z, value.w);
    }

    public void loadUniform(String uniformName, Vector4f[] values) {
        bind();
        float[] arr = new float[values.length * 4];
        for (int i = 0; i < values.length; i++) {
            arr[i * 4] = values[i].x;
            arr[i * 4 + 1] = values[i].y;
            arr[i * 4 + 2] = values[i].z;
            arr[i * 4 + 3] = values[i].w;
        }
        GL20.glUniform4fv(GL20.glGetUniformLocation(program, uniformName), arr);
    }

    public void loadUniform(String uniformName, boolean transpose, Matrix2f value) {
        bind();
        float[] arr = new float[4];
        value.get(arr);
        GL20.glUniformMatrix2fv(GL20.glGetUniformLocation(program, uniformName), transpose, arr);
    }

    public void loadUniform(String uniformName, boolean transpose, Matrix2f[] values) {
        bind();
        float[] arr = new float[values.length * 4];
        for (int i = 0; i < values.length; i++) {
            float[] value = new float[4];
            values[i].get(value);
            System.arraycopy(value, 0, arr, i * 4, value.length);
        }
        GL20.glUniformMatrix2fv(GL20.glGetUniformLocation(program, uniformName), transpose, arr);
    }

    public void loadUniform(String uniformName, boolean transpose, Matrix3f value) {
        bind();
        float[] arr = new float[9];
        value.get(arr);
        GL20.glUniformMatrix3fv(GL20.glGetUniformLocation(program, uniformName), transpose, arr);
    }

    public void loadUniform(String uniformName, boolean transpose, Matrix3f[] values) {
        bind();
        float[] arr = new float[values.length * 9];
        for (int i = 0; i < values.length; i++) {
            float[] value = new float[9];
            values[i].get(value);
            System.arraycopy(value, 0, arr, i * 9, value.length);
        }
        GL20.glUniformMatrix3fv(GL20.glGetUniformLocation(program, uniformName), transpose, arr);
    }

    public void loadUniform(String uniformName, boolean transpose, Matrix4f value) {
        bind();
        float[] arr = new float[16];
        value.get(arr);
        GL20.glUniformMatrix4fv(GL20.glGetUniformLocation(program, uniformName), transpose, arr);
    }

    public void loadUniform(String uniformName, boolean transpose, Matrix4f[] values) {
        bind();
        float[] arr = new float[values.length * 16];
        for (int i = 0; i < values.length; i++) {
            float[] value = new float[16];
            values[i].get(value);
            System.arraycopy(value, 0, arr, i * 16, value.length);
        }
        GL20.glUniformMatrix4fv(GL20.glGetUniformLocation(program, uniformName), transpose, arr);
    }

    public void loadUniform(String uniformName, int value) {
        bind();
        GL20.glUniform1i(GL20.glGetUniformLocation(program, uniformName), value);
    }

    public void loadUniform(String uniformName, int[] values) {
        bind();
        GL20.glUniform1iv(GL20.glGetUniformLocation(program, uniformName), values);
    }

    public void loadUniform(String uniformName, Vector2i value) {
        bind();
        GL20.glUniform2i(GL20.glGetUniformLocation(program, uniformName), value.x, value.y);
    }

    public void loadUniform(String uniformName, Vector2i[] values) {
        bind();
        int[] arr = new int[values.length * 2];
        for (int i = 0; i < values.length; i++) {
            arr[i * 2] = values[i].x;
            arr[i * 2 + 1] = values[i].y;
        }
        GL20.glUniform2iv(GL20.glGetUniformLocation(program, uniformName), arr);
    }

    public void loadUniform(String uniformName, Vector3i value) {
        bind();
        GL20.glUniform3i(GL20.glGetUniformLocation(program, uniformName), value.x, value.y, value.z);
    }

    public void loadUniform(String uniformName, Vector3i[] values) {
        bind();
        int[] arr = new int[values.length * 3];
        for (int i = 0; i < values.length; i++) {
            arr[i * 3] = values[i].x;
            arr[i * 3 + 1] = values[i].y;
            arr[i * 3 + 2] = values[i].z;
        }
        GL20.glUniform3iv(GL20.glGetUniformLocation(program, uniformName), arr);
    }

    public void loadUniform(String uniformName, Vector4i value) {
        bind();
        GL20.glUniform4i(GL20.glGetUniformLocation(program, uniformName), value.x, value.y, value.z, value.w);
    }

    public void loadUniform(String uniformName, Vector4i[] values) {
        bind();
        int[] arr = new int[values.length * 4];
        for (int i = 0; i < values.length; i++) {
            arr[i * 4] = values[i].x;
            arr[i * 4 + 1] = values[i].y;
            arr[i * 4 + 2] = values[i].z;
            arr[i * 4 + 3] = values[i].w;
        }
        GL20.glUniform4iv(GL20.glGetUniformLocation(program, uniformName), arr);
    }

    public void loadUniform(String uniformName, boolean value) {
        loadUniform(uniformName, value ? 1 : 0);
    }

    public void loadUniform(String uniformName, boolean[] values) {
        int[] int_values = new int[values.length];
        for (int i = 0; i < values.length; i++) {
            int_values[i] = values[i] ? 1 : 0;
        }
        loadUniform(uniformName, int_values);
    }

    public void loadUniform(String uniformName, boolean x, boolean y) {
        loadUniform(uniformName, new Vector2i(x ? 1 : 0, y ? 1 : 0));
    }

    public void loadUniform(String uniformName, boolean x, boolean y, boolean z) {
        loadUniform(uniformName, new Vector3i(x ? 1 : 0, y ? 1 : 0, z ? 1 : 0));
    }

    public void loadUniform(String uniformName, boolean x, boolean y, boolean z, boolean w) {
        loadUniform(uniformName, new Vector4i(x ? 1 : 0, y ? 1 : 0, z ? 1 : 0, w ? 1 : 0));
    }

    public static int loadVertexShader(InputStream inputStream) throws IOException {
        String src = new String(inputStream.readAllBytes());
        int id = glCreateShader(GL_VERTEX_SHADER);
        glShaderSource(id, src);
        glCompileShader(id);
        if (glGetShaderi(id, GL_COMPILE_STATUS) == GL_FALSE) {
            System.err.println("Error: Vertex Shader - " + glGetShaderInfoLog(id));
        }
        return id;
    }

    public static int loadGeometryShader(InputStream inputStream) throws IOException {
        String src = new String(inputStream.readAllBytes());
        int id = glCreateShader(GL_GEOMETRY_SHADER);
        glShaderSource(id, src);
        glCompileShader(id);
        if (glGetShaderi(id, GL_COMPILE_STATUS) == GL_FALSE) {
            System.err.println("Error: Geometry Shader - " + glGetShaderInfoLog(id));
        }
        return id;
    }

    public static int loadFragmentShader(InputStream inputStream) throws IOException {
        String src = new String(inputStream.readAllBytes());
        int id = glCreateShader(GL_FRAGMENT_SHADER);
        glShaderSource(id, src);
        glCompileShader(id);
        if (glGetShaderi(id, GL_COMPILE_STATUS) == GL_FALSE) {
            System.err.println("Error: Fragment Shader - " + glGetShaderInfoLog(id));
        }
        return id;
    }

    public static int loadComputeShader(InputStream inputStream) throws IOException {
        String src = new String(inputStream.readAllBytes());
        int id = glCreateShader(GL_COMPUTE_SHADER);
        glShaderSource(id, src);
        glCompileShader(id);
        if (glGetShaderi(id, GL_COMPILE_STATUS) == GL_FALSE) {
            System.err.println("Error: Compute Shader - " + glGetShaderInfoLog(id));
        }
        return id;
    }

    public static void deleteShader(int shader) {
        if (shader != 0) glDeleteShader(shader);
    }
}
