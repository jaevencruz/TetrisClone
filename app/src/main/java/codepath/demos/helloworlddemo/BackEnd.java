package codepath.demos.helloworlddemo;

import android.graphics.Rect;

import java.util.concurrent.TimeUnit;

public class BackEnd extends Thread{
    public static Rect tetromino[];
    private GridBlock [][] grid;
    public static RectPlayer tb = new RectPlayer();

    public BackEnd(RectPlayer player, GridBlock [][] grid){
        this.tb = player;
        this.grid = grid;
    }

    public BackEnd(){
    }


    public void run(){
        if(tetromino == null){
            initializeRectArray();
        }
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

    public static void initializeRectArray(){
        if(tetromino == null){
            tetromino = new Rect[4];
            for(int i = 0; i < tetromino.length;i++){
               tetromino[i] = new Rect();
            }
        }
    }

}
