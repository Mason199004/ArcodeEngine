package ArcodeEngine.Cabinet.Games

import ArcodeEngine.Engine.*
import ArcodeEngine.Engine.GFX.Renderer
import ArcodeEngine.Engine.Geometry.Rectangle
import ArcodeEngine.Engine.Util.OpenGL
import org.joml.Vector3f
import org.lwjgl.opengl.GL46.*
import kotlin.random.Random




class Chess(window: Window) : GameState("Chess", window) {





    companion object {
        val pmap = mapOf(Pair('p', PieceType.PAWN), Pair('n', PieceType.KNIGHT), Pair('b', PieceType.BISHOP), Pair('r', PieceType.ROOK), Pair('q', PieceType.QUEEN), Pair('k', PieceType.KING))
        var BOARD_START = 0f
        fun FENToPieces(str: String): MutableList<Piece>
        {
            var list = mutableListOf<Piece>()
            var num = 0
            for (i in str)
            {
                if (i != '/')
                {
                    if (!i.isDigit()) {
                        list.add(
                                Piece(
                                        Color = i.isUpperCase(),
                                        Type = pmap[i.toLowerCase()]!!,
                                        x = (num % 8),
                                        y = 7 - num / 8
                                )
                        )
                        num++
                    }
                    else
                        num += (i - '0')

                }

            }
            return list
        }
        @JvmStatic
        lateinit var wind: Window
        @JvmStatic
        fun GetWindow() : Long
        {
            return wind.GetWindowHandle()
        }

    }
    var BKING = ArcodeEngine.RegisterTexture("src\\main\\kotlin\\ArcodeEngine\\Cabinet\\res\\bking.png", GL_NEAREST, GL_NEAREST)
    var WKING = ArcodeEngine.RegisterTexture("src\\main\\kotlin\\ArcodeEngine\\Cabinet\\res\\wking.png", GL_NEAREST, GL_NEAREST)
    var BROOK = ArcodeEngine.RegisterTexture("src\\main\\kotlin\\ArcodeEngine\\Cabinet\\res\\brook.png", GL_NEAREST, GL_NEAREST)
    var WROOK = ArcodeEngine.RegisterTexture("src\\main\\kotlin\\ArcodeEngine\\Cabinet\\res\\wrook.png", GL_NEAREST, GL_NEAREST)
    var BBISHOP = ArcodeEngine.RegisterTexture("src\\main\\kotlin\\ArcodeEngine\\Cabinet\\res\\bbishop.png", GL_NEAREST, GL_NEAREST)
    var WBISHOP = ArcodeEngine.RegisterTexture("src\\main\\kotlin\\ArcodeEngine\\Cabinet\\res\\wbishop.png", GL_NEAREST, GL_NEAREST)
    var BKNIGHT = ArcodeEngine.RegisterTexture("src\\main\\kotlin\\ArcodeEngine\\Cabinet\\res\\bknight.png", GL_NEAREST, GL_NEAREST)
    var WKNIGHT = ArcodeEngine.RegisterTexture("src\\main\\kotlin\\ArcodeEngine\\Cabinet\\res\\wknight.png", GL_NEAREST, GL_NEAREST)
    var BPAWN = ArcodeEngine.RegisterTexture("src\\main\\kotlin\\ArcodeEngine\\Cabinet\\res\\bpawn.png", GL_NEAREST, GL_NEAREST)
    var WPAWN = ArcodeEngine.RegisterTexture("src\\main\\kotlin\\ArcodeEngine\\Cabinet\\res\\wpawn.png", GL_NEAREST, GL_NEAREST)
    var BQUEEN = ArcodeEngine.RegisterTexture("src\\main\\kotlin\\ArcodeEngine\\Cabinet\\res\\bqueen.png", GL_NEAREST, GL_NEAREST)
    var WQUEEN = ArcodeEngine.RegisterTexture("src\\main\\kotlin\\ArcodeEngine\\Cabinet\\res\\wqueen.png", GL_NEAREST, GL_NEAREST)

    var Pieces = mutableListOf<Piece>()
    val rmap = mapOf(Pair(PieceType.PAWN,Pair(WPAWN, BPAWN)), Pair(PieceType.KNIGHT,Pair(WKNIGHT, BKNIGHT)), Pair(PieceType.BISHOP,Pair(WBISHOP, BBISHOP)), Pair(PieceType.ROOK,Pair(WROOK, BROOK)), Pair(PieceType.QUEEN,Pair(WQUEEN, BQUEEN)), Pair(PieceType.KING,Pair(WKING, BKING)))
    override fun Init() {
        BOARD_START = window.GetMaxWidth() / 2 - (4 * 5)
        OpenGL.GLClearColor(0.1f, 0.1f, 0.1f, 0f)

        wind = window;
        spaces = mutableListOf();
        for (i in 0..63)
        {
            spaces.add(Rectangle(i%8*5f + BOARD_START,i/8 * 5f + 5, 5f, 5f));
        }
        Pieces = FENToPieces("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR")
        StateManager.Start()
    }

    var ticks = 0
    var spaces = mutableListOf<Rectangle>()
    var rect = Rectangle(0f,0f, 5f, 5f)
    override fun Update(ts: Float)
    {
        if (ticks % 20 == 0)
        {
            Pieces[Random.nextInt(0, Pieces.size)].X = Random.nextInt(0, 8).also { Pieces[Random.nextInt(0, Pieces.size)].Y = Random.nextInt(0, 8) }
        }

        ticks++

        var ii = 0
        for (i in spaces)
        {
            if ((ii / 8) % 2 == 0)
            {
                if (ii % 2 != 0)
                    Renderer.DrawRect(window, i, Vector3f(1f,1f,1f))
                else
                    Renderer.DrawRect(window, i, Vector3f(0.2f,0.3f,0.2f))
            }
            else
            {
                if (ii % 2 ==0)
                    Renderer.DrawRect(window, i, Vector3f(1f,1f,1f))
                else
                    Renderer.DrawRect(window, i, Vector3f(0.2f,0.3f,0.2f))
            }
            ii++
        }

        for (i in Pieces)
        {
            Renderer.DrawRect(window, i.rect, when(i.Color)
            {
                true -> rmap[i.Type]?.first!!
                false -> rmap[i.Type]?.second!!
            })
        }
    }
}

enum class PieceType
{
    KING,
    QUEEN,
    ROOK,
    BISHOP,
    KNIGHT,
    PAWN
}

class Piece constructor(var FirstMove: Boolean = true, var Color: Boolean = false, var Type: PieceType)
{
    constructor(x: Int = 0,y: Int = 0, FirstMove: Boolean = true, Color: Boolean = false, Type: PieceType) : this(FirstMove, Color, Type)
    {
        X = x
        Y =y
    }
    var X: Int = 0
        set(value)
        {
            rect.SetX((value * 5) + Chess.BOARD_START)
            field = value
        }
    var Y: Int = 0
        set(value)
        {
            rect.SetY((value * 5) + 5f)
            field = value
        }
    var rect = Rectangle((X * 5) + Chess.BOARD_START, (Y * 5) + 5f, 5f ,5f)
}