package com.rovicorp.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class RoviParameters {
	
	private static final Logger logger=LoggerFactory.getLogger(RoviParameters.class);
	
	private String extractStartTime;
	private String extractEndTime;

	public void setResetDate(boolean resetDate, int extractScheduleDuration){
		if(resetDate){
			logger.info("resetting date for job");
			setExtractStartTime(new SimpleDateFormat("yyyy-MM-dd 00:00:00").format(new Date()));
			Calendar c = Calendar.getInstance();
			c.setTime(new Date());
			c.add(Calendar.DATE, extractScheduleDuration);
			setExtractEndTime(new SimpleDateFormat("yyyy-MM-dd 23:59:59").format(c.getTime()));
			logger.info("Start time:{} End Time:{}", getExtractStartTime(), getExtractEndTime());
		}else{
			logger.warn("not resetting dates!!!!!!!  Start time:{} End Time:{}", getExtractStartTime(), getExtractEndTime());
		}
	}
	
	public String getExtractStartTime() {
		return extractStartTime;
	}

	public void setExtractStartTime(String extractStartTime) {
		this.extractStartTime = extractStartTime;
	}
	
	public String getExtractEndTime() {
		return extractEndTime;
	}

	public void setExtractEndTime(String extractEndTime) {
		this.extractEndTime = extractEndTime;
	}

}
