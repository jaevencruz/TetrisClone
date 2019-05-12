package codepath.demos.helloworlddemo;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import org.jetbrains.annotations.Nullable;
import java.util.*;
import java.util.Random;



public class CustomView extends View {

    private static final int SQUARE_SIZE_DEF = getScreenWidth()/16 ;

    private Rect [][] tetrisGrid;
    private Rect [] tetromino;
    private Paint tGridPaint;
    private Paint dPaint;

    public CustomView(Context context) {
        super(context);
        init(null);
    }

    public CustomView(Context context, AttributeSet attrs){
        super(context,attrs);
        init(attrs);
    }

    public CustomView(Context context, AttributeSet attrs, int defStyleAttr){
        super(context,attrs,defStyleAttr);
    }

    public CustomView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes){
        super(context,attrs,defStyleAttr,defStyleRes);
    }

    //the init function is the place to initialize stuff, such as creating a new rectangle and such
    private void init(@Nullable AttributeSet set){
        Random tetrominoPicker = new Random();
        tetrisGrid = new Rect[16][10];
        tetromino = new Rect[4];
        dPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        tGridPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        tGridPaint.setColor(Color.LTGRAY);
        //Initializes each rectangle in Tetromino array
        for(int i = 0; i < tetromino.length; i++){
           tetromino[i] = new Rect();
        }

        //Initializes each rectangle in tetrisGrid
        for(int i = 0; i < 16; i++){
            for(int j = 0; j < 10;j++) {
                tetrisGrid[i][j] = new Rect();
                tetrisGrid[i][j].left = j*(SQUARE_SIZE_DEF + 1);
                tetrisGrid[i][j].top = i*(SQUARE_SIZE_DEF + 1);
                tetrisGrid[i][j].right = tetrisGrid[i][j].left + SQUARE_SIZE_DEF;
                tetrisGrid[i][j].bottom = tetrisGrid[i][j].top + SQUARE_SIZE_DEF;
            }
        }
        tetrominoPicker();
        dPaint.setColor(colorRandom());
    }

    @Override
    protected void onDraw(Canvas canvas){
        for(int i = 0; i < 16; i++){
            for(int j = 0; j < 10; j++){
                canvas.drawRect(tetrisGrid[i][j],tGridPaint);
            }

        }
        for(int i = 0; i < tetromino.length; i++){
            canvas.drawRect(tetromino[i], dPaint);
        }

    }

    public void swapColor(){
        dPaint.setColor(colorRandom());
        postInvalidate();
    }

    public void moveDown(){
        //Checks if block is at bottom and restricts movement there
        for(int i = 0; i<tetromino.length;i++){
            if ((tetromino[i].bottom + SQUARE_SIZE_DEF )> (16*(SQUARE_SIZE_DEF+1))){
                return;
            }
        }

        for(int i = 0; i < tetromino.length; i++) {
            tetromino[i].top = tetromino[i].top + SQUARE_SIZE_DEF;

            tetromino[i].bottom = tetromino[i].bottom + SQUARE_SIZE_DEF;
        }
        postInvalidate();
    }

    public int colorRandom(){
        Random rnd = new Random();
        return Color.argb(255,rnd.nextInt(256),rnd.nextInt(256),rnd.nextInt(256));
    }

    public void moveRight(){
        //Checks if block is at right corner and restricts movement there
        for(int i = 0; i<tetromino.length;i++){
            if ((tetromino[i].right + SQUARE_SIZE_DEF )> 10*(SQUARE_SIZE_DEF+1)){
                return;
            }
        }
        for(int i = 0; i < tetromino.length; i++) {
            tetromino[i].left = tetromino[i].left + SQUARE_SIZE_DEF;

            tetromino[i].right = tetromino[i].right + SQUARE_SIZE_DEF;
        }
        postInvalidate();
    }

    public void moveLeft(){
        //Checks if block is at left corner and restricts movement there
        for(int i = 0; i<tetromino.length;i++){
            if ((tetromino[i].left - SQUARE_SIZE_DEF )< 0){
                return;
            }
        }
        for(int i = 0; i < tetromino.length; i++) {
            tetromino[i].left = tetromino[i].left - SQUARE_SIZE_DEF;

            tetromino[i].right = tetromino[i].right - SQUARE_SIZE_DEF;
        }
        postInvalidate();
    }

    public void moveUp(){
        //Checks if block is at the top and restricts movement there
        for(int i = 0; i<tetromino.length;i++){
            if ((tetromino[i].top - SQUARE_SIZE_DEF )< 0){
                return;
            }
        }
        for(int i = 0; i < tetromino.length; i++) {
            tetromino[i].top = tetromino[i].top - SQUARE_SIZE_DEF;

            tetromino[i].bottom = tetromino[i].bottom - SQUARE_SIZE_DEF;
        }
        postInvalidate();
    }

    public static int getScreenWidth() {
        return Resources.getSystem().getDisplayMetrics().widthPixels;
    }

    //Initializes Z block
    public void zBlockInit(){
        tetromino[0].left = 0;
        tetromino[0].top = 0;
        tetromino[0].right = tetromino[0].left + SQUARE_SIZE_DEF;
        tetromino[0].bottom = tetromino[0].top + SQUARE_SIZE_DEF;

        tetromino[1].left = tetromino[0].left + SQUARE_SIZE_DEF + 1;
        tetromino[1].top = 0;
        tetromino[1].right = tetromino[1].left + SQUARE_SIZE_DEF ;
        tetromino[1].bottom = tetromino[1].top + SQUARE_SIZE_DEF;

        tetromino[2].left = tetromino[0].left + SQUARE_SIZE_DEF + 1;
        tetromino[2].top = SQUARE_SIZE_DEF + 1;
        tetromino[2].right = tetromino[2].left + SQUARE_SIZE_DEF ;
        tetromino[2].bottom = tetromino[2].top + SQUARE_SIZE_DEF;

        tetromino[3].left = tetromino[2].left + SQUARE_SIZE_DEF + 1;
        tetromino[3].top = SQUARE_SIZE_DEF + 1;
        tetromino[3].right = tetromino[3].left + SQUARE_SIZE_DEF ;
        tetromino[3].bottom = tetromino[3].top + SQUARE_SIZE_DEF;


    }

    //Initializes S Block
    public void sBlockInit(){
        tetromino[0].left = 0;
        tetromino[0].top = SQUARE_SIZE_DEF + 1;
        tetromino[0].right = tetromino[0].left + SQUARE_SIZE_DEF;
        tetromino[0].bottom = tetromino[0].top + SQUARE_SIZE_DEF;

        tetromino[1].left = tetromino[0].left + SQUARE_SIZE_DEF + 1;
        tetromino[1].top =  SQUARE_SIZE_DEF + 1;
        tetromino[1].right = tetromino[1].left + SQUARE_SIZE_DEF ;
        tetromino[1].bottom = tetromino[1].top + SQUARE_SIZE_DEF;

        tetromino[2].left = tetromino[0].left + SQUARE_SIZE_DEF + 1;
        tetromino[2].top = 0;
        tetromino[2].right = tetromino[2].left + SQUARE_SIZE_DEF ;
        tetromino[2].bottom = tetromino[2].top + SQUARE_SIZE_DEF;

        tetromino[3].left = tetromino[2].left + SQUARE_SIZE_DEF + 1;
        tetromino[3].top = 0;
        tetromino[3].right = tetromino[3].left + SQUARE_SIZE_DEF ;
        tetromino[3].bottom = tetromino[3].top + SQUARE_SIZE_DEF;

    }

    //Initializes O Block
    public void oBlockInit(){
        tetromino[0].left = 0;
        tetromino[0].top = 0;
        tetromino[0].right = tetromino[0].left + SQUARE_SIZE_DEF;
        tetromino[0].bottom = tetromino[0].top + SQUARE_SIZE_DEF;

        tetromino[1].left = tetromino[0].left + SQUARE_SIZE_DEF + 1;
        tetromino[1].top =  0;
        tetromino[1].right = tetromino[1].left + SQUARE_SIZE_DEF ;
        tetromino[1].bottom = tetromino[1].top + SQUARE_SIZE_DEF;

        tetromino[2].left = 0;
        tetromino[2].top = SQUARE_SIZE_DEF + 1;
        tetromino[2].right = tetromino[2].left + SQUARE_SIZE_DEF ;
        tetromino[2].bottom = tetromino[2].top + SQUARE_SIZE_DEF;

        tetromino[3].left = tetromino[2].left + SQUARE_SIZE_DEF + 1;
        tetromino[3].top = SQUARE_SIZE_DEF + 1;
        tetromino[3].right = tetromino[3].left + SQUARE_SIZE_DEF ;
        tetromino[3].bottom = tetromino[3].top + SQUARE_SIZE_DEF;

    }

    //Initializes I block
    public void iBlockInit(){
        tetromino[0].left = 0;
        tetromino[0].top = 0;
        tetromino[0].right = tetromino[0].left + SQUARE_SIZE_DEF;
        tetromino[0].bottom = tetromino[0].top + SQUARE_SIZE_DEF;

        tetromino[1].left = 0;
        tetromino[1].top =  SQUARE_SIZE_DEF + 1;
        tetromino[1].right = tetromino[1].left + SQUARE_SIZE_DEF ;
        tetromino[1].bottom = tetromino[1].top + SQUARE_SIZE_DEF;

        tetromino[2].left = 0;
        tetromino[2].top = 2*(SQUARE_SIZE_DEF + 1);
        tetromino[2].right = tetromino[2].left + SQUARE_SIZE_DEF ;
        tetromino[2].bottom = tetromino[2].top + SQUARE_SIZE_DEF;

        tetromino[3].left = 0;
        tetromino[3].top = 3*(SQUARE_SIZE_DEF + 1);
        tetromino[3].right = tetromino[3].left + SQUARE_SIZE_DEF ;
        tetromino[3].bottom = tetromino[3].top + SQUARE_SIZE_DEF;

    }

    //Initializes L block
    public void lBlockInit(){
        tetromino[0].left = 0;
        tetromino[0].top = 0;
        tetromino[0].right = tetromino[0].left + SQUARE_SIZE_DEF;
        tetromino[0].bottom = tetromino[0].top + SQUARE_SIZE_DEF;

        tetromino[1].left = 0;
        tetromino[1].top =  SQUARE_SIZE_DEF + 1;
        tetromino[1].right = tetromino[1].left + SQUARE_SIZE_DEF ;
        tetromino[1].bottom = tetromino[1].top + SQUARE_SIZE_DEF;

        tetromino[2].left = 0;
        tetromino[2].top = 2*(SQUARE_SIZE_DEF + 1);
        tetromino[2].right = tetromino[2].left + SQUARE_SIZE_DEF ;
        tetromino[2].bottom = tetromino[2].top + SQUARE_SIZE_DEF;

        tetromino[3].left = SQUARE_SIZE_DEF + 1;
        tetromino[3].top = 2*(SQUARE_SIZE_DEF + 1);
        tetromino[3].right = tetromino[3].left + SQUARE_SIZE_DEF ;
        tetromino[3].bottom = tetromino[3].top + SQUARE_SIZE_DEF;
    }

    //Initializes J Block
    public void jBlockInit(){
        tetromino[0].left = 0;
        tetromino[0].top = 2*(SQUARE_SIZE_DEF+1);
        tetromino[0].right = tetromino[0].left + SQUARE_SIZE_DEF;
        tetromino[0].bottom = tetromino[0].top + SQUARE_SIZE_DEF;

        tetromino[1].left = SQUARE_SIZE_DEF + 1;
        tetromino[1].top =  2*(SQUARE_SIZE_DEF + 1);
        tetromino[1].right = tetromino[1].left + SQUARE_SIZE_DEF ;
        tetromino[1].bottom = tetromino[1].top + SQUARE_SIZE_DEF;

        tetromino[2].left = SQUARE_SIZE_DEF + 1;
        tetromino[2].top = (SQUARE_SIZE_DEF + 1);
        tetromino[2].right = tetromino[2].left + SQUARE_SIZE_DEF ;
        tetromino[2].bottom = tetromino[2].top + SQUARE_SIZE_DEF;

        tetromino[3].left = SQUARE_SIZE_DEF + 1;
        tetromino[3].top = 0;
        tetromino[3].right = tetromino[3].left + SQUARE_SIZE_DEF ;
        tetromino[3].bottom = tetromino[3].top + SQUARE_SIZE_DEF;
    }

    //Initializes T Block
    public void tBlockInit(){
        tetromino[0].left = (SQUARE_SIZE_DEF + 1);
        tetromino[0].top = 0;
        tetromino[0].right = tetromino[0].left + SQUARE_SIZE_DEF;
        tetromino[0].bottom = tetromino[0].top + SQUARE_SIZE_DEF;

        tetromino[1].left = 0;
        tetromino[1].top =  0;
        tetromino[1].right = tetromino[1].left + SQUARE_SIZE_DEF ;
        tetromino[1].bottom = tetromino[1].top + SQUARE_SIZE_DEF;

        tetromino[2].left = 2*(SQUARE_SIZE_DEF + 1);
        tetromino[2].top = 0;
        tetromino[2].right = tetromino[2].left + SQUARE_SIZE_DEF ;
        tetromino[2].bottom = tetromino[2].top + SQUARE_SIZE_DEF;

        tetromino[3].left = SQUARE_SIZE_DEF + 1;
        tetromino[3].top = SQUARE_SIZE_DEF + 1;
        tetromino[3].right = tetromino[3].left + SQUARE_SIZE_DEF ;
        tetromino[3].bottom = tetromino[3].top + SQUARE_SIZE_DEF;
    }
    public void rotateclock(){
        Rect temp[] = new Rect[3];
        double current=0, rotate, radius;
        int newx, newy, x, y;
        int torquex =1, torquey = 1;

        for(int i = 0; i < 3;i++){
            for(int j = 0; j < 3; j++){
                x = torquex-i;
                y = torquey=j;
                radius = Math.sqrt(x*x+y*y);
                if(radius==0) {
                    newx=i;
                    newy=j;
                }
                else{
                    current = Math.atan2((torquex - i), (torquey - j));
                    rotate = current - 3.14159 / 2;
                    newx = (int) (radius * Math.cos(rotate));
                    newy = (int) (radius * Math.sin(rotate));
                }
                tetromino[0].left = (newx - tetromino[0].left) * SQUARE_SIZE_DEF;
                tetromino[0].top = (newy - tetromino[0].top) * SQUARE_SIZE_DEF;
                tetromino[0].right = (newx - tetromino[0].right) * SQUARE_SIZE_DEF;
                tetromino[0].bottom = (newy - tetromino[0].bottom) * SQUARE_SIZE_DEF;

                tetromino[1].left = (newx - tetromino[1].left) * SQUARE_SIZE_DEF;
                tetromino[1].top =  (newy - tetromino[1].top) * SQUARE_SIZE_DEF;
                tetromino[1].right = (newx - tetromino[1].right) * SQUARE_SIZE_DEF;
                tetromino[1].bottom = (newy - tetromino[1].bottom) * SQUARE_SIZE_DEF;

                tetromino[2].left = (newx - tetromino[2].left) * SQUARE_SIZE_DEF;
                tetromino[2].top = (newy - tetromino[2].top) * SQUARE_SIZE_DEF;
                tetromino[2].right = (newx - tetromino[2].right) * SQUARE_SIZE_DEF;
                tetromino[2].bottom = (newy - tetromino[2].bottom) * SQUARE_SIZE_DEF;

                tetromino[3].left = (newx - tetromino[3].left) * SQUARE_SIZE_DEF;
                tetromino[3].top = (newy - tetromino[3].top) * SQUARE_SIZE_DEF;
                tetromino[3].right = (newx - tetromino[3].right) * SQUARE_SIZE_DEF;
                tetromino[3].bottom = (newy - tetromino[3].bottom) * SQUARE_SIZE_DEF;
            }
        }
        //use sin-cos to rotate within a 3x3 space
    }

    public void rotatecounter(){
        Rect temp[] = new Rect[3];
        double current=0, rotate, radius;
        int newx, newy, x, y;
        int torquex =1, torquey = 1;

        for(int i = 0; i < 3;i++){
            for(int j = 0; j < 3; j++){
                x = torquex-i;
                y = torquey-j;
                radius = Math.sqrt(x*x+y*y);
                if(radius==0) {
                    newx=i;
                    newy=j;
                }
                else{
                    current = Math.atan2((torquex - i), (torquey - j));
                    rotate = current + 3.14159 / 2;
                    newx = (int) (radius * Math.cos(rotate));
                    newy = (int) (radius * Math.sin(rotate));
                }
                tetromino[0].left = (newx - tetromino[0].left) * SQUARE_SIZE_DEF;
                tetromino[0].top = (newy - tetromino[0].top) * SQUARE_SIZE_DEF;
                tetromino[0].right = (newx - tetromino[0].right) * SQUARE_SIZE_DEF;
                tetromino[0].bottom = (newy - tetromino[0].bottom) * SQUARE_SIZE_DEF;

                tetromino[1].left = (newx - tetromino[1].left) * SQUARE_SIZE_DEF;
                tetromino[1].top =  (newy - tetromino[1].top) * SQUARE_SIZE_DEF;
                tetromino[1].right = (newx - tetromino[1].right) * SQUARE_SIZE_DEF;
                tetromino[1].bottom = (newy - tetromino[1].bottom) * SQUARE_SIZE_DEF;

                tetromino[2].left = (newx - tetromino[2].left) * SQUARE_SIZE_DEF;
                tetromino[2].top = (newy - tetromino[2].top) * SQUARE_SIZE_DEF;
                tetromino[2].right = (newx - tetromino[2].right) * SQUARE_SIZE_DEF;
                tetromino[2].bottom = (newy - tetromino[2].bottom) * SQUARE_SIZE_DEF;

                tetromino[3].left = (newx - tetromino[3].left) * SQUARE_SIZE_DEF;
                tetromino[3].top = (newy - tetromino[3].top) * SQUARE_SIZE_DEF;
                tetromino[3].right = (newx - tetromino[3].right) * SQUARE_SIZE_DEF;
                tetromino[3].bottom = (newy - tetromino[3].bottom) * SQUARE_SIZE_DEF;
            }
        }
        //use sin-cos to rotate within a 3x3 space
    }

    //Randomly picks a piece to initialize
    public void tetrominoPicker(){
        Random tPicker = new Random();
        while(true){
            if(tPicker.nextInt(7)== 0){
                oBlockInit();
                break;
            }
            else if(tPicker.nextInt(7) == 1){
                iBlockInit();
                break;
            }
            else if(tPicker.nextInt(7) == 2){
                sBlockInit();
                break;
            }
            else if(tPicker.nextInt(7) == 3){
                zBlockInit();
                break;
            }
            else if(tPicker.nextInt(7) == 4){
                lBlockInit();
                break;
            }
            else if(tPicker.nextInt(7) == 5){
                jBlockInit();
                break;
            }
            else if(tPicker.nextInt(7) == 6){
                tBlockInit();
                break;
            }
            else{
                continue;
            }
        }
        postInvalidate();
    }

}


