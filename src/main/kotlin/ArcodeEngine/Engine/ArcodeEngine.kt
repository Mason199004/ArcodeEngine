package ArcodeEngine.Engine

import java.util.jar.Attributes.Name

class ArcodeEngine
{
    var Games: MutableList<GameState> = mutableListOf()


    companion object
    {
        var Assets: MutableMap<Long, String> = mutableMapOf()
        fun submitStateChangeRequest(request: StateRequest, state: State) {
            when(request) {
                StateRequest.QUIT_CURRENT -> StateManager.popState()
                StateRequest.PUSH -> StateManager.pushState(state)
            }
        }
        fun registerAsset(Asset: String) : Long
        {

            var num: Long
            Assets[Assets.count().also { num = it.toLong() }.toLong()] =  Asset
            return num
        }
    }

    enum class StateRequest {
        QUIT_CURRENT,
        PUSH
    }

}