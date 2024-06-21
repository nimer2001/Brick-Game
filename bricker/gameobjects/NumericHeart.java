package bricker.gameobjects;

import danogl.GameObject;
import danogl.gui.rendering.Renderable;
import danogl.gui.rendering.TextRenderable;
import danogl.util.Counter;
import danogl.util.Vector2;

import java.awt.*;

public class NumericHeart extends GameObject {
    /**
     * Construct a new GameObject instance.
     *
     * @param topLeftCorner Position of the object, in window coordinates (pixels).
     *                      Note that (0,0) is the top-left corner of the window.
     * @param dimensions    Width and height in window coordinates.
     * @param renderable    The renderable representing the object. Can be null, in which case
     *                      the GameObject will not be rendered.
     */

    private TextRenderable text;
    private Counter heartNum;
    public NumericHeart(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable, Counter heartNum) {
        super(topLeftCorner, dimensions, renderable);
        this.text = (TextRenderable) renderable;
        this.heartNum = heartNum;
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        this.text.setString(String.valueOf(this.heartNum.value()));
        if (this.heartNum.value() >= 3){
            this.text.setColor(Color.green);
        }
        if (this.heartNum.value() == 2){
            this.text.setColor(Color.yellow);
        } else if (this.heartNum.value() == 1) {
            this.text.setColor(Color.red);
        }
    }


}
