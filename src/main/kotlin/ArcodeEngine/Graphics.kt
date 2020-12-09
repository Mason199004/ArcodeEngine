package ArcodeEngine

import org.libsdl.SDL
import org.lwjgl.glfw.*
import org.lwjgl.glfw.GLFW.GLFW_KEY_ESCAPE
import org.lwjgl.glfw.GLFW.GLFW_RELEASE
import org.lwjgl.glfw.GLFW.GLFW_RESIZABLE
import org.lwjgl.glfw.GLFW.GLFW_VISIBLE
import org.lwjgl.glfw.GLFW.glfwDefaultWindowHints
import org.lwjgl.glfw.GLFW.glfwGetPrimaryMonitor
import org.lwjgl.glfw.GLFW.glfwGetVideoMode
import org.lwjgl.glfw.GLFW.glfwInit
import org.lwjgl.glfw.GLFW.glfwMakeContextCurrent
import org.lwjgl.glfw.GLFW.glfwSetKeyCallback
import org.lwjgl.glfw.GLFW.glfwSetWindowPos
import org.lwjgl.glfw.GLFW.glfwSetWindowShouldClose
import org.lwjgl.glfw.GLFW.glfwShowWindow
import org.lwjgl.glfw.GLFW.glfwSwapInterval
import org.lwjgl.glfw.GLFW.glfwWindowHint
import java.nio.IntBuffer
import org.lwjgl.glfw.Callbacks.*
import org.lwjgl.glfw.GLFW.*
import org.lwjgl.opengl.GL11.*

import org.lwjgl.system.MemoryUtil.*


 class Graphics
{
   //TODO: fix later
   /*
   companion object
   {
      var window: Long = NULL
      fun init() {


         GLFWErrorCallback.createPrint(System.err).set()

         // Initialize GLFW. Most GLFW functions will not work before doing this.

         // Initialize GLFW. Most GLFW functions will not work before doing this.
         check(glfwInit() == 1) { "Unable to initialize GLFW" }

         // Configure GLFW

         // Configure GLFW
         glfwDefaultWindowHints() // optional, the current window hints are already the default

         glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE) // the window will stay hidden after creation

         glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE) // the window will be resizable


         // Create the window

         // Create the window
         window = glfwCreateWindow(300, 300, "Hello World!", NULL, NULL)
         if (window === NULL) throw RuntimeException("Failed to create the GLFW window")

         // Setup a key callback. It will be called every time a key is pressed, repeated or released.

         // Setup a key callback. It will be called every time a key is pressed, repeated or released.
         glfwSetKeyCallback(
            window,
            GLFWKeyCallback({ window, key, scancode, action, mods ->
               if (key === GLFW_KEY_ESCAPE && action === GLFW_RELEASE) glfwSetWindowShouldClose(
                  window,
                  1
               ) // We will detect this in the rendering loop
            })
         )

         // Get the thread stack and push a new frame
         stackPush().use({ stack ->
            val pWidth: IntBuffer = stack.mallocInt(1) // int*
            val pHeight: IntBuffer = stack.mallocInt(1) // int*

            // Get the window size passed to glfwCreateWindow
            glfwGetWindowSize(window, pWidth, pHeight)

            // Get the resolution of the primary monitor
            val vidmode: GLFWVidMode = glfwGetVideoMode(glfwGetPrimaryMonitor())

            // Center the window
            glfwSetWindowPos(
               window,
               (vidmode.width() - pWidth[0]) / 2,
               (vidmode.height() - pHeight[0]) / 2
            )
         })

         // Make the OpenGL context current

         // Make the OpenGL context current
         glfwMakeContextCurrent(window)
         // Enable v-sync
         // Enable v-sync
         glfwSwapInterval(1)

         // Make the window visible

         // Make the window visible
         glfwShowWindow(window)

      }
   */
      fun drawRect(x: Int, y: Int, width: Int, height: Int): Boolean {
         return false
      }
}
