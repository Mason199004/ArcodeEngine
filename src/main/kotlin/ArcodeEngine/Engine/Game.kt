package ArcodeEngine.Engine

import ArcodeEngine.Engine.*
import org.lwjgl.glfw.GLFW

abstract class GameState(val name: String, var window: Window) {
    abstract fun Init()
    abstract fun Tick()
    abstract fun Render()

    fun TryExit() {
        var esc = GLFW.glfwGetKey(window.GetWindowHandle(), GLFW.GLFW_KEY_ESCAPE)
        if (esc == GLFW.GLFW_PRESS) {
            while (esc != GLFW.GLFW_RELEASE) {
                GLFW.glfwPollEvents()
                esc = GLFW.glfwGetKey(window.GetWindowHandle(), GLFW.GLFW_KEY_ESCAPE)
            }
            StateManager.PopState()
            return
        }
    }
}