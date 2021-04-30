package ArcodeEngine.Engine.GFX

import ArcodeEngine.Engine.Geometry.Rectangle
import org.joml.Vector2f

/**
 * This is the object representation of a string of text.
 */

class Text (private var x: Float, private var y: Float, private var msg: String, private var textScale: Float){
    companion object {
        const val DEFAULT_TEXT_WIDTH = 1f
        const val DEFAULT_TEXT_HEIGHT = 16/9f
    }
}