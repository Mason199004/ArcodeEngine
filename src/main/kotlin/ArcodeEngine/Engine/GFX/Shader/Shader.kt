package ArcodeEngine.Engine.GFX.Shader

import ArcodeEngine.Engine.Util.OpenGL
import org.joml.Matrix4f
import org.joml.Vector3f
import org.lwjgl.BufferUtils
import org.lwjgl.opengl.GL46
import org.lwjgl.opengl.GL46.*
import java.io.BufferedReader
import java.io.FileReader
import java.io.IOException
import java.security.InvalidParameterException
import kotlin.system.exitProcess


class Shader(vertFile: String, fragFile: String) {
    private val programID: Int
    private val vertexShaderID: Int
    private val fragmentShaderID: Int
    private val matBuffer = BufferUtils.createFloatBuffer(16)

    private var uniforms: HashMap<String, Int> = HashMap()

    fun start() {
        OpenGL.GLUseProgram(programID)
    }

    fun stop() {
        OpenGL.GLUseProgram(0)
    }

    fun cleanUp() {
        stop()
        OpenGL.GLDetachShader(programID, vertexShaderID)
        OpenGL.GLDetachShader(programID, fragmentShaderID)
        OpenGL.GLDeleteShader(vertexShaderID)
        OpenGL.GLDeleteShader(fragmentShaderID)
        OpenGL.GLDeleteProgram(programID)
    }

    fun bindAttribute(attribute: Int, variableName: String) {
        OpenGL.GLBindAttribLocation(programID, attribute, variableName)
    }

    fun loadFloat(uniformName: String, value: Float) {
        TryGetUniform(uniformName)
        OpenGL.GLUniform1f(uniforms[uniformName]!!, value)
    }

    fun loadVector3f(uniformName: String, vec: Vector3f) {
        TryGetUniform(uniformName)
        OpenGL.GLUniform3f(uniforms[uniformName]!!, vec.x, vec.y, vec.z)
    }

    fun loadBoolean(uniformName: String, value: Int) {
        TryGetUniform(uniformName)
        OpenGL.GLUniform1i(uniforms[uniformName]!!, value)
    }

    fun loadMatrix4f(uniformName: String, mat: Matrix4f) {
        TryGetUniform(uniformName)
        mat[matBuffer]
        matBuffer.clear()
        OpenGL.GLUniformMatrix4fv(uniforms[uniformName]!!, false, matBuffer)
    }

    private fun TryGetUniform(name: String) {
        val location: Int = OpenGL.GLGetUniformLocation(programID, name)
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
                exitProcess(-1)
            }
            val shaderID = OpenGL.GLCreateShader(type)
            OpenGL.GLShaderSource(shaderID, shaderSource)
            OpenGL.GLCompileShader(shaderID)
            if (OpenGL.GLGetShaderi(shaderID, GL_COMPILE_STATUS) == GL_FALSE) {
                println(OpenGL.GLGetShaderInfoLog(shaderID, 500))
                System.err.println("Could not compile shader!")
                exitProcess(-1)
            }
            return shaderID
        }
    }

    init {
        vertexShaderID = loadShader(vertFile, GL_VERTEX_SHADER)
        fragmentShaderID = loadShader(fragFile, GL_FRAGMENT_SHADER)
        programID = OpenGL.GLCreateProgram()
        OpenGL.GLAttachShader(programID, vertexShaderID)
        OpenGL.GLAttachShader(programID, fragmentShaderID)
        OpenGL.GLLinkProgram(programID)
        OpenGL.GLValidateProgram(programID)
    }
}