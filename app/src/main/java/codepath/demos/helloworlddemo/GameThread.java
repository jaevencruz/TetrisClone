package codepath.demos.helloworlddemo;

import android.content.Context;

public class GameThread extends Thread {
    private Context context;
    private CustomView TetrisSpace;
    private boolean running;
    private int score;

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
        score = TetrisSpace.returnScore();
        while(running){
            try {
                synchronized (TetrisSpace) {
                    TetrisSpace.moveDown();
                }
            } catch (Exception e){
            }
            try {
                if(score > 1000) {
                    currentThread().sleep(1500);
                }
                else if(score > 2500 ){
                    currentThread().sleep(1000);
                }
                else if(score > 5000){
                    currentThread().sleep(750);
                }
                else if(score > 7500){
                    currentThread().sleep(500);
                }
                else{
                    currentThread().sleep(1000);
                }
            }catch (Exception e){}

        }
    }

}
