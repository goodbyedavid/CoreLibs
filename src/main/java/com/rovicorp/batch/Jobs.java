package com.rovicorp.batch;

import java.util.Map;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.configuration.ListableJobLocator;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class Jobs implements JobsInterface, ApplicationContextAware {
	
	private ListableJobLocator registry;
	
	private JobRepository jobRepository;

	private ApplicationContext applicationContext;
	
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}
	
	public void setRegistry(ListableJobLocator registry) {
        this.registry = registry;
	}
	
	public void setJobRepository(JobRepository jobRepository) {
		this.jobRepository = jobRepository;
	}

	public String getJobNames() {
        String jobNames = "";
        for (String name : registry.getJobNames()) {
        	jobNames += name;
        }
        return jobNames;
	}
	
	public String getJobStatus() {
		JobParameters jobParameters = new JobParameters();
		JobExecution jobExecution = jobRepository.getLastJobExecution("comcast-dpi-job", jobParameters);
		boolean value = jobRepository.isJobInstanceExists("comcast-dpi-job", jobParameters);
		Map<String, JobExecution> jobExecutionMap = applicationContext.getBeansOfType(JobExecution.class);
		return "";
	}
}
