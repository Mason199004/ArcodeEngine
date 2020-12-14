package ArcodeEngine.Engine

abstract class GameState(val Name: String, var window: Window) {
    abstract fun tick();
    abstract fun render();
    abstract fun init();
}