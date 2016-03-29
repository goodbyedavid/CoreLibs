package com.rovicorp.utils;

import java.io.File;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.TrueFileFilter;
import org.apache.commons.lang3.StringUtils;

public class RoviDeltaUtil {
	
	public static boolean isThisADeltaRun(String deltaExpression, String directory, String zipFileNameStartsWith, String zipFileNameFullName) {
		
		String[] delta1 = StringUtils.split(deltaExpression, "|");
		String[] deltaDaily = StringUtils.split(delta1[0].split("DAILY:")[1], ", ");
		String[] weeklyFull = StringUtils.split(delta1[1].split("WEEKLYFULL:")[1], ", ");
		String[] monthlyFull = StringUtils.split(delta1[2].split("MONTHLYFULL:")[1], ", ");
		
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		
		if(weeklyFull != null && weeklyFull.length > 0) {
			if(!weeklyFull[0].equals("*")) {
				for(String todaysWeek : weeklyFull) {
					if(todaysWeek.equalsIgnoreCase(""+c.get(Calendar.DAY_OF_WEEK))) {
						return false;
					} else
						return true;
				}
			}
		}
		
		if(monthlyFull != null && monthlyFull.length > 0) {
			if(!monthlyFull[0].equals("*")) {
				if(monthlyFull[0].equalsIgnoreCase(""+c.get(Calendar.DAY_OF_MONTH)))
					return false;
				else
					return true;
			}
		}
		
		if(deltaDaily != null && deltaDaily.length > 0) {
			if(deltaDaily.length == 1 && (deltaDaily[0].equals("*") || deltaDaily[0].equalsIgnoreCase("F"))) {
				if(isDailyFullRan(directory, zipFileNameStartsWith, zipFileNameFullName))
					return false;
				else
					return false;
			} else {
				//check is this the first run then return 0 else 1
				if(isDailyFullRan(directory, zipFileNameStartsWith, zipFileNameFullName))
					return true;
				else
					return false;
			}
		}
		return true;
	}
	
	public static boolean isDailyFullRan(String directory, String zipFileNameStartsWith, String zipFileNameFullName) {
		Collection<File> files = FileUtils.listFiles(FileUtils.getFile(directory), TrueFileFilter.INSTANCE, TrueFileFilter.INSTANCE);
		for(File file : files) {
			Calendar c = Calendar.getInstance();
			c.setTime(new Date());
			
			Date d = new Date(file.lastModified());
			Calendar c1 = Calendar.getInstance();
			c1.setTime(d);
				
			if((c.get(Calendar.YEAR) == c1.get(Calendar.YEAR)) && (c.get(Calendar.MONTH) == c1.get(Calendar.MONTH)) && (c.get(Calendar.DAY_OF_MONTH) == c1.get(Calendar.DAY_OF_MONTH)) && file.getName().startsWith(zipFileNameStartsWith))
				//if(file.getName().startsWith(zipFileNameStartsWith)) { //&& file.getName().contains(zipFileNameFullName)) {
				return true;
			else
				return false;
		}
		return false;
	}

}
