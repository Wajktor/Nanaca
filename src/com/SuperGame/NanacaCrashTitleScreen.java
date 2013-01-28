package com.SuperGame;

import android.R.color;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

public class NanacaCrashTitleScreen extends Activity {
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,   
        WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        
		setContentView(R.layout.nanacacrashtitlescreen);
		

        
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

            final ImageView btnNew = (ImageView) findViewById(R.id.nanacastartbutton);
            btnNew.setOnClickListener(new View.OnClickListener() {

                  public void onClick(View v) {
          			Intent myIntent = new Intent(getApplicationContext(), NanacaCrashActivity.class);
        			startActivity(myIntent);
                  }

                });  
            
            btnNew.setOnTouchListener(new View.OnTouchListener() {
				
				public boolean onTouch(View v, MotionEvent event) {
					if(event.getAction() == MotionEvent.ACTION_DOWN)
					btnNew.setAlpha(100);
					
					if(event.getAction() == MotionEvent.ACTION_UP)
					btnNew.setAlpha(255);
					
					return false;
				}
			});


	}
}
