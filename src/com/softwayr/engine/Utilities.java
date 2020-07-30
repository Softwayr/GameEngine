package com.softwayr.engine;

public class Utilities {

	public static boolean isInteger(String s) {
	    try { 
	        Integer.parseInt(s); 
	    } catch(Exception e) { 
	        return false; 
	    }
	    return true;
	}
	
}
