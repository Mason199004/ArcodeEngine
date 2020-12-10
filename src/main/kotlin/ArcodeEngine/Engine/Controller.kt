package ArcodeEngine.Engine

import org.lwjgl.glfw.GLFW

class Controller
{
    companion object
    {
        fun getJoystickAxes() : FloatArray
        {
            var arr = FloatArray(2)
            if(GLFW.glfwJoystickPresent(GLFW.GLFW_JOYSTICK_1))
                arr[0] = GLFW.glfwGetJoystickAxes(GLFW.GLFW_JOYSTICK_1)!![0]
                arr[1] = GLFW.glfwGetJoystickAxes(GLFW.GLFW_JOYSTICK_1)!![1]

            return arr
        }
    }
}