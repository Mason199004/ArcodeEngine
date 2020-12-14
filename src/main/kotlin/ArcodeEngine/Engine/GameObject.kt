package ArcodeEngine.Engine

abstract class GameObject
{
    abstract fun OnTick(ticksExisted: Long)
    abstract fun Render(ticksExisted: Long)
}