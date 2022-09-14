package com.mike.simplejgl.rendering.shaders;

import com.mike.simplejgl.matricies.Matrix2f;
import com.mike.simplejgl.matricies.Matrix3f;
import com.mike.simplejgl.matricies.Matrix4f;
import com.mike.simplejgl.vectors.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
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

    public void loadUniform(String name, int i) {
        bind();
        glUniform1i(glGetUniformLocation(program, name), i);
    }

    public void loadUniform(String name, Vector2i v) {
        bind();
        glUniform2i(glGetUniformLocation(program, name), v.x, v.y);
    }

    public void loadUniform(String name, Vector3i v) {
        bind();
        glUniform3i(glGetUniformLocation(program, name), v.x, v.y, v.z);
    }

    public void loadUniform(String name, Vector4i v) {
        bind();
        glUniform4i(glGetUniformLocation(program, name), v.x, v.y, v.z, v.w);
    }

    public void loadUniform(String name, float f) {
        bind();
        glUniform1f(glGetUniformLocation(program, name), f);
    }

    public void loadUniform(String name, Vector2f v) {
        bind();
        glUniform2f(glGetUniformLocation(program, name), v.x, v.y);
    }

    public void loadUniform(String name, Vector3f v) {
        bind();
        glUniform3f(glGetUniformLocation(program, name), v.x, v.y, v.z);
    }

    public void loadUniform(String name, Vector4f v) {
        bind();
        glUniform4f(glGetUniformLocation(program, name), v.x, v.y, v.z, v.w);
    }

    public void loadUniform(String name, boolean transpose, Matrix2f m) {
        bind();
        glUniformMatrix2fv(glGetUniformLocation(program, name), transpose, m.get());
    }

    public void loadUniform(String name, boolean transpose, Matrix3f m) {
        bind();
        glUniformMatrix3fv(glGetUniformLocation(program, name), transpose, m.get());
    }

    public void loadUniform(String name, boolean transpose, Matrix4f m) {
        bind();
        glUniformMatrix4fv(glGetUniformLocation(program, name), transpose, m.get());
    }

    public void loadUniform(String name, boolean b) {
        loadUniform(name, b ? 1 : 0);
    }

    public void loadUniform(String name, int[] i) {
        bind();
        glUniform1iv(glGetUniformLocation(program, name), i);
    }

    public void loadUniform(String name, Vector2i[] v) {
        bind();
        int[] arr = new int[v.length * 2];
        for (int i = 0; i < v.length; i++) {
            arr[i * 2] = v[i].x;
            arr[i * 2 + 1] = v[i].y;
        }
        glUniform2iv(glGetUniformLocation(program, name), arr);
    }

    public void loadUniform(String name, Vector3i[] v) {
        bind();
        int[] arr = new int[v.length * 3];
        for (int i = 0; i < v.length; i++) {
            arr[i * 3] = v[i].x;
            arr[i * 3 + 1] = v[i].y;
            arr[i * 3 + 2] = v[i].z;
        }
        glUniform3iv(glGetUniformLocation(program, name), arr);
    }

    public void loadUniform(String name, Vector4i[] v) {
        bind();
        int[] arr = new int[v.length * 4];
        for (int i = 0; i < v.length; i++) {
            arr[i * 4] = v[i].x;
            arr[i * 4 + 1] = v[i].y;
            arr[i * 4 + 2] = v[i].z;
            arr[i * 4 + 3] = v[i].w;
        }
        glUniform4iv(glGetUniformLocation(program, name), arr);
    }

    public void loadUniform(String name, float[] f) {
        bind();
        glUniform1fv(glGetUniformLocation(program, name), f);
    }

    public void loadUniform(String name, Vector2f[] v) {
        bind();
        float[] arr = new float[v.length * 2];
        for (int i = 0; i < v.length; i++) {
            arr[i * 2] = v[i].x;
            arr[i * 2 + 1] = v[i].y;
        }
        glUniform2fv(glGetUniformLocation(program, name), arr);
    }

    public void loadUniform(String name, Vector3f[] v) {
        bind();
        float[] arr = new float[v.length * 3];
        for (int i = 0; i < v.length; i++) {
            arr[i * 3] = v[i].x;
            arr[i * 3 + 1] = v[i].y;
            arr[i * 3 + 2] = v[i].z;
        }
        glUniform3fv(glGetUniformLocation(program, name), arr);
    }

    public void loadUniform(String name, Vector4f[] v) {
        bind();
        float[] arr = new float[v.length * 4];
        for (int i = 0; i < v.length; i++) {
            arr[i * 4] = v[i].x;
            arr[i * 4 + 1] = v[i].y;
            arr[i * 4 + 2] = v[i].z;
            arr[i * 4 + 3] = v[i].w;
        }
        glUniform4fv(glGetUniformLocation(program, name), arr);
    }

    public void loadUniform(String name, boolean transpose, Matrix2f[] m) {
        bind();
        float[] arr = new float[m.length * 4];
        for (int i = 0; i < m.length; i++) {
            System.arraycopy(m[i].get(), 0, arr, i * 4, 4);
        }
        glUniformMatrix2fv(glGetUniformLocation(program, name), transpose, arr);
    }

    public void loadUniform(String name, boolean transpose, Matrix3f[] m) {
        bind();
        float[] arr = new float[m.length * 9];
        for (int i = 0; i < m.length; i++) {
            System.arraycopy(m[i].get(), 0, arr, i * 9, 9);
        }
        glUniformMatrix3fv(glGetUniformLocation(program, name), transpose, arr);
    }

    public void loadUniform(String name, boolean transpose, Matrix4f[] m) {
        bind();
        float[] arr = new float[m.length * 16];
        for (int i = 0; i < m.length; i++) {
            System.arraycopy(m[i].get(), 0, arr, i * 16, 16);
        }
        glUniformMatrix4fv(glGetUniformLocation(program, name), transpose, arr);
    }

    public void loadUniform(String name, boolean[] b) {
        int[] arr = new int[b.length];
        for (int i = 0; i < b.length; i++) {
            arr[i] = b[i] ? 1 : 0;
        }
        loadUniform(name, arr);
    }

    public static int loadVertexShader(File file) {
        try {
            if (file.exists()) {
                int id = glCreateShader(GL_VERTEX_SHADER);
                glShaderSource(id, Files.readString(Paths.get(file.toURI())));
                glCompileShader(id);
                if (glGetShaderi(id, GL_COMPILE_STATUS) == GL_FALSE) {
                    System.err.println("Error: Vertex Shader - " + glGetShaderInfoLog(id));
                }
                return id;
            }
        } catch (IOException ignored) {

        }
        return 0;
    }

    public static int loadGeometryShader(File file) {
        try {
            if (file.exists()) {
                int id = glCreateShader(GL_GEOMETRY_SHADER);
                glShaderSource(id, Files.readString(Paths.get(file.toURI())));
                glCompileShader(id);
                if (glGetShaderi(id, GL_COMPILE_STATUS) == GL_FALSE) {
                    System.err.println("Error: Geometry Shader - " + glGetShaderInfoLog(id));
                }
                return id;
            }
        } catch (IOException ignored) {

        }
        return 0;
    }

    public static int loadFragmentShader(File file) {
        try {
            if (file.exists()) {
                int id = glCreateShader(GL_FRAGMENT_SHADER);
                glShaderSource(id, Files.readString(Paths.get(file.toURI())));
                glCompileShader(id);
                if (glGetShaderi(id, GL_COMPILE_STATUS) == GL_FALSE) {
                    System.err.println("Error: Fragment Shader - " + glGetShaderInfoLog(id));
                }
                return id;
            }
        } catch (IOException ignored) {

        }
        return 0;
    }

    public static int loadComputeShader(File file) {
        try {
            if (file.exists()) {
                int id = glCreateShader(GL_COMPUTE_SHADER);
                glShaderSource(id, Files.readString(Paths.get(file.toURI())));
                glCompileShader(id);
                if (glGetShaderi(id, GL_COMPILE_STATUS) == GL_FALSE) {
                    System.err.println("Error: Compute Shader - " + glGetShaderInfoLog(id));
                }
                return id;
            }
        } catch (IOException ignored) {

        }
        return 0;
    }

    public static void deleteShader(int shader) {
        if (shader != 0) glDeleteShader(shader);
    }
}
