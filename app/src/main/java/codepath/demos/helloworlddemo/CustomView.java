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

        mRectSquare.left = 50;
        mRectSquare.top = 50;
        mRectSquare.right = mRectSquare.left + SQUARE_SIZE_DEF;
        mRectSquare.bottom = mRectSquare.top + SQUARE_SIZE_DEF;



        canvas.drawRect(mRectSquare,mPaintSquare);
    }

    public void swapColor(){
        mPaintSquare.setColor(mPaintSquare.getColor() == mSquareColor ? Color.RED : Color.GREEN);
        postInvalidate();
    }
}


