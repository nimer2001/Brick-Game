//package bricker.gameobjects;
//
//import bricker.main.BrickerGameManager;
//import bricker.main.Constants;
//import danogl.GameObject;
//import danogl.collisions.Collision;
//import danogl.collisions.GameObjectCollection;
//import danogl.collisions.Layer;
//import danogl.components.CoordinateSpace;
//import danogl.gui.rendering.Renderable;
//import danogl.util.Counter;
//import danogl.util.Vector2;
//
//import java.util.ArrayList;
//
//public class ExtraHeart extends GraphicHeart {
//    private Counter heartNum;
//    private GameObjectCollection gameObjects;
//    private Vector2 windowDimension;
//
//    public ExtraHeart(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable, Counter heartNum,
//                      GameObjectCollection gameObjects,Vector2 windowDimension) {
//        super(topLeftCorner, dimensions, renderable, gameObjects, heartNum);
//        this.heartNum = heartNum;
//        this.gameObjects = gameObjects;
//        this.windowDimension = windowDimension;
//    }
//
//    public void addExtraHearts(GameObjectCollection gameObjects) {
//        setTag("ExtraHeart");
//        gameObjects.addGameObject(this);
//        setVelocity(Vector2.DOWN.mult(Constants.HEART_SPEED));
//
//    }
//
//    @Override
//    public boolean shouldCollideWith(GameObject other) {
//        super.shouldCollideWith(other);
//        return other.getTag().equals("Paddle");
//    }
//    @Override
//    public void onCollisionEnter(GameObject other, Collision collision) {
//        super.onCollisionEnter(other, collision);
//
//        if (this.heartNum.value() < Constants.MAX_HEART_NUMBER) {
//            GameObject lastHeart = BrickerGameManager.heartArray.get(this.heartNum.value() - 1);
//            this.heartNum.increment();
//            BrickerGameManager.heartArray.add(this);
//            gameObjects.removeGameObject(this);
//            gameObjects.addGameObject(this, Layer.BACKGROUND);
//            setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
//            this.setCenter(new Vector2(lastHeart.getTopLeftCorner().x() + lastHeart.getDimensions().x() + 18,
//                    windowDimension.y() -  18));
//            this.setVelocity(Vector2.ZERO);
//        }
//        else {
//            gameObjects.removeGameObject(this);
//        }
//
//    }
//    @Override
//    public void update(float deltaTime) {
//        super.update(deltaTime);
//        if (getCenter().y() > this.windowDimension.y()){
//            gameObjects.removeGameObject(this);
//        }
//    }
//}
