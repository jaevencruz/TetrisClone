package codepath.demos.helloworlddemo;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

public class GridBlock implements GameObject{
    private Rect gridBlock;
    private Paint tGridBlockPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    public GridBlock(){
    }

    //Con
    public GridBlock(Rect rect, Paint paint){
            this.gridBlock = rect;
            this.tGridBlockPaint = paint;
    }

    public void setPaint(Paint paint){
        this.tGridBlockPaint = paint;
    }

    public void setRect(int left, int top, int right, int bottom){
        this.gridBlock.left = left;
        this.gridBlock.top = top;
        this.gridBlock.right = right;
        this.gridBlock.bottom = bottom;
    }

    public void setRect(Rect r){
        this.gridBlock = r;
    }

    public void initializeBlock(){
        if(gridBlock == null){
            gridBlock = new Rect();
        }

    }

    @Override
    public void draw(Canvas canvas){
        canvas.drawRect(gridBlock,tGridBlockPaint);
    }

    @Override
    public void update() {

    }

    public Rect returnRect(){
        return this.gridBlock;
    }

    public int left(){
        return this.gridBlock.left;
    }

    public int right(){
        return this.gridBlock.right;
    }

    public int bottom(){
        return this.gridBlock.bottom;
    }

    public int top(){
        return this.gridBlock.top;
    }


}
