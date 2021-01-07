package ArcodeEngine.Engine

import ArcodeEngine.Engine.GFX.Loader
import ArcodeEngine.Engine.Util.OpenGL
import org.lwjgl.glfw.GLFW
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
                        OpenGL.GLViewport(0, 0, currentState.window.dimensions.first, currentState.window.dimensions.second)
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
                    currentState.window.Destroy()
                    Loader.CleanUp()
                    ArcodeEngine.ColoredShader.CleanUp()
                    ArcodeEngine.TexturedShader.CleanUp()
                    exitProcess(0)
                }
            }
        }

        fun PushState(state: GameState) {
            states.push(state)
            currentState = state
            currentState.Init()
        }

        fun PopState(): GameState {
            return states.pop()
        }
    }
}