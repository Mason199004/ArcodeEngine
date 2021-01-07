package ArcodeEngine.Engine

import org.joml.Matrix4f
import org.joml.Vector3f

enum class ProjectionMode {
    PERSPECTIVE,
    ORTHOGRAPHIC
}

class Camera(var position: Vector3f, var rotation: Vector3f, var mode: ProjectionMode, var bounds: Pair<Float, Float>) {
    private lateinit var projectionMatrix: Matrix4f

    constructor(mode: ProjectionMode, screenDimension: Pair<Float, Float>)
            : this(Vector3f(), Vector3f(), mode, screenDimension)
    constructor(position: Vector3f, mode: ProjectionMode, screenDimension: Pair<Float, Float>)
            : this(position, Vector3f(), mode, screenDimension)

    init{
        CalculateProjectionMatrix()
    }

    fun SetProjectionMode(mode: ProjectionMode) {
        this.mode = mode
        CalculateProjectionMatrix()
    }

    fun GetProjectionMatrix(): Matrix4f {
        return projectionMatrix
    }

    private fun CalculateProjectionMatrix() {
        projectionMatrix = when(mode) {
            ProjectionMode.PERSPECTIVE ->
                Matrix4f().identity()
                        .perspective(Math.PI.toFloat() / 3, bounds.first/bounds.second, -1f, 1000f)
            ProjectionMode.ORTHOGRAPHIC ->
                Matrix4f().identity()
                        .ortho(0f, 50f * (bounds.first / bounds.second), 0f, 50f, -1f, 1f)
        }
    }

    fun GetViewMatrix(): Matrix4f {
        return Matrix4f().identity()
            .rotateX(rotation.x)
            .rotateY(rotation.y)
            .rotateZ(rotation.z)
            .translate(-position.x, -position.y, -position.z)
    }
}
