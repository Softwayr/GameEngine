package com.softwayr.engine;

import java.util.Random;

public class Screen {

	private static Random random = new Random();
	private static int[] pixels;
	
	protected static void Init() {
		pixels = new int[GameEngine.width() * GameEngine.height()];
		
		for( int i = 0; i < pixels.length; i++ ) {
			pixels[i] = random.nextInt(0xffffff);
		}
	}
	
	protected static void Clear() {
		for( int i = 0; i < pixels.length; i++ ) {
			GameEngine.pixels[i] = 0;
		}
	}
	
	protected static void Render() {
		for( int i = 0; i < pixels.length; i++ ) {
			GameEngine.pixels[i] = pixels[i];
		}
	}
	
}
