package Games.Pong;

import ArcodeEngine.Engine.*;
import ArcodeEngine.Engine.Geometry.Text;
import ArcodeEngine.Engine.Util.Direction;
import org.joml.*;

import ArcodeEngine.Engine.GFX.*;
import ArcodeEngine.Engine.Geometry.Rectangle;

public class PongExample extends GameState {
    private final Window window;

    private Rectangle leftPaddle = null;
    private Rectangle rightPaddle = null;
    private Rectangle ball = null;

    private static final float SCORE_TEXT_SCALE = 4f;
    private int leftScore = 0;
    private int rightScore = 0;
    private float leftScoreX;
    private float rightScoreX;
    private float scoreY;

    private static final float BALL_H_SPEED = 20f;
    private static final float BALL_V_SPEED = 17f;
    private final Vector2f ballVelocity = new Vector2f(BALL_H_SPEED, 0f);

    public PongExample(Window window) {
        super("Pong! (Example)", window);
        this.window = window;
    }

    @Override
    public void Init() {
        leftScore = 0;
        rightScore = 0;

        leftScoreX = 0f;
        rightScoreX = window.GetMaxWidth() - Text.CHAR_WIDTH * SCORE_TEXT_SCALE * 2;
        scoreY = window.GetMaxHeight() - Text.CHAR_HEIGHT * SCORE_TEXT_SCALE;

        leftPaddle = new Rectangle(1f, 10f, 2f, 9f);
        rightPaddle = new Rectangle(window.GetMaxWidth() - 3, 10f, 2f, 9f);
        ball = new Rectangle(window.GetMaxWidth() / 2, window.GetMaxHeight() / 2, 2f, 2f);
    }

    @Override
    public void Update(float ts) {
        float leftJoystick = Controller.GetJoystickState(1).GetY() * 1f * 17f;
        float rightJoystick = Controller.GetJoystickState(2).GetY() * 17f;

        leftPaddle.Move(0f, leftJoystick * ts);
        rightPaddle.Move(0f, rightJoystick * ts);

        if(ball.GetY() < 0) {
            ball.SetY(0f);
            ballVelocity.y *= -1;
        } else if(ball.GetY() + ball.GetHeight() > window.GetMaxHeight()) {
            ball.SetY(window.GetMaxHeight() - ball.GetHeight());
            ballVelocity.y *= -1;
        }

        if(leftPaddle.IsColliding(ball)) {
            if(ball.GetY() > leftPaddle.GetY() + (leftPaddle.GetHeight() * (2f / 3f))) {
                ballVelocity.y = BALL_V_SPEED;
                ballVelocity.x = BALL_H_SPEED / 2;
            } else if(ball.GetY() < leftPaddle.GetY() + (leftPaddle.GetHeight() / 3f)) {
                ballVelocity.y = -BALL_V_SPEED;
                ballVelocity.x = BALL_H_SPEED / 2;
            } else {
                ballVelocity.y = 0f;
                ballVelocity.x = BALL_H_SPEED;
            }
        } else if(ball.GetX() < leftPaddle.GetX() + leftPaddle.GetWidth() - 0.5f) {
            ball = new Rectangle(window.GetMaxWidth() / 2, window.GetMaxHeight() / 2, 2f, 2f);
            ballVelocity.x = BALL_H_SPEED;
            ballVelocity.y = 0f;
            rightScore++;
        }

        if(rightPaddle.IsColliding(ball)) {
            if(ball.GetY() > rightPaddle.GetY() + (rightPaddle.GetHeight() * (2f / 3f))) {
                ballVelocity.y = BALL_V_SPEED;
                ballVelocity.x = -BALL_H_SPEED / 2;
            } else if(ball.GetY() < rightPaddle.GetY() + (rightPaddle.GetHeight() / 3f)) {
                ballVelocity.y = -BALL_V_SPEED;
                ballVelocity.x = -BALL_H_SPEED / 2;
            } else {
                ballVelocity.y = 0f;
                ballVelocity.x = -BALL_H_SPEED;
            }
        } else if(ball.GetX() + ball.GetWidth() > rightPaddle.GetX() + 0.5f) {
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

        ball.Move(ballVelocity.x * ts, ballVelocity.y * ts);

        Renderer.DrawRect(window, leftPaddle, new Vector3f(0f, 1f, 1f));
        Renderer.DrawRect(window, rightPaddle, new Vector3f(0f, 1f, 1f));
        Renderer.DrawRect(window, ball, new Vector3f(1f, 0f, 0f));

        Renderer.DrawString(window, String.valueOf(leftScore), leftScoreX, scoreY, 4f);
        Renderer.DrawString(window, String.valueOf(rightScore), rightScoreX, scoreY, 4f);
    }

}