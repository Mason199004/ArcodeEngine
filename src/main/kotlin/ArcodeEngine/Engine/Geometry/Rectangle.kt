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
            0f, 1f,
            1f, 1f,
            1f, 0f,
            0f, 0f
    )

    private val indices: IntArray = intArrayOf(
            0, 1, 2, 2, 3, 0
    )

    var mesh: Mesh

    fun IsColliding(other: Rectangle): Boolean {
        return (
            (((this.position.x <= other.position.x) && (this.position.x + this.scaleXY.first >= other.position.x))
            || ((this.position.x >= other.position.x) && (this.position.x <= other.position.x + other.scaleXY.first)))
            &&
            (((this.position.y <= other.position.y) && (this.position.y + this.scaleXY.second >= other.position.y))
            || ((this.position.y >= other.position.y) && (this.position.y <= other.position.y + other.scaleXY.second)))
        )
    }
    fun IsColliding(leftBound: Float, rightBound: Float, bottomBound: Float, topBound: Float): Boolean {
        return (
            (this.position.x <= leftBound || this.position.x + this.scaleXY.first >= rightBound
            || this.position.y + scaleXY.second >= topBound || this.position.y <= bottomBound)
        )
    }
    init {
        mesh = Loader.LoadToVAO(vertices, textureCoordinates, indices)
    }
}