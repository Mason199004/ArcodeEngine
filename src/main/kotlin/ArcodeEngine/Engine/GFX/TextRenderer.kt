package ArcodeEngine.Engine.GFX

import ArcodeEngine.Engine.ArcodeEngine
import ArcodeEngine.Engine.Geometry.Rectangle
import ArcodeEngine.Engine.Geometry.Text
import ArcodeEngine.Engine.Util.OpenGL
import ArcodeEngine.Engine.Window
import org.joml.Vector3f
import org.lwjgl.opengl.GL46

class TextRenderer {
    companion object {
        var fontMapID = ArcodeEngine.RegisterTexture("src/main/kotlin/ArcodeEngine/Engine/res/arcade-font.png")

        private val DEFAULT_COLOR = Vector3f(1.0f)
        @JvmStatic
        fun DrawString(window: Window, text: Text) {
            for(r in text.chars)
                DrawGlyph(window, r, DEFAULT_COLOR, fontMapID)
        }

        /**
         * Draw a string with a specified color
         * @param window The window you want to draw to
         * @param foregroundColor the color (r,g,b)
         */
        @JvmStatic
        fun DrawStringRGB(window: Window, text: Text, foregroundColor: Vector3f) {
            for (r in text.chars)
                DrawGlyph(window, r, foregroundColor, fontMapID)
        }

        /**
         * This is used for drawing individual characters.
         * @param window The window you want to draw to
         * @param foregroundColor The color (r,g,b) you want the text to be. Note: the limit for the color values is 0-1, not 0-255
         * @param textureID The id of the texture you want the glyph to have
         */
        @JvmStatic
        fun DrawGlyph(window: Window, rectangle: Rectangle, foregroundColor: Vector3f, textureID: Int) {
            ArcodeEngine.GlyphShader.Bind()
            ArcodeEngine.GlyphShader.LoadMatrix4f("mvMatrix", rectangle.GetTransformMatrix())
            ArcodeEngine.GlyphShader.LoadMatrix4f("projMatrix", window.GetCamera().GetProjectionMatrix())
            ArcodeEngine.GlyphShader.LoadVector3f("fgColor", foregroundColor)
            OpenGL.GLBindVertexArray(rectangle.GetMesh().vaoID)
            OpenGL.GLEnableVertexAttribArray(0)
            OpenGL.GLEnableVertexAttribArray(1)
            OpenGL.GLActiveTexture(GL46.GL_TEXTURE0)
            OpenGL.GLBindTexture(GL46.GL_TEXTURE_2D, textureID)
            OpenGL.GLDrawElements(GL46.GL_TRIANGLES, rectangle.GetMesh().vertices.size, GL46.GL_UNSIGNED_INT, 0)
            OpenGL.GLDisableVertexAttribArray(1)
            OpenGL.GLDisableVertexAttribArray(0)
            OpenGL.GLBindVertexArray(0)
            ArcodeEngine.GlyphShader.Unbind()
        }
    }
}