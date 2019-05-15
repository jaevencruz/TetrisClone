package codepath.demos.helloworlddemo;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

public class MainThread extends Thread{

    private SurfaceHolder surfaceHolder;
    private CustomSurfaceView customSurfaceView;
    private CustomView customView;
    private boolean running;
    public static Canvas canvas;

    public MainThread(CustomView customView){
        super();
        this.customView = customView;
    }

    public void setRunning(boolean isRunning){
        running = isRunning;
    }

    @Override
    public void run(){

        while(running){
            try {

            } catch (Exception e){
            }

            }
        }
}





