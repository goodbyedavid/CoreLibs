package com.rovicorp.utils;

import java.util.Hashtable;

public class RoviMax24HourScheduleDuration {

	public static final int secondsPerDay = 86400;

	public static Hashtable<Integer, Integer> splitScheduleDuration(int duration) {
		Hashtable<Integer, Integer> durations = new Hashtable<Integer, Integer>();
		int count = 0;
		if((duration % secondsPerDay) > 0) 
			count = 1; 
		count = (duration / secondsPerDay) + count;
		
		for(int i =1;i<=count;i++) {
			if(i < count)
				durations.put(i, secondsPerDay);
			else {
				if((duration % secondsPerDay) > 0)
					durations.put(i, (duration % secondsPerDay));
				else
					durations.put(i, secondsPerDay);
			}
		}
		return durations;
	}
}
