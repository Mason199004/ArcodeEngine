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
         * @param window the window you want to render to.
         * @param color the color (r,g,b) you want the rectangle to be. Note: the values are within 0 and 1, not 0 and 255
         */
        @JvmStatic
        fun DrawColoredRectRGB(window: Window, rectangle: Rectangle, color: Vector3f) {
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
        fun DrawColoredRectRGBA(window: Window, rectangle: Rectangle, color: Vector4f) {
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