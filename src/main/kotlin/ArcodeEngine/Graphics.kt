package ArcodeEngine

import org.libsdl.SDL
import org.libsdl.SDL_Joystick


class Graphics
{
   fun init()
   {
      SDL.SDL_Init(SDL.SDL_INIT_JOYSTICK or SDL.SDL_INIT_VIDEO)
   }

   fun drawRect(x: Int, y: Int, width: Int, height: Int): Boolean
   {
      return false
   }
}