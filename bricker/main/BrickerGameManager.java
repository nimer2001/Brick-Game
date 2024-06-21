package bricker.main;

import bricker.brick_strategies.*;
import bricker.gameobjects.*;
import danogl.GameManager;
import danogl.GameObject;
import danogl.collisions.Layer;
import danogl.components.CoordinateSpace;
import danogl.gui.*;
import danogl.gui.rendering.Renderable;
import danogl.gui.rendering.TextRenderable;
import danogl.util.Counter;
import danogl.util.Vector2;

import java.util.ArrayList;
import java.awt.event.KeyEvent;
import java.util.Random;



public class BrickerGameManager extends GameManager {

    private Ball ball;
    private int brickPerRow;
    private Counter heartNum;
    private int rowsNum;
    private Vector2 windowDimensions;

    private ArrayList<GameObject> heartArray;//Changed to public
    private UserInputListener inputListener;
    private WindowController windowController;
    private Counter bricksNum;
    private NumericHeart numericHeart;
    private GraphicHeart graphicHeart;

    public BrickerGameManager(String windowTitle, Vector2 windowDimensions,
                              int brickPerRow, int rowsNum) {
        super(windowTitle, windowDimensions);
        this.brickPerRow = brickPerRow;
        this.rowsNum = rowsNum;
        this.heartNum = new Counter(Constants.HEART_NUMBER);
        this.bricksNum = new Counter(this.brickPerRow * this.rowsNum);
    }

    @Override
    public void initializeGame(ImageReader imageReader, SoundReader soundReader,
                               UserInputListener inputListener, WindowController windowController) {
        this.inputListener = inputListener;
        this.windowController = windowController;
        //creating ball
        super.initializeGame(imageReader, soundReader, inputListener, windowController);
        windowController.setTargetFramerate(80);
        createBall(imageReader, soundReader);
        this.windowDimensions = windowController.getWindowDimensions();
        // set the ball to the center of the screen
        this.ball.setCenter(this.windowDimensions.mult(Constants.FACTOR));
        //create paddle
        createPaddle(imageReader);
        //create walls
        createWalls();
        //create background
        createBackground(imageReader);
        //ball velocity
        ballVelocity();
        //create graphic hearts
        createGraphicHearts(imageReader);
        //create brick
        createBricks(imageReader, soundReader);
        //create numeric hearts
        createNumericHeart();
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        float ballHeight = ball.getCenter().y();
        String prompt = "";
        if (ballHeight > this.windowDimensions.y()) {
            this.heartNum.decrement();
            if (this.heartNum.value() == 0) {
                prompt += Constants.LOSE_MESSAGE;
            } else {
                this.graphicHeart.removeHearts();
                ball.setCenter(Vector2.ZERO);
                ball.setCenter(this.windowDimensions.mult(Constants.FACTOR));
                ballVelocity();
                this.numericHeart.update(deltaTime);
            }
        }
        if (this.bricksNum.value() == 0 || this.inputListener.isKeyPressed(KeyEvent.VK_W)) {
            prompt += Constants.WIN_MESSAGE;
        }
        if (!prompt.isEmpty()) {
            prompt += Constants.RESTART_GAME_MESSAGE;
            if (this.windowController.openYesNoDialog(prompt)) {
                this.heartNum = new Counter(Constants.HEART_NUMBER);
                this.bricksNum = new Counter(this.brickPerRow * this.rowsNum);
                this.windowController.resetGame();
                ExtraPaddle.extraPaddle = false;

            } else {
                this.windowController.closeWindow();
            }
        }
    }

    private void ballVelocity() {
        float ballVelX = Constants.BALL_SPEED;
        float ballVelY = Constants.BALL_SPEED;
        Random rand = new Random();
        if (rand.nextBoolean()) {
            ballVelX *= -1;
        }
        if (rand.nextBoolean()) {
            ballVelY *= -1;
        }
        this.ball.setVelocity(new Vector2(ballVelX, ballVelY));
    }

    private void createBall(ImageReader imageReader, SoundReader soundReader){
        Sound ballSound = soundReader.readSound("blop_cut_silenced.wav");
        Renderable ballImage = imageReader.readImage("ball.png", true);
        this.ball = new Ball(Vector2.ZERO, new Vector2(Constants.BALL_WIDTH, Constants.BALL_HEIGHT), ballImage, ballSound,
                new CameraStrategy(this, this.gameObjects(), this.windowController, this.bricksNum));
        this.ball.setTag("Ball");
        gameObjects().addGameObject(ball);
        this.ball.setVelocity(Vector2.DOWN.mult(Constants.BALL_SPEED));

    }
    private void createGraphicHearts(ImageReader imageReader) {
        heartArray = new ArrayList<>();
        Renderable heartImage = imageReader.readImage("heart.png", true);
        graphicHeart = new GraphicHeart(Vector2.ZERO, new Vector2(Constants.HEART_WIDTH, Constants.HEART_HEIGHT),heartImage, gameObjects(), heartNum, windowDimensions, heartArray);
        graphicHeart.createInitialHearts();

    }

    private void createNumericHeart() {
        TextRenderable text = new TextRenderable(String.valueOf(this.heartNum.value()));
        this.numericHeart = new NumericHeart(new Vector2(Constants.HEART_NUMBER_POSITION, this.windowDimensions.y() - 33),
                new Vector2(Constants.HEART_WIDTH, Constants.HEART_HEIGHT), text, this.heartNum);
        numericHeart.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
        gameObjects().addGameObject(numericHeart, Layer.BACKGROUND);
    }

    private void createPaddle(ImageReader imageReader) {
        Renderable paddleImage = imageReader.readImage("paddle.png", true);
        Paddle paddle = new Paddle(Vector2.ZERO, new Vector2(Constants.PADDLE_WIDTH, Constants.PADDLE_HEIGHT), paddleImage,
                this.inputListener, this.windowDimensions);
        paddle.setCenter(new Vector2(this.windowDimensions.x() / 2, windowDimensions.y() - 30));
        paddle.setTag("Paddle");
        gameObjects().addGameObject(paddle);
    }

    private void createWalls() {
        //create right wall
        GameObject rightWall = new GameObject(new Vector2(windowDimensions.x() - Constants.LEFT_WALL_WIDTH, 0),
                new Vector2(Constants.LEFT_WALL_WIDTH, this.windowDimensions.y()),
                null);
        gameObjects().addGameObject(rightWall);

        //create left wall
        GameObject leftWall = new GameObject(Vector2.ZERO, new Vector2(Constants.LEFT_WALL_WIDTH, this.windowDimensions.y()),
                null);
        gameObjects().addGameObject(leftWall);

        //create up wall
        GameObject uptWall = new GameObject(Vector2.ZERO, new Vector2(this.windowDimensions.x(),
                Constants.UP_WALL_HEIGHT), null);
        gameObjects().addGameObject(uptWall);
    }

    private void createBackground(ImageReader imageReader) {
        Renderable backgroundImage = imageReader.readImage("DARK_BG2_small.jpeg",
                false);
        GameObject background = new GameObject(Vector2.ZERO, new Vector2(this.windowDimensions.x(),
                this.windowDimensions.y()), backgroundImage);
        background.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
        gameObjects().addGameObject(background, Layer.BACKGROUND);
    }


    private void createBricks(ImageReader imageReader, SoundReader soundReader) {
        Random random = new Random();
        Factory factory = new Factory(bricksNum, gameObjects(), windowDimensions,
                soundReader, imageReader, inputListener, this,
                windowController, heartNum, heartArray);

        float brickWidth = (this.windowDimensions.x() - Constants.LEFT_WALL_WIDTH * 2 -
                (Constants.SPACE_BETWEEN_BRICKS * this.brickPerRow)) / this.brickPerRow;
        int y = Constants.BRICK_HEIGHT;
        for (int i = 0; i < this.rowsNum; i++) {
            int x = Constants.LEFT_WALL_WIDTH;
            for (int j = 0; j < this.brickPerRow; j++) {
                double percentage = random.nextDouble(0, 1);
                CollisionStrategy collisionStrategy = factory.buildBricks(percentage);
                Renderable brickImage = imageReader.readImage("brick.png", false);
                GameObject brick = new Brick(new Vector2(x, y), new Vector2(brickWidth, Constants.BRICK_HEIGHT),
                        brickImage, collisionStrategy);
                brick.setTag("Brick");
                gameObjects().addGameObject(brick, Layer.STATIC_OBJECTS);
                x += (int) (brickWidth) + Constants.SPACE_BETWEEN_BRICKS;
            }
            y += Constants.SPACE_BETWEEN_ROWS;
        }

    }


    public static void main(String[] args) {
        if (args.length == 2) {
            new BrickerGameManager("Bricker", new Vector2(700, 500),
                    Integer.parseInt(args[0]), Integer.parseInt(args[1])).run();
        } else {
            new BrickerGameManager("Bricker", new Vector2(700, 500),
                    Constants.DEFAULT_BRICKS_PER_ROW, Constants.DEFAULT_NUM_OF_ROWS).run();

        }
    }
}
