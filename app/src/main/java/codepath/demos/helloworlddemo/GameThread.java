package codepath.demos.helloworlddemo;

import android.content.Context;

public class GameThread extends Thread {
    private Context context;
    private CustomView TetrisSpace;
    private boolean running;

    public GameThread(Context context){
        super();
        this.context = context;
        TetrisSpace = new CustomView(context);
    }

    public GameThread(Context context,CustomView customView){
        super();
        this.context = context;
        this.TetrisSpace = customView;
    }

    public GameThread(CustomView customView){
        super();
        this.TetrisSpace = customView;
    }

    public void setCustomView(CustomView c){
        this.TetrisSpace = c;
    }

    public CustomView returnCustomView(){
        return this.TetrisSpace;
    }

    public void setRunning(boolean isRunning){
        running = isRunning;
    }



    @Override
    public void run(){

        while(running){
            try {
                synchronized (TetrisSpace) {
                    TetrisSpace.moveDown();
                }
            } catch (Exception e){
            }
            try {
                currentThread().sleep(1000);
            }catch (Exception e){}

        }
    }

}
