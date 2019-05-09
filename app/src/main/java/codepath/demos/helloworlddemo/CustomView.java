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

public class CustomView extends View {

    private static final int SQUARE_SIZE_DEF = 200;

    private Rect mRectSquare;
    private Paint mPaintSquare;
    private Rect [][] rectArray;

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

    private void init(@Nullable AttributeSet set){


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
    }

    @Override
    protected void onDraw(Canvas canvas){
        //canvas.drawColor(Color.RED);
        Rect[][] rectArray = new Rect[10][16];
        Paint gray = new Paint();
        gray.setColor(Color.LTGRAY);

        /*for(int i = 0; i < 10; i++){
            for(int j = 0; j < 16; j++){
                rectArray[i][j].left = 1*(j);
                rectArray[i][j].top = 1*i;
                rectArray[i][j].right = rectArray[i][j].left + SQUARE_SIZE_DEF;
                rectArray[i][j].bottom = rectArray[i][j].top + SQUARE_SIZE_DEF;
                canvas.drawRect(rectArray[i][j],gray);
                postInvalidate();

            }
        }*/

        mRectSquare.left = 50;
        mRectSquare.top = 50;
        mRectSquare.right = mRectSquare.left + SQUARE_SIZE_DEF;
        mRectSquare.bottom = mRectSquare.top + SQUARE_SIZE_DEF;

        canvas.drawRect(mRectSquare,mPaintSquare);
    }

    public void swapColor(){
        if(mPaintSquare.getColor() == Color.RED){
            mPaintSquare.setColor(Color.GREEN);
        }
        else if(mPaintSquare.getColor() == Color.GREEN){
            mPaintSquare.setColor(Color.RED);
        }
        else {
            mPaintSquare.setColor(mPaintSquare.getColor() == mSquareColor ? Color.RED : Color.GREEN);
        }
        postInvalidate();
    }

    public void moveDown(){
        mRectSquare.left = 50;
        mRectSquare.top = 50;
        mRectSquare.right = mRectSquare.left + SQUARE_SIZE_DEF;
        mRectSquare.bottom = mRectSquare.top + SQUARE_SIZE_DEF;
    }
}


