package com.softwayr.engine;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JFrame;

public class GameEngine {

	private static String title;
	private static int width, height, scale;
	
	private static Canvas canvas;
	private static JFrame window;
	private static Thread thread;
	private static BufferedImage image;
	
	protected static int[] pixels;
	
	private static boolean running;
	
	public static void Init() {
		Init( new HashMap<String, String>() );
	}
	
	public static void Init(Map<String, String> options) {
		canvas = new Canvas();
		
		title = options.containsKey("title") ? options.get("title") : "Game Engine";
		width = options.containsKey("width") && Utilities.isInteger(options.get("width")) ? Integer.parseInt( options.get("width") ) : 300;
		height = options.containsKey("height") && Utilities.isInteger(options.get("height")) ? Integer.parseInt( options.get("height") ) : width / 16 * 9;
		scale = options.containsKey("scale") && Utilities.isInteger(options.get("scale")) ? Integer.parseInt( options.get("scale") ) : 3;
		
		canvas.setPreferredSize( new Dimension( width * scale, height * scale ) );
		
		image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		pixels = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();
		
		Screen.Init();
		
		window = new JFrame();
		window.setResizable(false);
		window.setTitle(title);
		window.add(canvas);
		window.pack();
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setLocationRelativeTo(null);
		window.setVisible(true);
	}
	
	public synchronized static void start() {
		running = true;
		thread = new Thread( "Display" );
		thread.start();
		
		run();
	}
	
	private static void run() {
		long lastTime = System.nanoTime();
		long timer = System.currentTimeMillis();
		final double ns = 1000000000.0 / 60.0;
		double delta = 0;
		int frames = 0;
		int updates = 0;
		
		while( running ) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while( delta >= 1 ) {
				update();
				updates++;
				delta--;
			}
			render();
			frames++;
			
			if( System.currentTimeMillis() - timer > 1000 ) {
				timer += 1000;
				
				window.setTitle(title + " [FPS: " + frames + " UPS: " + updates + "]");
				
				updates = 0;
				frames = 0;
			}
		}
		stop();
	}
	
	private static void update() {
		
	}
	
	private static void render() {
		BufferStrategy bs = canvas.getBufferStrategy();
		if(bs == null) {
			canvas.createBufferStrategy(3);
			return;
		}
		
		Screen.Clear();
		Screen.Render();
		
		Graphics g = bs.getDrawGraphics();
		g.drawImage(image, 0, 0, width(true), height(true), null);
		
		g.dispose();
		bs.show();
	}
	
	public synchronized static void stop() {
		running = false;
		try {
			thread.join();
		} catch ( Exception e ) {
			e.printStackTrace();
		}
	}
	
	public static int width() {
		return width;
	}
	
	public static int width( boolean scaled ) {
		return scaled ? width * scale : width;
	}
	
	public static int height() {
		return height;
	}
	
	public static int height( boolean scaled ) {
		return scaled ? height * scale : height;
	}
	
}
