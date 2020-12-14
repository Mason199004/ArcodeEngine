package ArcodeEngine.Engine

interface State {
    fun tick()
    fun render()
    fun init()
}

abstract class GameState(val Name: String) : State {}