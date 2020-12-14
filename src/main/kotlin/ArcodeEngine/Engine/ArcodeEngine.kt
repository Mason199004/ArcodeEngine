package ArcodeEngine.Engine

import java.util.jar.Attributes.Name

class ArcodeEngine
{
    var Games: MutableList<GameState> = mutableListOf()


    companion object
    {
        var Assets: MutableMap<Long, String> = mutableMapOf()
        fun SubmitStateChangeRequest(request: StateRequest, state: GameState) {
            when(request) {
                StateRequest.QUIT_CURRENT -> StateManager.popState()
                StateRequest.PUSH -> StateManager.pushState(state)
            }
        }
        fun RegisterAsset(Asset: String) : Long
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