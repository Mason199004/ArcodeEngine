package ArcodeEngine.Engine.Geometry

import ArcodeEngine.Engine.GFX.Loader
import ArcodeEngine.Engine.GFX.Mesh
import org.joml.Vector2f

/**
 * This is the object representation of a four-sided 2-D primitive.
 * */
class Rectangle(x: Float, y: Float, width: Float, height: Float) : Geometry(Vector2f(x, y), 0f, Pair(width, height)){
    private val vertices: FloatArray = floatArrayOf(
            0f, 0f, 0f,
            1f, 0f, 0f,
            1f, 1f, 0f,
            0f, 1f, 0f
    )

    private val textureCoordinates: FloatArray = floatArrayOf(
            0f, 0f,
            1f, 0f,
            1f, 1f,
            0f, 1f
    )

    private val indices: IntArray = intArrayOf(
            0, 1, 2, 2, 3, 0
    )

    var mesh: Mesh

    init {
        mesh = Loader.LoadToVAO(vertices, textureCoordinates, indices)
    }
}