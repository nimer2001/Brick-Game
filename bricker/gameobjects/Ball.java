package bricker.gameobjects;

import bricker.brick_strategies.CameraStrategy;
import bricker.main.Constants;
import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.gui.Sound;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

public class Ball extends GameObject {
    private final Sound collisionSound;
    private int collisionCounter;
    private CameraStrategy camera;


    /**
     * Construct a new GameObject instance.
     *
     * @param topLeftCorner Position of the object, in window coordinates (pixels).
     *                      Note that (0,0) is the top-left corner of the window.
     * @param dimensions    Width and height in window coordinates.
     * @param renderable    The renderable representing the object. Can be null, in which case
     *                      the GameObject will not be rendered.
     */

    public Ball(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable, Sound collisionSound, CameraStrategy camera) {
        super(topLeftCorner, dimensions, renderable);
        this.collisionSound = collisionSound;
        this.collisionCounter = 0;
        this.camera = camera;
    }

    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);
        Vector2 newVel = getVelocity().flipped(collision.getNormal());
        setVelocity(newVel);
        this.collisionSound.play();
        if(camera == null){
            return;
        }
        this.collisionCounter++;
        camera.onCollision(this, other);
    }

    public int getCollisionCounter() {
        return this.collisionCounter;
    }

    public void setCollisionCounter(int collisionCounter) {
        this.collisionCounter = collisionCounter;
    }
}
