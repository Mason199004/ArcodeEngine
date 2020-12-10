package ArcodeEngine.Cabinet

import ArcodeEngine.Engine.ArcodeEngine
import ArcodeEngine.Engine.GameState

// This is the main class responsible for browsing all the current games in the cabinet

class Cabinet : GameState("Arcade Cabinet") {

    init {
        ArcodeEngine.submitStateChangeRequest(ArcodeEngine.StateRequest.PUSH, this);
    }

    override fun tick() {}
    override fun render() {}
}