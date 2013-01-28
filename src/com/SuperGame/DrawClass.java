package com.SuperGame;

import java.io.IOException;
import java.util.Random;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.media.MediaPlayer;
import android.os.SystemClock;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import java.net.*;


public class DrawClass extends View implements OnTouchListener{

	Display screen;
	Paint paint;
	//Player is static
	static Player player;
	
	int enemyAmount = 4;
	Enemy[] enemy;
	
	float distance = 0;
	
	//Buttons
	CButton resetButton;
	CButton musicToggle;
	
	DegreeArrow arrow;
	
	int FPS = 30;
	int skip = 1000 / FPS;
	long sleep = 0;
	long next = SystemClock.elapsedRealtime();
	
	Bitmap landscape;
	Bitmap tree, tree2;
	Bitmap mountain;
	Bitmap musicButton;
	Bitmap musicButtonCross;
	float mntnShift;
	float treeX;
	float treeY;
	float tree2X;
	float tree2Y;
	
	boolean pressing = false;
	
	public Bitmap getResizedBitmap(Bitmap bm, int newHeight, int newWidth) {
		 
		int width = bm.getWidth();
		 
		int height = bm.getHeight();
		 
		float scaleWidth = ((float) newWidth) / width;
		 
		float scaleHeight = ((float) newHeight) / height;
		 
		// create a matrix for the manipulation
		 
		Matrix matrix = new Matrix();
		 
		// resize the bit map
		 
		matrix.postScale(scaleWidth, scaleHeight);
		 
		// recreate the new Bitmap
		 
		Bitmap resizedBitmap = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, false);
		 
		return resizedBitmap;
		 
		}



	public DrawClass(Context context, Display screen) {		
		
		super(context);
		this.screen = screen;
		paint = new Paint();
		paint.setAntiAlias(true);
		paint.setColor(Color.RED);
		paint.setTextSize(40);
		setBackgroundColor(Color.WHITE);
		
		
		landscape = BitmapFactory.decodeResource(getResources(), R.drawable.nanacalandscape);
		
		mountain = BitmapFactory.decodeResource(getResources(), R.drawable.nanacamountain);
		
		musicButton = BitmapFactory.decodeResource(getResources(), R.drawable.nanacamusicbutton);
		
		musicButtonCross = BitmapFactory.decodeResource(getResources(), R.drawable.musiccrossover);
		
		tree = BitmapFactory.decodeResource(getResources(), R.drawable.nanacatrees);
		treeX = screen.getWidth()/1.5f;
		treeY = 200;
		
		tree2 = BitmapFactory.decodeResource(getResources(), R.drawable.nanacatrees);
		tree2 = getResizedBitmap(tree2, 100, 100);
		tree2X = screen.getWidth() - tree2.getWidth();
		tree2Y = screen.getHeight() - 220;
		mntnShift = 0;
		
		arrow = new DegreeArrow();
		
		resetButton = new CButton(50,50,150,180,Color.GREEN, "Reset");
		resetButton.released = true;
		
		musicToggle = new CButton(musicButton, 600, 50);
		
		player = new Player(screen);
		player.radius = 20;
		player.Reset();
			

		
		enemy = new Enemy[enemyAmount];
		
		//define enemies and shift them 'distance' from each other
		
		for(int i = 0; i < enemyAmount; ++i)
		{
			enemy[i] = new Enemy(screen);
			enemy[i].posX += (float)(screen.getWidth() + 2 * enemy[0].radius) * i / enemyAmount;
		}

		
		this.setFocusable(true);
		this.setClickable(true);
		this.setOnTouchListener(this);
				
	}
	
	
	public void onDraw(Canvas c){

		distance += player.velX/50;
		
				
		//Player boost
		if(pressing == true && !musicToggle.pressed && !resetButton.pressed)
		{
			if(player.launched)
				player.RocketBoost();
		}
			

		//When player reaches middle of screen
		if(player.posX >= screen.getWidth()/3)
		{
			treeX -= player.velX;
			tree2X -= player.velX/2;
			mntnShift -= player.velX/20;
			mntnShift = (mntnShift < -(mountain.getWidth())) ? 0 : mntnShift;
		}
		if(treeX < -tree.getWidth())
		{
			treeX = screen.getWidth();
		}
		if(tree2X < -tree2.getWidth())
		{
			tree2X = screen.getWidth();
		}
		
		//Position enemies at equal distances every time
		
		for(int i = 0; i < enemyAmount; ++i)
		{
			if(enemy[i].posX <= - enemy[i].radius)
			{
				if(enemy[( (i == 0) ? (enemyAmount-1) : (i-1) )].posX < (screen.getWidth() - (screen.getWidth() + 2 * enemy[i].radius)/enemyAmount))
						{
							enemy[i].posX = enemy[( (i == 0) ? (enemyAmount-1) : (i-1) )].posX + enemy[0].radius + (screen.getWidth() + 2 * enemy[i].radius)/enemyAmount;
							enemy[i].typeID = new Random().nextInt(2) + 1;

						}
				//enemy[i].posX = screen.getWidth() + enemy[i].radius;
			}
		}
		
		
		//draw landscape and next landscape
		//c.drawBitmap(landscape, landscpX, landscpY, paint);
		//c.drawBitmap(landscape, screen.getWidth()+landscpX, landscpY, paint);
		
		//ground
		paint.setARGB(255, 10, 150, 50);
		c.drawRect(0, screen.getHeight() - 140, screen.getWidth(), screen.getHeight(), paint);
		//c.drawRect(left, top, right, bottom, paint)
		//sky
		paint.setColor(Color.CYAN);
		c.drawRect(0, 0, screen.getWidth(), (screen.getHeight() - 140), paint);
		//draw tree
		for(int i = 0; (i) * mountain.getWidth() < (screen.getWidth() + mountain.getWidth()); ++i)
		{
			c.drawBitmap(mountain, i * mountain.getWidth() + mntnShift, screen.getHeight() - mountain.getHeight() - 140, paint);
		}
		c.drawBitmap(tree2, tree2X, tree2Y, paint);
		c.drawBitmap(tree, treeX, treeY, paint);
		
		
		//Music on/off button
		musicToggle.Draw(c, paint);
		if(Globals.mp.isPlaying())
			c.drawBitmap(musicButtonCross, musicToggle.left, musicToggle.top, paint);
				
		//Score display (distance display)
		paint.setColor(Color.BLACK);
		paint.setTextSize(30);
		c.drawText("Score: " + String.valueOf((int)distance), 500, 100, paint);
		c.drawText(String.valueOf(player.velX), 500, 200, paint);
		c.drawText(String.valueOf(Globals.mp.isPlaying()), 500, 300, paint);
		
		//player.rktBoostFuel = 0.50f;
		
		//Rocketfuel booster gauge below
		c.drawRect(screen.getWidth()-42, 18, screen.getWidth()-18, 122, paint);
		//This also
		paint.setARGB(255, (player.rktBoostFuel <= 0.50f) ? 255 : 254 -(int)(2 * (player.rktBoostFuel - 0.5f) * 253), (player.rktBoostFuel >= 0.5f) ? 255 : (int)(2 * player.rktBoostFuel * 255), 0);
		//Height of fuelgauge
		c.drawRect(screen.getWidth()-40, 120-(100 * player.rktBoostFuel), screen.getWidth()-20, 120, paint);
		
		
		player.Update(screen);
		player.Draw(c);
		
		
		//All enemies update
		for(int i = 0; i<enemy.length; ++i)
		{
			enemy[i].Update(player);
			enemy[i].Draw(c);
		}
		
		arrow.Update();
		arrow.Draw(c, screen);
		resetButton.Draw(c, paint);
		
				
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
		float	posX   = event.getX(),
				posY   = event.getY();
		if(action == MotionEvent.ACTION_DOWN)
			pressing = true;
		if(action == MotionEvent.ACTION_UP)
			pressing = false;
		
		//If resetButton is pressed down
		
		/*if(action == MotionEvent.ACTION_DOWN && posX > resetButton.left && posX < resetButton.right && posY < resetButton.bottom && posY > resetButton.top){
			resetButton.released = false;
		}
		*/
		
		if(action == MotionEvent.ACTION_DOWN){
			arrow.arrowStop = true;

	    }
				
		if(action == MotionEvent.ACTION_UP){
			if(!player.launched)
			{
				arrow.powerStop = true;
				player.launched = true;
				player.Launch(arrow.radians, arrow.powerRadius, arrow.length);
				arrow.disappear = true;
			}

			//Release resetButton
			/*if(!resetButton.released && posX > resetButton.left && posX < resetButton.right && posY < resetButton.bottom && posY > resetButton.top)
			{
				player.Reset();
				arrow.Reset();
				
				for(int i = 0; i<enemy.length; ++i)
				{
					enemy[i] = new Enemy(screen);
					enemy[i].posX += (float)(screen.getWidth() + 2 * enemy[0].radius) * i / enemyAmount;
				}
				distance = 0;
				treeX = screen.getWidth() - 100;
				mntnShift = 0;
				resetButton.released = true;
				player.stopped = false;
				
			}
			else 
				resetButton.released = false;*/
				
	    }
		
		if(resetButton.clicked(action, posX, posY))
		{
			player.Reset();
			arrow.Reset();
			
			for(int i = 0; i<enemy.length; ++i)
			{
				enemy[i] = new Enemy(screen);
				enemy[i].posX += (float)(screen.getWidth() + 2 * enemy[0].radius) * i / enemyAmount;
			}
			distance = 0;
			treeX = screen.getWidth() - 100;
			mntnShift = 0;
			resetButton.released = true;
			player.stopped = false;
		}
		
		if(musicToggle.clicked(action, posX, posY))
		{
			
			if(Globals.mpPlaying)
				Globals.mp.pause();
			if(!Globals.mpPlaying)
			    Globals.mp.start();
			
			Globals.mpPlaying = !Globals.mpPlaying;
		}
		
		
		
		return true;
	}
}
