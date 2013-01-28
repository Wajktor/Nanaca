package com.SuperGame;
import java.util.Random;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.Display;

public class Enemy {
	float posX;
	float posY;
	int screenWidth;
	int screenHeight;
	int radius;
	int typeID;
	int ID;
	Paint paint;
	
	static int soundIDFast;
	static int soundIDSlow;
	
	static int count = 0;
	
	boolean hitPlayer;

	public Enemy(Display screen)
	{
		paint = new Paint();
		paint.setAntiAlias(true);
		radius = 15;
		screenWidth = screen.getWidth();
		screenHeight = screen.getHeight();
		typeID = new Random().nextInt(2) + 1;
		hitPlayer = false;
		posX = (float) 3/2* screen.getWidth() + radius;
		posY = screen.getHeight()-radius;
		ID = count;
		++count;
		
		soundIDFast = Globals.sp.load(NanacaCrashActivity.NanacaContext, R.raw.swisheffect, 1);
		soundIDSlow = Globals.sp.load(NanacaCrashActivity.NanacaContext, R.raw.slow, 1);
	}
	
	public void Draw(Canvas c)
	{
		if(typeID == 1)
		{
			paint.setColor(Color.RED);
			c.drawCircle(posX, posY, radius, paint);
		}
		if(typeID == 2)
		{
			paint.setColor(Color.YELLOW);
			c.drawCircle(posX, posY, radius, paint);
		}
	}
	
	public void Update(Player player)
	{
		posX -= player.velX;
		
		//Collision detection
		
		if(Math.sqrt((player.posX - posX) * (player.posX - posX) + (player.posY - posY) * (player.posY - posY)) < (player.radius + radius))
		{
			if(!hitPlayer)
			{				
				if(typeID == 1)
				{
					player.velX += 5;
					player.velY = -Math.abs(player.velY) - 5;
					Globals.sp.play(soundIDFast, 1f, 1f, 1, 0, 1);
				}
				if(typeID == 2)
				{
					player.velX *= (float) 2/3;
					player.velY *= (float) 2/3;
					Globals.sp.play(soundIDSlow, 1f, 1f, 1, 0, 1);
				}
			}
			
			hitPlayer = true;
		}
		else
			hitPlayer = false;
		
	}
	
	/*public void Reset()
	{
		posX = (float) screenWidth + (screenWidth + 2 * radius) * ID / count;
	}*/
}
