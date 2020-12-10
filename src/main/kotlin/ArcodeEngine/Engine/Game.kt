package ArcodeEngine.Engine

interface State {
    fun tick();
    fun render();
}

abstract class GameState(Name: String) : State {}