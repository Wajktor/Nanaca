package com.SuperGame;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.Display;

public class Player {

	float posX, posY;
	float velX, velY, maxSpeed;
	float radius;
	Paint paint;
	
	public Player(){
			
		paint = new Paint();
		paint.setAntiAlias(true);
		velX = 0;
		velY = 0;
		maxSpeed = 30;
		
	}
	
	public void Reset(Display screen){
		
		posX = radius + 150;
		posY = screen.getHeight() - radius;
		
	}
	
	public void Launch(float radians, float powerRadius, float length){
		
		float powerPercentage = powerRadius / length;
		
		velX = (float) (Math.cos(radians)*maxSpeed * powerPercentage);
		velY =  - (float) (Math.sin(radians)*maxSpeed * powerPercentage);
		
	}
	
	public void Move(){
		
		posX += velX;
		posY += velY;
		
	}
	
	public void Draw(Canvas c){
		
		c.drawCircle(posX, posY, radius, paint);
		
	}
	
}
