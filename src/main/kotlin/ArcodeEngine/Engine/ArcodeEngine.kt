package ArcodeEngine.Engine

import ArcodeEngine.Engine.GFX.Shader.Shader
import ArcodeEngine.Engine.GFX.Texture
import java.security.InvalidParameterException

class ArcodeEngine
{
    companion object
    {
        lateinit var ColoredShaderRGB: Shader
        lateinit var ColoredShaderRGBA: Shader
        lateinit var TexturedShader: Shader
        lateinit var GlyphShader: Shader

        var textures: HashMap<Int, Texture> = hashMapOf()

        /**
         * Load a texture
         * @param path The absolute path to the texture
         * @return The ID of the texture
         */
        @JvmStatic
        fun RegisterTexture(path: String): Int {
            val texture = Texture(path)
            val textureID = texture.textureID

            textures[textureID] = texture

            return textureID
        }

        /**
         * Load a texture for the specified GameState
         * @param state The GameState you want to register the texture for
         * @param fileName The file name of the texture. NOT THE PATH
         */
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
}