package ArcodeEngine.Engine.Geometry

import ArcodeEngine.Engine.Util.Direction
import org.joml.Matrix4f
import org.joml.Vector2f
import org.joml.Vector3f
import java.awt.Dimension

/**
 * This is the base class for all 2-D primitives
 * */
open class Geometry(protected var position: Vector2f, private var rotation: Float, protected var scaleXY: Pair<Float, Float>) {

    fun Move(x: Float, y: Float) {
        position.add(x, y)
    }
    fun Move(vec: Vector2f) {
        position.add(vec)
    }

    /**
     * @param direction The direction (LEFT or RIGHT) in which you want to rotate.
     * @param angle The angle, in degrees, that you want to rotate.
     * */
    fun Rotate(direction: Direction, angle: Float) {
        when(direction) {
            Direction.LEFT -> {
                rotation += (angle * (Math.PI.toFloat() / 180))
            }
            Direction.RIGHT -> {
                rotation -= (angle * (Math.PI.toFloat() / 180))
            }
            else -> {}
        }
    }

    /**
     * This function calculates, and returns the transformation matrix of the shape.
     * @return The shape's transformation matrix
     * */
    fun GetTransformMatrix(): Matrix4f {
        return Matrix4f().identity()
                .translate(position.x, position.y, 0f)
                .rotate(rotation, Vector3f(0f, 0f, 1f))
                .scale(scaleXY.first, scaleXY.second, 1f)
    }

    fun GetX(): Float {
        return position.x
    }

    fun SetX(value: Float) {
        position.x = value
    }

    fun GetY(): Float {
        return position.y
    }

    fun SetY(value: Float) {
        position.y = value
    }

    fun GetRotation(): Float {
        return rotation
    }

    fun GetWidth(): Float {
        return scaleXY.first
    }

    fun GetHeight(): Float {
        return scaleXY.second
    }

    fun SetDimensions(width: Float, height: Float) {
        scaleXY = Pair(width, height)
    }
}