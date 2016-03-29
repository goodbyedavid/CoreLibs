package com.rovicorp.mbeans;

import java.util.ArrayList;
import java.util.List;

import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.stereotype.Component;

@Component
@ManagedResource(objectName="com.rovicorp.mbeans:name=JobStatusMbean", description="JobStatusMbean: Provides the status of the jobs...")
public class JobStatusMbean implements MbeansResetter {
	
	private boolean jobFailed = false;
	private List<String> jobStatus;

	@ManagedAttribute
	public boolean isJobFailed() {
		return jobFailed;
	}

	public void setJobFailed(boolean jobFailed) {
		this.jobFailed = jobFailed;
	}

	public JobStatusMbean() {
		jobStatus = new ArrayList<String>();
	}
	
	@ManagedAttribute
	public List<String> getJobStatus() {
		return jobStatus;
	}

	public void setJobStatus(List<String> jobStatus) {
		this.jobStatus = jobStatus;
	}
	
	public void reset() {
		this.jobFailed = false;
		this.jobStatus.clear();
	}

}
