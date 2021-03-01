package ArcodeEngine.Cabinet

import Games.Pong.PongExample
import ArcodeEngine.Engine.*
import ArcodeEngine.Engine.GFX.Shader.Shader
import ArcodeEngine.Engine.GFX.TextRenderer
import ArcodeEngine.Engine.Geometry.Text
import ArcodeEngine.Engine.Util.OpenGL
import java.util.ArrayList
import kotlin.concurrent.timerTask

class Cabinet(window: Window) : GameState("Arcade Cabinet", window) {

    private lateinit var gameTitleList: ArrayList<Text>
    private lateinit var menuTitle: Text

    private var cursorY = window.GetMaxHeight() - 8.5f

    init {
        ArcodeEngine.SubmitStateChangeRequest(ArcodeEngine.StateRequest.PUSH, this)
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
            gameLibrary[game.name] = game;
        }

        @JvmStatic
        fun main(args: Array<String>) {
            wind = Window(Pair(960, 540))
            Cabinet(wind)
        }
    }

    override fun Init() {
        OpenGL.CreateCapabilities();

        ArcodeEngine.ColoredShader = Shader("src/main/kotlin/ArcodeEngine/Engine/GFX/Shader/coloredVert.glsl", "src/main/kotlin/ArcodeEngine/Engine/GFX/Shader/coloredFrag.glsl")
        ArcodeEngine.TexturedShader = Shader("src/main/kotlin/ArcodeEngine/Engine/GFX/Shader/texturedVert.glsl", "src/main/kotlin/ArcodeEngine/Engine/GFX/Shader/texturedFrag.glsl")

        gameTitleList = arrayListOf()

        menuTitle = Text(0.5f, window.GetMaxHeight() - 5f, "GAMES", 1.5f)

        /* ADD ALL NEW GAMES JUST AFTER THIS LINE SO YOU CAN RUN THEM */
        AddGame(PongExample(window))

        OpenGL.GLClearColor(0f, 0f, 0f, 0f)

        GenerateGameList()

        StateManager.TickState()
    }

    override fun Tick() {
        // TODO: Make a cursor sprite
        // TODO: Make the cursor a rectangle, not a text object
    }

    override fun Render() {
        TextRenderer.DrawString(window, menuTitle)

        for(title in gameTitleList)
            TextRenderer.DrawString(window, title)
    }

    fun GenerateGameList() {
        if(gameLibrary.isNotEmpty()) {
            for ((idx, title) in gameLibrary.keys.withIndex()) {
                gameTitleList.add(Text(4.5f, (window.GetMaxHeight() - 8.5f) - (4f * idx), title, 1f))
            }
        }
    }
}