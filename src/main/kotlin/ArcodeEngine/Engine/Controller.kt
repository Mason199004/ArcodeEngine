package ArcodeEngine.Engine

import org.lwjgl.glfw.GLFW
import org.lwjgl.glfw.GLFWJoystickCallback

class Controller
{
    companion object
    {
        private fun getJoystickAxes() : FloatArray
        {
            val arr = FloatArray(2)
            //Joystick1 is reserved or something, always -1
            if(GLFW.glfwJoystickPresent(GLFW.GLFW_JOYSTICK_2))
                arr[0] = GLFW.glfwGetJoystickAxes(GLFW.GLFW_JOYSTICK_2)!![0]
                arr[1] = GLFW.glfwGetJoystickAxes(GLFW.GLFW_JOYSTICK_2)!![1]

            return arr
        }

        enum class JoyState(xAxis: Int, yAxis: Int)
        {
            Up(0,1),
            UpRight(-1, 1),
            Right(-1, 0),
            DownRight(-1, -1),
            Down(0, -1),
            DownLeft(1, -1),
            Left(1, 0),
            UpLeft(1, 1)

        }
        fun getJoystickState(Joystick: Int = 1) : JoyState
        {
            if (Joystick != 1 || Joystick != 2)
            {
                throw IllegalArgumentException("There are only 2 Joysticks connected!\nAttempted to access Joystick $Joystick")
            }
            val rawJoyState = getJoystickAxes()
            when (rawJoyState[0].toInt())
            {
                -1 ->
                {
                    when (rawJoyState[1].toInt())
                    {
                        -1 -> return JoyState.DownRight
                        0 -> return JoyState.Right
                        1 -> return JoyState.UpRight
                    }
                }
                0 ->
                {
                    when (rawJoyState[1].toInt())
                    {
                        -1 -> return
                    }
                }
            }
        }
    }
}