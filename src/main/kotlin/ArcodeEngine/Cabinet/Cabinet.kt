package ArcodeEngine.Cabinet

import ArcodeEngine.Engine.*
import ArcodeEngine.Engine.GFX.Renderer
import ArcodeEngine.Engine.GFX.Shader.Shader
import ArcodeEngine.Engine.Geometry.Rectangle
import ArcodeEngine.Engine.Util.Direction
import ArcodeEngine.Engine.Util.OpenGL
import org.joml.Vector2f
import org.joml.Vector3f
import org.lwjgl.glfw.GLFW
import org.lwjgl.opengl.GL30

// This is the main class responsible for browsing all the current games in the cabinet

// MASON PLS TEST THIS IN CLASS IDK IF IT ACTUALLY WORKS IT SHOULD JUST BE A RED SCREEN

class Cabinet(window: Window) : GameState("Arcade Cabinet", window) {

    lateinit var leftPaddle: Rectangle
    lateinit var rightPaddle: Rectangle
    lateinit var ball: Rectangle

    private val ballSpeed: Float = 0.2f;
    private var ballVelocity: Vector2f = Vector2f(0.1f, 0f)

    var missingTexture: Int = 0

    init {
        ArcodeEngine.SubmitStateChangeRequest(ArcodeEngine.StateRequest.PUSH, this)
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

        missingTexture = ArcodeEngine.RegisterTexture("src/main/kotlin/ArcodeEngine/Cabinet/res/missing.png")

        leftPaddle = Rectangle(1f, 10f, 2f, 7f)
        rightPaddle = Rectangle(window.maxWidth - 3, 10f, 2f, 7f)
        ball = Rectangle(window.maxWidth / 2, window.maxHeight / 2, 2f, 2f)

        OpenGL.GLClearColor(0f, 0f, 0f, 0f)
        StateManager.TickState()
    }

    var ticks = 0

    override fun Tick()
    {

        var value = Controller.GetJoystickState(1).GetY() * -1
        leftPaddle.Move(Direction.UP, value.toFloat() * 0.3f)
        rightPaddle.Move(Direction.UP, Controller.GetJoystickState(2).GetY() * -0.3f)
        ball.Move(ballVelocity)
        if(leftPaddle.IsColliding(ball) || rightPaddle.IsColliding(ball) || ball.IsColliding(0f, window.maxWidth, 0f, window.maxHeight))
            ballVelocity.x *= -1
        ticks++
    }
    override fun Render() {
        OpenGL.GLClear(GL30.GL_COLOR_BUFFER_BIT or GL30.GL_DEPTH_BUFFER_BIT)

        Renderer.DrawColoredRect(window, leftPaddle, Vector3f(0f, 1f, 1f))
        Renderer.DrawColoredRect(window, rightPaddle, Vector3f(0f, 1f, 1f))
        Renderer.DrawColoredRect(window, ball, Vector3f(1f, 0f, 0f))

        GLFW.glfwSwapBuffers(window.GetWindowHandle())

        GLFW.glfwPollEvents()
    }
}