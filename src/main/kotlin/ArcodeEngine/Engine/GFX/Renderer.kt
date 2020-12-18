package ArcodeEngine.Engine.GFX

import ArcodeEngine.Engine.ArcodeEngine
import ArcodeEngine.Engine.Geometry.Rectangle
import ArcodeEngine.Engine.Window
import org.joml.*
import org.lwjgl.opengl.GL30

class Renderer {
    companion object {
        /**
         * Draws a Rectangle with specified color at the specified coordinates with the specified width and height
         * @param rectangle The rectangle you want to render
         *
         */
        fun DrawColoredRect(rectangle: Rectangle, color: Vector3f) {
            ArcodeEngine.ColoredShader.start()
            ArcodeEngine.ColoredShader.loadVector3f("inColor", color)
            ArcodeEngine.ColoredShader.loadMatrix4f("mvpMatrix", rectangle.GetTransformMatrix().mul(Window.projectionMatrix))
            GL30.glBindVertexArray(rectangle.mesh.vaoID);
            GL30.glEnableVertexAttribArray(0);
            GL30.glDrawElements(GL30.GL_TRIANGLES, rectangle.mesh.vertices.size, GL30.GL_UNSIGNED_INT, 0)
            GL30.glDisableVertexAttribArray(0);
            GL30.glBindVertexArray(0)
            ArcodeEngine.ColoredShader.stop()
        }
    }
}