package ArcodeEngine.Engine

import org.libsdl.SDL
import org.lwjgl.glfw.GLFWErrorCallback
import org.lwjgl.glfw.GLFWKeyCallback
import java.nio.IntBuffer
import org.lwjgl.glfw.Callbacks.*
import org.lwjgl.glfw.GLFW.*
import org.lwjgl.glfw.GLFWVidMode
import org.lwjgl.opengl.GL11.*

import org.lwjgl.system.MemoryUtil.*
import java.lang.IllegalStateException


public class Graphics(private var windowSize: Pair<Int, Int>) {
   private var errorCallback : GLFWErrorCallback? = null
   private var keyCallback : GLFWKeyCallback? = null

   private var window : Long = 0

   private fun init() {

      // Setup an error callback. The default implementation
      // will print the error message in System.err.
      errorCallback = glfwSetErrorCallback(GLFWErrorCallback.createPrint(System.err))

      // Initialize GLFW. Most GLFW functions will not work before doing this.
      if ( !glfwInit() ) {
         throw IllegalStateException("Unable to initialize GLFW")
      }

      // Configure our window
      glfwDefaultWindowHints()
      glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE)
      glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE)

      // Create the window
      window = glfwCreateWindow(windowSize.first, windowSize.second, "Hello World!", NULL, NULL)
      if (window == NULL) {
         throw RuntimeException("Failed to create the GLFW window")
      }

      // Setup a key callback. It will be called every time a key is pressed, repeated or released.
      keyCallback = glfwSetKeyCallback(window, object : GLFWKeyCallback() {
         override fun invoke(window: Long,
                             key: Int,
                             scancode: Int,
                             action: Int,
                             mods: Int) {

            if (key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE ) {
               glfwSetWindowShouldClose(window, true)
            }

         }
      })

      // Get the resolution of the primary monitor
      val vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor())

      // Center our window
      if (vidmode != null) {
         glfwSetWindowPos(
            window,
            (vidmode.width() - windowSize.first) / 2,
            (vidmode.height() - windowSize.second) / 2
         )
      };

      // Make the OpenGL context current
      glfwMakeContextCurrent(window)

      // Enable v-sync (Change to 0/GLFW_FALSE to turn it off
      glfwSwapInterval(GLFW_TRUE)

      // Make the window visible
      glfwShowWindow(window)
   }
}