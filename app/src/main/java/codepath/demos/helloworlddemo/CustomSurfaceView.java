package codepath.demos.helloworlddemo;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.Random;

public class CustomSurfaceView extends SurfaceView implements SurfaceHolder.Callback {
    private MainThread thread;

    private static final int SQUARE_SIZE_DEF = 100 ;

    private Rect mRectSquare;
    private Paint mPaintSquare;
    private Rect [][] rectArray;
    private Rect [] doubleRect;
    private Paint dPaint;

    private int mSquareColor;
    private int mSquareSize;

    public CustomSurfaceView(Context context){
        super(context);

        getHolder().addCallback(this);
        thread = new MainThread(getHolder(),this);

        setFocusable(true);

    }


    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height){

    }

    //Starts Game loop
    @Override
    public void surfaceCreated(SurfaceHolder holder){
        if(thread != null) return;
        thread = new MainThread(getHolder(),this);

        thread.setRunning(true);
        thread.start();

        doubleRect = new Rect[4];
        dPaint = new Paint();
        dPaint.setColor(Color.LTGRAY);
        for(int i = 0 ; i < 4; i++){
            doubleRect[i] = new Rect();
        }

        mRectSquare = new Rect();
        mPaintSquare = new Paint(Paint.ANTI_ALIAS_FLAG);


        TypedArray ta = getContext().obtainStyledAttributes(null,R.styleable.CustomView);
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
    }

    //Stops Game loop
    @Override
    public void surfaceDestroyed(SurfaceHolder holder){
        boolean retry = true;
        while (retry) {
            try {
                thread.setRunning(false);
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            retry = false;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        return super.onTouchEvent(event);

    }

    public void update(){

    }

    @Override
    public void draw(Canvas canvas){
        super.draw(canvas);
    }

    @Override
    public void onDraw(Canvas canvas){
        dPaint.setColor(colorRandom());


        for(int i = 0; i < doubleRect.length; i++){
            canvas.drawRect(doubleRect[i], dPaint);
        }
    }

    public int colorRandom(){
        Random rnd = new Random();
        return Color.argb(255,rnd.nextInt(256),rnd.nextInt(256),rnd.nextInt(256));
    }
}

