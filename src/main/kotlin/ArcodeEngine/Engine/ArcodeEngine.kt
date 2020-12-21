package ArcodeEngine.Engine

import ArcodeEngine.Engine.GFX.Shader.Shader
import ArcodeEngine.Engine.GFX.Texture
import java.security.InvalidParameterException

class ArcodeEngine
{
    companion object
    {
        lateinit var ColoredShader: Shader
        lateinit var TexturedShader: Shader

        var textures: HashMap<Int, Texture> = hashMapOf()
        fun SubmitStateChangeRequest(request: StateRequest, state: GameState) {
            when(request) {
                StateRequest.QUIT_CURRENT -> StateManager.PopState()
                StateRequest.PUSH -> StateManager.PushState(state)
            }
        }
        fun RegisterTexture(path: String): Int {
            var texture: Texture = Texture(path)
            var textureID = texture.textureID

            textures[textureID] = texture

            return textureID
        }

        fun GetTexture(textureID: Int): Texture {
            if(textures.containsKey(textureID))
                return textures[textureID]!!
            else
                throw InvalidParameterException("The texture with id: $textureID could not be found. Have you registered it?")
        }
    }

    enum class StateRequest {
        QUIT_CURRENT,
        PUSH
    }

}