package codepath.demos.helloworlddemo;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

public class CharacterSprite {
    public static Bitmap image;


    public CharacterSprite(Bitmap bmp){
        image = bmp;
    }

    public static void draw(Canvas canvas, Rect getRect){
        canvas.drawBitmap(image,null,getRect, null);
    }
}
