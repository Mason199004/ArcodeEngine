package ArcodeEngine.Engine.Geometry

import org.joml.Vector2f

class Text (private var x: Float, private var y: Float, private var msg: String, private var textScale: Float){
    companion object {
        private const val NUMBER_OFFSET: Int = 38
        private const val CHAR_WIDTH: Int = 2
        private const val CHAR_HEIGHT: Int = 3
    }

    private var cursorPosition: Vector2f = Vector2f()
    lateinit var chars: Array<Rectangle>

    init {
        Generate()
    }

    private fun Generate() {
        msg = msg.toUpperCase()

        chars = Array(msg.length - msg.filter{ c -> c == ' '}.length) { i ->
            Rectangle(0f, 0f, 0f, 0f, floatArrayOf())
        }


        var spaces = 0
        for(idx in msg.indices) {
            val char = msg[idx]

            if(char == ' ') {
                cursorPosition.x += CHAR_WIDTH * textScale
                spaces++
                continue
            }

            val texCoords = when {
                IsAlpha(char) != -1 -> CalculateTextureCoords('A', IsAlpha(char))
                IsNumber(char) != -1 -> CalculateTextureCoords('0', IsNumber(char))
                else -> CalculateTextureCoords(char, 0x420beef)
            }

            chars[idx - spaces] = Rectangle(
                    x + cursorPosition.x,
                    y + cursorPosition.y,
                    CHAR_WIDTH * textScale,
                    CHAR_HEIGHT * textScale,
                    texCoords
            )
            cursorPosition.x += CHAR_WIDTH * textScale
        }
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
                return floatArrayOf(
                        col/16f,   ((row/3f) + 1/3f),
                        col/16f + 1/16f, ((row/3f) + 1/3f),
                        col/16f + 1/16f, (row/3f),
                        col/16f,   (row/3f)
                )
            }

            '0' -> {
                row = (idx+ NUMBER_OFFSET) / 16
                col = (idx+ NUMBER_OFFSET) % 16

                return floatArrayOf(
                        col/16f,   ((row/3f) + 1/3f),
                        col/16f + 1/16f, ((row/3f) + 1/3f),
                        col/16f + 1/16f, (row/3f),
                        col/16f,   (row/3f)
                )
            }

            '!'-> {
                return floatArrayOf(
                        10/16f, 2/3f,
                        11/16f, 2/3f,
                        11/16f, 1/3f,
                        10/16f, 1/3f
                )
            }

            '*' -> {
                return floatArrayOf(
                        11f / 16, 2f / 3,
                        12f / 16, 2f / 3,
                        12f / 16, 1f / 3,
                        11f / 16, 1f / 3
                )
            }

            '#' -> {
                return floatArrayOf(
                        12f / 16, 2f / 3,
                        13f / 16, 2f / 3,
                        13f / 16, 1f / 3,
                        12f / 16, 1f / 3
                )
            }

            '%' -> {
                return floatArrayOf(
                        13f / 16, 2f / 3,
                        14f / 16, 2f / 3,
                        14f / 16, 1f / 3,
                        13f / 16, 1f / 3
                )
            }

            '(' -> {
                return floatArrayOf(
                        14f / 16, 2f / 3,
                        15f / 16, 2f / 3,
                        15f / 16, 1f / 3,
                        14f / 16, 1f / 3
                )
            }

            ')' -> {
                return floatArrayOf(
                        15f / 16, 2f / 3,
                        1f, 2f / 3,
                        1f, 1f / 3,
                        15f / 16, 1f / 3
                )
            }

            '-' -> {
                return floatArrayOf(
                        0f, 1f,
                        1 / 16f, 1f,
                        1 / 16f, 2/3f,
                        0f, 2/3f
                )
            }

            '+' -> {
                return floatArrayOf(
                        1 / 16f, 1f,
                        2 / 16f, 1f,
                        2 / 16f, 2/3f,
                        1 / 16f, 2/3f
                )
            }

            '=' -> {
                return floatArrayOf(
                        2 / 16f, 1f,
                        3 / 16f, 1f,
                        3 / 16f, 2/3f,
                        2 / 16f, 2/3f
                )
            }

            '.' -> {
                return floatArrayOf(
                        3 / 16f, 1f,
                        4 / 16f, 1f,
                        4 / 16f, 2/3f,
                        3 / 16f, 2/3f
                )
            }

            ',' -> {
                return floatArrayOf(
                        4 / 16f, 1f,
                        5 / 16f, 1f,
                        5 / 16f, 2/3f,
                        4 / 16f, 2/3f
                )
            }

            '?' -> {
                return floatArrayOf(
                        5 / 16f, 1f,
                        6 / 16f, 1f,
                        6 / 16f, 2/3f,
                        5 / 16f, 2/3f
                )
            }

            else -> {
                System.err.println("The character you entered: $char is not supported")
                /* displays asterisk if an unsupported character is passed */
                return floatArrayOf(
                        11f / 16, 2f / 3,
                        12f / 16, 2f / 3,
                        12f / 16, 1f / 3,
                        11f / 16, 1f / 3
                )
            }
        }
    }

    fun SetMsg(msg: String) {
        this.msg = msg
        cursorPosition.x = 0f
        cursorPosition.y = 0f
        Generate()
    }

    fun AddY(y: Float) {
        for(c in chars)
            c.SetY(c.GetY() + y)
    }
}