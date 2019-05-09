package codepath.demos.helloworlddemo;

import android.graphics.Canvas;
import android.graphics.Rect;

public class RectPlayer implements GameObject {
    private Rect rectangle;
    private int color;

    public RectPlayer(Rect rectangle, int color) {
        this.rectangle = rectangle;
        this.color = color;
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
