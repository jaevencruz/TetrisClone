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

    private Rect [][] tetrisGrid;  //Use this and make position
    private Rect [] tetromino;
    private RectPlayer [][] usedPieces;
    private Paint tGridPaint;
    private Paint dPaint;
    public static RectPlayer rPlayer;

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

        rPlayer = new RectPlayer(tetromino,dPaint);
        rPlayer.setPaint(dPaint);
        rPlayer.initializeTetromino();
        rPlayer.tetrominoPicker();

        usedPieces = new RectPlayer[16][10];
        //Initializes each rectangle in Tetromino array
        for(int i = 0; i < tetromino.length; i++){
           tetromino[i] = new Rect();
        }


        //Initializes each rectangle in tetrisGrid
        for(int i = 0; i < 16; i++){
            for(int j = 0; j < 10;j++) {
                tetrisGrid[i][j] = new Rect();
                if(i == 0 && j == 0) {
                    tetrisGrid[i][j].left = j * (SQUARE_SIZE_DEF+1);
                    tetrisGrid[i][j].top = i * (SQUARE_SIZE_DEF+1);
                    tetrisGrid[i][j].right = tetrisGrid[i][j].left + SQUARE_SIZE_DEF;
                    tetrisGrid[i][j].bottom = tetrisGrid[i][j].top + SQUARE_SIZE_DEF;
                }
                else{
                    tetrisGrid[i][j].left = j * (SQUARE_SIZE_DEF+1);
                    tetrisGrid[i][j].top = i * (SQUARE_SIZE_DEF+1);
                    tetrisGrid[i][j].right = tetrisGrid[i][j].left + SQUARE_SIZE_DEF;
                    tetrisGrid[i][j].bottom = tetrisGrid[i][j].top + SQUARE_SIZE_DEF;
                }

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
        rPlayer.draw(canvas);
        /*for(int i = 0; i < tetromino.length; i++){
            canvas.drawRect(tetromino[i], dPaint);
        }*/
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
        tetromino[2].top = 2*(SQUARE_SIZE_DEF+1);
        tetromino[2].right = tetromino[2].left + SQUARE_SIZE_DEF ;
        tetromino[2].bottom = tetromino[2].top + SQUARE_SIZE_DEF;

        tetromino[3].left = 0;
        tetromino[3].top = 3*(SQUARE_SIZE_DEF+1 );
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
    public void rotatecw(){
        int pivotx = tetromino[2].centerX();
        int pivoty = tetromino[2].centerY();
        int rotatex, rotatey, turnx, turny, t1, t2, finalx, finaly;
        for(int i = 0;i < 4; i++){
            rotatex = tetromino[i].centerX();
            rotatey = tetromino[i].centerY();
            System.out.println("The old coords for block" + i + "are" + "(" + rotatex + "," + rotatey + ")");
            turnx = rotatex - pivotx;
            turny = rotatey - pivoty;
            t1 = 0 * turnx + 1 * turny;
            t2 = 1 * turnx + 0 * turny;
            finalx = pivotx + t1;
            finaly = pivoty + t2;
            System.out.println("The new coords for block " + i + " are " + "(" + finalx + "," + finaly + ")");
            tetromino[i].left = finalx - (SQUARE_SIZE_DEF/2);
            tetromino[i].right = finalx + (SQUARE_SIZE_DEF/2);
            tetromino[i].top = finaly - (SQUARE_SIZE_DEF/2);
            tetromino[i].bottom = finaly + (SQUARE_SIZE_DEF/2);

        }
        boundTetromino();
        postInvalidate();
        //use sin-cos to rotate within a 3x3 space
    }

    public void rotateccw(){
        int pivotx = tetromino[2].centerX();
        int pivoty = tetromino[2].centerY();
        int rotatex, rotatey, turnx, turny, t1, t2, finalx, finaly;
        for(int i = 0;i < 4; i++){
            rotatex = tetromino[i].centerX();
            rotatey = tetromino[i].centerY();
            turnx = rotatex - pivotx;
            turny = rotatey - pivoty;
            t1 = 0 * turnx - 1 * turny;
            t2 = 1 * turnx + 0 * turny;
            finalx = pivotx + t1;
            finaly = pivoty + t2;
            tetromino[i].left = finalx - (SQUARE_SIZE_DEF/2);
            tetromino[i].right = finalx + (SQUARE_SIZE_DEF/2);
            tetromino[i].top = finaly - (SQUARE_SIZE_DEF/2);
            tetromino[i].bottom = finaly + (SQUARE_SIZE_DEF/2);
        }
        boundTetromino();
        postInvalidate();
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

    public void blockAtBottom(){
        for(int a = 0; a<tetromino.length;a++){
            if ((tetromino[a].bottom + SQUARE_SIZE_DEF )> (16*(SQUARE_SIZE_DEF+1))){
                for(int i = 0; i < 16; i++){
                    for(int j = 0; j < 10;j++) {
                        tetrisGrid[i][j].left = j*(SQUARE_SIZE_DEF ) + 1;
                        tetrisGrid[i][j].top = i*(SQUARE_SIZE_DEF ) + 1;
                        tetrisGrid[i][j].right = tetrisGrid[i][j].left + SQUARE_SIZE_DEF;
                        tetrisGrid[i][j].bottom = tetrisGrid[i][j].top + SQUARE_SIZE_DEF;
                    }
                }
            }
        }
    }

    public void boundTetromino(){
        //This for loop checks if the rotation of the tetromino is out of bounds and offsets it accordingly to prevent it from going off grid.
        for(int i = 0; i<tetromino.length;i++){
            if ((tetromino[i].right + SQUARE_SIZE_DEF )> 10*(SQUARE_SIZE_DEF)){
                moveLeft();
                break;
            }
            else if ((tetromino[i].left - SQUARE_SIZE_DEF )< 0){
                moveRight();
                break;
            }
            else if ((tetromino[i].top - SQUARE_SIZE_DEF )< 0){
                moveDown();
                break;
            }
            else if ((tetromino[i].bottom + SQUARE_SIZE_DEF )> (16*(SQUARE_SIZE_DEF))){
                moveUp();
                break;
            }
        }
    }

    public void gridCheck(){
            for(int i = 0; i<16; i++){
                for(int j = 0; j < 10; j++){
                }
            }
    }

}


