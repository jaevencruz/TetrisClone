package codepath.demos.helloworlddemo;

import android.graphics.Rect;

import java.util.concurrent.TimeUnit;

public class BackEnd {
    public void fall(Rect getRect, Rect TetrisSpace[][]){
        int i = 0, j = 0;
        int width = TetrisSpace[1].length;
        TetrisSpace[0][width] = getRect;
        while(TetrisSpace[i+1][width] == null){
            TetrisSpace[i][width] = null;
            i++;
            TetrisSpace[i][width] = getRect;
            try {
                TimeUnit.SECONDS.sleep(1);
            }catch(InterruptedException e){
                e.printStackTrace();
            }
        }


    }
}
