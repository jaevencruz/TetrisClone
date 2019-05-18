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
    private static final int SQUARE_SIZE_PREV = getScreenWidth()/24 ;

    private Rect [] tetrominoPrev;
    private Rect [] tetrominoInit;
    private GridBlock [][] grid;
    private Paint tGridPaint;
    private Paint dPaint;
    private Paint tPaint;
    private Paint textPaint;
    private Paint prevTet;
    public static RectPlayer rPlayer;
    private String scoreStr;
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
        scoreStr = new String();
        scoreStr = "Score: " + score;
        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        prevTet = new Paint(Paint.ANTI_ALIAS_FLAG);
        prevTet.setColor(Color.BLACK);
        textPaint.setColor(Color.BLACK);
        tetrominoPrev = new Rect[4];
        tetrominoInit = new Rect[4];
        dPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        dPaint.setColor(colorRandom());
        tGridPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        tGridPaint.setColor(Color.LTGRAY);
        tPaint = new Paint();
        tPaint.setColor(colorRandom());

        rPlayer = new RectPlayer(tetrominoInit,tPaint);

        rPlayer.initializeTetromino();
        rPlayer.tetrominoPicker();

        for(int i = 0;i<tetrominoPrev.length;i++){
            tetrominoPrev[i] = new Rect();
        }

        grid = new GridBlock[16][10];
        setTextSizeForWidth(textPaint,200,scoreStr);
        tetrominoPrevPicker();

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
        scoreStr = "Score: " + score;
        clearRow();
        gravity();
        collisionDetection(rPlayer);
        gridBottomCheck(rPlayer);
        rPlayer.boundTetromino();
        for(int i = 0; i < 16; i++){
            for(int j = 0; j < 10; j++){
                grid[i][j].draw(canvas);
            }

        }
        for(int k = 0;k<4;k++){
            canvas.drawRect(tetrominoPrev[k],prevTet);
        }
        rPlayer.draw(canvas);

        canvas.drawText(scoreStr, 11*SQUARE_SIZE_DEF, 100, textPaint);
        canvas.drawText("Next Piece: ", 11*SQUARE_SIZE_DEF, 150, textPaint);
        postInvalidate();
    }

    public void swapColor(){
        dPaint.setColor(colorRandom());
        postInvalidate();
    }

    //Sets size of score text
    private static void setTextSizeForWidth(Paint paint, float desiredWidth,
                                            String text) {
        final float testTextSize = 48f;

        paint.setTextSize(testTextSize);
        Rect bounds = new Rect();
        paint.getTextBounds(text, 0, text.length(), bounds);

        float desiredTextSize = testTextSize * desiredWidth / bounds.width();

        paint.setTextSize(desiredTextSize);
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
            return Color.BLACK;
        }
    }

    public void moveDown(Rect[] tetromino){
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

    public void moveRight(Rect[] tetromino){
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

    public void moveLeft(Rect[] tetromino){
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

    public void moveDown(){
        //Checks if block is at bottom and restricts movement there
        rPlayer.moveDown();
        this.score += 10;
        postInvalidate();
    }

    public void moveRight(){
        //Checks if block is at right corner and restricts movement there
        rPlayer.moveRight();
        postInvalidate();
    }

    public void moveLeft(){
        //Checks if block is at left corner and restricts movement there
        rPlayer.moveLeft();
        postInvalidate();
    }

    public void moveUp(){
        //Checks if block is at the top and restricts movement there
        rPlayer.moveUp();
        postInvalidate();
    }

    public static int getScreenWidth() {
        return Resources.getSystem().getDisplayMetrics().widthPixels;
    }

    public void gridBottomCheck(RectPlayer rPlayer){
            int gridX = 0;
            int gridY = 0;
            int color = rPlayer.returnPaint().getColor();
            boolean resetRPlayer = false;
            boolean underGrid = false;
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
                            grid[i][j].setPaint(color);
                            resetRPlayer = true;
                        }
                    }
                }
            }
            if(resetRPlayer == true){
                rPlayer.tetrominoPicker();
                rPlayer.setNextPiece();
                tetrominoPrevPicker();
            }
            postInvalidate();
    }

    public void collisionDetection(RectPlayer rPlayer){
        int gridX;
        int gridY;
        boolean collision = false;
        int color = rPlayer.returnPaint().getColor();
        Rect[] tempRectArray;
        tempRectArray = rPlayer.returnTetromino();
        for(int i = 0; i<16; i++){
            for(int j = 0; j < 10; j++){
                for(int k = 0; k < tempRectArray.length; k++) {
                    gridX = grid[i][j].getX();
                    gridY = grid[i][j].getY();
                    if (tempRectArray[k].contains(gridX,gridY) == true && grid[i][j].returnPaint().getColor() != Color.LTGRAY){
                        //If else checks if the player collides with a colored grid from the right or left and merely prevents them from moving rather than setting the tetrominoPrev at that point
                        if(rPlayer.returnMove() == 1){
                            moveLeft();
                            continue;
                        }
                        else if(rPlayer.returnMove() == 2){
                            moveRight();
                            continue;
                        }
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
                            grid[i][j].setPaint(color);
                        }
                    }
                }
            }
            rPlayer.tetrominoPicker();
            rPlayer.setNextPiece();
            tetrominoPrevPicker();
        }

        postInvalidate();
    }

    //Method to return int specifically made for the instafall method
    public int isCollide(RectPlayer rPlayer){
        int gridX;
        int gridY;
        Rect[] tempRectArray;
        boolean underGrid = false;
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
                    if( tempRectArray[k].contains(gridX,gridY) == true && underGrid == true){
                        //This returns when the
                        return 1;
                    }
                    else if (tempRectArray[k].contains(gridX,gridY) == true && grid[i][j].returnPaint().getColor() != Color.LTGRAY ){
                        return 2;
                    }

                }
            }
        }
        postInvalidate();
        return 0;
    }

    public void gravity(){
        boolean empty = true;
        int tempColor;
        for(int i = 0; i<16; i++){
            empty = true;
            for(int j = 0; j < 10; j++){
                //If anyone gridblock is not light gray, the row is not empty and the boolean is set false.
                if(grid[i][j].returnPaint().getColor() != Color.LTGRAY ){
                    empty = false;
                }
            }
            //If the full boolean is still true, it will "clear it" by making the entire grid the light gray color
            if(empty == true) {
                for (int k = i; k < 0; k--) {
                    for (int l = 0; l < 10; l++) {
                        if (k == 0) {
                            continue;
                        }
                        tempColor = grid[k - 1][l].returnPaint().getColor();
                        grid[k][l].setPaint(tempColor);
                    }
                }
            }
        }
        invalidate();
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
        invalidate();
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
        this.score = 0;
    }

    public void scoreUp(){
        score += 100;
        System.out.println(score);
    }

    public void moveToBottom(){
        while(true){
            if(isCollide(rPlayer) == 1){
                moveDown();
                gridBottomCheck(rPlayer);
                break;
            }
            else if(isCollide(rPlayer)==2){

                collisionDetection(rPlayer);
                break;
            }
            moveDown();
        }

    }

    /*The init methods within this CustomView are used to generate the next Tetromino preview on the RHS of the screen*/

    public void sBlockInit(){
        tetrominoPrev[0].left = 12 * SQUARE_SIZE_DEF;
        tetrominoPrev[0].top = 4*SQUARE_SIZE_DEF;
        tetrominoPrev[0].right = tetrominoPrev[0].left + SQUARE_SIZE_PREV;
        tetrominoPrev[0].bottom = tetrominoPrev[0].top + SQUARE_SIZE_PREV;

        tetrominoPrev[1].left = tetrominoPrev[0].left + SQUARE_SIZE_PREV + 1;
        tetrominoPrev[1].top = tetrominoPrev[0].top;
        tetrominoPrev[1].right = tetrominoPrev[1].left + SQUARE_SIZE_PREV ;
        tetrominoPrev[1].bottom = tetrominoPrev[1].top + SQUARE_SIZE_PREV;


        tetrominoPrev[2].left = tetrominoPrev[1].left;
        tetrominoPrev[2].top = tetrominoPrev[1].top - SQUARE_SIZE_PREV - 1;
        tetrominoPrev[2].right = tetrominoPrev[2].left + SQUARE_SIZE_PREV ;
        tetrominoPrev[2].bottom = tetrominoPrev[2].top+SQUARE_SIZE_PREV;


        tetrominoPrev[3].left = tetrominoPrev[2].left + SQUARE_SIZE_PREV + 1;
        tetrominoPrev[3].top = tetrominoPrev[2].top;
        tetrominoPrev[3].right = tetrominoPrev[3].left + SQUARE_SIZE_PREV ;
        tetrominoPrev[3].bottom = tetrominoPrev[3].top + SQUARE_SIZE_PREV;


    }

    //Initializes S Block
    public void zBlockInit(){
        tetrominoPrev[0].left = 12* SQUARE_SIZE_DEF;
        tetrominoPrev[0].top = 3*SQUARE_SIZE_DEF;
        tetrominoPrev[0].right = tetrominoPrev[0].left + SQUARE_SIZE_PREV;
        tetrominoPrev[0].bottom = tetrominoPrev[0].top + SQUARE_SIZE_PREV;

        tetrominoPrev[1].left = tetrominoPrev[0].left + SQUARE_SIZE_PREV + 1;
        tetrominoPrev[1].top =  tetrominoPrev[0].top;
        tetrominoPrev[1].right = tetrominoPrev[1].left + SQUARE_SIZE_PREV ;
        tetrominoPrev[1].bottom = tetrominoPrev[1].top + SQUARE_SIZE_PREV;

        tetrominoPrev[2].left = tetrominoPrev[1].left ;
        tetrominoPrev[2].top = tetrominoPrev[1].top + SQUARE_SIZE_PREV + 1;
        tetrominoPrev[2].right = tetrominoPrev[2].left + SQUARE_SIZE_PREV ;
        tetrominoPrev[2].bottom = tetrominoPrev[2].top + SQUARE_SIZE_PREV;

        tetrominoPrev[3].left = tetrominoPrev[2].left + SQUARE_SIZE_PREV + 1;
        tetrominoPrev[3].top = tetrominoPrev[2].top;
        tetrominoPrev[3].right = tetrominoPrev[3].left + SQUARE_SIZE_PREV;
        tetrominoPrev[3].bottom = tetrominoPrev[3].top + SQUARE_SIZE_PREV;

    }

    //Initializes O Block
    public void oBlockInit(){
        tetrominoPrev[0].left = 12*SQUARE_SIZE_DEF + 25;
        tetrominoPrev[0].top = 3*SQUARE_SIZE_DEF;
        tetrominoPrev[0].right = tetrominoPrev[0].left + SQUARE_SIZE_PREV;
        tetrominoPrev[0].bottom = tetrominoPrev[0].top + SQUARE_SIZE_PREV;

        tetrominoPrev[1].left = tetrominoPrev[0].left + SQUARE_SIZE_PREV + 1;
        tetrominoPrev[1].top =  tetrominoPrev[0].top;
        tetrominoPrev[1].right = tetrominoPrev[1].left + SQUARE_SIZE_PREV;
        tetrominoPrev[1].bottom = tetrominoPrev[1].top + SQUARE_SIZE_PREV;

        tetrominoPrev[2].left = tetrominoPrev[0].left;
        tetrominoPrev[2].top = tetrominoPrev[0].top + SQUARE_SIZE_PREV + 1;
        tetrominoPrev[2].right = tetrominoPrev[2].left + SQUARE_SIZE_PREV ;
        tetrominoPrev[2].bottom = tetrominoPrev[2].top + SQUARE_SIZE_PREV;

        tetrominoPrev[3].left = tetrominoPrev[2].left + SQUARE_SIZE_PREV + 1;
        tetrominoPrev[3].top = tetrominoPrev[2].top;
        tetrominoPrev[3].right = tetrominoPrev[3].left + SQUARE_SIZE_PREV ;
        tetrominoPrev[3].bottom = tetrominoPrev[3].top + SQUARE_SIZE_PREV;

    }

    //Initializes I block
    public void iBlockInit(){
        tetrominoPrev[0].left = 12*SQUARE_SIZE_DEF + 35;
        tetrominoPrev[0].top = 3*SQUARE_SIZE_DEF;
        tetrominoPrev[0].right = tetrominoPrev[0].left + SQUARE_SIZE_PREV;
        tetrominoPrev[0].bottom = tetrominoPrev[0].top + SQUARE_SIZE_PREV;

        tetrominoPrev[1].left = tetrominoPrev[0].left;
        tetrominoPrev[1].top =  tetrominoPrev[0].top+SQUARE_SIZE_PREV+1;
        tetrominoPrev[1].right = tetrominoPrev[1].left + SQUARE_SIZE_PREV ;
        tetrominoPrev[1].bottom = tetrominoPrev[1].top + SQUARE_SIZE_PREV;

        tetrominoPrev[2].left = tetrominoPrev[1].left;
        tetrominoPrev[2].top = tetrominoPrev[1].top+SQUARE_SIZE_PREV+1;
        tetrominoPrev[2].right = tetrominoPrev[2].left + SQUARE_SIZE_PREV ;
        tetrominoPrev[2].bottom = tetrominoPrev[2].top + SQUARE_SIZE_PREV;

        tetrominoPrev[3].left = tetrominoPrev[2].left;
        tetrominoPrev[3].top = tetrominoPrev[2].top + SQUARE_SIZE_PREV + 1;
        tetrominoPrev[3].right = tetrominoPrev[3].left + SQUARE_SIZE_PREV ;
        tetrominoPrev[3].bottom = tetrominoPrev[3].top + SQUARE_SIZE_PREV;

    }

    //Initializes L block
    public void lBlockInit(){
        tetrominoPrev[0].left = 12*SQUARE_SIZE_DEF + 25;
        tetrominoPrev[0].top = 3*SQUARE_SIZE_DEF;
        tetrominoPrev[0].right = tetrominoPrev[0].left + SQUARE_SIZE_PREV;
        tetrominoPrev[0].bottom = tetrominoPrev[0].top + SQUARE_SIZE_PREV;

        tetrominoPrev[1].left = tetrominoPrev[0].left;
        tetrominoPrev[1].top =  tetrominoPrev[0].top+SQUARE_SIZE_PREV+1;
        tetrominoPrev[1].right = tetrominoPrev[1].left + SQUARE_SIZE_PREV ;
        tetrominoPrev[1].bottom = tetrominoPrev[1].top + SQUARE_SIZE_PREV;

        tetrominoPrev[2].left = tetrominoPrev[1].left;
        tetrominoPrev[2].top = tetrominoPrev[1].top+SQUARE_SIZE_PREV+1;
        tetrominoPrev[2].right = tetrominoPrev[2].left + SQUARE_SIZE_PREV ;
        tetrominoPrev[2].bottom = tetrominoPrev[2].top + SQUARE_SIZE_PREV;

        tetrominoPrev[3].left = tetrominoPrev[2].left + SQUARE_SIZE_PREV + 1;
        tetrominoPrev[3].top = tetrominoPrev[2].top;
        tetrominoPrev[3].right = tetrominoPrev[3].left + SQUARE_SIZE_PREV ;
        tetrominoPrev[3].bottom = tetrominoPrev[3].top + SQUARE_SIZE_PREV;
    }

    //Initializes J Block
    public void jBlockInit(){
        tetrominoPrev[0].left = 12*SQUARE_SIZE_DEF + 25;
        tetrominoPrev[0].top = 3*SQUARE_SIZE_DEF;
        tetrominoPrev[0].right = tetrominoPrev[0].left + SQUARE_SIZE_PREV;
        tetrominoPrev[0].bottom = tetrominoPrev[0].top + SQUARE_SIZE_PREV;

        tetrominoPrev[1].left = tetrominoPrev[0].left;
        tetrominoPrev[1].top =  tetrominoPrev[0].top+SQUARE_SIZE_PREV+1;
        tetrominoPrev[1].right = tetrominoPrev[1].left + SQUARE_SIZE_PREV ;
        tetrominoPrev[1].bottom = tetrominoPrev[1].top + SQUARE_SIZE_PREV;

        tetrominoPrev[2].left = tetrominoPrev[1].left;
        tetrominoPrev[2].top = tetrominoPrev[1].top+SQUARE_SIZE_PREV+1;
        tetrominoPrev[2].right = tetrominoPrev[2].left + SQUARE_SIZE_PREV ;
        tetrominoPrev[2].bottom = tetrominoPrev[2].top + SQUARE_SIZE_PREV;

        tetrominoPrev[3].left = tetrominoPrev[2].left - SQUARE_SIZE_PREV - 1;
        tetrominoPrev[3].top = tetrominoPrev[2].top;
        tetrominoPrev[3].right = tetrominoPrev[3].left + SQUARE_SIZE_PREV ;
        tetrominoPrev[3].bottom = tetrominoPrev[3].top + SQUARE_SIZE_PREV;
    }

    //Initializes T Block
    public void tBlockInit(){
        tetrominoPrev[0].left = 12* SQUARE_SIZE_DEF;
        tetrominoPrev[0].top = 3*SQUARE_SIZE_DEF;
        tetrominoPrev[0].right = tetrominoPrev[0].left + SQUARE_SIZE_PREV;
        tetrominoPrev[0].bottom = tetrominoPrev[0].top + SQUARE_SIZE_PREV;

        tetrominoPrev[1].left = tetrominoPrev[0].left + SQUARE_SIZE_PREV + 1;
        tetrominoPrev[1].top =  tetrominoPrev[0].top;
        tetrominoPrev[1].right = tetrominoPrev[1].left + SQUARE_SIZE_PREV ;
        tetrominoPrev[1].bottom = tetrominoPrev[1].top + SQUARE_SIZE_PREV;

        tetrominoPrev[2].left = tetrominoPrev[1].left ;
        tetrominoPrev[2].top = tetrominoPrev[1].top + SQUARE_SIZE_PREV + 1;
        tetrominoPrev[2].right = tetrominoPrev[2].left + SQUARE_SIZE_PREV ;
        tetrominoPrev[2].bottom = tetrominoPrev[2].top + SQUARE_SIZE_PREV;

        tetrominoPrev[3].left = tetrominoPrev[2].left + SQUARE_SIZE_PREV + 1;
        tetrominoPrev[3].top = tetrominoPrev[1].top;
        tetrominoPrev[3].right = tetrominoPrev[3].left + SQUARE_SIZE_PREV;
        tetrominoPrev[3].bottom = tetrominoPrev[3].top + SQUARE_SIZE_PREV;
    }

    public void tetrominoPrevPicker(){

        while(true){
            if(rPlayer.returnNextPiece() == 0){
                oBlockInit();
                break;
            }
            else if(rPlayer.returnNextPiece() == 1){
                iBlockInit();
                break;
            }
            else if(rPlayer.returnNextPiece() == 2){
                sBlockInit();
                break;
            }
            else if(rPlayer.returnNextPiece() == 3){
                zBlockInit();
                break;
            }
            else if(rPlayer.returnNextPiece() == 4){
                lBlockInit();
                break;
            }
            else if(rPlayer.returnNextPiece() == 5){
                jBlockInit();
                break;
            }
            else if(rPlayer.returnNextPiece() == 6){
                tBlockInit();
                break;
            }
            else{
                continue;
            }
        }

    }
}


