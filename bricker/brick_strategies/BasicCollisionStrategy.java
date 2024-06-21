package bricker.brick_strategies;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;
import danogl.util.Counter;

public class BasicCollisionStrategy implements CollisionStrategy{
    private GameObjectCollection gameObjects;
    private  Counter bricksNum;

    public BasicCollisionStrategy(GameObjectCollection gameObjects, Counter bricksNum) {
        this.gameObjects = gameObjects;
        this.bricksNum = bricksNum;
    }

    @Override
    public void onCollision(GameObject gameObject1, GameObject gameObject2) {

        if(this.gameObjects.removeGameObject(gameObject1, Layer.STATIC_OBJECTS)){
            this.bricksNum.decrement();
        }
        }

}
