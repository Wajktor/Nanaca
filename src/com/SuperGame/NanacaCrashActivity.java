package com.SuperGame;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.media.SoundPool.OnLoadCompleteListener;
import android.os.Bundle;
import android.view.Display;
import android.view.Window;
import android.view.WindowManager;

public class NanacaCrashActivity extends Activity {
		
	DrawClass draw;
	Display screen;
	    	
	SoundPool soundPool;
	int soundID;
	boolean loaded = false;
	
	public static Context NanacaContext;

	
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        NanacaContext = getApplicationContext();
        
        Globals.mp = new MediaPlayer();
        Globals.mp = MediaPlayer.create(this, R.raw.nanacamusik);
        
        Globals.sp = new SoundPool(5, AudioManager.STREAM_MUSIC, 0);
        
        soundPool = new SoundPool(2, AudioManager.STREAM_MUSIC, 0);
       
        String FILENAME = "hello_file";
        String string = "hello world PENISAR!";

        try {
			Globals.DataStore = openFileOutput(FILENAME, Context.MODE_PRIVATE);
	        Globals.DataStore.write(string.getBytes());
	        Globals.DataStore.close();
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        try {
			Globals.DataRead = openFileInput(FILENAME);
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        
       try {
			Globals.DataRead.read();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		soundID = Globals.sp.load(this, R.raw.swisheffect, 1);
		
        
        this.setVolumeControlStream(AudioManager.STREAM_MUSIC);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,   
        WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        
        Globals.mp.setLooping(true);
        try {
			Globals.mp.prepare();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        //mp.start();
        
        
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        screen = getWindowManager().getDefaultDisplay();
        draw = new DrawClass(this, screen);
        setContentView(draw);
        draw.requestFocus();
        

        Globals.sp.play(soundID, 1, 1, 1, 0, 1f);

    }
    protected void onPause()
    {
    	super.onPause();
    	Globals.sp.play(soundID, 1, 1, 1, 0, 1f);
    	Globals.mp.stop();
    }
}