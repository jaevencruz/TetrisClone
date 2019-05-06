package codepath.demos.helloworlddemo;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class CharacterSprite {
    public Bitmap image;

    public CharacterSprite(Bitmap bmp){
        image = bmp;
    }

    public static void draw(Canvas canvas){
        canvas.drawBitmap();
    }
}
