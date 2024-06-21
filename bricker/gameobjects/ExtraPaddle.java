package bricker.gameobjects;

import bricker.brick_strategies.ExtraBallsStrategy;
import bricker.main.Constants;
import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.collisions.GameObjectCollection;
import danogl.gui.UserInputListener;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;
/**
 * this class represent a paddle,that is the similar to original paddle in size and its movement.
 */
public class ExtraPaddle extends Paddle {
    private   Counter ballPaddleCollisionCounter;
    private final GameObjectCollection gameObjects;

    /**
     * the param extraPaddle represents wether or not there is an extra paddle in the curren frame of teh game
     */
    public static  boolean extraPaddle = false;

    /**
     * a construtor
     * @param topLeftCorner a vector represents the position of the paddle on the scrren
     * @param dimensions a vector represents the dimension of the paddle
     * @param renderable a rendrearble repersents the image of the paddle
     * @param inputListener an input lisetner that listen to the keyboard input
     * @param windowDimensions a vector represent the dimension of the window
     * @param ballPaddleCollisionCounter a counter that represent how many collisons did the extra paddle
     *                                  have with the balls.(can be a regualar ball or a puck ball)
     * @param gameObject a game object
     */
    public ExtraPaddle(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable,
                       UserInputListener inputListener, Vector2 windowDimensions,
                       Counter ballPaddleCollisionCounter,GameObjectCollection gameObject) {
        super(topLeftCorner, dimensions, renderable, inputListener, windowDimensions);
        this.ballPaddleCollisionCounter = ballPaddleCollisionCounter;
        this.gameObjects = gameObject;
    }
    /**
     * this method override the original method.
     * @param other The GameObject with which a collision occurred.
     * @param collision Information regarding this collision.
     *                  A reasonable elastic behavior can be achieved with:
     *                  setVelocity(getVelocity().flipped(collision.getNormal()));
     */
    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);
        if(other.getTag().equals("Ball") || other.getTag().equals("PuckBall")){
            if(this.ballPaddleCollisionCounter.value() == Constants.COLLISIONS_UNTIL_PADDLE_DISAPPEARS - 1){
                this.gameObjects.removeGameObject(this);
                this.ballPaddleCollisionCounter.decrement();
                extraPaddle = false;
            }
            else {
                this.ballPaddleCollisionCounter.increment();
            }

        }
    }
}
