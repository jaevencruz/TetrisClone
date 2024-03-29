package codepath.demos.helloworlddemo;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import java.util.Random;


public class RectPlayer implements GameObject   {
    private Rect tetromino[] = new Rect[4];
    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private static final int SQUARE_SIZE_DEF = getScreenWidth()/16 ;
    //prevMove is used to determine the direction that the player moved (0 = no move, 1 = moved right, 2 = moved left, 3 = moved down)
    private int prevMove = 0;
    private int nextPiece = 0;

    public RectPlayer(Rect tetromino[], Paint paint) {
        this.tetromino = tetromino;
        this.paint = paint;
    }
    public RectPlayer(){
    }

    public Paint returnPaint(){
        return this.paint;
    }

    public int returnNextPiece(){
        return this.nextPiece;
    }

    public void setBlock(Rect rect, int i){
        tetromino[i] = rect;
    }

    public void initializeTetromino(){
        for(int i = 0; i < tetromino.length; i++){
            tetromino[i] = new Rect();
        }
        setNextPiece();
    }

    public Rect[] returnTetromino(){
        return tetromino;
    }

    @Override
    public void draw(Canvas canvas){
        for(int i = 0; i < tetromino.length; i++){
            canvas.drawRect(tetromino[i], paint);
        }
    }

    @Override
    public void update(){
        //l,t,r,b
        //rectangle.set();
    }


    public void setPaint(Paint paint){
        this.paint = paint;
    }

    public int returnMove(){
        return this.prevMove;
    }

    public synchronized void fallout(){
        BackEnd td = new BackEnd();
        td.start();

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
        /*for(int i = 0; i<tetromino.length;i++){
            if ((tetromino[i].centerX() + SQUARE_SIZE_DEF )> 10*(SQUARE_SIZE_DEF+1)){
                return;
            }
        }*/
        for(int i = 0; i < tetromino.length; i++) {
            tetromino[i].left = tetromino[i].left + SQUARE_SIZE_DEF;

            tetromino[i].right = tetromino[i].right + SQUARE_SIZE_DEF;
        }
        this.prevMove = 1;
    }

    public void moveLeft(){
        //Checks if block is at left corner and restricts movement there
        /*for(int i = 0; i<tetromino.length;i++){
            if ((tetromino[i].left - SQUARE_SIZE_DEF )< 0){
                return;
            }
        }*/
        for(int i = 0; i < tetromino.length; i++) {
            tetromino[i].left = tetromino[i].left - SQUARE_SIZE_DEF;

            tetromino[i].right = tetromino[i].right - SQUARE_SIZE_DEF;
        }
        this.prevMove = 2;
    }

    public void moveUp(){
        //Checks if block is at the top and restricts movement there
        /*for(int i = 0; i<tetromino.length;i++){
            if ((tetromino[i].top - SQUARE_SIZE_DEF )< 0){
                return;
            }
        }*/
        for(int i = 0; i < tetromino.length; i++) {
            tetromino[i].top = tetromino[i].top - SQUARE_SIZE_DEF;

            tetromino[i].bottom = tetromino[i].bottom - SQUARE_SIZE_DEF;
        }
        this.prevMove = 4;
    }

    public void moveDown(){
        //Checks if block is at bottom and restricts movement there
        /*for(int i = 0; i<tetromino.length;i++){
            if ((tetromino[i].bottom)> (16*(SQUARE_SIZE_DEF+1))){
                tetrominoPicker();
            }
        }*/
        for(int i = 0; i < tetromino.length; i++) {
            tetromino[i].top = tetromino[i].top + SQUARE_SIZE_DEF;

            tetromino[i].bottom = tetromino[i].bottom + SQUARE_SIZE_DEF;
        }
        this.prevMove = 3;
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
        //use sin-cos to rotate within a 3x3 space
    }

    public void setNextPiece(){
        Random tPicker = new Random();
        int num = tPicker.nextInt(7);
        this.nextPiece = num;
    }
    //Randomly picks a piece to initialize
    public void tetrominoPicker(){

        paint.setColor(colorRandom());
        while(true){
            if(nextPiece == 0){
                oBlockInit();
                for(int i = 0; i<4;i++){
                    moveRight();
                }
                break;
            }
            else if(nextPiece == 1){
                iBlockInit();
                for(int i = 0; i<4;i++){
                    moveRight();
                }
                break;
            }
            else if(nextPiece == 2){
                sBlockInit();
                for(int i = 0; i<4;i++){
                    moveRight();
                }
                break;
            }
            else if(nextPiece == 3){
                zBlockInit();
                for(int i = 0; i<4;i++){
                    moveRight();
                }
                break;
            }
            else if(nextPiece == 4){
                lBlockInit();
                for(int i = 0; i<4;i++){
                    moveRight();
                }
                break;
            }
            else if(nextPiece == 5){
                jBlockInit();
                for(int i = 0; i<4;i++){
                    moveRight();
                }
                break;
            }
            else if(nextPiece == 6){
                tBlockInit();
                for(int i = 0; i<3;i++){
                    moveRight();
                }
                break;
            }
            else{
                continue;
            }
        }

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
            else if (tetromino[i].centerY() > 16*SQUARE_SIZE_DEF){
                moveUp();
                break;
            }
            else if (tetromino[i].centerX() < 0){
                moveRight();
                break;
            }
        }
    }

    public Rect tetrominoBlockReturn(int i){
        if(i > 4){
            return tetromino[0];
        }
        return tetromino[i];
    }


}
