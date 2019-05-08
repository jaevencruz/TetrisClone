package codepath.demos.helloworlddemo;

import android.content.Context;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class TetrisView extends SurfaceView implements SurfaceHolder.Callback {

    private MainThread thread;

    public TetrisView(Context context) {
        super(context);

        getHolder().addCallback(this);

        thread = new MainThread(getHolder(), this);
        setFocusable(true);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        //CharacterSprite = new CharacterSprite(BitmapFactory.decodeResource(getResources(),R.drawable.bridge));
        thread.setRunning(true);
        thread.start();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
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

    public void update() {
        System.out.println("Haha");
    }

    //@Override
    /*public void onDraw(Canvas canvas, Rect rectangle){
        super.draw(canvas);
        if(canvas!=null){
            CharacterSprite.draw(canvas, rectangle);
        }
    }*/
}

