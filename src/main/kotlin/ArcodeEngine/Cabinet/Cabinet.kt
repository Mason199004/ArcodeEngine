package ArcodeEngine.Cabinet

import ArcodeEngine.Engine.ArcodeEngine
import ArcodeEngine.Engine.GameState
import ArcodeEngine.Engine.StateManager
import ArcodeEngine.Engine.Window
import org.lwjgl.glfw.GLFW
import org.lwjgl.opengl.GL
import org.lwjgl.opengl.GL11.*

// This is the main class responsible for browsing all the current games in the cabinet

// MASON PLS TEST THIS IN CLASS IDK IF IT ACTUALLY WORKS IT SHOULD JUST BE A RED SCREEN

class Cabinet : GameState("Arcade Cabinet") {
    private var window: Window

    init {
        window = Window(Pair(720, 480))
        ArcodeEngine.submitStateChangeRequest(ArcodeEngine.StateRequest.PUSH, this)

    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            Cabinet()
        }
    }

    override fun init() {
        GL.createCapabilities();

        glClearColor(1f, 0f, 0f, 0f)
        StateManager.tickState()
    }

    override fun tick() {}
    override fun render() {
        glClear(GL_COLOR_BUFFER_BIT or GL_DEPTH_BUFFER_BIT)
        GLFW.glfwSwapBuffers(window.getWindowHandle())

        GLFW.glfwPollEvents()
    }
}