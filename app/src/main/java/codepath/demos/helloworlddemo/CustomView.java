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

import java.util.Random;



public class CustomView extends View {

    private static final int SQUARE_SIZE_DEF = getScreenWidth()/16 ;

    private Rect [] tetromino;
    private GridBlock [][] grid;
    private Paint tGridPaint;
    private Paint dPaint;
    public static RectPlayer rPlayer;
    private int score = 0;

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
        tetromino = new Rect[4];
        dPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        dPaint.setColor(colorRandom());
        tGridPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        tGridPaint.setColor(Color.LTGRAY);

        rPlayer = new RectPlayer(tetromino,dPaint);

        rPlayer.initializeTetromino();
        rPlayer.tetrominoPicker();

        grid = new GridBlock[16][10];


        for(int i = 0; i < 16; i++){
            for(int j = 0; j < 10;j++) {
                grid[i][j] = new GridBlock();
                grid[i][j].initializeBlock();
                grid[i][j].setPaint(tGridPaint);
                if(i == 0 && j == 0) {
                    grid[i][j].setRect(j * (SQUARE_SIZE_DEF+1),i * (SQUARE_SIZE_DEF+1),j * (SQUARE_SIZE_DEF+1)+SQUARE_SIZE_DEF,i * (SQUARE_SIZE_DEF+1)+SQUARE_SIZE_DEF);
                }
                else{
                    grid[i][j].setRect(j * (SQUARE_SIZE_DEF+1),i * (SQUARE_SIZE_DEF+1),j * (SQUARE_SIZE_DEF+1)+SQUARE_SIZE_DEF,i * (SQUARE_SIZE_DEF+1)+SQUARE_SIZE_DEF);
                }

            }
        }


    }

    @Override
    protected void onDraw(Canvas canvas){
        clearRow();
        collisionDetection(rPlayer);
        gridBottomCheck(rPlayer);
        rPlayer.boundTetromino();
        for(int i = 0; i < 16; i++){
            for(int j = 0; j < 10; j++){
                grid[i][j].draw(canvas);
            }

        }
        rPlayer.draw(canvas);
        postInvalidate();

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
        int numGen = rnd.nextInt(3);
        if(numGen == 0){
            return Color.GREEN;
        }
        else if(numGen == 1){
            return Color.RED;
        }
        else if (numGen == 2){
            return Color.BLUE;
        }
        else{
            return Color.YELLOW;
        }
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

    public void moveUp(Rect[] tetromino){
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

    public void gridBottomCheck(RectPlayer rPlayer){
            int gridX = 0;
            int gridY = 0;
            boolean resetRPlayer = false;
            boolean underGrid = false;
            Paint tempPaint = rPlayer.returnPaint();
            Rect[] tempRectArray;
            tempRectArray = rPlayer.returnTetromino();
            if(bottomCheck(tempRectArray) == true){
                underGrid = true;
                moveUp(tempRectArray);
            }
            for(int i = 0; i<16; i++){
                for(int j = 0; j < 10; j++){
                    for(int k = 0; k < tempRectArray.length; k++) {
                        gridX = grid[i][j].getX();
                        gridY = grid[i][j].getY();
                        if (tempRectArray[k].contains(gridX,gridY) == true && underGrid == true){
                            grid[i][j].setPaint(tempPaint);
                            resetRPlayer = true;
                        }
                    }
                }
            }
            if(resetRPlayer == true){
                rPlayer.tetrominoPicker();
            }
            postInvalidate();
    }

    public void collisionDetection(RectPlayer rPlayer){
        int gridX;
        int gridY;
        boolean collision = false;
        Paint tempPaint = rPlayer.returnPaint();
        Rect[] tempRectArray;
        tempRectArray = rPlayer.returnTetromino();
        for(int i = 0; i<16; i++){
            for(int j = 0; j < 10; j++){
                for(int k = 0; k < tempRectArray.length; k++) {
                    gridX = grid[i][j].getX();
                    gridY = grid[i][j].getY();
                    if (tempRectArray[k].contains(gridX,gridY) == true && grid[i][j].returnPaint().getColor() != Color.LTGRAY){
                        collision = true;
                        break;
                    }
                }
            }
        }
        if(collision == true){
            moveUp(tempRectArray);
            for(int i = 0; i<16; i++){
                for(int j = 0; j < 10; j++){
                    for(int k = 0; k < tempRectArray.length; k++) {
                        gridX = grid[i][j].getX();
                        gridY = grid[i][j].getY();
                        if (tempRectArray[k].contains(gridX,gridY) == true){
                            grid[i][j].setPaint(tempPaint);
                        }
                    }
                }
            }
            rPlayer.tetrominoPicker();
        }

        postInvalidate();
    }

    //Able to clear a row but does not move everything down
    public void clearRow(){
        //Full is initially true.  Guilty until proven innocent.
        boolean full = true;
        for(int i = 0; i<16; i++){
            for(int j = 0; j < 10; j++){
                //If anyone gridblock is light gray, the column is not full and the boolean is set false.
                if(grid[i][j].returnPaint().getColor() == Color.LTGRAY ){
                    full = false;
                }
            }
            //If the full boolean is still true, it will "clear it" by making the entire grid the light gray color
            if(full == true){
                for(int k = 0; k < 10; k++){
                    grid[i][k].setPaint(Color.LTGRAY);
                }
                score += 100;
            }
            full = true;
        }
        postInvalidate();
    }

    public boolean bottomCheck(Rect[] tetromino){
        for(int i = 0; i<tetromino.length; i++){
            if(tetromino[i].centerY() > (16*SQUARE_SIZE_DEF)){
                return true;
            }
        }
        return false;
    }

    public void resetGrid(){
        for(int i =0; i<16;i++){
            for(int j = 0; j<10;j++){
                grid[i][j].setPaint(Color.LTGRAY);
            }
        }
    }

    public int returnScore(){
        return this.score;
    }

    public void scoreUp(){
        score += 100;
        System.out.println(score);
    }
}


