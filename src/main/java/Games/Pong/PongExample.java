package Games.Pong;

import ArcodeEngine.Engine.*;
import ArcodeEngine.Engine.GFX.Text;
import ArcodeEngine.Engine.Util.Direction;
import org.joml.*;

import ArcodeEngine.Engine.GFX.*;
import ArcodeEngine.Engine.Geometry.Rectangle;

import java.lang.Math;

public class PongExample extends GameState {
    private final Window window;

    private Rectangle leftPaddle = null;
    private Rectangle rightPaddle = null;

    private Rectangle ball = null;

    private int leftScore = 0;
    private int rightScore = 0;

    private static final float SCORE_TEXT_SCALE = 2f;
    private final float scoreTextY;

    private final float BALL_H_SPEED = 0.4f;
    private final float BALL_V_SPEED = 0.3f;
    private final Vector2f ballVelocity = new Vector2f(BALL_H_SPEED, 0f);

    public PongExample(Window window) {
        super("Pong", window);
        this.window = window;

        scoreTextY = window.GetMaxHeight() - (Text.DEFAULT_TEXT_HEIGHT * SCORE_TEXT_SCALE);
    }

    @Override
    public void Init() {
        leftScore = 0;
        rightScore = 0;

        leftPaddle = new Rectangle(1f, 10f, 2f, 9f);
        rightPaddle = new Rectangle(window.GetMaxWidth() - 3, 10f, 2f, 9f);
        ball = new Rectangle(window.GetMaxWidth() / 2, window.GetMaxHeight() / 2, 2f, 2f);
    }

    @Override
    public void Tick() {
        float leftJoystick = Controller.GetJoystickState(1).GetY() * -0.3f;
        float rightJoystick = Controller.GetJoystickState(2).GetY() * -0.3f;

        leftPaddle.Move(Direction.UP, leftJoystick);
        rightPaddle.Move(Direction.UP, rightJoystick);

        if(ball.GetY() < 0) {
            ball.SetY(0f);
            ballVelocity.y *= -1;
        } else if(ball.GetY() + ball.GetHeight() > window.GetMaxHeight()) {
            ball.SetY(window.GetMaxHeight() - ball.GetHeight());
            ballVelocity.y *= -1;
        }

        if(leftPaddle.IsColliding(ball)) {
            ballVelocity.x = BALL_H_SPEED;
            float t = -(ballVelocity.y + leftJoystick);
            if (t > 0)
                t = Math.min(t, BALL_V_SPEED);
            else
                t = Math.max(t, -BALL_V_SPEED);
            ballVelocity.y = t;
        } else if(ball.GetX() < leftPaddle.GetX() - 0.5f) {
            ball = new Rectangle(window.GetMaxWidth() / 2, window.GetMaxHeight() / 2, 2f, 2f);
            ballVelocity.x = BALL_H_SPEED;
            ballVelocity.y = 0f;
            rightScore++;
        }

        if(rightPaddle.IsColliding(ball)) {
            ballVelocity.x = -BALL_H_SPEED;
            float t = -(ballVelocity.y + leftJoystick);
            if (t > 0)
                t = Math.min(t, BALL_V_SPEED);
            else
                t = Math.max(t, -BALL_V_SPEED);
            ballVelocity.y = t;
        } else if(ball.GetX() > rightPaddle.GetX() + 0.5f) {
            ball = new Rectangle(window.GetMaxWidth() / 2, window.GetMaxHeight() / 2, 2f, 2f);
            ballVelocity.x = -BALL_H_SPEED;
            ballVelocity.y = 0f;
            leftScore++;
        }

        if(leftPaddle.GetY() < 0) {
            leftPaddle.SetY(0);
        } else if((leftPaddle.GetY() + rightPaddle.GetHeight()) > window.GetMaxHeight()) {
            leftPaddle.SetY(window.GetMaxHeight() - leftPaddle.GetHeight());
        }

        if(rightPaddle.GetY() < 0) {
            rightPaddle.SetY(0);
        } else if((rightPaddle.GetY() + rightPaddle.GetHeight()) > window.GetMaxHeight()) {
            rightPaddle.SetY(window.GetMaxHeight() - rightPaddle.GetHeight());
        }

        ball.Move(ballVelocity);
    }

    @Override
    public void Render() {
        Renderer.DrawRect(window, leftPaddle, new Vector3f(0f, 1f, 1f));
        Renderer.DrawRect(window, rightPaddle, new Vector3f(0f, 1f, 1f));
        Renderer.DrawRect(window, ball, new Vector3f(1f, 0f, 0f));

        Renderer.DrawString(window, 0.5f, scoreTextY, "" + leftScore);
        Renderer.DrawString(window, window.GetMaxWidth() - (Text.DEFAULT_TEXT_WIDTH * SCORE_TEXT_SCALE * 2), scoreTextY, "" + rightScore);
    }

}
