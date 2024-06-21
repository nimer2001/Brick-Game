package bricker.main;

import bricker.brick_strategies.*;
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

public class Factory {
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

    public Factory(Counter bricksNum, GameObjectCollection gameObjects, Vector2 windowDimension
            , SoundReader soundReader, ImageReader imageReader,
                   UserInputListener inputListener, GameManager gameManager,
                   WindowController windowController, Counter heartNum, ArrayList<GameObject> heartArray) {
        this.bricksNum = bricksNum;
        this.gameObjects = gameObjects;
        this.windowDimension = windowDimension;
        this.soundReader = soundReader;
        this.imageReader = imageReader;
        this.inputListener = inputListener;
        this.gameManager = gameManager;
        this.windowController = windowController;
        this.heartNum = heartNum;
        this.heartArray = heartArray;

    }

    public CollisionStrategy buildBricks(double percentage) {
        if (percentage < 0.5) {
            return new BasicCollisionStrategy(gameObjects,bricksNum);
        }
        if(percentage >= 0.5 && percentage < 0.6){
            return new ExtraBallsStrategy(bricksNum,gameObjects,windowDimension,soundReader,imageReader);
        }
        if(percentage >= 0.6 && percentage < 0.7){
            return new ExtraPaddleStrategy(gameObjects,imageReader,inputListener,windowDimension,bricksNum);
        }
        if(percentage>=0.7 && percentage <0.8){
            return new CameraStrategy(gameManager,gameObjects,windowController,bricksNum);
        }
        if(percentage>=0.8 && percentage<0.9){
            return new ExtraHeartStrategy(imageReader,gameObjects,bricksNum,heartNum,windowDimension, heartArray);
        }
        if(percentage>=0.9 && percentage<1){
            return new DoubleBehaviorStrategy(bricksNum, gameObjects, windowDimension, soundReader,
                    imageReader, inputListener, gameManager, windowController, heartNum,1, heartArray);
        }
       return null;
    }
}
