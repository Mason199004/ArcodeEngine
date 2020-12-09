package ArcodeEngine.Engine

import org.libsdl.SDL.*
import org.libsdl.SDL_Joystick


class Controller
{
    companion object
    {
        lateinit var JoySticks: MutableList<SDL_Joystick>
        fun init()
        {
            try {
                if (SDL_Init(SDL_INIT_JOYSTICK) < 0)
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
            catch (e: InitException)
            {
                throw e
            }
            catch (e: Exception)
            {
                e.printStackTrace()
                throw InitException("Failed to Initialize SDL")
            }



        }
    }
}

class InitException(message: String) : Exception(message)