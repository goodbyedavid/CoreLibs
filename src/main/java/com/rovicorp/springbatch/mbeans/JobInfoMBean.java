package com.rovicorp.springbatch.mbeans;

import java.util.List;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobInstance;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.stereotype.Component;

@Component
@ManagedResource(objectName="com.rovicorp.springbatch.mbeans:name=JobInfoMBean", description="JobInfoMBean: Information related to Jobs...")
public class JobInfoMBean {
	
	private JobExplorer jobExplorer;
	@SuppressWarnings("unused")
	private List<String> jobNames;
	//private String historyOfJob;
	
	public JobExplorer getJobExplorer() {
		return jobExplorer;
	}
	
	public void setJobExplorer(JobExplorer jobExplorer) {
		this.jobExplorer = jobExplorer;
	}
	
	@ManagedAttribute
	public List<String> getJobNames() {
		return jobExplorer.getJobNames();
	}
	
	public void setJobNames(List<String> jobNames) {
		this.jobNames = jobNames;
	}
	
	@ManagedOperation
	public String getHistoryOfJob(String jobName) {
		List<JobInstance> jobInstances= jobExplorer.getJobInstances(jobName,0,30);
		StringBuilder jobHistory = new StringBuilder();
		for (JobInstance jobInstance : jobInstances) {
			jobHistory.append("Job history for JobID: " + jobInstance.getId());
			jobHistory.append("\tJobName: " + jobInstance.getJobName());
			List<JobExecution> jbes=jobExplorer.getJobExecutions(jobInstance);
			for(JobExecution jexe:jbes){
				jobHistory.append("\tJob Start Time: " + jexe.getStartTime());
				jobHistory.append("\tJob End Time: " + jexe.getEndTime());
				jobHistory.append("\tJob Status: " + jexe.getStatus() + "\r\n");
			}
		}
		return jobHistory.toString();
	}
	
	@ManagedOperation
	public String getLastJobStatus(String jobName, long jobExecutionID) {
		//List<JobInstance> jobInstances= jobExplorer.getJobInstances(jobName,0,40);
		StringBuilder jobHistory = new StringBuilder();
		JobExecution jbes=jobExplorer.getJobExecution(jobExecutionID);
		jobHistory.append("\tJob Start Time: " + jbes.getStartTime());
		jobHistory.append("\tJob End Time: " + jbes.getEndTime());
		jobHistory.append("\tJob Status: " + jbes.getStatus() + "\r\n");
		return jobHistory.toString();
	}

}
