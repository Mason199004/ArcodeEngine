package ArcodeEngine.Engine.Text

import ArcodeEngine.Engine.ArcodeEngine
import ArcodeEngine.Engine.GFX.Renderer
import ArcodeEngine.Engine.Geometry.Rectangle
import ArcodeEngine.Engine.Window
import org.joml.Vector2f

class TextRenderer {
    companion object {
        private var cursorPosition: Vector2f = Vector2f()
        private var chars: ArrayList<Rectangle> = ArrayList()

        private val FONT_MAP_ID: Int = ArcodeEngine.RegisterTexture("res/arcade-font.png")

        private const val NUMBER_OFFSET: Int = 16
        private const val CHAR_WIDTH: Int = 2
        private const val CHAR_HEIGHT: Int = 3

        fun DrawString(window: Window, x: Float, y: Float, msg: String, textScale: Float) {
            for(c in msg)
                DrawChar(window, x, y, c, textScale)
        }

        fun DrawChar(window: Window, x: Float, y:Float, char: Char, textScale: Float) {
            if(char == ' ') {
                cursorPosition.x += CHAR_WIDTH * textScale
                return
            }

            val isAlpha: Int = IsAlpha(char)
            val isNumber: Int = IsNumber(char)

            val texCoords = if(isAlpha != -1)
                CalculateTextureCoords('A', isAlpha)
            else if(isNumber != -1)
                CalculateTextureCoords('0', isNumber)
            else
                CalculateTextureCoords(char, 0x420beef)

            val rect: Rectangle = Rectangle(x + cursorPosition.x, y + cursorPosition.y, CHAR_WIDTH * textScale, CHAR_HEIGHT * textScale, texCoords)
            Renderer.DrawTexturedRect(window, rect, FONT_MAP_ID)

            chars.add(rect)
            cursorPosition.x += CHAR_WIDTH * textScale
        }

        private fun IsAlpha(c: Char): Int {
            if(c in 'A'..'Z')
                return (c - 0x41).toInt()
            return -1
        }
        private fun IsNumber(c: Char): Int {
            if(c in '0'..'9')
                return (c - 0x30).toInt()
            return -1
        }

        private fun CalculateTextureCoords(char: Char, idx: Int) : FloatArray {
            var row: Int = idx / 16
            var col: Int = idx % 16

            when(char) {
                'A' -> {
                    floatArrayOf(
                        col/16f,   (1 - row/3f),
                        col+1/16f, (1 - row/3f),
                        col+1/16f, (1 - ((row + 1)/3f)),
                        col/16f,   (1 - ((row + 1) / 3f))
                    )
                }
                '0' -> {
                    row = (idx+NUMBER_OFFSET) / 16
                    col = (idx+NUMBER_OFFSET) % 16

                    floatArrayOf(
                        col/16f,   (1 - row/3f),
                        col+1/16f, (1 - row/3f),
                        col+1/16f, (1 - ((row + 1)/3f)),
                        col/16f,   (1 - ((row + 1) / 3f))
                    )
                }
            }

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