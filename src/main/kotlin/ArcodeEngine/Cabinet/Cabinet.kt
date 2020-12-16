package ArcodeEngine.Cabinet

import ArcodeEngine.Engine.*
import ArcodeEngine.Engine.GFX.Renderer
import ArcodeEngine.Engine.GFX.Shader.Shader
import org.lwjgl.glfw.GLFW
import org.lwjgl.opengl.GL
import org.lwjgl.opengl.GL11.*

// This is the main class responsible for browsing all the current games in the cabinet

// MASON PLS TEST THIS IN CLASS IDK IF IT ACTUALLY WORKS IT SHOULD JUST BE A RED SCREEN

class Cabinet(window: Window) : GameState("Arcade Cabinet", window) {

    init {

        ArcodeEngine.SubmitStateChangeRequest(ArcodeEngine.StateRequest.PUSH, this)

    }

    companion object {
        val TEX1 = ArcodeEngine.RegisterAsset("tex1")
        val TEX2 = ArcodeEngine.RegisterAsset("tex2")
        val TEX3 = ArcodeEngine.RegisterAsset("tex3")
        val TEX4 = ArcodeEngine.RegisterAsset("tex4")
        val TEX5 = ArcodeEngine.RegisterAsset("tex5")
        val TEX6 = ArcodeEngine.RegisterAsset("tex6")

        @JvmStatic
        lateinit var wind: Window
        @JvmStatic
        fun getWindow() : Long
        {
            return wind.GetWindowHandle()
        }

        @JvmStatic
        fun main(args: Array<String>) {
            wind = Window(Pair(720, 480))
            Cabinet(wind)
        }
    }

    override fun Init() {
        GL.createCapabilities();

        ArcodeEngine.ColoredShader = Shader("src/main/kotlin/ArcodeEngine/Engine/GFX/Shader/coloredVert.glsl", "src/main/kotlin/ArcodeEngine/Engine/GFX/Shader/coloredFrag.glsl")
        ArcodeEngine.TexturedShader = Shader("src/main/kotlin/ArcodeEngine/Engine/GFX/Shader/texturedVert.glsl", "src/main/kotlin/ArcodeEngine/Engine/GFX/Shader/texturedFrag.glsl")

        glClearColor(1f, 0f, 0f, 0f)
        StateManager.TickState()
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
    override fun Tick()
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
            val joyState1 = Controller.GetJoystickState(1)
            val joyState2 = Controller.GetJoystickState(2)
            //r1xpos -= joyState.getX() * 0.025f
            r1ypos += joyState1.GetY() * 0.025f
            r2ypos += joyState2.GetY() * 0.025f
        }
        xpos += (rectxspeed * 0.0025f)
        ypos += (rectyspeed * 0.0025f)
        ticks++
    }
    override fun Render() {
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
        GLFW.glfwSwapBuffers(window.GetWindowHandle())

        GLFW.glfwPollEvents()
    }
}