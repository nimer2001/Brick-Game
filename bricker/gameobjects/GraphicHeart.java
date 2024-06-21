package bricker.gameobjects;

import bricker.main.Constants;
import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;
import danogl.components.CoordinateSpace;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;

import java.util.ArrayList;

public class GraphicHeart extends GameObject{

    private Renderable heartImage;
    private GameObjectCollection gameObjects;
    private Counter heartNum;
    private Vector2 windowDimensions;
    private ArrayList<GameObject> heartArray;

    /**
     * Construct a new GameObject instance.
     *
     * @param topLeftCorner Position of the object, in window coordinates (pixels).
     *                      Note that (0,0) is the top-left corner of the window.
     * @param dimensions    Width and height in window coordinates.
     * @param renderable    The renderable representing the object. Can be null, in which case
     *                      the GameObject will not be rendered.
     */
    public GraphicHeart(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable, GameObjectCollection gameObjects, Counter heartNum, Vector2 windowDimension,  ArrayList<GameObject> heartArray) {
        super(topLeftCorner, dimensions, renderable);
        this.heartImage = renderable;
        this.gameObjects = gameObjects;
        this.heartNum = heartNum;
        this.windowDimensions = windowDimension;
        this.heartArray = heartArray;


    }

    public void createInitialHearts(){
        int x = Constants.LEFT_WALL_WIDTH * 2;
        for (int i = 0; i < heartNum.value(); i++) {
            GameObject heart = new GameObject(new Vector2(x, this.windowDimensions.y() - 30),
                    new Vector2(Constants.HEART_WIDTH, Constants.HEART_HEIGHT), heartImage);
            heart.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
            this.heartArray.add(heart);
            gameObjects.addGameObject(heart, Layer.BACKGROUND);
            x += Constants.LEFT_WALL_WIDTH * 2;
        }

    }
    public void addExtraHearts() {
        setTag("ExtraHeart");
        gameObjects.addGameObject(this);
        setVelocity(Vector2.DOWN.mult(Constants.HEART_SPEED));

    }

    public void removeHearts() {
        GameObject removedHeart = this.heartArray.remove(this.heartNum.value());
        this.gameObjects.removeGameObject(removedHeart, Layer.BACKGROUND);
    }

    @Override
    public boolean shouldCollideWith(GameObject other) {
        super.shouldCollideWith(other);
        return other.getTag().equals("Paddle");
    }
    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);
        if (this.getTag().equals("ExtraHeart")){
            if (this.heartNum.value() < Constants.MAX_HEART_NUMBER) {
                GameObject lastHeart = this.heartArray.get(this.heartNum.value() - 1);
                this.heartNum.increment();
                this.heartArray.add(this);

                gameObjects.removeGameObject(this);
                gameObjects.addGameObject(this, Layer.BACKGROUND);
                setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
                this.setCenter(new Vector2(lastHeart.getTopLeftCorner().x() + lastHeart.getDimensions().x() + 18,
                        windowDimensions.y() -  18));
                this.setVelocity(Vector2.ZERO);
            }
            else {
                gameObjects.removeGameObject(this);
            }
        }
    }
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        if (this.getTag().equals("ExtraHart") && getCenter().y() > this.windowDimensions.y()){
            gameObjects.removeGameObject(this);
        }
    }

}
