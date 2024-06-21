package bricker.brick_strategies;

import bricker.main.Constants;
import danogl.GameManager;
import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.gui.ImageReader;
import danogl.gui.SoundReader;
import danogl.gui.UserInputListener;
import danogl.gui.WindowController;
import danogl.util.Counter;
import danogl.util.Vector2;

import java.util.ArrayList;
import java.util.Random;
//interface Decorator extends CollisionStrategy{}
public class DoubleBehaviorStrategy implements CollisionStrategy {
    private Random random;
    private int numberOfSpecialBehavior;
    private CollisionStrategy firstCollisionStrategy;
    private CollisionStrategy secondCollisionStrategy;
    private Counter bricksNum;
    private GameObjectCollection gameObjects;
    private Vector2 windowDimension;
    private SoundReader soundReader;
    private ImageReader imageReader;
    private UserInputListener inputListener;
    private GameManager gameManager;
    private WindowController windowController;
    private Counter heartNum;
    private ArrayList<GameObject> heartArray;




    public DoubleBehaviorStrategy(Counter bricksNum, GameObjectCollection gameObjects, Vector2 windowDimension
            , SoundReader soundReader, ImageReader imageReader, UserInputListener inputListener, GameManager gameManager,
                                  WindowController windowController, Counter heartNum,int currentDepth, ArrayList<GameObject> heartArray) {

        this.random = new Random();
        this.bricksNum = bricksNum;
        this.gameObjects = gameObjects;
        this.windowDimension = windowDimension;
        this.soundReader = soundReader;
        this.imageReader = imageReader;
        this.inputListener = inputListener;
        this.gameManager = gameManager;
        this.numberOfSpecialBehavior = currentDepth;
        this.windowController = windowController;
        this.heartNum = heartNum;
        this.heartArray = heartArray;
    }

    @Override
    public void onCollision(GameObject gameObject1, GameObject gameObject2) {


        CollisionStrategy[] collisionStrategies = {this.firstCollisionStrategy, this.secondCollisionStrategy};
        for (int i = 0; i < collisionStrategies.length; i++) {
            double collisionProbability = random.nextDouble(0, 1);
            if (collisionProbability >= 0 && collisionProbability < 0.2) {
                collisionStrategies[i] = new ExtraBallsStrategy(bricksNum, gameObjects, windowDimension, soundReader, imageReader);

            } else if (collisionProbability >= 0.2 && collisionProbability < 0.4) {
                collisionStrategies[i] = new ExtraPaddleStrategy(gameObjects, imageReader, inputListener, windowDimension, bricksNum);

            } else if (collisionProbability >= 0.4 && collisionProbability < 0.6) {
                collisionStrategies[i] = new CameraStrategy(gameManager, gameObjects, windowController, bricksNum);

            } else if (collisionProbability >= 0.6 && collisionProbability < 0.8) {
                collisionStrategies[i] = new ExtraHeartStrategy(imageReader, gameObjects, bricksNum, heartNum, windowDimension, heartArray);

            } else if (collisionProbability >= 0.8 && collisionProbability < 1) {
                if (this.numberOfSpecialBehavior < Constants.MAX_NUMBER_OF_DOUBLE_BEHAVIORS) {
                    collisionStrategies[i] = new DoubleBehaviorStrategy(bricksNum, gameObjects, windowDimension, soundReader,
                            imageReader, inputListener, gameManager, windowController, heartNum,this.numberOfSpecialBehavior+1, heartArray);
                }else {
                    i--;
                }
            }
        }
        collisionStrategies[0].onCollision(gameObject1, gameObject2);
        collisionStrategies[1].onCollision(gameObject1, gameObject2);
    }
}



