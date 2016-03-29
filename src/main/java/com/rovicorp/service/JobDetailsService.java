package com.rovicorp.service;

import java.util.HashMap;

import org.springframework.stereotype.Component;

import com.rovicorp.dto.JobDetails;

@Component
public class JobDetailsService {
	
	private HashMap<String, JobDetails> jobDetails =  new HashMap<String, JobDetails>();

	public HashMap<String, JobDetails> getJobDetails() {
		return jobDetails;
	}

	public void setJobDetails(HashMap<String, JobDetails> jobDetails) {
		this.jobDetails = jobDetails;
	}
	
	public void addJobDetails(JobDetails jobDetails) {
		this.jobDetails.put(jobDetails.getJobName(), jobDetails);
	}

}
