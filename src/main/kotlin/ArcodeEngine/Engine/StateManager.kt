package ArcodeEngine.Engine

import org.lwjgl.glfw.GLFW
import java.util.*
import kotlin.system.exitProcess

class StateManager {
    companion object {
        private val states: Stack<GameState> = Stack()
        private lateinit var currentState: GameState

        fun tickState() {
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
                    currentState.tick()
                    delta--
                }
                currentState.tick()
                currentState.render()
                frames++
                if (System.currentTimeMillis() - timer > 1000) {
                    timer += 1000
                    println("FPS: $frames, Joystick2_State: ${Controller.getJoystickState(1)}")
                    frames = 0
                }

                if(GLFW.glfwWindowShouldClose(currentState.window.getWindowHandle())) {
                    currentState.window.destroy()
                    exitProcess(0)
                }
            }
        }

        fun pushState(state: GameState) {
            states.push(state)
            currentState = state
            currentState.init()
        }

        fun popState(): GameState {
            return states.pop()
        }
    }
}