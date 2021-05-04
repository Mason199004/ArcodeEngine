package ArcodeEngine.Engine

import ArcodeEngine.Engine.GFX.Loader
import ArcodeEngine.Engine.GFX.Shader.Shader
import ArcodeEngine.Engine.Util.Input
import ArcodeEngine.Engine.Util.OpenGL
import org.lwjgl.glfw.GLFW
import org.lwjgl.opengl.GL46.*
import java.util.*
import kotlin.system.exitProcess

class StateManager {
    companion object {
        private val states: Stack<GameState> = Stack()
        private lateinit var currentState: GameState

        private var lastTime = 0f

        fun Start() {
            while(true) {
                val time = GLFW.glfwGetTime().toFloat()
                val deltaTime = time - lastTime
                lastTime = time

                currentState.window.Clear()
                currentState.Update(deltaTime)
                currentState.TryExit()

                currentState.window.SwapBuffers()
                currentState.window.PollEvents()

                if(currentState.window.ShouldClose()) {
                    CleanUp()
                    exitProcess(0)
                }
            }
        }

        private fun CleanUp() {
            currentState.window.Destroy()
            Loader.CleanUp()
            ArcodeEngine.ColoredShaderRGB.CleanUp()
            ArcodeEngine.ColoredShaderRGBA.CleanUp()
            ArcodeEngine.GlyphShader.CleanUp()
            ArcodeEngine.TexturedShader.CleanUp()
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

        fun GetSize(): Int {
            return states.size
        }
    }
}