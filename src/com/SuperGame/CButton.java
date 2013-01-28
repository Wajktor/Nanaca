package com.SuperGame;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.media.MediaPlayer;
import android.view.MotionEvent;

public class CButton{
	float left, top, right, bottom;
	String buttonText;
	int color;
	
	Bitmap bm;
	
	boolean released;
	boolean pressed;
	
	public CButton(int l, int t, int r, int b, int c, String buttontext){
		left = l;
		top = t;
		right = r;
		bottom = b;
		color = c;
		buttonText = buttontext;
		bm = null;
	}
	public CButton() {
		this(0,0,0,0,Color.MAGENTA, "");
	}
	
	public CButton(Bitmap _bm, float posX, float posY)
	{
		bm = _bm;
		left = posX;
		right = posX + bm.getWidth();
		top = posY;
		bottom = posY + bm.getHeight();
	}
	
	
	public boolean clicked(int action, float posX, float posY)
	{
		if(action == MotionEvent.ACTION_DOWN && posX > left && posX < right && posY < bottom && posY > top)	
			{
				pressed = true;
			}
		else if(action == MotionEvent.ACTION_UP   && posX > left && posX < right && posY < bottom && posY > top && pressed)
		{
			pressed = false;
			return true;
		}
		else if(action == MotionEvent.ACTION_UP   && (posX < left || posX > right || posY > bottom || posY < top))
			pressed = false;
		return false;
	}

	
	public void Draw(Canvas c, Paint paint){
		if(bm == null)
		{
			paint.setColor(color);
			c.drawRect(left, top, right, bottom, paint);
			paint.setColor(Color.BLACK);
			paint.setTextSize(20);
			c.drawText(buttonText, ((left+right)/2 - buttonText.length()*paint.getTextSize()/4 ), ((top+bottom)/2 + paint.getTextSize()/4), paint);
		}
		else
		{
			c.drawBitmap(bm, left, top, paint);
		}
	}

}
