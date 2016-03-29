package com.rovicorp.job.listeners;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.beans.factory.annotation.Autowired;

import com.rovicorp.constants.MultiRunJobParametersList;
import com.rovicorp.mbeans.JobStatusMbean;

/*
 * This class is the listener for Multi Run Products. If jobs fails then it will remember that job and will be rerun after some time.
 */
public class RoviMultiRunJobListener implements JobExecutionListener {

	
	@Autowired
	JobStatusMbean jobStatusMbean;
	
	private static final Logger logger = LoggerFactory.getLogger(RoviMultiRunJobListener.class);
	
	private String folderToLogFailedJobs;
	private String jobParameterNameForFileName;

	@Override
	public void beforeJob(JobExecution jobExecution) {
		
	}
	
	@Override
	public void afterJob(JobExecution jobExecution) {
		
		if(jobExecution.getStatus() == BatchStatus.FAILED) {
				try {
					StringBuilder sb = new StringBuilder();
					Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(getFolderToLogFailedJobs() + File.separatorChar + jobExecution.getJobParameters().getParameters().get(getJobParameterNameForFileName()) + ".txt"), "utf-8"));
					for(String key: jobExecution.getJobParameters().getParameters().keySet()) {
						if(key.equalsIgnoreCase("datePattern")) {
							for(HashMap<String, String> parameters : MultiRunJobParametersList.getJobParametersList()) {
								if(parameters.get(getJobParameterNameForFileName()).equalsIgnoreCase(jobExecution.getJobParameters().getParameters().get(getJobParameterNameForFileName()).toString())) {
									writer.write(key + "|" + parameters.get("datePattern") + "\r\n");
							        sb.append(key + "|" + parameters.get("datePattern") + "\r\n");
							        break;
								}
							}
						} else {
							writer.write(key + "|" + jobExecution.getJobParameters().getParameters().get(key) + "\r\n");
							sb.append(key + "|" + jobExecution.getJobParameters().getParameters().get(key) + "\r\n");
						}
					}
					writer.flush();
					writer.close();
					logger.info("Job with following parameters Failed. Application will restart this particular job after some time. \r\n" + sb.toString());
				} catch (UnsupportedEncodingException | FileNotFoundException e) {
					logger.error("Exception occured while saving the failed job to the file. {}", e);
				} catch (IOException e) {
					logger.error("Exception occured while saving the failed job to the file. {}", e);
				} catch(Exception e) {
					logger.error("Exception occured while saving the failed job to the file. {}", e);
				}
		} else if(jobExecution.getStatus() == BatchStatus.COMPLETED) {
			jobStatusMbean.getJobStatus().add("" + jobExecution.getJobInstance().getJobName() + " job completed at " + jobExecution.getEndTime());
		}
		
	}
	
	public String getFolderToLogFailedJobs() {
		return folderToLogFailedJobs;
	}
	public void setFolderToLogFailedJobs(String folderToLogFailedJobs) {
		this.folderToLogFailedJobs = folderToLogFailedJobs;
	}
	public String getJobParameterNameForFileName() {
		return jobParameterNameForFileName;
	}
	public void setJobParameterNameForFileName(String jobParameterNameForFileName) {
		this.jobParameterNameForFileName = jobParameterNameForFileName;
	}

}
