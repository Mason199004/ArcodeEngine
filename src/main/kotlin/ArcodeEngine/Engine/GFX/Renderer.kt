package ArcodeEngine.Engine.GFX

import ArcodeEngine.Engine.ArcodeEngine
import ArcodeEngine.Engine.Geometry.Rectangle
import ArcodeEngine.Engine.Util.OpenGL
import ArcodeEngine.Engine.Window
import org.joml.*
import org.lwjgl.opengl.GL46.*

class Renderer {
    companion object {
        /**
         * Draws a Rectangle with specified color at the specified coordinates with the specified width and height
         * @param rectangle The rectangle you want to render
         *
         */
        @JvmStatic
        fun DrawColoredRect(window: Window, rectangle: Rectangle, color: Vector3f) {
            ArcodeEngine.ColoredShader.Bind()
            ArcodeEngine.ColoredShader.LoadVector3f("inColor", color)
            ArcodeEngine.ColoredShader.LoadMatrix4f("mvMatrix", rectangle.GetTransformMatrix().mul(window.GetCamera().GetViewMatrix()))
            ArcodeEngine.ColoredShader.LoadMatrix4f("projMatrix", window.GetCamera().GetProjectionMatrix())
            OpenGL.GLBindVertexArray(rectangle.GetMesh().vaoID);
            OpenGL.GLEnableVertexAttribArray(0);
            OpenGL.GLDrawElements(GL_TRIANGLES, rectangle.GetMesh().vertices.size, GL_UNSIGNED_INT, 0)
            OpenGL.GLDisableVertexAttribArray(0);
            OpenGL.GLBindVertexArray(0)
            ArcodeEngine.ColoredShader.Unbind()
        }

        @JvmStatic
        fun DrawTexturedRect(window: Window, rectangle: Rectangle, textureID: Int) {
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
    }
}