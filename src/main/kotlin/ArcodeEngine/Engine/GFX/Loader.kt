package ArcodeEngine.Engine.GFX

import ArcodeEngine.Engine.Util.OpenGL
import org.lwjgl.BufferUtils
import org.lwjgl.opengl.GL30
import org.lwjgl.opengl.GL30.*
import java.nio.FloatBuffer
import java.nio.IntBuffer
import java.util.*

object Loader {
    var vaos = ArrayList<Int>()
    var vbos = ArrayList<Int>()
    var textures = ArrayList<Int>()
    fun LoadToVAO(positions: FloatArray, textureCoords: FloatArray, indices: IntArray): Mesh {
        val vaoId = CreateVAO()
        BindIndicesBuffer(indices)
        StoreDataInAttributeList(0, 3, positions)
        StoreDataInAttributeList(1, 2, textureCoords)
        UnbindVAO()
        return Mesh(vaoId, positions, textureCoords, indices)
    }

    fun CleanUp() {
        for (vao in vaos) {
            OpenGL.GLDeleteVertexArrays(vao)
        }
        for (vbo in vbos) {
            OpenGL.GLDeleteBuffers(vbo)
        }
    }

    fun CreateVAO(): Int {
        val vaoID = OpenGL.GLGenVertexArrays()
        vaos.add(vaoID)
        OpenGL.GLBindVertexArray(vaoID)
        return vaoID
    }

    private fun StoreDataInAttributeList(attribNumber: Int, size: Int, data: FloatArray) {
        val vboID = OpenGL.GLGenBuffers()
        vbos.add(vboID)
        OpenGL.GLBindBuffer(GL_ARRAY_BUFFER, vboID) // START
        val buffer = StoreDataInFloatBuffer(data)
        glBufferData(GL_ARRAY_BUFFER, buffer, GL_STATIC_DRAW)
        glVertexAttribPointer(attribNumber, size, GL_FLOAT, false, 0, 0)
        glBindBuffer(GL_ARRAY_BUFFER, 0) // END
    }

    private fun UnbindVAO() {
        GL30.glBindVertexArray(0)
    }

    private fun BindIndicesBuffer(indices: IntArray) {
        val vboID = glGenBuffers()
        vbos.add(vboID)
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, vboID)
        val buffer = StoreDataInIntBuffer(indices)
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, buffer, GL_STATIC_DRAW)
    }

    private fun StoreDataInIntBuffer(data: IntArray): IntBuffer {
        val buffer = BufferUtils.createIntBuffer(data.size)
        buffer.put(data)
        buffer.flip()
        return buffer
    }

    private fun StoreDataInFloatBuffer(data: FloatArray): FloatBuffer {
        val buffer = BufferUtils.createFloatBuffer(data.size)
        buffer.put(data)
        buffer.flip()
        return buffer
    }
}