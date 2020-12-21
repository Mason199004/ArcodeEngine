package ArcodeEngine.Engine.GFX

import org.lwjgl.BufferUtils
import org.lwjgl.opengl.GL11
import org.lwjgl.opengl.GL15
import org.lwjgl.opengl.GL20
import org.lwjgl.opengl.GL30
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
            GL30.glDeleteVertexArrays(vao)
        }
        for (vbo in vbos) {
            GL15.glDeleteBuffers(vbo)
        }
    }

    fun CreateVAO(): Int {
        val vaoID = GL30.glGenVertexArrays()
        vaos.add(vaoID)
        GL30.glBindVertexArray(vaoID)
        return vaoID
    }

    private fun StoreDataInAttributeList(attribNumber: Int, size: Int, data: FloatArray) {
        val vboID = GL15.glGenBuffers()
        vbos.add(vboID)
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboID) // START
        val buffer = StoreDataInFloatBuffer(data)
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW)
        GL20.glVertexAttribPointer(attribNumber, size, GL11.GL_FLOAT, false, 0, 0)
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0) // END
    }

    private fun UnbindVAO() {
        GL30.glBindVertexArray(0)
    }

    private fun BindIndicesBuffer(indices: IntArray) {
        val vboID = GL15.glGenBuffers()
        vbos.add(vboID)
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, vboID)
        val buffer = StoreDataInIntBuffer(indices)
        GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW)
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