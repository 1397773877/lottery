package com.newwing.util;

import java.io.FileInputStream;
import java.io.IOException;

import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

public class SoundUtils {
	
	public static void play(String filePath) {
		try { 
			FileInputStream fileau = new FileInputStream(filePath); 
			AudioStream as = new AudioStream(fileau); 
			AudioPlayer.player.start(as); 
		} catch (IOException ie) {
			ie.printStackTrace();
		}
	}
	
}
