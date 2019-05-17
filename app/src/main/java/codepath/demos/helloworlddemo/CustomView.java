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


    public void boundTetromino(){
        //This for loop checks if the rotation of the tetromino is out of bounds and offsets it accordingly to prevent it from going off grid.
        for(int i = 0; i<tetromino.length;i++){
            if(tetromino[i].centerX() > (10*SQUARE_SIZE_DEF)){
                moveLeft();
                break;
            }
            else if (tetromino[i].centerY() < 0){
                moveDown();
                break;
            }
            else if (tetromino[i].centerX() < 0){
                moveRight();
                break;
            }
        }
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


