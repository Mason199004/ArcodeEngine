package ArcodeEngine.Cabinet

import ArcodeEngine.Cabinet.Games.Chess
import Games.Pong.PongExample
import ArcodeEngine.Engine.*
import ArcodeEngine.Engine.GFX.Renderer
import ArcodeEngine.Engine.GFX.Shader.Shader
import ArcodeEngine.Engine.Geometry.Text
import ArcodeEngine.Engine.Util.OpenGL
import org.joml.Vector3f
import org.lwjgl.glfw.GLFW
import java.util.ArrayList

/**
 * This is the main class for the arcade cabinet, it's what initializes everything and starts the state manager.
 */

class Cabinet(window: Window) : GameState("Cabinet", window) {

    private lateinit var gameTitleList: ArrayList<Text>
    private lateinit var menuTitle: Text
    private lateinit var cursorSymbol: Text

    private val ACCENT_COLOR = Vector3f(0f, 1f, 1f)

    private var cursorTexture = 0
    private var highlightIndex = 0

    private var isNavigating = false

    private var cursorY = window.GetMaxHeight() - 8.5f

    init {
        StateManager.PushState(this)
    }

    companion object {
        var gameLibrary = hashMapOf<String, GameState>()

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

        cursorSymbol = Text(1.5f, window.GetMaxHeight() - 8.5f, ">", 1f)
        menuTitle = Text(0.5f, window.GetMaxHeight() - 5f, "GAMES", 1.5f)

        /* ADD ALL NEW GAMES JUST AFTER THIS LINE SO YOU CAN RUN THEM */
        AddGame(PongExample(window))
        AddGame(Chess(window))

        OpenGL.GLClearColor(0f, 0f, 0f, 0f)

        GenerateGameList()
        StateManager.Start()
    }

    override fun Update(ts: Float) {
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

        cursorSymbol.SetY(cursorY)

        Renderer.DrawString(window, menuTitle, ACCENT_COLOR)

        for(title in gameTitleList)
            Renderer.DrawString(window, title)

        Renderer.DrawString(window, cursorSymbol, ACCENT_COLOR)
    }

    private fun GenerateGameList() {
        if(gameLibrary.isNotEmpty()) {
            for ((idx, title) in gameLibrary.keys.withIndex()) {
                gameTitleList.add(Text(4.5f, (window.GetMaxHeight() - 8.5f) - (4f * idx), title, 1f))
            }
        }
    }

    private fun SelectHighlitedElement() {
        StateManager.PushState(gameLibrary[gameTitleList[highlightIndex].GetMsg()]!!)
    }
}