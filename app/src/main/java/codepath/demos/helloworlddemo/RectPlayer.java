package codepath.demos.helloworlddemo;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

public class RectPlayer implements GameObject {
    private Rect tetromino[];
    private int rotateState;
    private Paint paint;

    public RectPlayer(Rect tetromino[], Paint paint, int rotateState) {
        this.tetromino = tetromino;
        this.paint = paint;
        this.rotateState = 0;
    }

    @Override
    public void draw(Canvas canvas){

    }

    @Override
    public void update(){
        //l,t,r,b
        //rectangle.set();
    }
}
