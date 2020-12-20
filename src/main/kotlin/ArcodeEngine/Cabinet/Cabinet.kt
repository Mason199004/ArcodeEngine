package ArcodeEngine.Cabinet

import ArcodeEngine.Engine.*
import ArcodeEngine.Engine.GFX.Renderer
import ArcodeEngine.Engine.GFX.Shader.Shader
import ArcodeEngine.Engine.Geometry.Rectangle
import ArcodeEngine.Engine.Util.Direction
import org.joml.Vector3f
import org.lwjgl.glfw.GLFW
import org.lwjgl.opengl.GL
import org.lwjgl.opengl.GL30

// This is the main class responsible for browsing all the current games in the cabinet

// MASON PLS TEST THIS IN CLASS IDK IF IT ACTUALLY WORKS IT SHOULD JUST BE A RED SCREEN

class Cabinet(window: Window) : GameState("Arcade Cabinet", window) {

    lateinit var rectangle: Rectangle

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

        var gameLibrary: ArrayList<GameState> = ArrayList()

        @JvmStatic
        lateinit var wind: Window
        @JvmStatic
        fun GetWindow() : Long
        {
            return wind.GetWindowHandle()
        }

        fun AddGame(game: GameState) {
            gameLibrary.add(game);
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

        rectangle = Rectangle(50f, 50f, 2f, 2f)

        GL30.glClearColor(0f, 0f, 0f, 0f)
        StateManager.TickState()
    }

    var ticks = 0

    override fun Tick()
    {
        if (ticks % 30 == 0)
        {
            rectangle.Move(Direction.RIGHT, 1f)
        }
        ticks++
    }
    override fun Render() {
        GL30.glClear(GL30.GL_COLOR_BUFFER_BIT or GL30.GL_DEPTH_BUFFER_BIT)

        Renderer.DrawColoredRect(rectangle, Vector3f(1f, 0f, 1f))

        GLFW.glfwSwapBuffers(window.GetWindowHandle())

        GLFW.glfwPollEvents()
    }
}