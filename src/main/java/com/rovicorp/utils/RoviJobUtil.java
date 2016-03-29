package com.rovicorp.utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.rovicorp.constants.MultipleJobsMultipleRunJobParametersList;
import com.rovicorp.constants.MultiRunJobParametersList;
import com.rovicorp.dto.PackageDetails;
import com.rovicorp.springbatch.mbeans.JobInfoMBean;

public class RoviJobUtil {
	
	@Autowired
	private static JobInfoMBean jobInfoMBean;
	
	@Autowired
	private PackageDetails packageDetails;
	
	public static void run(String[] config) {
		ApplicationContext context = new ClassPathXmlApplicationContext(config);
		updateMBean(context);
	}
	
	public static void run(ApplicationContext context, String jobName, JobParameters jobParameters) throws JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException, JobParametersInvalidException {
		JobLauncher jobLauncher = (JobLauncher) context.getBean("jobLauncher");
		Job job = (Job) context.getBean(jobName);
		@SuppressWarnings("unused")
		JobExecution execution = jobLauncher.run(job, jobParameters);
	}

	public static void runNow(String[] config, String jobName, int startInMins) throws InterruptedException, JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException, JobParametersInvalidException {
		Thread.sleep(startInMins*60*1000);
		@SuppressWarnings("resource")
		ApplicationContext context = new ClassPathXmlApplicationContext(config);
		
		JobLauncher jobLauncher = (JobLauncher) context.getBean("jobLauncher");
		Job job = (Job) context.getBean(jobName);
		JobParameters jobParameters = new JobParametersBuilder().addLong("time", System.currentTimeMillis()).toJobParameters();
		@SuppressWarnings("unused")
		JobExecution execution = jobLauncher.run(job, jobParameters);
	}
	
	public static void runNow(String[] config, String[] jobNames, int startInMins) throws InterruptedException, JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException, JobParametersInvalidException {
		Thread.sleep(startInMins*60*1000);
		@SuppressWarnings("resource")
		ApplicationContext context = new ClassPathXmlApplicationContext(config);
		
		for(String jobName : jobNames) {
			JobLauncher jobLauncher = (JobLauncher) context.getBean("jobLauncher");
			Job job = (Job) context.getBean(jobName);
			JobParameters jobParameters = new JobParametersBuilder().addLong("time", System.currentTimeMillis()).toJobParameters();
			@SuppressWarnings("unused")
			JobExecution execution = jobLauncher.run(job, jobParameters);
		}
	}
	
	private static void updateMBean(ApplicationContext context) {
		JobExplorer jobExplorer=context.getBean("jobExplorer",JobExplorer.class);
        jobInfoMBean = context.getBean(JobInfoMBean.class);
        jobInfoMBean.setJobExplorer(jobExplorer);
	}
	
	public static ApplicationContext multiRunNow(String[] config, String jobName, int startInMins) throws InterruptedException, JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException, JobParametersInvalidException {
		Thread.sleep(startInMins*60*1000);
		@SuppressWarnings({ "resource" })
		ApplicationContext context = new ClassPathXmlApplicationContext(config);
		
		runJobMultipleTimesWithDifferentJobParameters((JobLauncher) context.getBean("jobLauncher"), (Job) context.getBean(jobName));
		
		return context;
		
	}
	
	public static void multiRunNowForSpecificList(String[] config, String jobName, int startInMins, ArrayList<String> listOfParameters, String keyName) throws InterruptedException, JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException, JobParametersInvalidException {
		Thread.sleep(startInMins*60*1000);
		@SuppressWarnings({ "resource" })
		ApplicationContext context = new ClassPathXmlApplicationContext(config);
		
		ArrayList<HashMap<String, String>> jobParametersList = new ArrayList<HashMap<String, String>>();
		for(String nextParameter : listOfParameters) {
			for(int i=0; i < MultiRunJobParametersList.getJobParametersList().size(); i++) {
				if(MultiRunJobParametersList.getJobParametersList().get(i).get(keyName).equalsIgnoreCase(nextParameter)) {
					HashMap<String, String> jobParameters = new HashMap<String, String>();
					for(String parameter : MultiRunJobParametersList.getJobParametersList().get(i).keySet()) {
						jobParameters.put(parameter, MultiRunJobParametersList.getJobParametersList().get(i).get(parameter));
					}
					jobParametersList.add(jobParameters);
					break;
				}
			}
		}
		runJobMultipleTimesWithDifferentJobParameters((JobLauncher) context.getBean("jobLauncher"), (Job) context.getBean(jobName), jobParametersList);
		
	}
	
	public static void runJobMultipleTimesWithDifferentJobParameters(JobLauncher jobLauncher, Job job) {
		for(int i=0; i < MultiRunJobParametersList.getJobParametersList().size(); i++) {
			JobParameters jobParameters = new JobParameters();
			JobParametersBuilder jobParametersBuilder = new JobParametersBuilder();
			for(String parameter : MultiRunJobParametersList.getJobParametersList().get(i).keySet()) {
				if(parameter.equalsIgnoreCase("datePattern"))
					jobParametersBuilder.addString(parameter, new SimpleDateFormat(MultiRunJobParametersList.getJobParametersList().get(i).get(parameter)).format(new Date()).toString());
				else
					jobParametersBuilder.addString(parameter, MultiRunJobParametersList.getJobParametersList().get(i).get(parameter));
			}
			jobParametersBuilder.addLong("time",System.currentTimeMillis());
			jobParameters = jobParametersBuilder.toJobParameters();
			try {
				jobLauncher.run(job, jobParameters);
			} catch (JobExecutionAlreadyRunningException | JobRestartException | JobInstanceAlreadyCompleteException | JobParametersInvalidException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public static void runJobMultipleTimesWithDifferentJobParameters(JobLauncher jobLauncher, Job job, ArrayList<HashMap<String, String>> jobParametersList) {
		for(int i=0; i < jobParametersList.size(); i++) {
			JobParameters jobParameters = new JobParameters();
			JobParametersBuilder jobParametersBuilder = new JobParametersBuilder();
			for(String parameter : jobParametersList.get(i).keySet()) {
				jobParametersBuilder.addString(parameter, jobParametersList.get(i).get(parameter));
			}
			jobParametersBuilder.addLong("time",System.currentTimeMillis());
			jobParameters = jobParametersBuilder.toJobParameters();
			try {
				jobLauncher.run(job, jobParameters);
			} catch (JobExecutionAlreadyRunningException | JobRestartException | JobInstanceAlreadyCompleteException | JobParametersInvalidException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public static void runJobNow(ApplicationContext context, String jobName) throws JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException, JobParametersInvalidException {
		JobLauncher jobLauncher = (JobLauncher) context.getBean("jobLauncher");
		Job job = (Job) context.getBean(jobName);
		JobParameters jobParameters = new JobParametersBuilder().addLong("time", System.currentTimeMillis()).toJobParameters();
		@SuppressWarnings("unused")
		JobExecution execution = jobLauncher.run(job, jobParameters);
	}
	
	public static ApplicationContext runMultipleJobsMultipleTimesNow(String[] config, String[] jobNames, int startInMins) throws InterruptedException, JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException, JobParametersInvalidException {
		Thread.sleep(startInMins*60*1000);
		@SuppressWarnings({ "resource" })
		ApplicationContext context = new ClassPathXmlApplicationContext(config);
		
		for(int i=0;i<jobNames.length;i++) {
			runMultiplepleJobsMultipleTimesWithDifferentJobParameters((JobLauncher) context.getBean("jobLauncher"), (Job) context.getBean(jobNames[i]));
		}
		return context;
		
	}
	
	public static ApplicationContext runSingleJobFromMultipleJobsMultipleTimesNow(String[] config, String jobName, int startInMins) throws InterruptedException, JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException, JobParametersInvalidException {
		Thread.sleep(startInMins*60*1000);
		@SuppressWarnings({ "resource" })
		ApplicationContext context = new ClassPathXmlApplicationContext(config);
		
		runMultiplepleJobsMultipleTimesWithDifferentJobParameters((JobLauncher) context.getBean("jobLauncher"), (Job) context.getBean(jobName));
		return context;
		
	}
	
	public static ApplicationContext runSingleJobForSpecificListNowFromMultipleJobsMultipleParameters(String[] config, String jobName, int startInMins, ArrayList<String> listOfParameters, String keyColumn) throws InterruptedException {
		Thread.sleep(startInMins*60*1000);
		@SuppressWarnings({ "resource" })
		ApplicationContext context = new ClassPathXmlApplicationContext(config);
		
		for(String parameterName : listOfParameters) {
			if(MultipleJobsMultipleRunJobParametersList.getMultipleJobsMultipleRunJobParametersMap().get(jobName) != null) {
				for(int i=0;i<MultipleJobsMultipleRunJobParametersList.getMultipleJobsMultipleRunJobParametersMap().get(jobName).size();i++) {
					if(MultipleJobsMultipleRunJobParametersList.getMultipleJobsMultipleRunJobParametersMap().get(jobName).get(i).containsKey(keyColumn)) {
						if(MultipleJobsMultipleRunJobParametersList.getMultipleJobsMultipleRunJobParametersMap().get(jobName).get(i).get(keyColumn).equalsIgnoreCase(parameterName)) {
							try {
								JobLauncher jobLauncher = (JobLauncher) context.getBean("jobLauncher");
								jobLauncher.run((Job) context.getBean(jobName), constructJobParametersFromMap(MultipleJobsMultipleRunJobParametersList.getMultipleJobsMultipleRunJobParametersMap().get(jobName).get(i)));
							} catch (JobExecutionAlreadyRunningException | JobRestartException | JobInstanceAlreadyCompleteException | JobParametersInvalidException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}
				}
			}
		}
		
		return context;
	}
	
	public static void runMultiplepleJobsMultipleTimesWithDifferentJobParameters(JobLauncher jobLauncher, Job job) {
		if(MultipleJobsMultipleRunJobParametersList.getMultipleJobsMultipleRunJobParametersMap().get(job.getName()) != null) {
			for(int i=0; i < MultipleJobsMultipleRunJobParametersList.getMultipleJobsMultipleRunJobParametersMap().get(job.getName()).size(); i++) {
				try {
					jobLauncher.run(job, constructJobParametersFromMap(MultipleJobsMultipleRunJobParametersList.getMultipleJobsMultipleRunJobParametersMap().get(job.getName()).get(i)));
				} catch (JobExecutionAlreadyRunningException | JobRestartException | JobInstanceAlreadyCompleteException | JobParametersInvalidException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
	private static JobParameters constructJobParametersFromMap(HashMap<String, String> parametersMap) {
		JobParametersBuilder jobParametersBuilder = new JobParametersBuilder();
		for(String parameter : parametersMap.keySet()) {
			if(parameter.equalsIgnoreCase("datePattern"))
				jobParametersBuilder.addString(parameter, new SimpleDateFormat(parametersMap.get(parameter)).format(new Date()).toString());
			else
				jobParametersBuilder.addString(parameter, parametersMap.get(parameter));
		}
		jobParametersBuilder.addLong("time",System.currentTimeMillis());
		
		return jobParametersBuilder.toJobParameters();
	}

}
