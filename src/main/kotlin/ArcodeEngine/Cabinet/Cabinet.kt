package ArcodeEngine.Cabinet

import ArcodeEngine.Cabinet.Games.Chess
import Games.Pong.PongExample
import ArcodeEngine.Engine.*
import ArcodeEngine.Engine.GFX.Renderer
import ArcodeEngine.Engine.GFX.Shader.Shader
import ArcodeEngine.Engine.GFX.Text
import ArcodeEngine.Engine.Util.OpenGL
import org.joml.Vector3f
import org.lwjgl.glfw.GLFW
import java.util.ArrayList

/**
 * This is the main class for the arcade cabinet, it's what initializes everything and starts the state manager.
 */

class Cabinet(window: Window) : GameState("Cabinet", window) {
    private lateinit var gameTitleList: ArrayList<String>

    private val ACCENT_COLOR = Vector3f(0f, 1f, 1f)

    private val MENU_TITLE_SCALE = 2f
    private val MENU_TITLE_X = 0.5f
    private val MENU_TITLE_Y = window.GetMaxHeight() - Text.DEFAULT_TEXT_HEIGHT * MENU_TITLE_SCALE

    private var cursorTexture = 0
    private var highlightIndex = 0

    private var isNavigating = false

    private var cursorY = window.GetMaxHeight() - 8.5f

    init {
        StateManager.PushState(this)
    }

    companion object {
        @JvmStatic
        lateinit var wind: Window
        @JvmStatic
        fun GetWindow() : Long
        {
            return wind.GetWindowHandle()
        }

        fun AddGame(game: GameState) {
            gameLibrary[game.name] = game
        }

        var gameLibrary = hashMapOf<String, GameState>()

        private const val MENU_TITLE = "Games:"
        private const val CURSOR_SYM = ">"

        @JvmStatic
        fun main(args: Array<String>) {
            wind = Window(Pair(960, 540), false)
            Cabinet(wind)
        }
    }

    override fun Init() {
        OpenGL.CreateCapabilities()

        ArcodeEngine.ColoredShaderRGB = Shader("src/main/kotlin/ArcodeEngine/Engine/GFX/Shader/coloredVert.glsl", "src/main/kotlin/ArcodeEngine/Engine/GFX/Shader/coloredFrag.glsl")
        ArcodeEngine.ColoredShaderRGBA = Shader("src/main/kotlin/ArcodeEngine/Engine/GFX/Shader/coloredRGBAVert.glsl", "src/main/kotlin/ArcodeEngine/Engine/GFX/Shader/coloredRGBAFrag.glsl")
        ArcodeEngine.TexturedShader = Shader("src/main/kotlin/ArcodeEngine/Engine/GFX/Shader/texturedVert.glsl", "src/main/kotlin/ArcodeEngine/Engine/GFX/Shader/texturedFrag.glsl")
        ArcodeEngine.GlyphShader = Shader("src/main/kotlin/ArcodeEngine/Engine/GFX/Shader/glyphVert.glsl", "src/main/kotlin/ArcodeEngine/Engine/GFX/Shader/glyphFrag.glsl")

        gameTitleList = arrayListOf()

        cursorTexture = ArcodeEngine.RegisterProjectTexture(this, "arcade-cursor.png")

        /* ADD ALL NEW GAMES JUST AFTER THIS LINE SO YOU CAN RUN THEM */
        AddGame(PongExample(window))
        AddGame(Chess(window))

        OpenGL.GLClearColor(0f, 0f, 0f, 0f)

        GenerateGameList()
        StateManager.TickState()
    }

    override fun Tick() {
        val esc = GLFW.glfwGetKey(window.GetWindowHandle(), GLFW.GLFW_KEY_ESCAPE)
        if(esc == GLFW.GLFW_PRESS)
            GLFW.glfwSetWindowShouldClose(window.GetWindowHandle(), true)

        if(!isNavigating) {
            if(Controller.GetJoystickState(2).GetY() > 0 && cursorY < window.GetMaxHeight() - 8.5f) {
                cursorY += 4f
                isNavigating = true
                highlightIndex--
            }
            else if(Controller.GetJoystickState(2).GetY() < 0 && cursorY - 4f > 0f && cursorY >(window.GetMaxHeight() - (8.5f + (4f * (gameTitleList.size - 1))))) {
                cursorY -= 4f
                isNavigating = true
                highlightIndex++
            }
        }
        isNavigating = Controller.GetJoystickState(2).GetY() != 0

        val select = GLFW.glfwGetKey(window.GetWindowHandle(), GLFW.GLFW_KEY_ENTER)
        if(select == GLFW.GLFW_PRESS)
            SelectHighlitedElement()
    }

    override fun Render() {
        Renderer.DrawString(window, 0f, 0f, "A")
/*
        Renderer.DrawString(
            window,
            0.5f,
            0.5f,
            MENU_TITLE,
            ACCENT_COLOR,
            MENU_TITLE_SCALE
        )

        if(gameLibrary.isNotEmpty()) {
            for ((idx, title) in gameLibrary.keys.withIndex()) {
                Renderer.DrawString(window, 2.5f, (window.GetMaxHeight() - 8.5f) - (idx * Text.DEFAULT_TEXT_HEIGHT * 1.5f + 0.5f), title, 1.5f)
            }
        }

        Renderer.DrawString(window, 0.5f, cursorY, CURSOR_SYM, ACCENT_COLOR)
 */
    }

    private fun GenerateGameList() {

    }

    private fun SelectHighlitedElement() {
        StateManager.PushState(gameLibrary[gameTitleList[highlightIndex]]!!)
    }
}