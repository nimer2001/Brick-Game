package bricker.brick_strategies;

//import bricker.gameobjects.ExtraPaddle;
import bricker.gameobjects.ExtraPaddle;
import bricker.gameobjects.Paddle;
import bricker.main.BrickerGameManager;
import bricker.main.Constants;
import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;
import danogl.gui.ImageReader;
import danogl.gui.UserInputListener;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;

/**
 * This class represent the extra paddle strtategy that creates another paddle after the ball colide with the brick
 */
public class ExtraPaddleStrategy  implements CollisionStrategy {

    private GameObjectCollection gameObjects;
    private ImageReader imageReader;
    private Vector2 windowDimensions;
    private Counter bricksNum;
    private UserInputListener inputListener;
    private Counter ballPaddleCollisionCounter;
    /**
     * a constructor
     * @param gameObjects a gameobjects
     * @param imageReader an image reader to display an image on the screeen
     * @param inputListener an input listener that listnes to the keyboard
     * @param windowDimensions a vector that represents the window dimension
     * @param bricksNum a Counter that represents the bricks number
     */
    public ExtraPaddleStrategy(GameObjectCollection gameObjects, ImageReader imageReader,
                               UserInputListener inputListener, Vector2 windowDimensions, Counter bricksNum) {
        this.gameObjects = gameObjects;
        this.imageReader = imageReader;
        this.bricksNum = bricksNum;
        this.windowDimensions = windowDimensions;
        this.inputListener = inputListener;
        this.ballPaddleCollisionCounter = new Counter(0);
    }

    /**
     * This method override the method in the upper class,and creates an extrapaddle
     * upon the collison of the ball and a brick
     * @param gameObject1 a game object
     * @param gameObject2 a game object
     */
    @Override
    public void onCollision(GameObject gameObject1, GameObject gameObject2) {
        if (!ExtraPaddle.extraPaddle){
            Renderable paddleImage = imageReader.readImage("paddle.png", true);
            ExtraPaddle paddle = new ExtraPaddle(this.windowDimensions.mult(Constants.FACTOR),
                    new Vector2(Constants.PADDLE_WIDTH, Constants.PADDLE_HEIGHT), paddleImage,
                    this.inputListener, this.windowDimensions, ballPaddleCollisionCounter, gameObjects);
            paddle.setCenter(this.windowDimensions.mult(Constants.FACTOR));
            this.gameObjects.addGameObject(paddle);
            ExtraPaddle.extraPaddle = true;
        }
        //removeBricks

        if(this.gameObjects.removeGameObject(gameObject1, Layer.STATIC_OBJECTS)){
            this.bricksNum.decrement();
        }

        }



}
