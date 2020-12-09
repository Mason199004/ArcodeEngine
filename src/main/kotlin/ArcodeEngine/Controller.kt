package ArcodeEngine

import org.libsdl.SDL.*
import org.libsdl.SDL_Joystick


class Controller
{
    companion object
    {
        lateinit var JoySticks: MutableList<SDL_Joystick>
        fun init()
        {
            if (SDL_Init(SDL_INIT_TIMER or SDL_INIT_VIDEO or SDL_INIT_JOYSTICK or SDL_INIT_GAMECONTROLLER or SDL_INIT_HAPTIC) < 0)
            {
                throw InitException("Failed to Initialize SDL")
            }
            else
            {
                JoySticks = mutableListOf()
                SDL_JoystickEventState(1)
                for (i in 0..SDL_NumJoysticks())
                {
                    JoySticks.add(SDL_Joystick.JoystickOpen(i))
                }
                var event: Event = Event()
                while (true)
                {
                    SDL_PollEvent(event)
                    println(JoySticks[0].getAxis(0))
                }
            }

        }
    }
}

class InitException(message: String) : Exception(message)