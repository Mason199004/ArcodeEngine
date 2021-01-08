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

class Cabinet(window: Window) : GameState("Arcade Cabinet", window) {

    lateinit var leftPaddle: Rectangle
    lateinit var rightPaddle: Rectangle
    lateinit var ball: Rectangle

    private val ballHSpeed: Float = 0.2f
    private val ballVSpeed: Float = 0.1f
    private var ballVelocity: Vector2f = Vector2f(0.1f, 0f)

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

        leftPaddle = Rectangle(1f, 10f, 2f, 9f)
        rightPaddle = Rectangle(window.maxWidth - 3, 10f, 2f, 9f)
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

        if(ball.IsColliding(0f, window.maxWidth, 0f, window.maxHeight)) {
            ballVelocity.y *= -1
        }

        if(leftPaddle.IsColliding(ball)) {
            if(ball.position.y > leftPaddle.position.y + (leftPaddle.scaleXY.second * (2f / 3f))) {
                ballVelocity.y = ballVSpeed
                ballVelocity.x = ballHSpeed / 2
            } else if(ball.position.y < leftPaddle.position.y + (leftPaddle.scaleXY.second / 3f)) {
                ballVelocity.y = -ballVSpeed
                ballVelocity.x = ballHSpeed / 2
            } else {
                ballVelocity.y = 0f
                ballVelocity.x = ballHSpeed / 2
            }
        }

        if(rightPaddle.IsColliding(ball)) {
            if(ball.position.y > rightPaddle.position.y + (rightPaddle.scaleXY.second * (2f / 3f))) {
                ballVelocity.y = ballVSpeed
                ballVelocity.x = -ballHSpeed / 2
            } else if(ball.position.y < rightPaddle.position.y + (rightPaddle.scaleXY.second / 3f)) {
                ballVelocity.y = -ballVSpeed
                ballVelocity.x = -ballHSpeed / 2
            } else {
                ballVelocity.y = 0f
                ballVelocity.x = -ballHSpeed / 2
            }
        }

        ball.Move(ballVelocity)
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