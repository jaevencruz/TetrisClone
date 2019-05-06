package codepath.demos.helloworlddemo;

import android.graphics.Canvas;
import android.view.Surface;
import android.view.SurfaceHolder;

public class MainThread extends Thread{
    private SurfaceHolder surfaceHolder;
    private TetrisView tetrisView;
    private boolean running;
    public static Canvas canvas;

    public MainThread(SurfaceHolder surfaceHolder, TetrisView tetrisView){
        super();
        this.surfaceHolder = surfaceHolder;
        this.tetrisView = tetrisView;
    }

    public void setRunning(boolean isRunning){
        running = isRunning;
    }

    @Override
    public void run(){
        while(running){
            canvas = null;
            try {
                canvas = this.surfaceHolder.lockCanvas();
                synchronized (surfaceHolder) {
                    this.tetrisView.update();
                    this.tetrisView.draw(canvas);
                }
            } catch (Exception e){
            }
            finally{
                if(canvas!=null){
                    try{
                        surfaceHolder.unlockCanvasAndPost(canvas);
                    }
                    catch(Exception e){e.printStackTrace();}
                }
            }
        }
    }

}

