package ArcodeEngine.Engine

import ArcodeEngine.Engine.*
import org.lwjgl.glfw.GLFW

abstract class GameState(val name: String, var window: Window) {
    /**
     * Called when state is first pushed to the StateManager.
     * All initialization code should be called here.
     */
    abstract fun Init()

    /**
     * Handles all the logic for the GameState.
     * NO RENDERING SHOULD BE DONE IN THIS FUNCTION.
     */
    abstract fun Tick()

    /**
     * Handles all the rendering logic for a GameState.
     * NO LOGIC SHOULD BE DONE IN THIS FUNCTION.
     */
    abstract fun Render()

    /**
     * This code handles the exiting process of a GameState.
     * This should be called at the VERY BEGINNING of every GameState's Tick function.
     */
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