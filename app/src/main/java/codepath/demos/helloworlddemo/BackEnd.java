package codepath.demos.helloworlddemo;

import android.graphics.Rect;

import java.util.concurrent.TimeUnit;

public class BackEnd {
    public void fall(Rect getRect, int TetrisSpace[][]){
        int i = 0, j = 0;
        int width = TetrisSpace[1].length;
        TetrisSpace[0][width] = 1;
        while(TetrisSpace[i+1][width] == 0){
            TetrisSpace[i][width] = 0;
            i++;
            TetrisSpace[i][width] = 1;
            try {
                TimeUnit.SECONDS.sleep(1);
            }catch(InterruptedException e){
                e.printStackTrace();
            }
        }


    }
}
