package ArcodeEngine.Engine

import ArcodeEngine.Cabinet.Cabinet
import org.lwjgl.glfw.GLFW
import org.lwjgl.glfw.GLFWJoystickCallback
import java.lang.Exception
import java.lang.IllegalArgumentException
import kotlin.reflect.KProperty

class Controller
{
    companion object
    {
        private fun getJoystickAxes(joy: Int) : FloatArray
        {
            val arr = FloatArray(2)
            //Joystick1 is reserved or something, always -1
            when (joy)
            {
                1 ->
                {
                    if(GLFW.glfwJoystickPresent(GLFW.GLFW_JOYSTICK_2))
                        arr[0] = GLFW.glfwGetJoystickAxes(GLFW.GLFW_JOYSTICK_2)!![0]
                        arr[1] = GLFW.glfwGetJoystickAxes(GLFW.GLFW_JOYSTICK_2)!![1]
                }
                2 ->
                {
                    if(GLFW.glfwJoystickPresent(GLFW.GLFW_JOYSTICK_3))
                        arr[0] = GLFW.glfwGetJoystickAxes(GLFW.GLFW_JOYSTICK_3)!![0]
                        arr[1] = GLFW.glfwGetJoystickAxes(GLFW.GLFW_JOYSTICK_3)!![1]
                }
            }
            return arr
        }

        enum class JoyState(val xAxis: Int,val yAxis: Int)
        {

            Up(0,1),
            UpRight(-1, 1),
            Right(-1, 0),
            DownRight(-1, -1),
            Down(0, -1),
            DownLeft(1, -1),
            Left(1, 0),
            UpLeft(1, 1),
            None(0,0);
            fun getX(): Int
            {
                return xAxis
            }
            fun getY(): Int
            {
                return yAxis
            }
        }

        private fun getJoyState(Joystick: Int) : JoyState
        {
            var raw = FloatArray(2)
            if (Joystick == 1)
            {
                if (GLFW.glfwJoystickPresent(GLFW.GLFW_JOYSTICK_2))
                {
                    val buf = GLFW.glfwGetJoystickAxes(GLFW.GLFW_JOYSTICK_2)
                    raw[0] = buf?.get(0)!!
                    raw[1] = buf[1]
                }
            }
            else
            {
                if (GLFW.glfwJoystickPresent(GLFW.GLFW_JOYSTICK_3))
                {
                    val buf = GLFW.glfwGetJoystickAxes(GLFW.GLFW_JOYSTICK_3)
                    raw[0] = buf?.get(0)!!
                    raw[1] = buf[1]
                }
            }
            return getJoyStateFromAxis(raw[0].toInt(), raw[1].toInt())
        }

        private fun getJoyStateFromAxis(xAxis: Int, yAxis: Int) : JoyState
        {

            val rawJoyState: Array<Float> = arrayOf(xAxis.toFloat(), yAxis.toFloat())
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
                        -1 -> return JoyState.Down
                        0 -> return JoyState.None
                        1 -> return JoyState.Up
                    }
                }
                1 ->
                {
                    when (rawJoyState[1].toInt())
                    {
                        -1 -> return JoyState.DownLeft
                        0 -> return JoyState.Left
                        1 -> return JoyState.UpLeft
                    }
                }
            }
            throw RuntimeException("Joystick returned an invalid state!")
        }

        fun getJoystickState(Joystick: Int) : JoyState
        {
            val state = getJoyState(Joystick)
            if (state == JoyState.None)
            {
                when (Joystick)
                {
                    1 ->
                    {
                        val w = GLFW.glfwGetKey(Cabinet.getWindow(), GLFW.GLFW_KEY_W)
                        val a = GLFW.glfwGetKey(Cabinet.getWindow(), GLFW.GLFW_KEY_A)
                        val s = GLFW.glfwGetKey(Cabinet.getWindow(), GLFW.GLFW_KEY_S)
                        val d = GLFW.glfwGetKey(Cabinet.getWindow(), GLFW.GLFW_KEY_D)
                        var xAxis = 0
                        var yAxis = 0
                        if (w == GLFW.GLFW_PRESS)
                        {
                            yAxis++
                        }
                        if (s == GLFW.GLFW_PRESS)
                        {
                            yAxis--
                        }
                        if (a == GLFW.GLFW_PRESS)
                        {
                            xAxis++
                        }
                        if (d == GLFW.GLFW_PRESS)
                        {
                            xAxis--
                        }
                        return getJoyStateFromAxis(xAxis, yAxis)
                    }
                    2 ->
                    {
                        val w = GLFW.glfwGetKey(Cabinet.getWindow(), GLFW.GLFW_KEY_UP)
                        val a = GLFW.glfwGetKey(Cabinet.getWindow(), GLFW.GLFW_KEY_LEFT)
                        val s = GLFW.glfwGetKey(Cabinet.getWindow(), GLFW.GLFW_KEY_DOWN)
                        val d = GLFW.glfwGetKey(Cabinet.getWindow(), GLFW.GLFW_KEY_RIGHT)
                        var xAxis = 0
                        var yAxis = 0
                        if (w == GLFW.GLFW_PRESS)
                        {
                            yAxis++
                        }
                        if (s == GLFW.GLFW_PRESS)
                        {
                            yAxis--
                        }
                        if (a == GLFW.GLFW_PRESS)
                        {
                            xAxis++
                        }
                        if (d == GLFW.GLFW_PRESS)
                        {
                            xAxis--
                        }
                        return getJoyStateFromAxis(xAxis, yAxis)
                    }
                }
            }
            return state
        }
    }
}