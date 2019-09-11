package codepath.demos.helloworlddemo;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

public class MainActivity extends Activity {

	//private MainThread thread;
	private GameThread th;

	//The onCreate function initializes stuff
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_hello_world);

		th = new GameThread(this.getBaseContext());
		th.setCustomView((CustomView) findViewById(R.id.customView));
		th.setRunning(true);
		th.start();

		//mCustomView = (CustomView) findViewById(R.id.customView);



		findViewById(R.id.btn_move_down).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				th.returnCustomView().moveDown();
			}
		});

		findViewById(R.id.btn_move_right).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				th.returnCustomView().moveRight();
			}
		});


        findViewById(R.id.btn_move_left).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
				th.returnCustomView().moveLeft();
            }
        });


		findViewById(R.id.rotatecounter).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				th.returnCustomView().rPlayer.rotateccw();
			}
		});

		findViewById(R.id.rst_grid).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {

				th.returnCustomView().resetGrid();
			}
		});
		findViewById(R.id.instafall).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				th.returnCustomView().moveToBottom();
			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_hello_world, menu);
		return true;
	}


}
