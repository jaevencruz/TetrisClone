package codepath.demos.helloworlddemo;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.Random;


public class CustomView extends View {

    private static final int SQUARE_SIZE_DEF = 100 ;

    private Rect mRectSquare;
    private Paint mPaintSquare;
    private Rect [][] rectArray;
    private Rect [] doubleRect;
    private Paint dPaint;

    private int mSquareColor;
    private int mSquareSize;

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
        doubleRect = new Rect[4];
        dPaint = new Paint();
        dPaint.setColor(Color.LTGRAY);
        for(int i = 0 ; i < 4; i++){
           doubleRect[i] = new Rect();
        }

        mRectSquare = new Rect();
        mPaintSquare = new Paint(Paint.ANTI_ALIAS_FLAG);


        if(set == null){
            return;
        }
        TypedArray ta = getContext().obtainStyledAttributes(set,R.styleable.CustomView);
        mSquareColor = ta.getColor(R.styleable.CustomView_square_color, Color.GREEN);
        mSquareSize = ta.getDimensionPixelSize(R.styleable.CustomView_square_size,SQUARE_SIZE_DEF);

        mPaintSquare.setColor(mSquareColor);
        ta.recycle();

        mRectSquare.left = 0;
        mRectSquare.top = 0;
        mRectSquare.right = mRectSquare.left + SQUARE_SIZE_DEF;
        mRectSquare.bottom = mRectSquare.top + SQUARE_SIZE_DEF;

        doubleRect[0].left = 0;
        doubleRect[0].top = 0;
        doubleRect[0].right = doubleRect[0].left + SQUARE_SIZE_DEF;
        doubleRect[0].bottom = doubleRect[0].top + SQUARE_SIZE_DEF;

        doubleRect[1].left = doubleRect[0].left + SQUARE_SIZE_DEF + 1;
        doubleRect[1].top = 0;
        doubleRect[1].right = doubleRect[1].left + SQUARE_SIZE_DEF ;
        doubleRect[1].bottom = doubleRect[1].top + SQUARE_SIZE_DEF;

        //Printing an array of squares curr
        /*for(int i = 0; i < 10; i++){
            for(int j = 0; j < 16; j++){
                rectArray[i][j].left = 1*(j);
                rectArray[i][j].top = 1*i;
                rectArray[i][j].right = rectArray[i][j].left + SQUARE_SIZE_DEF;
                rectArray[i][j].bottom = rectArray[i][j].top + SQUARE_SIZE_DEF;

            }
        }*/
    }

    @Override
    protected void onDraw(Canvas canvas){

        dPaint.setColor(colorRandom());
        //canvas.drawColor(Color.RED);
        /*for(int i = 0; i < 10; i++){
            for(int j = 0; j < 16; i++){
                canvas.drawRect(rectArray[i][j],gray);
            }
        }*/


        for(int i = 0; i < doubleRect.length; i++){
            canvas.drawRect(doubleRect[i], dPaint);
        }

    }

    public void swapColor(){
        dPaint.setColor(colorRandom());
        postInvalidate();
    }

    public void moveDown(){
        for(int i = 0; i < doubleRect.length; i++) {
            doubleRect[i].top = doubleRect[i].top + SQUARE_SIZE_DEF;

            doubleRect[i].bottom = doubleRect[i].bottom + SQUARE_SIZE_DEF;
        }
        postInvalidate();
    }

    public int colorRandom(){
        Random rnd = new Random();
        return Color.argb(255,rnd.nextInt(256),rnd.nextInt(256),rnd.nextInt(256));
    }

    public void moveRight(){
        for(int i = 0; i < doubleRect.length; i++) {
            doubleRect[i].left = doubleRect[i].left + SQUARE_SIZE_DEF;

            doubleRect[i].right = doubleRect[i].left + SQUARE_SIZE_DEF;
        }
        postInvalidate();
    }

    public void moveLeft(){
        for(int i = 0; i < doubleRect.length; i++) {
            doubleRect[i].left = doubleRect[i].left - SQUARE_SIZE_DEF;

            doubleRect[i].right = doubleRect[i].left - SQUARE_SIZE_DEF;
        }
        postInvalidate();
    }

    public void moveUp(){
        for(int i = 0; i < doubleRect.length; i++) {
            doubleRect[i].top = doubleRect[i].top - SQUARE_SIZE_DEF;

            doubleRect[i].bottom = doubleRect[i].bottom - SQUARE_SIZE_DEF;
        }
        postInvalidate();
    }


}


