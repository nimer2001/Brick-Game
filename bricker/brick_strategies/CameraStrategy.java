package bricker.brick_strategies;

import bricker.gameobjects.Ball;
import bricker.main.Constants;
import danogl.GameManager;
import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;
import danogl.gui.WindowController;
import danogl.gui.rendering.Camera;
import danogl.util.Counter;
import danogl.util.Vector2;


public class CameraStrategy implements CollisionStrategy  {

    private GameManager gameManager;
    private WindowController windowController;
    private GameObjectCollection gameObjects;
    private Counter bricksNum;
    public CameraStrategy(GameManager gameManager,GameObjectCollection gameObjects,  WindowController windowController,Counter bricksNum) {
        this.gameManager = gameManager;
        this.windowController = windowController;
        this.gameObjects =gameObjects;
        this.bricksNum = bricksNum;
    }

    @Override
    public void onCollision(GameObject gameObject1, GameObject gameObject2) {
        if (gameObject2.getTag().equals("Ball")){
            Ball ball  = (Ball) gameObject2;
            if (this.gameManager.camera() == null){
                ball.setCollisionCounter(0);
                this.gameManager.setCamera(new Camera(ball, Vector2.ZERO,
                        this.windowController.getWindowDimensions().mult(1.2f),
                        this.windowController.getWindowDimensions()));

            }
            if (ball.getCollisionCounter() == Constants.COLLISIONS_UNTIL_CAMERA_CHANGES +1){
                this.gameManager.setCamera(null);
            }
        }
        if (gameObject1.getTag().equals("Ball")) {
            Ball ball  = (Ball) gameObject1;
            if (ball.getCollisionCounter() == Constants.COLLISIONS_UNTIL_CAMERA_CHANGES + 1){
                this.gameManager.setCamera(null);
            }
        }
        if (gameObject1.getTag().equals("Brick")){
            if(this.gameObjects.removeGameObject(gameObject1, Layer.STATIC_OBJECTS)){
                this.bricksNum.decrement();
            }

        }


    }
}
