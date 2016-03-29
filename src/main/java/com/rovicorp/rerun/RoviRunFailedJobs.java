package com.rovicorp.rerun;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.rovicorp.utils.RoviJobUtil;

/*
 * This class will run the failed jobs for Multiple Run products. Example for LG Extract it will rerun all the failed zipcodes.
 */
public class RoviRunFailedJobs implements ApplicationContextAware {

	private static final Logger logger = LoggerFactory.getLogger(RoviRunFailedJobs.class);
	
	private String folderToLogFailedJobs;
	private ApplicationContext applicationContext;
	private String jobName;
	
	public void startProcessingFailedJobs() {
		logger.info("Process started for failed jobs.");
				
		ArrayList<HashMap<String, String>> jobParametersList = new ArrayList<HashMap<String, String>>();
		String nextLine = "";
		StringBuilder sb = new StringBuilder();
		try {
			List<File> files = (List<File>)FileUtils.listFiles(new File(getFolderToLogFailedJobs()), null, false);
			for(File eachFile : files) {
				try {
					sb.setLength(0);
					BufferedReader br = new BufferedReader(new FileReader(eachFile));
					nextLine = "";
					HashMap<String, String> parameter = new HashMap<String, String>();
					while((nextLine = br.readLine()) != null) {
						String[] values = StringUtils.splitPreserveAllTokens(nextLine, "|");
						parameter.put(values[0], values[1]);
						sb.append(values[0]).append(":").append(values[1]).append("\r\n");
					}
					br.close();
					jobParametersList.add(parameter);
					FileUtils.deleteQuietly(eachFile);
				} catch (FileNotFoundException e) {
					logger.error("Error occured while restarting the failed job with parameters {}. {}", sb.toString(), e);
				} catch (IOException e) {
					logger.error("Error occured while restarting the failed job with parameters {}. {}", sb.toString(), e);
				} catch(Exception e) {
					logger.error("Error occured while restarting the failed job with parameters {}. {}", sb.toString(), e);
				}
			}
		}catch(Exception e) {
			logger.error("Error occured while restarting the failed job. {}", e);
		}
		
		if(jobParametersList.size() == 0)
			logger.info("No Failed jobs. Nothing to Process..");
		else {
			for(int i=0; i < jobParametersList.size(); i++) {
				sb.setLength(0);
				JobParameters jobParameters = new JobParameters();
				JobParametersBuilder jobParametersBuilder = new JobParametersBuilder();
				for(String parameter : jobParametersList.get(i).keySet()) {
					jobParametersBuilder.addString(parameter, jobParametersList.get(i).get(parameter));
					sb.append(parameter).append(":").append(jobParametersList.get(i).get(parameter)).append("\r\n");
				}
				jobParametersBuilder.addLong("time",System.currentTimeMillis());
				jobParameters = jobParametersBuilder.toJobParameters();
				try {
					logger.info("Restarting the failed job with parameters {}", sb.toString());
					RoviJobUtil.run(getApplicationContext(), getJobName(), jobParameters);
				} catch (JobExecutionAlreadyRunningException | JobRestartException | JobInstanceAlreadyCompleteException | JobParametersInvalidException e) {
					logger.error("Failed to restart job with parameters {}", sb.toString());
				}
			}
		}
	}
	
	public String getFolderToLogFailedJobs() {
		return folderToLogFailedJobs;
	}
	public void setFolderToLogFailedJobs(String folderToLogFailedJobs) {
		this.folderToLogFailedJobs = folderToLogFailedJobs;
	}
	public ApplicationContext getApplicationContext() {
		return this.applicationContext;
	}
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}
	public String getJobName() {
		return jobName;
	}
	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

}
