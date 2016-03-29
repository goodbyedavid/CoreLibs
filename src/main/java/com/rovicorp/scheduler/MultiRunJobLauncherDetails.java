package com.rovicorp.scheduler;

import java.util.Map;

import org.quartz.JobExecutionContext;
import org.springframework.batch.core.configuration.JobLocator;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.NoSuchJobException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.rovicorp.utils.RoviJobUtil;

public class MultiRunJobLauncherDetails extends QuartzJobBean {
	 
	static final String JOB_NAME = "jobName";
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
		String jobName = (String) jobDataMap.get(JOB_NAME);
		
		/*for(int i=0; i < MultiRunJobParametersList.getJobParametersList().size(); i++) {
			JobParameters jobParameters = new JobParameters();
			JobParametersBuilder jobParametersBuilder = new JobParametersBuilder();
			for(String parameter : MultiRunJobParametersList.getJobParametersList().get(i).keySet()) {
				jobParametersBuilder.addString(parameter, MultiRunJobParametersList.getJobParametersList().get(i).get(parameter));
			}
			jobParametersBuilder.addLong("time",System.currentTimeMillis());
			jobParameters = jobParametersBuilder.toJobParameters();
			try {
				jobLauncher.run(jobLocator.getJob(jobName), jobParameters);
			} catch (JobExecutionAlreadyRunningException | JobRestartException | JobInstanceAlreadyCompleteException | JobParametersInvalidException | NoSuchJobException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}*/
		
		try {
			RoviJobUtil.runJobMultipleTimesWithDifferentJobParameters(jobLauncher, jobLocator.getJob(jobName));
		} catch (NoSuchJobException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}