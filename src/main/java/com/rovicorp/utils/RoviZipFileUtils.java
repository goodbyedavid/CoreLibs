package com.rovicorp.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class RoviZipFileUtils {
	
	private static HashMap<String, String> zipFileNames = new HashMap<String, String>();
	
	public static String getZipFileNameByJobName(String jobname) {
		if(getZipFileNames() == null)
			zipFileNames = new HashMap<String, String>();
		if(!getZipFileNames().containsKey(jobname) || getZipFileNames().get(jobname).equals(""))
			getZipFileNames().put(jobname, "NoName_" + new SimpleDateFormat("yyyyMMddHHmm").format(new Date()) + ".zip");
		return getZipFileNames().get(jobname);
	}
	
	public static String getZipFileNameByJobNameWithoutFileExtension(String jobName) {
		return getZipFileNames().get(jobName).substring(0, getZipFileNames().get(jobName).lastIndexOf("."));
	}
	
	public static void addZipFileName(String jobname, String fileNameBeforeDateTime, String fileNameDateTimeFormat, String fileNameAfterDateTime, String fileFormat, boolean appendFullOrDelta) {
		if(appendFullOrDelta)
			getZipFileNames().put(jobname, fileNameBeforeDateTime + new SimpleDateFormat(fileNameDateTimeFormat).format(new Date()) + fileNameAfterDateTime + "Full" + "." + fileFormat);
		else
			getZipFileNames().put(jobname, fileNameBeforeDateTime + new SimpleDateFormat(fileNameDateTimeFormat).format(new Date()) + fileNameAfterDateTime + "." + fileFormat);
	}

	public static HashMap<String, String> getZipFileNames() {
		return zipFileNames;
	}

	public static void setZipFileNames(HashMap<String, String> zipFileNames) {
		RoviZipFileUtils.zipFileNames = zipFileNames;
	}

}
