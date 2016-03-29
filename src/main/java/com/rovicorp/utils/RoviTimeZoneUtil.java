package com.rovicorp.utils;

import java.util.HashMap;
import java.util.Map;

public class RoviTimeZoneUtil {
	
	private static Map<String, String> timeZone;
	 
	static {
		timeZone= new HashMap<String, String>();
		timeZone.put( "ALASKA", "AKS");
		timeZone.put( "ATLANTIC", "AT");
		timeZone.put( "CENTRAL", "CT");
		timeZone.put( "CENTRAL EUROPE", "CED");
		timeZone.put( "E. BRAZIL", "BZD");
		timeZone.put( "EASTERN", "ET");
		timeZone.put( "GMT", "GMT");
		timeZone.put( "HAWAII", "HST");
		timeZone.put( "JAPAN", "JPT");
		timeZone.put( "MOUNTAIN", "MT");
		timeZone.put( "NEWFOUNDLAND", "NST");
		timeZone.put( "PACIFIC", "PT");
	}
	
	public static String getTimeZoneCode(String key)
	{
		if(key == null)
			return "";
		String keyUpperCase = key.toUpperCase();
		if(timeZone.containsKey(keyUpperCase))
			return timeZone.get(keyUpperCase);
		else 
			return key;
	}

}
