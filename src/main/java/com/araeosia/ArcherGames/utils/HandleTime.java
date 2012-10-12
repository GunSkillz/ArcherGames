package com.araeosia.ArcherGames.utils;

public class HandleTime {

	public static boolean isEchoable(int Time){
		if(Time%3600==0){
			// Clean hour.
			return true;
		}
		if(Time%60==0){
			// Clean minute.
			if((Time%900==0) || Time==60 || (Time==300)){
				// Divisible by 15 minutes, divisible by 5 minutes, or 1 minute left.
				return true;
			}
		}
		if(Time%4==0){
			
		}
		return false;
	}
	public static String echoString(int Time) {
		if(Time%3600==0){
			// Clean hour.
		}
		if(Time%60==0){
			// Clean minute.
			if(((Time/4)==(new Double(Time)/4)) || Time==60){
				// Divisible by 4, or 1 minute left.
			}
		}
		return "";
	}
}
