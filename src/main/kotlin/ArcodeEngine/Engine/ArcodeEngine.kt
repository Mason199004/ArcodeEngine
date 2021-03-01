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
        lateinit var GlyphShader: Shader

        var textures: HashMap<Int, Texture> = hashMapOf()
        fun SubmitStateChangeRequest(request: StateRequest, state: GameState) {
            when(request) {
                StateRequest.QUIT_CURRENT -> StateManager.PopState()
                StateRequest.PUSH -> StateManager.PushState(state)
            }
        }

        @JvmStatic
        fun RegisterTexture(path: String): Int {
            val texture = Texture(path)
            val textureID = texture.textureID

            textures[textureID] = texture

            return textureID
        }

        @JvmStatic
        fun RegisterProjectTexture(state: GameState, fileName: String): Int {
            val texture = Texture("res/${state.name}/$fileName")
            val textureID = texture.textureID

            textures[textureID] = texture
            return textureID
        }

        @JvmStatic
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