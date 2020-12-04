package ArcodeEngine

abstract class GameObject
{
    abstract fun onTick(ticksExisted: Long)
    abstract fun render(ticksExisted: Long)
}