package ArcodeEngine.Engine

import ArcodeEngine.Engine.GFX.Loader
import org.lwjgl.glfw.GLFW
import org.lwjgl.opengl.GL30
import java.util.*
import kotlin.system.exitProcess

class StateManager {
    companion object {
        private val states: Stack<GameState> = Stack()
        private lateinit var currentState: GameState

        fun TickState() {
            var lastTime = System.nanoTime()
            val amountOfTicks = 60.0
            val ns = 1000000000 / amountOfTicks
            var delta = 0.0
            var frames = 0
            var timer = System.currentTimeMillis()
            while (true) {
                val now = System.nanoTime()
                delta += (now - lastTime) / ns
                lastTime = now
                while (delta >= 1) {
                    currentState.Tick()
                    if(currentState.window.resized)
                        GL30.glViewport(0, 0, currentState.window.dimensions.first, currentState.window.dimensions.second)
                    delta--
                }
                currentState.Tick()
                currentState.Render()
                frames++
                if (System.currentTimeMillis() - timer > 1000) {
                    timer += 1000
                    println("FPS: $frames, Joystick2_State: ${Controller.GetJoystickState(1)}")
                    frames = 0
                }

                if(GLFW.glfwWindowShouldClose(currentState.window.GetWindowHandle())) {
                    currentState.window.destroy()
                    Loader.CleanUp()
                    ArcodeEngine.ColoredShader.cleanUp()
                    ArcodeEngine.TexturedShader.cleanUp()
                    exitProcess(0)
                }
            }
        }

        fun pushState(state: GameState) {
            states.push(state)
            currentState = state
            currentState.Init()
        }

        fun popState(): GameState {
            return states.pop()
        }
    }
}