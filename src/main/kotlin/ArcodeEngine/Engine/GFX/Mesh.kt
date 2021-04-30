package ArcodeEngine.Engine.GFX

import org.lwjgl.BufferUtils
import org.lwjgl.opengl.GL11.*
import java.io.FileInputStream
import java.io.IOException
import java.nio.IntBuffer
import javax.imageio.ImageIO


open class Mesh(var vaoID: Int, var vertices: FloatArray, var textureCoordinates: FloatArray, var indices: IntArray) {}

class Texture(var path: String) {
    var width: Int = 0
    var height: Int = 0

    var textureID: Int

    init {
        textureID = Load(path)
    }

    private fun Load(path: String): Int {
        var pixels: IntArray? = null
        var IMG_SIZE = 0
        try {
            val img = ImageIO.read(FileInputStream(path))
            width = img.width
            height = img.height
            IMG_SIZE = width * height
            pixels = IntArray(IMG_SIZE)
            img.getRGB(0, 0, width, height, pixels, 0, width)
        } catch (e: IOException) {
            e.printStackTrace()
        }
        val data = IntArray(IMG_SIZE)
        for (i in 0 until IMG_SIZE) {
            val a = pixels!![i] and -0x1000000 shr 24
            val r = pixels[i] and 0xff0000 shr 16
            val g = pixels[i] and 0xff00 shr 8
            val b = pixels[i] and 0xff
            data[i] = a shl 24 or (b shl 16) or (g shl 8) or r
        }
        val buffer: IntBuffer = BufferUtils.createIntBuffer(data.size)
        buffer.put(data)
        buffer.flip()
        val tex: Int = glGenTextures()
        glBindTexture(GL_TEXTURE_2D, tex)
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR)
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST)
        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, buffer)
        glBindTexture(GL_TEXTURE_2D, 0)
        return tex
    }
}