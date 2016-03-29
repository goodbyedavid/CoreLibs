package com.rovicorp.dto;

public class JobDetails {
	
	private String jobName;
	private String clientName;
	private boolean doesItHaveDeltaPackages;
	
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
