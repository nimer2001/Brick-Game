package bricker.brick_strategies;

//import bricker.gameobjects.ExtraHeart;
import bricker.gameobjects.GraphicHeart;
import bricker.main.BrickerGameManager;
import bricker.main.Constants;
import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;
import danogl.gui.ImageReader;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;

import java.util.ArrayList;

public class ExtraHeartStrategy implements CollisionStrategy{

    private final GameObjectCollection gameObjects;
    private final Counter bricksNum;
    private ImageReader imageReader;
    private  Counter heartNum;
    private Vector2 windowDimension;
    private ArrayList<GameObject> heartArray;

    public ExtraHeartStrategy(ImageReader imageReader, GameObjectCollection gameObjects, Counter bricksNum,
                              Counter heartNum, Vector2 windowDimension, ArrayList<GameObject> heartArray) {

        this.gameObjects = gameObjects;
        this.imageReader = imageReader;
        this.bricksNum = bricksNum;
        this.heartNum = heartNum;
        this.windowDimension = windowDimension;
        this.heartArray = heartArray;

    }

    @Override
    public void onCollision(GameObject gameObject1, GameObject gameObject2) {
        Renderable heartImage = this.imageReader.readImage("heart.png", true);
        float ballXCor = (float) (gameObject1.getTopLeftCorner().x() + gameObject1.getDimensions().x() * 0.5);
        float ballYCor = (float) (gameObject1.getTopLeftCorner().y() + gameObject1.getDimensions().y() * 0.5);
        GraphicHeart heart = new GraphicHeart(new Vector2(ballXCor, ballYCor), new Vector2(Constants.HEART_WIDTH,
                Constants.HEART_HEIGHT), heartImage,gameObjects,heartNum, windowDimension, heartArray);
        heart.addExtraHearts();
       if(this.gameObjects.removeGameObject(gameObject1,Layer.STATIC_OBJECTS)){
           this.bricksNum.decrement();
       }

    }


}
