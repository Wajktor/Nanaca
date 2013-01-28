package com.SuperGame;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.Display;

public class Player {

	float posX, posY;
	float velX, velY, maxSpeed;
	float radius;
	float gravity;
	float rktBoostFuel;
	Paint paint;
	int screenHeight, screenWidth;
	boolean launched;
	boolean stopped;
	
	public Player(Display screen){		
		paint = new Paint();
		paint.setAntiAlias(true);
		velX = 0;
		velY = 0;
		maxSpeed = 30;
		gravity = 0.5f;
		screenHeight = screen.getHeight();
		screenWidth = screen.getWidth();
		launched = false;
		rktBoostFuel = 1f;
		stopped = false;
	}
	
	public void Reset(){
		posX = radius + 150;
		posY = screenHeight - radius;
		velX = 0;
		velY = 0;
		launched = false;
		stopped = false;
		rktBoostFuel = 1f;
	}
	
	public void Launch(float radians, float powerRadius, float length){
		float powerPercentage = powerRadius / length;
		posY -= 0.1;
		velX =  (float) (Math.cos(radians)*maxSpeed * powerPercentage);
		velY = -(float) (Math.sin(radians)*maxSpeed * powerPercentage);
	}
	
	public void Boost()
	{
		velX += 5;
		velY = -velY;
		velY += velY/3;
	}
	
	public void RocketBoost()
	{
		if(rktBoostFuel > 0.02f)
		{
		velY -= gravity * 1.5;
		rktBoostFuel = (rktBoostFuel < 0.02f) ? 0 : rktBoostFuel - 0.01f;
		}
		else
		rktBoostFuel = 0;
	}
	
	public void specBoost(int typeID)
	{
		if(typeID == 0)
			velY = -velY;
		if(typeID == 1)
			velX += 10;
	}
	public void Update(Display screen){
		velY += gravity;
		if(posX < screen.getWidth()/3)
		posX += velX;
		posY += velY;
		if(posY >= (screen.getHeight()-radius)){
			posY = screen.getHeight()-radius;
			velY *= (float) -1 * 5/6;
			velX *= (float) 5/6;
		}
		
		if(velX < 0.5)
		{
			velX = 0;
			stopped = true;
		}
		
		rktBoostFuel = (float) ((rktBoostFuel >= 1f) ? 1f : (rktBoostFuel + 0.0030f));
	}
	
	public void Draw(Canvas c){
		paint.setColor(Color.BLUE);
		c.drawCircle(posX, posY, radius, paint);
	}
}
