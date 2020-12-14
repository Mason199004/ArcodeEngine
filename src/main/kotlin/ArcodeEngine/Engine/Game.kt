package ArcodeEngine.Engine

abstract class GameState(val Name: String, var window: Window) {
    abstract fun Tick();
    abstract fun Render();
    abstract fun Init();
}