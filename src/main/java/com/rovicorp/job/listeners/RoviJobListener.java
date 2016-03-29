package com.rovicorp.job.listeners;

import java.text.ParseException;

import javax.sql.DataSource;

import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.StepExecution;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import com.rovicorp.mbeans.JobStatusMbean;
import com.rovicorp.utils.RoviDatabaseUtils;
import com.rovicorp.utils.RoviSchedulerUtils;

public class RoviJobListener implements JobExecutionListener {

	private String defaultCronExression ;
	private DataSource dataSource;
	
	@Autowired
	SchedulerFactoryBean schedulerFactoryBean;
	@Autowired
	JobStatusMbean jobStatusMbean;
	
	private static final Logger logger = LoggerFactory.getLogger(RoviJobListener.class);
	
	@Override
	public void beforeJob(JobExecution jobExecution) {
		
		boolean isLogShippingRunning = RoviDatabaseUtils.isLogShippingRunning(getDataSource());
		if(isLogShippingRunning) {
			try {
				RoviSchedulerUtils.changeSchedule(schedulerFactoryBean, "cronTrigger", Scheduler.DEFAULT_GROUP, "0 0/30 * * * ?");
				jobStatusMbean.getJobStatus().add("Log Shipping is running. Rescheduled job " + jobExecution.getJobInstance().getJobName() + " to run after 30 mins.");
				jobStatusMbean.setJobFailed(true);
			} catch (SchedulerException e) {
				logger.error("Exception occured while chaging the schedule.", e);
				e.printStackTrace();
			} catch (ParseException e) {
				logger.error("Exception occured while chaging the schedule.", e);
				e.printStackTrace();
			}
		} else {
			jobStatusMbean.getJobStatus().add("Job " + jobExecution.getJobInstance().getJobName() + " started at " + jobExecution.getStartTime());
		}
	}

	@Override
	public void afterJob(JobExecution jobExecution) {
		String cronExpression = "";
		if(jobExecution.getStatus() == BatchStatus.FAILED) {
			StepExecution lastStep = (StepExecution) jobExecution.getStepExecutions().toArray()[jobExecution.getStepExecutions().toArray().length-1];
			jobStatusMbean.setJobFailed(true);
			jobStatusMbean.getJobStatus().add("Job " + jobExecution.getJobInstance().getJobName() + " failed at " + jobExecution.getEndTime() + " on step " + lastStep.getStepName() + " with exceptions " + lastStep.getFailureExceptions() + " Job will restart in 30 mins.");
			cronExpression = "0 0/30 * * * ?";
		} else if(jobExecution.getStatus() == BatchStatus.COMPLETED) {
			jobStatusMbean.getJobStatus().add("" + jobExecution.getJobInstance().getJobName() + " job completed at " + jobExecution.getEndTime());
			cronExpression = getDefaultCronExression();
		}
		
		try {
			RoviSchedulerUtils.changeSchedule(schedulerFactoryBean, "cronTrigger", Scheduler.DEFAULT_GROUP, cronExpression);
		} catch (SchedulerException e) {
			logger.error("Exception occured while chaging the schedule.", e);
			e.printStackTrace();
		} catch (ParseException e) {
			logger.error("Exception occured while chaging the schedule.", e);
			e.printStackTrace();
		}
	}

	public String getDefaultCronExression() {
		return defaultCronExression;
	}

	public void setDefaultCronExression(String defaultCronExression) {
		this.defaultCronExression = defaultCronExression;
	}

	public DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

}
