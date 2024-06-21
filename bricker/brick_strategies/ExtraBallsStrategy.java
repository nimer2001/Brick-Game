package bricker.brick_strategies;

//import bricker.gameobjects.PuckBall;
import bricker.gameobjects.Ball;
import bricker.main.BrickerGameManager;
import bricker.main.Constants;
import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;
import danogl.gui.ImageReader;
import danogl.gui.Sound;
import danogl.gui.SoundReader;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;

import java.util.Random;


public class ExtraBallsStrategy implements CollisionStrategy {
    private ImageReader imageReader;
    private SoundReader soundReader;
    private Counter bricksNum;
    private GameObjectCollection gameObjects;
    private Vector2 windowDimension;

    public ExtraBallsStrategy(Counter bricksNum, GameObjectCollection gameObjects,
                              Vector2 windowDimension,
                              SoundReader soundReader,
                              ImageReader imageReader) {
        this.bricksNum = bricksNum;
        this.windowDimension = windowDimension;
        this.gameObjects = gameObjects;
        this.imageReader = imageReader;
        this.soundReader = soundReader;

    }

    @Override
    public void onCollision(GameObject gameObject1, GameObject gameObject2) {
        Renderable puckImage = this.imageReader.readImage("mockBall.png", false);
        Sound ballSound = this.soundReader.readSound("blop_cut_silenced.wav");

        //remove brick

        if(this.gameObjects.removeGameObject(gameObject1, Layer.STATIC_OBJECTS)){
            this.bricksNum.decrement();
        }


        //create pucks

        float ballXCor = (float) (gameObject1.getTopLeftCorner().x() + gameObject1.getDimensions().x() * 0.5);
        float ballYCor = (float) (gameObject1.getTopLeftCorner().y() + gameObject1.getDimensions().y() * 0.5);
        Ball puck1 = new Ball(new Vector2(ballXCor, ballYCor), new Vector2((float) (Constants.BALL_WIDTH * Constants.BALL_PUCK_BALL_SIZE_RATIO),
                (float) (Constants.BALL_WIDTH * Constants.BALL_PUCK_BALL_SIZE_RATIO)), puckImage, ballSound, null);
        Ball puck2 = new Ball(new Vector2(ballXCor, ballYCor), new Vector2((float) (Constants.BALL_WIDTH * Constants.BALL_PUCK_BALL_SIZE_RATIO),
                (float) (Constants.BALL_WIDTH * Constants.BALL_PUCK_BALL_SIZE_RATIO)), puckImage, ballSound, null);

        //add two buck to the game
        puck1.setTag("PuckBall");
        puck2.setTag("PuckBall");
        this.gameObjects.addGameObject(puck1);
        this.gameObjects.addGameObject(puck2);
        ballVelocity(puck1);
        ballVelocity(puck2);

    }

    private void ballVelocity(Ball puck){
        Random rand = new Random();
        double angel = rand.nextDouble() * Math.PI;
        float velocityX = (float) Math.cos(angel) * Constants.BALL_SPEED;
        float velocityY = (float) Math.sin(angel) * Constants.BALL_SPEED;
        puck.setVelocity(new Vector2(velocityX,velocityY));
    }


}
