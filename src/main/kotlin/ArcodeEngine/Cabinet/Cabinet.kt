package ArcodeEngine.Cabinet

import Games.Pong.PongExample
import ArcodeEngine.Engine.*
import ArcodeEngine.Engine.GFX.Shader.Shader
import ArcodeEngine.Engine.Util.OpenGL

class Cabinet(window: Window) : GameState("Arcade Cabinet", window) {
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
            wind = Window(Pair(960, 540))
            Cabinet(wind)
        }
    }

    override fun Init() {
        OpenGL.CreateCapabilities();

        ArcodeEngine.ColoredShader = Shader("src/main/kotlin/ArcodeEngine/Engine/GFX/Shader/coloredVert.glsl", "src/main/kotlin/ArcodeEngine/Engine/GFX/Shader/coloredFrag.glsl")
        ArcodeEngine.TexturedShader = Shader("src/main/kotlin/ArcodeEngine/Engine/GFX/Shader/texturedVert.glsl", "src/main/kotlin/ArcodeEngine/Engine/GFX/Shader/texturedFrag.glsl")

        AddGame(PongExample(window))

        OpenGL.GLClearColor(0f, 0f, 0f, 0f)
        ArcodeEngine.SubmitStateChangeRequest(ArcodeEngine.StateRequest.PUSH, gameLibrary[0])
        StateManager.TickState()
    }

    override fun Tick() {

    }
    override fun Render() {

    }
}