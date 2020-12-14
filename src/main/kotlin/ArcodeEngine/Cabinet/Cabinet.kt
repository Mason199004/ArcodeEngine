package ArcodeEngine.Cabinet

import ArcodeEngine.Engine.*
import org.lwjgl.glfw.GLFW
import org.lwjgl.opengl.GL
import org.lwjgl.opengl.GL11.*

// This is the main class responsible for browsing all the current games in the cabinet

// MASON PLS TEST THIS IN CLASS IDK IF IT ACTUALLY WORKS IT SHOULD JUST BE A RED SCREEN

class Cabinet(private var window: Window) : GameState("Arcade Cabinet") {




    init {

        ArcodeEngine.submitStateChangeRequest(ArcodeEngine.StateRequest.PUSH, this)

    }

    companion object {
        val TEX1 = ArcodeEngine.registerAsset("tex1")
        val TEX2 = ArcodeEngine.registerAsset("tex2")
        val TEX3 = ArcodeEngine.registerAsset("tex3")
        val TEX4 = ArcodeEngine.registerAsset("tex4")
        val TEX5 = ArcodeEngine.registerAsset("tex5")
        val TEX6 = ArcodeEngine.registerAsset("tex6")

        @JvmStatic
        lateinit var wind: Window
        @JvmStatic
        fun getWindow() : Long
        {
            return wind.getWindowHandle()
        }

        @JvmStatic
        fun main(args: Array<String>) {
            wind = Window(Pair(720, 480))
            Cabinet(wind)
        }
    }

    override fun init() {
        GL.createCapabilities();

        glClearColor(1f, 0f, 0f, 0f)
        StateManager.tickState()
    }
    var xpos = 1f
    var ypos = 1f
    var r1xpos = 1f
    var r1ypos = 1f
    var r2xpos = 1f
    var r2ypos = 1f
    var rectxspeed = 0f
    var rectyspeed = 0f
    var ticks = 0
    override fun tick()
    {
        if (ticks == 1)
        {
            r1xpos = -0.9f
            r2xpos = 0.9f
            rectxspeed = -1f
            rectyspeed = -0.5f
            println(TEX1)
            println(TEX2)
            println(TEX3)
            println(TEX4)
            println(TEX5)
            println(TEX6)
        }
        if (ticks % 10 == 0)
        {
            val joyState1 = Controller.getJoystickState(1)
            val joyState2 = Controller.getJoystickState(2)
            //r1xpos -= joyState.getX() * 0.025f
            r1ypos += joyState1.getY() * 0.025f
            r2ypos += joyState2.getY() * 0.025f
        }
        xpos += (rectxspeed * 0.0025f)
        ypos += (rectyspeed * 0.0025f)
        ticks++
    }
    override fun render() {
        glClear(GL_COLOR_BUFFER_BIT or GL_DEPTH_BUFFER_BIT)
        glColor3f(0.0f, 0.0f, 0.0f)
        glBegin(GL_QUADS)

        //rect1
        glVertex2f(-0.025f + r1xpos, 0.2f + r1ypos)
        glVertex2f(-0.025f + r1xpos, -0.2f + r1ypos)
        glVertex2f(0.025f + r1xpos, -0.2f + r1ypos)
        glVertex2f(0.025f + r1xpos, 0.2f + r1ypos)

        //rect2
        glVertex2f(-0.025f + r2xpos, 0.2f + r2ypos)
        glVertex2f(-0.025f + r2xpos, -0.2f + r2ypos)
        glVertex2f(0.025f + r2xpos, -0.2f + r2ypos)
        glVertex2f(0.025f + r2xpos, 0.2f + r2ypos)

        //ball
        glVertex2f(-0.025f + xpos, 0.025f + ypos)
        glVertex2f(-0.025f + xpos, -0.025f + ypos)
        glVertex2f(0.025f + xpos, -0.025f + ypos)
        glVertex2f(0.025f + xpos, 0.025f + ypos)

        glEnd()
        GLFW.glfwSwapBuffers(window.getWindowHandle())

        GLFW.glfwPollEvents()
    }
}