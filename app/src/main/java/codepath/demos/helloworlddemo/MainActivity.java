package codepath.demos.helloworlddemo;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

public class MainActivity extends Activity {

	private CustomView mCustomView;
	//private MainThread thread;

	//The onCreate function initializes stuff
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_hello_world);
		//setContentView(new GamePanel(this));

		//TetrisView tv = new TetrisView(this.getBaseContext(),t);
		//setContentView(tv); Thread stuff gets initialized

		mCustomView = (CustomView) findViewById(R.id.customView);


		findViewById(R.id.btn_swap_color).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				mCustomView.swapColor();
			}
		});
		findViewById(R.id.btn_move_down).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				mCustomView.moveDown();
			}
		});

		 int[][] tetrisSpace = new int[10][16];
		 for(int i = 0; i < 10; i++){
		 	for(int j = 0; j < 16; j++){
		 		tetrisSpace[i][j] = 0;
		 		System.out.println("The value of i: " + i + " The value of j: "+j);
			}
		 }

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_hello_world, menu);
		return true;
	}

}
