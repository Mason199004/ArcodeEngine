package ArcodeEngine.Engine

class ArcodeEngine
{
    var Games: MutableList<GameState> = mutableListOf()
    companion object
    {
        fun submitStateChangeRequest(request: StateRequest, state: State) {
            when(request) {
                StateRequest.QUIT_CURRENT -> StateManager.popState()
                StateRequest.PUSH -> StateManager.pushState(state)
            }
        }
    }

    enum class StateRequest {
        QUIT_CURRENT,
        PUSH
    }

}