package com.rovicorp.scheduler;

import java.util.ArrayList;
import java.util.Map;

import org.quartz.JobExecutionContext;
import org.springframework.batch.core.configuration.JobLocator;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.NoSuchJobException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.rovicorp.utils.RoviJobUtil;

public class MultipleJobsMultipleRunJobLauncher extends QuartzJobBean {

	static final String JOB_NAMES = "jobNames";
	private JobLocator jobLocator;
	private JobLauncher jobLauncher;
	
	public void setJobLocator(JobLocator jobLocator) {
		this.jobLocator = jobLocator;
	}
	
	public void setJobLauncher(JobLauncher jobLauncher) {
		this.jobLauncher = jobLauncher;
	}
	
	@SuppressWarnings("unchecked")
	protected void executeInternal(JobExecutionContext context) {
		Map<String, Object> jobDataMap = context.getMergedJobDataMap();
		
		ArrayList<String> jobNames = (ArrayList<String>) jobDataMap.get(JOB_NAMES);
		for(int i=0;i<jobNames.size();i++) {
			try {
				RoviJobUtil.runMultiplepleJobsMultipleTimesWithDifferentJobParameters(jobLauncher, jobLocator.getJob(jobNames.get(i)));
			} catch (NoSuchJobException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
