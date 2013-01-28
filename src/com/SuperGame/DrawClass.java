package com.SuperGame;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.SystemClock;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

public class DrawClass extends View implements OnTouchListener{

	Display screen;
	Paint paint;
	Player player;
	DegreeArrow arrow;
	
	int FPS = 30;
	int skip = 1000 / FPS;
	long sleep = 0;
	long next = SystemClock.elapsedRealtime();
	
	public DrawClass(Context context, Display screen) {
		super(context);
		this.screen = screen;
		paint = new Paint();
		paint.setAntiAlias(true);
		paint.setColor(Color.RED);
		paint.setTextSize(40);
		setBackgroundColor(Color.WHITE);
		
		arrow = new DegreeArrow();
		
		player = new Player();
		player.paint.setColor(Color.RED);
		player.radius = 30;
		player.Reset(screen);
		
		this.setFocusable(true);
		this.setClickable(true);
		this.setOnTouchListener(this);
		
	}
	
	public void onDraw(Canvas c){
		
		arrow.Update();
		player.Move();
		
		player.Draw(c);
		arrow.Draw(c, screen);
		
		next += skip;
		sleep = next - SystemClock.elapsedRealtime();
		if(sleep >= 0){
			try {
				Thread.sleep(sleep);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		invalidate();
	}

	public boolean onTouch(View v, MotionEvent event) {
		
		int action = event.getAction();
		
		if(action == MotionEvent.ACTION_DOWN){
			
			arrow.arrowStop = true;
			
	    }
		
		if(action == MotionEvent.ACTION_UP){
			
			arrow.powerStop = true;
			player.Launch(arrow.radians, arrow.powerRadius, arrow.length);
			
	    }
		
		return false;
	}

}
