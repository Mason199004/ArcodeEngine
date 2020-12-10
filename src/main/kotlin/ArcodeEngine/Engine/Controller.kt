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
                GLFW.glfwGetJoystickAxes(GLFW.GLFW_JOYSTICK_1)!!.get(arr)

            return arr
        }
    }
}