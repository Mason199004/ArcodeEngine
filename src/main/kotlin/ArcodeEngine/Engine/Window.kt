package ArcodeEngine.Engine

import org.lwjgl.glfw.*
import org.lwjgl.glfw.GLFW.*
import org.lwjgl.opengl.GL46.*
import org.lwjgl.system.MemoryUtil
import java.lang.IllegalStateException
import ArcodeEngine.Engine.Util.Input

class Window(var dimensions: Pair<Int, Int>, private val fullscreen: Boolean) {
    var resized: Boolean = false
    private var maxWidth = 50f * (dimensions.first.toFloat() / dimensions.second.toFloat())
    var maxHeight = 50f

    private var errorCallback: GLFWErrorCallback? = null
    private var keyCallback: GLFWKeyCallback? = null
    private var resizeCallback: GLFWFramebufferSizeCallback? = null

    private var window: Long = 0

    private var camera: Camera

    init {
        camera = Camera(
                ProjectionMode.ORTHOGRAPHIC,
                Pair(dimensions.first.toFloat(), dimensions.second.toFloat())
        )
        Init()
    }

    private fun Init() {

        // Setup an error callback. The default implementation
        // will print the error message in System.err.
        errorCallback = glfwSetErrorCallback(GLFWErrorCallback.createPrint(System.err))

        // Initialize  Most GLFW functions will not work before doing this.
        if ( !glfwInit()) {
            throw IllegalStateException("Unable to initialize GLFW")
        }

        // Configure our window
        glfwDefaultWindowHints()
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE)
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE)

        // Create the window
        if(fullscreen)
            window = glfwCreateWindow(1920, 1080, "Arcode Engine", glfwGetPrimaryMonitor(), MemoryUtil.NULL)
        else
            window = glfwCreateWindow(dimensions.first, dimensions.second, "Arcode Engine", MemoryUtil.NULL, MemoryUtil.NULL)
        if (window == MemoryUtil.NULL) {
            throw RuntimeException("Failed to create the GLFW window")
        }

        // Setup a key callback. It will be called every time a key is pressed, repeated or released.
        keyCallback = glfwSetKeyCallback(window, Input())

        resizeCallback = glfwSetFramebufferSizeCallback(window
        ) { _, width, height -> dimensions = Pair(width, height); resized = true }

        // Get the resolution of the primary monitor
        val vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor())

        // Center our window
        if (vidmode != null) {
            glfwSetWindowPos(
                    window,
                    (vidmode.width() - dimensions.first) / 2,
                    (vidmode.height() - dimensions.second) / 2
            )
        }

        // Make the OpenGL context current
        glfwMakeContextCurrent(window)

        // Enable v-sync (Change to 0/GLFW_FALSE to turn it off)
        glfwSwapInterval(GLFW_FALSE)

        // Make the window visible
        glfwShowWindow(window)
    }

    fun Destroy() {
        // Free the window callbacks and destroy the window
        Callbacks.glfwFreeCallbacks(window)
        glfwDestroyWindow(window)

        // Terminate GLFW and free the error callback
        glfwTerminate()
        glfwSetErrorCallback(null)?.free()
    }

    fun GetMaxWidth(): Float {
        return maxWidth
    }

    fun GetMaxHeight(): Float {
        return maxHeight
    }

    fun GetCamera(): Camera {
        return camera
    }

    fun SetCamera(camera: Camera) {
        this.camera = camera
    }

    fun GetWindowHandle(): Long {
        return window
    }

    fun ShouldClose(): Boolean {
        return glfwWindowShouldClose(window)
    }

    fun SetShouldClose(value: Boolean) {
        glfwSetWindowShouldClose(window, value)
    }

    fun SwapBuffers() {
        glfwSwapBuffers(window)
    }

    fun PollEvents() {
        glfwPollEvents()
    }

    fun Clear() {
        glClear(GL_COLOR_BUFFER_BIT or GL_DEPTH_BUFFER_BIT)
    }
}