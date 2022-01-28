package net.pacogames.coe.logic.utils;

public class Logger {

	public static boolean flag = false;
	
	public static void log(String message) {
		if(flag) System.out.println(message);
	}
	
}