package ArcodeEngine.Engine

import ArcodeEngine.Engine.*
import ArcodeEngine.Engine.Util.Input
import org.lwjgl.glfw.GLFW

abstract class GameState(val name: String, var window: Window) {
    /**
     * Called when state is first pushed to the StateManager.
     * All initialization code should be called here.
     */
    abstract fun Init()

    /**
     * Handles all the rendering logic for a GameState.
     * NO LOGIC SHOULD BE DONE IN THIS FUNCTION.
     */
    abstract fun Update(dt: Float)

    /**
     * This code handles the exiting process of a GameState.
     * This should be called at the VERY BEGINNING of every GameState's Tick function.
     */
    fun TryExit() {
        var released = false
        while(Input.IsPressed(GLFW.GLFW_KEY_ESCAPE)) {
            window.PollEvents()
            released = !Input.IsPressed(GLFW.GLFW_KEY_ESCAPE)
        }

        if(released) {
            if(StateManager.GetSize() == 1) {
                window.SetShouldClose(true)
            }
            else {
                StateManager.PopState()
            }
        }
    }
}