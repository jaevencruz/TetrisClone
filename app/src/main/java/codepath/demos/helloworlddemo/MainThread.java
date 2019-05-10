package codepath.demos.helloworlddemo;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

public class MainThread extends Thread{
    public static final int MAX_FPS = 30;
    private double averageFPS;
    private SurfaceHolder surfaceHolder;
    private CustomSurfaceView customSurfaceView;
    private boolean running;
    public static Canvas canvas;

    public MainThread(SurfaceHolder surfaceHolder, CustomSurfaceView customSurfaceView){
        super();
        this.surfaceHolder = surfaceHolder;
        this.customSurfaceView = customSurfaceView;
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
                    this.customSurfaceView.update();
                    this.customSurfaceView.draw(canvas);
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




