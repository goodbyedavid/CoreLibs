package com.rovicorp.tasklets;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;

import com.rovicorp.dto.JobDetails;
import com.rovicorp.service.JobDetailsService;

public class RoviResetJobDetailsTasklet implements Tasklet {
	
	private String jobName;
	private String clientName;
	private boolean doesItHaveDeltaPackages;
	
	@Autowired
	JobDetailsService jobDetailsService;

	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {

		JobDetails jobDetails = new JobDetails();
		jobDetails.setClientName(getClientName());
		jobDetails.setJobName(getJobName());
		jobDetails.setDoesItHaveDeltaPackages(isDoesItHaveDeltaPackages());
		jobDetailsService.addJobDetails(jobDetails);
		
		return RepeatStatus.FINISHED;
	}

	public String getJobName() {
		return jobName;
	}
	public void setJobName(String jobName) {
		this.jobName = jobName;
	}
	public String getClientName() {
		return clientName;
	}
	public void setClientName(String clientName) {
		this.clientName = clientName;
	}
	public boolean isDoesItHaveDeltaPackages() {
		return doesItHaveDeltaPackages;
	}
	public void setDoesItHaveDeltaPackages(boolean doesItHaveDeltaPackages) {
		this.doesItHaveDeltaPackages = doesItHaveDeltaPackages;
	}

}
