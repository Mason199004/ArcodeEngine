package ArcodeEngine.Engine.Util

import org.lwjgl.opengl.GL
import org.lwjgl.opengl.GL15
import org.lwjgl.opengl.GL30
import org.lwjgl.opengl.GL46.*
import java.nio.FloatBuffer

class OpenGL {
    companion object {
        fun CreateCapabilities() {
            GL.createCapabilities()
        }

        fun GLClear(mask: Int) {
            glClear(mask)
        }

        fun GLViewport(x: Int, y: Int, w: Int, h: Int) {
            glViewport(x, y, w, h)
        }

        fun GLClearColor(r: Float, g: Float, b: Float, a: Float) {
            glClearColor(r, g, b, a)
        }

        fun GLActiveTexture(texture: Int) {
            glActiveTexture(texture)
        }

        fun GLBindTexture(target: Int, texture: Int) {
            glBindTexture(target, texture)
        }

        fun GLGenVertexArrays(): Int {
            return glGenVertexArrays()
        }

        fun GLDeleteVertexArrays(array: Int) {
            glDeleteVertexArrays(array)
        }

        fun GLBindVertexArray(array: Int) {
            glBindVertexArray(array)
        }

        fun GLEnableVertexArray(index: Int) {
            glEnableVertexAttribArray(index)
        }

        fun GLEnableVertexAttribArray(index: Int) {
            glEnableVertexAttribArray(index)
        }

        fun GLDisableVertexAttribArray(index: Int) {
            glDisableVertexAttribArray(index)
        }

        fun GLGenBuffers(): Int {
            return glGenBuffers()
        }

        fun GLDeleteBuffers(buffer: Int) {
            glDeleteBuffers(buffer)
        }

        fun GLBindBuffer(type: Int, buffer: Int) {
            glBindBuffer(type, buffer)
        }

        fun GLBindAttribLocation(program: Int, index: Int, name: String) {
            glBindAttribLocation(program, index, name)
        }

        fun GLDrawElements(mode: Int, count: Int, type: Int, indices: Long) {
            glDrawElements(mode, count, type, indices);
        }

        fun GLCreateProgram(): Int {
            return glCreateProgram()
        }

        fun GLDeleteProgram(program: Int) {
            glDeleteProgram(program)
        }

        fun GLUseProgram(program: Int) {
            glUseProgram(program)
        }

        fun GLLinkProgram(program: Int) {
            glLinkProgram(program)
        }

        fun GLValidateProgram(program: Int) {
            glValidateProgram(program)
        }

        fun GLCreateShader(type: Int): Int {
            return glCreateShader(type)
        }

        fun GLDeleteShader(shader: Int) {
            glDeleteShader(shader)
        }

        fun GLAttachShader(program: Int, shader: Int) {
            glAttachShader(program, shader)
        }

        fun GLDetachShader(program: Int, shader: Int) {
            glDetachShader(program, shader)
        }

        fun GLShaderSource(shader: Int, string: CharSequence) {
            glShaderSource(shader, string)
        }

        fun GLCompileShader(shader: Int) {
            glCompileShader(shader)
        }

        fun GLGetShaderi(shader: Int, pname: Int): Int {
            return glGetShaderi(shader, pname)
        }

        fun GLGetShaderInfoLog(shader: Int, maxLength: Int): String {
            return glGetShaderInfoLog(shader, maxLength)
        }

        fun GLGetUniformLocation(program: Int, name: String): Int {
            return glGetUniformLocation(program, name)
        }

        fun GLUniform1f(uniform: Int, value: Float) {
            glUniform1f(uniform, value)
        }

        fun GLUniform1i(uniform: Int, value: Int) {
            glUniform1i(uniform, value)
        }

        fun GLUniform3f(uniform: Int, v0: Float, v1: Float, v2: Float) {
            glUniform3f(uniform, v0, v1, v2)
        }

        fun GLUniformMatrix4fv(uniform: Int, transpose: Boolean, value: FloatBuffer) {
            glUniformMatrix4fv(uniform, transpose, value)
        }
    }
}