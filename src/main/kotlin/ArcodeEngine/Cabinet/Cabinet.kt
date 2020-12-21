package ArcodeEngine.Cabinet

import ArcodeEngine.Engine.*
import ArcodeEngine.Engine.GFX.Renderer
import ArcodeEngine.Engine.GFX.Shader.Shader
import ArcodeEngine.Engine.Geometry.Rectangle
import ArcodeEngine.Engine.Util.Direction
import ArcodeEngine.Engine.Util.OpenGL
import org.joml.Vector3f
import org.lwjgl.glfw.GLFW
import org.lwjgl.opengl.GL
import org.lwjgl.opengl.GL30

// This is the main class responsible for browsing all the current games in the cabinet

// MASON PLS TEST THIS IN CLASS IDK IF IT ACTUALLY WORKS IT SHOULD JUST BE A RED SCREEN

class Cabinet(window: Window) : GameState("Arcade Cabinet", window) {

    lateinit var rectangle: Rectangle

    var missingTexture: Int

    init {
        ArcodeEngine.SubmitStateChangeRequest(ArcodeEngine.StateRequest.PUSH, this)

        missingTexture = ArcodeEngine.RegisterTexture("src/main/kotlin/ArcodeEngine/Cabinet/res/missing.png")
    }

    companion object {
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
        OpenGL.CreateCapabilities();

        ArcodeEngine.ColoredShader = Shader("src/main/kotlin/ArcodeEngine/Engine/GFX/Shader/coloredVert.glsl", "src/main/kotlin/ArcodeEngine/Engine/GFX/Shader/coloredFrag.glsl")
        ArcodeEngine.TexturedShader = Shader("src/main/kotlin/ArcodeEngine/Engine/GFX/Shader/texturedVert.glsl", "src/main/kotlin/ArcodeEngine/Engine/GFX/Shader/texturedFrag.glsl")

        rectangle = Rectangle(0f, 0f, 2f, 2f)

        OpenGL.GLClearColor(0f, 0f, 0f, 0f)
        StateManager.TickState()
    }

    var ticks = 0

    override fun Tick()
    {
        if (ticks % 30 == 0)
        {
            //rectangle.Move(Direction.RIGHT, 1f)
            //rectangle.Move(Direction.UP, 1f)
        }
        ticks++
    }
    override fun Render() {
        OpenGL.GLClear(GL30.GL_COLOR_BUFFER_BIT or GL30.GL_DEPTH_BUFFER_BIT)

        Renderer.DrawColoredRect(rectangle, Vector3f(0f, 1f, 1f))
        //Renderer.DrawTexturedRect(rectangle, missingTexture)

        GLFW.glfwSwapBuffers(window.GetWindowHandle())

        GLFW.glfwPollEvents()
    }
}