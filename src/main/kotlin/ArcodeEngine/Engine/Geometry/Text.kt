package ArcodeEngine.Engine.Geometry

class Text {
    companion object {
        private const val NUMBER_OFFSET = 38
        const val CHAR_WIDTH = 1f
        const val CHAR_HEIGHT = 3f/2f

        fun CalculateTextureCoords(char: Char, idx: Int) : FloatArray {
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
                    row = (idx + NUMBER_OFFSET) / 16
                    col = (idx + NUMBER_OFFSET) % 16

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
    }
}