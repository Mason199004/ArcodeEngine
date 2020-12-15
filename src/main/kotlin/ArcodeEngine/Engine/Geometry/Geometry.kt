package ArcodeEngine.Engine.Geometry

import ArcodeEngine.Engine.Util.Direction
import org.joml.Matrix4f
import org.joml.Vector2f
import org.joml.Vector3f

open class Geometry(var position: Vector2f, var rotation: Float) {
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

    fun GetTransformMatrix(): Matrix4f {
        return Matrix4f().identity()
                .translate(position.x, position.y, 0f)
                .rotate(rotation, Vector3f(0f, 0f, 1f))
                .scale(1f)
    }
}