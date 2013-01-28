package com.SuperGame;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.view.Display;

public class DegreeArrow {

	float radians, velocity, posX, posY, length;
	int direction;
	Paint paint;
	float powerRadius, powerVelocity, powerDirection;
	boolean arrowStop;
	boolean powerStop;
	
	public DegreeArrow(){
		
		radians = (float) Math.PI/4;
		velocity = (float) (Math.PI/32);
		direction = 1;
		paint = new Paint();
		paint.setColor(Color.BLACK);
		paint.setAntiAlias(true);
		paint.setStrokeWidth(3);
		paint.setTextSize(24);
		paint.setTypeface(Typeface.SERIF);
		length = 150;
		powerRadius = length/2;
		powerVelocity = length/16;
		arrowStop = false;
		powerDirection = 1;
		powerStop = false;
		
	}
	
	public void Update(){
		
		if(!arrowStop){
			if(radians >= Math.PI/2 || radians <= 0){
				direction *= -1;
			}
				
			radians += velocity * direction;
			
			if(radians < 0){
				radians -= 2 * velocity * direction;
				direction = 1;
			}
			
			posX = (float) Math.cos(radians);
			posY = (float) Math.sin(radians);
			}
		else{
			
			if(powerRadius >= length || powerRadius <= 0){
				powerDirection *= -1;
			}
			
			if(!powerStop)
			powerRadius += powerVelocity * powerDirection;
			
		}
		
	}
	
	public void Draw(Canvas c, Display screen){
		
		paint.setColor(Color.BLACK);
		c.drawText( (int) Math.toDegrees(radians) + "¡", 120, screen.getHeight() - 100, paint);
		c.drawCircle(0, screen.getHeight(), length + 3, paint);
		paint.setColor(Color.WHITE);
		c.drawCircle(0, screen.getHeight(), length, paint);
		if(arrowStop){
		paint.setColor(Color.RED);
		c.drawCircle(0, screen.getHeight(), powerRadius, paint);
		paint.setColor(Color.BLACK);
		c.drawText((int) (100*powerRadius/length) + "%", 145, screen.getHeight() - 75, paint);
		}
		paint.setColor(Color.BLACK);
		c.drawLine(0, screen.getHeight(), posX * length, screen.getHeight() - posY * length, paint);

		
		
	}
	
}
