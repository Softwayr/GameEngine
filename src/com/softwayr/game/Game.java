package com.softwayr.game;

import java.util.HashMap;
import java.util.Map;

import com.softwayr.engine.GameEngine;

public class Game {

	public static void main(String[] args) {
		Map<String, String> options = new HashMap<String, String>();
		
		options.put("title", "Softwayr Game");
		
		GameEngine.Init(options);
		GameEngine.start();
	}
	
}
