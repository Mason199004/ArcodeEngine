package ArcodeEngine.Engine.GFX

import org.joml.*

class Renderer {

    //I DIDNT REALIZE YOU ALREADY STARTED WITH GEOMETRY UNTIL I WROTE THIS
    //FEEL FREE TO DISREGARD ALL OF THIS



    companion object {
        /**
         * Draws a Rectangle with specified color at the specified coordinates with the specified width and height
         * @param x X Coordinate to draw at
         * @param y Y Coordinate to draw at
         * @param width Width of Rectangle
         * @param height Height of Rectangle
         *
         */
        fun DrawColoredRect(x: Int, y: Int, width: Int, height: Int,color: Vector3f)
        {
            throw NotImplementedError()
        }

        /**
         * Creates a Rectangle object
         * @return new Rectangle with default constructor
         */
        fun CreateRect(): Rectangle
        {
            return Rectangle()
        }
    }

}

/**
 *  Superclass for all shapes
 *  @property x X coordinate
 *  @property y Y coordinate
 */
abstract class Shape(val x: Int, val y: Int)
{
    /**
     * Sets position of Shape
     * @param x X position
     * @param y Y position
     * @return self with new parameters
     */
    fun SetPos(x: Int, y: Int): Shape
    {
        throw NotImplementedError()
    }

    /**
     * Rotates current Shape
     * @param radians new rotation in radians
     * @return self with new rotation
     */
    fun Rotate(radians: Double): Shape
    {
        throw NotImplementedError()
    }

    /**
     * Rotates current Shape
     * @param degrees new rotation in degrees
     * @return self with new rotation
     */
    fun Rotate(degrees: Int): Shape
    {
        throw NotImplementedError()
    }

    /**
     * Applies an effect
     * @param effect Effect to be applied
     * @return self with new effect
     */
    fun ApplyEffect(effect: Effect): Shape
    {
        throw NotImplementedError()
    }
}

/**
 * Rectangle
 * @property x X coordinate
 * @property y Y coordinate
 * @property width Width
 * @property height Height
 */
class Rectangle(x: Int = 0, y: Int = 0, val width: Int = 5, val height: Int = 5): Shape(x, y)
{
    /**
     * Resizes self to specified width and height
     * @param width new width
     * @param height new height
     * @return self with new parameters
     */
    fun Resize(width: Int, height: Int): Shape
    {
        throw NotImplementedError()
    }
}

/**
 * Effect enum class
 * @property intensity Intensity of effect
 * @property speed Speed of effect animation, if animated
 * @property tick Current tick, for animation
 */
enum class Effect(val intensity: Float = 1f, val speed: Float = 1f) //this could be used to allow for the making of simple effects, such as applying sine distortion
{                                                                   // which could (for example) be used on a fire sprite to add more 'realism'
    Sine;
    var tick: Long = 0

    /**
     * Sets the current tick for animated effects
     * @param tick Current tick
     */
    fun SetTick(tick: Long) {}
}