package ArcodeEngine.Engine

class ArcodeEngine
{
    var Games: MutableList<Game> = mutableListOf()
    companion object
    {
        @JvmStatic
        fun main(args: Array<String>)
        {
            Controller.init()
        }
    }

}