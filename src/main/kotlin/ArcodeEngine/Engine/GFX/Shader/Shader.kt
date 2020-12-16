package ArcodeEngine.Engine.GFX.Shader

import org.joml.Matrix4f
import org.joml.Vector3f
import org.lwjgl.BufferUtils
import org.lwjgl.opengl.GL11
import org.lwjgl.opengl.GL20
import java.io.BufferedReader
import java.io.FileReader
import java.io.IOException
import java.security.InvalidParameterException


class Shader(vertFile: String, fragFile: String) {
    private val programID: Int
    private val vertexShaderID: Int
    private val fragmentShaderID: Int
    private val matBuffer = BufferUtils.createFloatBuffer(16)

    private var uniforms: HashMap<String, Int> = HashMap()

    fun start() {
        GL20.glUseProgram(programID)
    }

    fun stop() {
        GL20.glUseProgram(0)
    }

    fun cleanUp() {
        stop()
        GL20.glDetachShader(programID, vertexShaderID)
        GL20.glDetachShader(programID, fragmentShaderID)
        GL20.glDeleteShader(vertexShaderID)
        GL20.glDeleteShader(fragmentShaderID)
        GL20.glDeleteProgram(programID)
    }

    fun bindAttribute(attribute: Int, variableName: String?) {
        GL20.glBindAttribLocation(programID, attribute, variableName)
    }

    fun loadFloat(uniformName: String, value: Float) {
        TryGetUniform(uniformName)
        GL20.glUniform1f(uniforms[uniformName]!!, value)
    }

    fun loadVector3f(uniformName: String, vec: Vector3f) {
        TryGetUniform(uniformName)
        GL20.glUniform3f(uniforms[uniformName]!!, vec.x, vec.y, vec.z)
    }

    fun loadBoolean(uniformName: String, value: Int) {
        TryGetUniform(uniformName)
        GL20.glUniform1i(uniforms[uniformName]!!, value)
    }

    fun loadMatrix4f(uniformName: String, mat: Matrix4f) {
        TryGetUniform(uniformName)
        mat[matBuffer]
        matBuffer.clear()
        GL20.glUniformMatrix4fv(uniforms[uniformName]!!, false, matBuffer)
    }

    private fun TryGetUniform(name: String) {
        val location: Int = GL20.glGetUniformLocation(programID, name)
        if(location == -1)
            throw InvalidParameterException("Could not find uniform variable: $name")
        else
            uniforms[name] = location
    }

    companion object {
        private fun loadShader(file: String, type: Int): Int {
            val shaderSource = StringBuilder()
            try {
                val reader = BufferedReader(FileReader(file))
                var line: String?
                while (reader.readLine().also { line = it } != null) {
                    shaderSource.append(line).append("\n")
                }
                reader.close()
            } catch (e: IOException) {
                System.err.println("Could not find file!")
                e.printStackTrace()
                System.exit(-1)
            }
            val shaderID = GL20.glCreateShader(type)
            GL20.glShaderSource(shaderID, shaderSource)
            GL20.glCompileShader(shaderID)
            if (GL20.glGetShaderi(shaderID, GL20.GL_COMPILE_STATUS) == GL11.GL_FALSE) {
                println(GL20.glGetShaderInfoLog(shaderID, 500))
                System.err.println("Could not compile shader!")
                System.exit(-1)
            }
            return shaderID
        }
    }

    init {
        vertexShaderID = loadShader(vertFile, GL20.GL_VERTEX_SHADER)
        fragmentShaderID = loadShader(fragFile, GL20.GL_FRAGMENT_SHADER)
        programID = GL20.glCreateProgram()
        GL20.glAttachShader(programID, vertexShaderID)
        GL20.glAttachShader(programID, fragmentShaderID)
        GL20.glLinkProgram(programID)
        GL20.glValidateProgram(programID)
    }
}