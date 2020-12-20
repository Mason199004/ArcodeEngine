package ArcodeEngine.Engine.Geometry

import ArcodeEngine.Engine.Util.Direction
import org.joml.Matrix4f
import org.joml.Vector2f
import org.joml.Vector3f

/**
 * This is the base class for all 2-D primitives
 * */
open class Geometry(var position: Vector2f, var rotation: Float, var scaleXY: Pair<Float, Float>) {
    /**
     * @param direction The direction (UP, DOWN, LEFT, RIGHT) that you want to move in.
     * @param distance The distance you want to move
     * */
    fun Move(direction: Direction, distance: Float) {
        when(direction) {
            Direction.UP -> {
                position.add(0f, -distance)
            }
            Direction.DOWN -> {
                position.add(0f, distance)
            }
            Direction.LEFT -> {
                position.add(-distance, 0f)
            }
            Direction.RIGHT -> {
                position.add(distance, 0f)
            }
        }
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
                .translate((position.x / 50f) - 1, (position.y / 50f) - 1, 0f)
                .rotate(rotation, Vector3f(0f, 0f, 1f))
                .scale(scaleXY.first, scaleXY.second, 1f)
    }
}