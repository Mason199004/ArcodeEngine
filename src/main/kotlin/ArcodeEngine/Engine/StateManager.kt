package ArcodeEngine.Engine

import java.util.*

class StateManager {
    companion object {
        private val states: Stack<State> = Stack()
        private lateinit var currentState: State //the entire point of using kotlin is to avoid null

        fun tickState() {
            if (currentState != null) {
                var lastTime = System.nanoTime()
                val amountOfTicks = 60.0
                val ns = 1000000000 / amountOfTicks
                var delta = 0.0
                var timer = System.currentTimeMillis()
                var frames = 0
                while (true) {
                    val now = System.nanoTime()
                    delta += (now - lastTime) / ns
                    lastTime = now
                    while (delta >= 1) {
                        currentState.tick()
                        delta--
                    }
                    currentState.tick()
                    currentState.render()
                    frames++
                    if (System.currentTimeMillis() - timer > 1000) {
                        timer += 1000
                        println("FPS: $frames, Joystick1_State: ${Controller.getJoystickAxes()[0]} ${Controller.getJoystickAxes()[1]}")
                        frames = 0
                    }
                }
            }
        }

        fun pushState(state: State) {
            states.push(state)
            currentState = state
            currentState.init()
        }

        fun popState(): State {
            return states.pop()
        }
    }
}