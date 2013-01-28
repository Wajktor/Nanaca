package com.SuperGame;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import android.media.MediaPlayer;
import android.media.SoundPool;

public class Globals {
	public static MediaPlayer mp;
	public static boolean mpPlaying = false;
	public static SoundPool sp;
	public static FileOutputStream DataStore;
	public static FileInputStream DataRead;
	public static byte DataBytes[];
}
