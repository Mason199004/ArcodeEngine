package ArcodeEngine.Engine

abstract class GameState(val name: String, var window: Window) {
    abstract fun Tick();
    abstract fun Render();
    abstract fun Init();
}