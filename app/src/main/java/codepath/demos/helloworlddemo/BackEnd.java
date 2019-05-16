package codepath.demos.helloworlddemo;

import android.graphics.Rect;

import java.util.concurrent.TimeUnit;

public class BackEnd extends Thread{
    public static Rect tetromino[];
    public static RectPlayer tb = new RectPlayer();

    public void run(){

        synchronized (tetromino){
            fall();
        }
    }

    public static void fall(){
        for(int i = 0; i < 5; i++) {

            moveDown();

            System.out.println("Iterate second:" + (i+1));
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


        }

    }
    public static void moveDown(){
        //Checks if block is at bottom and restricts movement there
        int SQUARE_SIZE_DEF = RectPlayer.getScreenWidth()/16 ;
        for(int i = 0; i<tetromino.length;i++){
            if ((tetromino[i].bottom + SQUARE_SIZE_DEF )> (16*(SQUARE_SIZE_DEF+1))){
                tb.tetrominoPicker();
            }
        }

        for(int i = 0; i < tetromino.length; i++) {
            tetromino[i].top = tetromino[i].top + SQUARE_SIZE_DEF;

            tetromino[i].bottom = tetromino[i].bottom + SQUARE_SIZE_DEF;
        }
    }




}
