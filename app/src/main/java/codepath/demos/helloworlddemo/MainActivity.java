package codepath.demos.helloworlddemo;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

public class MainActivity extends Activity {

	private CustomView mCustomView;
	private CustomSurfaceView mCustomSurfaceView;
	//private MainThread thread;

	//The onCreate function initializes stuff
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_hello_world);


		//mCustomSurfaceView  = new CustomSurfaceView(this.getBaseContext());

		//setContentView(mCustomSurfaceView);

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
				mCustomView.rPlayer.moveDown();
			}
		});

		findViewById(R.id.btn_move_right).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				mCustomView.rPlayer.moveRight();
			}
		});

		findViewById(R.id.btn_move_up).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				mCustomView.rPlayer.moveUp();
			}
		});

        findViewById(R.id.btn_move_left).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCustomView.rPlayer.moveLeft();
            }
        });

		findViewById(R.id.btn_rnd_piece).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				mCustomView.rPlayer.tetrominoPicker();
			}
		});
		findViewById(R.id.fall).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				mCustomView.rPlayer.rotatecw();
			}
		});
		findViewById(R.id.rotatecounter).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				mCustomView.rPlayer.rotateccw();
			}
		});
		findViewById(R.id.fall).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				mCustomView.rPlayer.fallout();
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
