package com.mike.simplejgl;

public interface InputListener {
    /**
     * This function is called, when a key changes its state.
     * @param key represents a glfw key code.
     * @param action represents a glfw key action (GLFW_RELEASE, GLFW_PRESS, GLFW_REPEAT).
     * @param mods represents a glfw key modifiers (GLFW_MOD_SHIFT, GLFW_MOD_CONTROL, GLFW_MOD_ALT, GLFW_MOD_SUPER, GLFW_MOD_CAPS_LOCK, GLFW_MOD_NUM_LOCK).
     */
    void keyEvent(int key, int action, int mods);

    /**
     * This function is called, when a mouse button changes its state.
     * @param x represents x coordinate of a cursor on the window.
     * @param y represents y coordinate of a cursor on the window.
     * @param button represents a glfw mouse button code.
     * @param action represents a glfw mouse button action (GLFW_RELEASE, GLFW_PRESS, GLFW_REPEAT).
     * @param mods represents a glfw mouse button modifiers (GLFW_MOD_SHIFT, GLFW_MOD_CONTROL, GLFW_MOD_ALT, GLFW_MOD_SUPER, GLFW_MOD_CAPS_LOCK, GLFW_MOD_NUM_LOCK).
     */
    void mouseButtonEvent(double x, double y, int button, int action, int mods);

    /**
     * This function is called, while scrolling a mouse wheel.
     * @param xScroll represents a scroll along an x-axis.
     * @param yScroll represents a scroll along a y-axis.
     */
    void mouseScrollEvent(double xScroll, double yScroll);

    /**
     * This function is called while moving a mouse.
     * @param x represents x coordinate of a cursor.
     * @param y represents y coordinate of a cursor.
     * @param dx represents a distance traveled by a cursor along x-axis.
     * @param dy represents a distance traveled by a cursor along y-axis.
     */
    void mouseMoveEvent(double x, double y, double dx, double dy);
}
