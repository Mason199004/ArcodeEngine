package ArcodeEngine.Engine

import ArcodeEngine.Engine.GFX.Loader
import ArcodeEngine.Engine.Util.OpenGL
import org.lwjgl.glfw.GLFW
import org.lwjgl.opengl.GL46.*
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

                Render(currentState::Render)
                frames++
                if (System.currentTimeMillis() - timer > 1000) {
                    timer += 1000
                    println("\u001b[1;34mFPS: \u001b[1;32m$frames\u001b[0;0m")
                    frames = 0
                }

                if(GLFW.glfwWindowShouldClose(currentState.window.GetWindowHandle())) {
                    currentState.window.Destroy()
                    Loader.CleanUp()
                    ArcodeEngine.ColoredShaderRGB.CleanUp()
                    ArcodeEngine.ColoredShaderRGBA.CleanUp()
                    ArcodeEngine.TexturedShader.CleanUp()
                    ArcodeEngine.GlyphShader.CleanUp()
                    exitProcess(0)
                }
            }
        }

        private fun Render(render: () -> Unit) {
            OpenGL.GLClear(GL_COLOR_BUFFER_BIT or GL_DEPTH_BUFFER_BIT)
            render()
            GLFW.glfwSwapBuffers(currentState.window.GetWindowHandle())
            GLFW.glfwPollEvents()
        }

        @JvmStatic
        fun PushState(state: GameState) {
            states.push(state)
            currentState = state
            currentState.Init()
        }

        @JvmStatic
        fun PopState() {
            states.pop()
            currentState = states.peek()
        }
    }
}