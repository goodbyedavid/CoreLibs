package com.rovicorp.utils;

public class RoviImageURLUtils {

	public static String constructImageURL(String fileURLServer, String fileURL) {
		StringBuilder url = new StringBuilder("http://cps-static.rovicorp.com/");
		if(fileURLServer.equalsIgnoreCase("http://image.allmusic.com/08"))
			url.append("1");
		else if(fileURLServer.equalsIgnoreCase("http://rad1ciprmw1/RichMediaAssets"))
			url.append("2");
		else if(fileURLServer.equalsIgnoreCase("http://tul1muspcr1/filestore1"))
			url.append("3");
		url.append(fileURL);
		return url.toString();
	}
}
