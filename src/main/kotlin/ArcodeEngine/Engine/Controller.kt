package ArcodeEngine.Engine

import org.lwjgl.glfw.GLFW

class Controller
{
    companion object
    {
        fun getJoystickAxes() : FloatArray
        {
            if(GLFW.glfwJoystickPresent(GLFW.GLFW_JOYSTICK_1))
                return GLFW.glfwGetJoystickAxes(GLFW.GLFW_JOYSTICK_1)!!.array()

            return FloatArray(2)
        }
    }
}