package ArcodeEngine.Engine

interface State {
    fun tick();
    fun render();
}

abstract class Game : State {}