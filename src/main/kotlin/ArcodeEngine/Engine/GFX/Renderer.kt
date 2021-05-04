package ArcodeEngine.Engine.GFX

import ArcodeEngine.Engine.ArcodeEngine
import ArcodeEngine.Engine.Geometry.Rectangle
import ArcodeEngine.Engine.Geometry.Text
import ArcodeEngine.Engine.Util.OpenGL
import ArcodeEngine.Engine.Window
import org.joml.*
import org.lwjgl.opengl.GL46
import org.lwjgl.opengl.GL46.*

class Renderer {
    companion object {
        /**
         * Draws a Rectangle with specified color at the specified coordinates with the specified width and height
         * @param window the window you want to render to.
         * @param color the color (r,g,b) you want the rectangle to be. Note: the values are within 0 and 1, not 0 and 255
         */
        @JvmStatic
        fun DrawRect(window: Window, rectangle: Rectangle, color: Vector3f) {
            ArcodeEngine.ColoredShaderRGB.Bind()
            ArcodeEngine.ColoredShaderRGB.LoadVector3f("inColor", color)
            ArcodeEngine.ColoredShaderRGB.LoadMatrix4f("mvMatrix", rectangle.GetTransformMatrix().mul(window.GetCamera().GetViewMatrix()))
            ArcodeEngine.ColoredShaderRGB.LoadMatrix4f("projMatrix", window.GetCamera().GetProjectionMatrix())
            OpenGL.GLBindVertexArray(rectangle.GetMesh().vaoID)
            OpenGL.GLEnableVertexAttribArray(0)
            OpenGL.GLDrawElements(GL_TRIANGLES, rectangle.GetMesh().vertices.size, GL_UNSIGNED_INT, 0)
            OpenGL.GLDisableVertexAttribArray(0)
            OpenGL.GLBindVertexArray(0)
            ArcodeEngine.ColoredShaderRGB.Unbind()
        }

        /**
         * Draws a Rectangle with specified color at the specified coordinates with the specified width and height
         * @param window the window you want to render to.
         * @param color the color (r,g,b,a) you want the rectangle to be. Note: the values are within 0 and 1, not 0 and 255
         */
        @JvmStatic
        fun DrawRect(window: Window, rectangle: Rectangle, color: Vector4f) {
            ArcodeEngine.ColoredShaderRGBA.Bind()
            ArcodeEngine.ColoredShaderRGBA.LoadVector4f("inColor", color)
            ArcodeEngine.ColoredShaderRGBA.LoadMatrix4f("mvMatrix", rectangle.GetTransformMatrix().mul(window.GetCamera().GetViewMatrix()))
            ArcodeEngine.ColoredShaderRGBA.LoadMatrix4f("projMatrix", window.GetCamera().GetProjectionMatrix())
            OpenGL.GLBindVertexArray(rectangle.GetMesh().vaoID)
            OpenGL.GLEnableVertexAttribArray(0)
            OpenGL.GLDrawElements(GL_TRIANGLES, rectangle.GetMesh().vertices.size, GL_UNSIGNED_INT, 0)
            OpenGL.GLDisableVertexAttribArray(0)
            OpenGL.GLBindVertexArray(0)
            ArcodeEngine.ColoredShaderRGBA.Unbind()
        }

        /**
         * Draws the passed rectangle with the texture associated with the the textureID you pass.
         * @param window the window you want to render to
         * @param textureID the id of the texture you want the rectangle to have
         */
        @JvmStatic
        fun DrawRect(window: Window, rectangle: Rectangle, textureID: Int) {
            ArcodeEngine.TexturedShader.Bind()
            ArcodeEngine.TexturedShader.LoadMatrix4f("mvMatrix", rectangle.GetTransformMatrix())
            ArcodeEngine.TexturedShader.LoadMatrix4f("projMatrix", window.GetCamera().GetProjectionMatrix())
            OpenGL.GLBindVertexArray(rectangle.GetMesh().vaoID)
            OpenGL.GLEnableVertexAttribArray(0)
            OpenGL.GLEnableVertexAttribArray(1)
            OpenGL.GLActiveTexture(GL_TEXTURE0)
            OpenGL.GLBindTexture(GL_TEXTURE_2D, textureID)
            OpenGL.GLDrawElements(GL_TRIANGLES, rectangle.GetMesh().vertices.size, GL_UNSIGNED_INT, 0)
            OpenGL.GLDisableVertexAttribArray(1)
            OpenGL.GLDisableVertexAttribArray(0)
            OpenGL.GLBindVertexArray(0)
            ArcodeEngine.TexturedShader.Unbind()
        }

        var fontMapID = ArcodeEngine.RegisterTexture("src/main/kotlin/ArcodeEngine/Engine/res/arcade-font.png")

        private val DEFAULT_COLOR = Vector4f(1.0f)

        private val characterTable = mapOf(
            Pair('A', Rectangle(0f, 0f, Text.CHAR_WIDTH, Text.CHAR_HEIGHT, Text.CalculateTextureCoords('A', 0))),
            Pair('B', Rectangle(0f, 0f, Text.CHAR_WIDTH, Text.CHAR_HEIGHT, Text.CalculateTextureCoords('A', 1))),
            Pair('C', Rectangle(0f, 0f, Text.CHAR_WIDTH, Text.CHAR_HEIGHT, Text.CalculateTextureCoords('A', 2))),
            Pair('D', Rectangle(0f, 0f, Text.CHAR_WIDTH, Text.CHAR_HEIGHT, Text.CalculateTextureCoords('A', 3))),
            Pair('E', Rectangle(0f, 0f, Text.CHAR_WIDTH, Text.CHAR_HEIGHT, Text.CalculateTextureCoords('A', 4))),
            Pair('F', Rectangle(0f, 0f, Text.CHAR_WIDTH, Text.CHAR_HEIGHT, Text.CalculateTextureCoords('A', 5))),
            Pair('G', Rectangle(0f, 0f, Text.CHAR_WIDTH, Text.CHAR_HEIGHT, Text.CalculateTextureCoords('A', 6))),
            Pair('H', Rectangle(0f, 0f, Text.CHAR_WIDTH, Text.CHAR_HEIGHT, Text.CalculateTextureCoords('A', 7))),
            Pair('I', Rectangle(0f, 0f, Text.CHAR_WIDTH, Text.CHAR_HEIGHT, Text.CalculateTextureCoords('A', 8))),
            Pair('J', Rectangle(0f, 0f, Text.CHAR_WIDTH, Text.CHAR_HEIGHT, Text.CalculateTextureCoords('A', 9))),
            Pair('K', Rectangle(0f, 0f, Text.CHAR_WIDTH, Text.CHAR_HEIGHT, Text.CalculateTextureCoords('A', 10))),
            Pair('L', Rectangle(0f, 0f, Text.CHAR_WIDTH, Text.CHAR_HEIGHT, Text.CalculateTextureCoords('A', 11))),
            Pair('M', Rectangle(0f, 0f, Text.CHAR_WIDTH, Text.CHAR_HEIGHT, Text.CalculateTextureCoords('A', 12))),
            Pair('N', Rectangle(0f, 0f, Text.CHAR_WIDTH, Text.CHAR_HEIGHT, Text.CalculateTextureCoords('A', 13))),
            Pair('O', Rectangle(0f, 0f, Text.CHAR_WIDTH, Text.CHAR_HEIGHT, Text.CalculateTextureCoords('A', 14))),
            Pair('P', Rectangle(0f, 0f, Text.CHAR_WIDTH, Text.CHAR_HEIGHT, Text.CalculateTextureCoords('A', 15))),
            Pair('Q', Rectangle(0f, 0f, Text.CHAR_WIDTH, Text.CHAR_HEIGHT, Text.CalculateTextureCoords('A', 16))),
            Pair('R', Rectangle(0f, 0f, Text.CHAR_WIDTH, Text.CHAR_HEIGHT, Text.CalculateTextureCoords('A', 17))),
            Pair('S', Rectangle(0f, 0f, Text.CHAR_WIDTH, Text.CHAR_HEIGHT, Text.CalculateTextureCoords('A', 18))),
            Pair('T', Rectangle(0f, 0f, Text.CHAR_WIDTH, Text.CHAR_HEIGHT, Text.CalculateTextureCoords('A', 19))),
            Pair('U', Rectangle(0f, 0f, Text.CHAR_WIDTH, Text.CHAR_HEIGHT, Text.CalculateTextureCoords('A', 20))),
            Pair('V', Rectangle(0f, 0f, Text.CHAR_WIDTH, Text.CHAR_HEIGHT, Text.CalculateTextureCoords('A', 21))),
            Pair('W', Rectangle(0f, 0f, Text.CHAR_WIDTH, Text.CHAR_HEIGHT, Text.CalculateTextureCoords('A', 22))),
            Pair('X', Rectangle(0f, 0f, Text.CHAR_WIDTH, Text.CHAR_HEIGHT, Text.CalculateTextureCoords('A', 23))),
            Pair('Y', Rectangle(0f, 0f, Text.CHAR_WIDTH, Text.CHAR_HEIGHT, Text.CalculateTextureCoords('A', 24))),
            Pair('Z', Rectangle(0f, 0f, Text.CHAR_WIDTH, Text.CHAR_HEIGHT, Text.CalculateTextureCoords('A', 25))),
            Pair('!', Rectangle(0f, 0f, Text.CHAR_WIDTH, Text.CHAR_HEIGHT, Text.CalculateTextureCoords('!', 26))),
            Pair('*', Rectangle(0f, 0f, Text.CHAR_WIDTH, Text.CHAR_HEIGHT, Text.CalculateTextureCoords('*', 27))),
            Pair('#', Rectangle(0f, 0f, Text.CHAR_WIDTH, Text.CHAR_HEIGHT, Text.CalculateTextureCoords('#', 28))),
            Pair('%', Rectangle(0f, 0f, Text.CHAR_WIDTH, Text.CHAR_HEIGHT, Text.CalculateTextureCoords('%', 29))),
            Pair('(', Rectangle(0f, 0f, Text.CHAR_WIDTH, Text.CHAR_HEIGHT, Text.CalculateTextureCoords('(', 30))),
            Pair(')', Rectangle(0f, 0f, Text.CHAR_WIDTH, Text.CHAR_HEIGHT, Text.CalculateTextureCoords(')', 31))),
            Pair('-', Rectangle(0f, 0f, Text.CHAR_WIDTH, Text.CHAR_HEIGHT, Text.CalculateTextureCoords('-', 32))),
            Pair('+', Rectangle(0f, 0f, Text.CHAR_WIDTH, Text.CHAR_HEIGHT, Text.CalculateTextureCoords('+', 33))),
            Pair('=', Rectangle(0f, 0f, Text.CHAR_WIDTH, Text.CHAR_HEIGHT, Text.CalculateTextureCoords('=', 34))),
            Pair('.', Rectangle(0f, 0f, Text.CHAR_WIDTH, Text.CHAR_HEIGHT, Text.CalculateTextureCoords('.', 35))),
            Pair(',', Rectangle(0f, 0f, Text.CHAR_WIDTH, Text.CHAR_HEIGHT, Text.CalculateTextureCoords(',', 36))),
            Pair('?', Rectangle(0f, 0f, Text.CHAR_WIDTH, Text.CHAR_HEIGHT, Text.CalculateTextureCoords('?', 37))),
            Pair('0', Rectangle(0f, 0f, Text.CHAR_WIDTH, Text.CHAR_HEIGHT, Text.CalculateTextureCoords('0', 0))),
            Pair('1', Rectangle(0f, 0f, Text.CHAR_WIDTH, Text.CHAR_HEIGHT, Text.CalculateTextureCoords('0', 1))),
            Pair('2', Rectangle(0f, 0f, Text.CHAR_WIDTH, Text.CHAR_HEIGHT, Text.CalculateTextureCoords('0', 2))),
            Pair('3', Rectangle(0f, 0f, Text.CHAR_WIDTH, Text.CHAR_HEIGHT, Text.CalculateTextureCoords('0', 3))),
            Pair('4', Rectangle(0f, 0f, Text.CHAR_WIDTH, Text.CHAR_HEIGHT, Text.CalculateTextureCoords('0', 4))),
            Pair('5', Rectangle(0f, 0f, Text.CHAR_WIDTH, Text.CHAR_HEIGHT, Text.CalculateTextureCoords('0', 5))),
            Pair('6', Rectangle(0f, 0f, Text.CHAR_WIDTH, Text.CHAR_HEIGHT, Text.CalculateTextureCoords('0', 6))),
            Pair('7', Rectangle(0f, 0f, Text.CHAR_WIDTH, Text.CHAR_HEIGHT, Text.CalculateTextureCoords('0', 7))),
            Pair('8', Rectangle(0f, 0f, Text.CHAR_WIDTH, Text.CHAR_HEIGHT, Text.CalculateTextureCoords('0', 8))),
            Pair('9', Rectangle(0f, 0f, Text.CHAR_WIDTH, Text.CHAR_HEIGHT, Text.CalculateTextureCoords('0', 9)))
        )

        /**
         * Draw a string with default white foreground
         * @param window The window you want to draw to
         */
        @JvmStatic
        fun DrawString(window: Window, text: String, x: Float, y: Float) {
            var offset = 0f
            lateinit var currentChar: Rectangle
            for(c in text.toUpperCase().toCharArray()) {
                if(c == ' ') {
                    offset += Text.CHAR_WIDTH
                    continue
                }
                currentChar = if (characterTable[c] == null) {
                    System.err.println("<Renderer::DrawString>: The character you entered \"$c\" is not supported.")
                    characterTable['*']!!
                } else {
                    characterTable[c]!!
                }
                currentChar.SetScale(Text.CHAR_WIDTH, Text.CHAR_HEIGHT)
                currentChar.SetX(x + offset)
                currentChar.SetY(y)

                DrawCharacter(window, currentChar, DEFAULT_COLOR, fontMapID)
                offset += Text.CHAR_WIDTH
            }
        }

        @JvmStatic
        fun DrawString(window: Window, text: String, x: Float, y: Float, scale: Float) {
            var offset = 0f
            lateinit var currentChar: Rectangle
            for(c in text.toUpperCase().toCharArray()) {
                if(c == ' ') {
                    offset += Text.CHAR_WIDTH * scale
                    continue
                }

                currentChar = if (characterTable[c] == null) {
                    System.err.println("The character you entered \"$c\" is not supported.")
                    characterTable['*']!!
                } else {
                    characterTable[c]!!
                }
                currentChar.SetScale(Text.CHAR_WIDTH * scale, Text.CHAR_HEIGHT * scale)
                currentChar.SetX(x + offset)
                currentChar.SetY(y)

                DrawCharacter(window, currentChar, DEFAULT_COLOR, fontMapID)
                offset += Text.CHAR_WIDTH * scale
            }
        }

        /**
         * Draw a string with a specified color
         * @param window The window you want to draw to
         * @param foregroundColor the color (r,g,b)
         */
        @JvmStatic
        fun DrawString(window: Window, text: String, x: Float, y: Float, foregroundColor: Vector4f) {
            var offset = 0f
            lateinit var currentChar: Rectangle
            for(c in text.toUpperCase().toCharArray()) {
                if(c == ' ') {
                    offset += Text.CHAR_WIDTH
                    continue
                }

                currentChar = if (characterTable[c] == null) {
                    System.err.println("The character you entered \"$c\" is not supported.")
                    characterTable['*']!!
                } else {
                    characterTable[c]!!
                }
                currentChar.SetScale(Text.CHAR_WIDTH, Text.CHAR_HEIGHT)
                currentChar.SetX(x + offset)
                currentChar.SetY(y)

                DrawCharacter(window, currentChar, foregroundColor, fontMapID)
                offset += Text.CHAR_WIDTH
            }
        }

        @JvmStatic
        fun DrawString(window: Window, text: String, x: Float, y: Float, scale: Float, foregroundColor: Vector4f) {
            var offset = 0f
            lateinit var currentChar: Rectangle
            for(c in text.toUpperCase().toCharArray()) {
                if(c == ' ') {
                    offset += Text.CHAR_WIDTH * scale
                    continue
                }

                currentChar = if (characterTable[c] == null) {
                    System.err.println("The character you entered \"$c\" is not supported.")
                    characterTable['*']!!
                } else {
                    characterTable[c]!!
                }
                currentChar.SetScale(Text.CHAR_WIDTH * scale, Text.CHAR_HEIGHT * scale)
                currentChar.SetX(x + offset)
                currentChar.SetY(y)

                DrawCharacter(window, currentChar, foregroundColor, fontMapID)
                offset += Text.CHAR_WIDTH * scale
            }
        }

        private fun DrawCharacter(window: Window, rectangle: Rectangle, foregroundColor: Vector4f, textureID: Int) {
            ArcodeEngine.GlyphShader.Bind()
            ArcodeEngine.GlyphShader.LoadMatrix4f("mvMatrix", rectangle.GetTransformMatrix())
            ArcodeEngine.GlyphShader.LoadMatrix4f("projMatrix", window.GetCamera().GetProjectionMatrix())
            ArcodeEngine.GlyphShader.LoadVector4f("fgColor", foregroundColor)
            OpenGL.GLBindVertexArray(rectangle.GetMesh().vaoID)
            OpenGL.GLEnableVertexAttribArray(0)
            OpenGL.GLEnableVertexAttribArray(1)
            OpenGL.GLActiveTexture(GL_TEXTURE0)
            OpenGL.GLBindTexture(GL_TEXTURE_2D, textureID)
            OpenGL.GLDrawElements(GL_TRIANGLES, rectangle.GetMesh().vertices.size, GL_UNSIGNED_INT, 0)
            OpenGL.GLDisableVertexAttribArray(1)
            OpenGL.GLDisableVertexAttribArray(0)
            OpenGL.GLBindVertexArray(0)
            ArcodeEngine.GlyphShader.Unbind()
        }
    }
}