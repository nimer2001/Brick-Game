//package bricker.gameobjects;
//
//import bricker.main.BrickerGameManager;
//import bricker.main.Constants;
//import danogl.GameObject;
//import danogl.collisions.Collision;
//import danogl.collisions.GameObjectCollection;
//import danogl.gui.Sound;
//import danogl.gui.SoundReader;
//import danogl.gui.rendering.Renderable;
//import danogl.util.Vector2;
//
//import java.util.Random;
//
//public class PuckBall extends Ball {
//    private final Vector2 windowDimension;
//    private final GameObjectCollection gameObjects;
//    private Sound collisionSound;
//
//    public PuckBall(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable, Sound collisionSound,
//                    Vector2 windowDimension, GameObjectCollection gameObjects) {
//        super(topLeftCorner, dimensions, renderable, collisionSound, null);
//        this.windowDimension = windowDimension;
//        this.gameObjects = gameObjects;
//        this.collisionSound = collisionSound;
//    }
//
//    @Override
//    public void onCollisionEnter(GameObject other, Collision collision) {
//        super.onCollisionEnter(other, collision);
//        Vector2 newVel = getVelocity().flipped(collision.getNormal());
//        setVelocity(newVel);
//        this.collisionSound.play();
//
//    }
//
//    @Override
//    public void update(float deltaTime) {
//        super.update(deltaTime);
//        if(getCenter().y() > this.windowDimension.y()){
//            this.gameObjects.removeGameObject(this);
//        }
//    }
//
//    public void ballVelocity(){
//        Random rand = new Random();
//        double angel = rand.nextDouble() * Math.PI;
//        float velocityX = (float) Math.cos(angel) * Constants.BALL_SPEED;
//        float velocityY = (float) Math.sin(angel) * Constants.BALL_SPEED;
//        setVelocity(new Vector2(velocityX,velocityY));
//    }
//
//}
