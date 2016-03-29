package com.rovicorp.utils;

import java.text.ParseException;
import java.util.Date;

import org.quartz.SchedulerException;
import org.springframework.scheduling.quartz.CronTriggerBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

public class RoviSchedulerUtils {
	
	public static void changeSchedule(SchedulerFactoryBean schedulerFactoryBean, String triggerName, String schedulerGroup, String cronExpression) throws SchedulerException, ParseException {
			CronTriggerBean cronTrigger = (CronTriggerBean) schedulerFactoryBean.getScheduler().getTrigger(triggerName, schedulerGroup);
			cronTrigger.setCronExpression(cronExpression);
			cronTrigger.setStartTime(new Date(System.currentTimeMillis()));
			schedulerFactoryBean.getScheduler().rescheduleJob(cronTrigger.getName(), schedulerGroup, cronTrigger);
			schedulerFactoryBean.getScheduler().start();
	}

}
