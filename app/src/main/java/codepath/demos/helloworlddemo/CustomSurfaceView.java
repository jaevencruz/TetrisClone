package codepath.demos.helloworlddemo;

import android.content.Context;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class CustomSurfaceView extends SurfaceView implements SurfaceHolder.Callback {
    private MainThread thread;

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
        thread = new MainThread(getHolder(),this);

        thread.setRunning(true);
        thread.start();
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
}

