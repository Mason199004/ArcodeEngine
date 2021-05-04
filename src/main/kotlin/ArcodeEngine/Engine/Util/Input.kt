package ArcodeEngine.Engine.Util

import org.lwjgl.glfw.GLFW
import org.lwjgl.glfw.GLFWKeyCallback

class Input: GLFWKeyCallback() {
    companion object {
        private val pressedKeys = BooleanArray(317)

        @JvmStatic
        fun IsPressed(key: Int): Boolean {
            return pressedKeys[key-32]
        }
    }

    override fun invoke(window: Long, key: Int, scancode: Int, action: Int, mods: Int) {
        if(key in 32..348) {
            pressedKeys[key-32] = (action != GLFW.GLFW_RELEASE)
        }
    }
}