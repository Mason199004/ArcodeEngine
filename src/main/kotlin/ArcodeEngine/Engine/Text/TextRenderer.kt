package ArcodeEngine.Engine.Text

import ArcodeEngine.Engine.Geometry.Rectangle
import org.joml.Vector2f

class TextRenderer {
    companion object {
        private var cursorPosition: Vector2f = Vector2f()
        private var chars: ArrayList<Rectangle> = ArrayList()

        fun DrawString(msg: String) {

        }

        fun DrawChar(char: Char) {

        }

        private fun CalculateTextureCoords(char: Char) : FloatArray {
            when(char) {
                'A' -> {

                }
                else -> {
                    System.err.println("The character you entered: $char is not supported")
                    /* displays asterisk if an unsupported character is passed */
                    return floatArrayOf(
                        12f / 16, 2f / 3,
                        13f / 16, 2f / 3,
                        13f / 16, 1f / 3,
                        12f / 16, 1f / 3
                    )
                }
            }
        }
    }
}