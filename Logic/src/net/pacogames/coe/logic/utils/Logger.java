package net.pacogames.coe.logic.utils;

/**Utility class for logging with turning on and off*/
public class Logger {

	public static boolean flag = false;
	
	public static void log(String message) {
		if(flag) System.out.println(message);
	}
	
}