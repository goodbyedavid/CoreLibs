package com.rovicorp.tasklets;

import java.io.File;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;

import com.rovicorp.core.file.FileMover;
import com.rovicorp.core.ftp.RoviFtpClient;
import com.rovicorp.mbeans.FtpPullMbean;
import com.rovicorp.utils.RoviFileUtils;

public class FtpTasklet implements Tasklet {

	@Autowired
	private RoviFtpClient ftpClient;
	@Autowired
	private FtpPullMbean ftpPullMbean;
	
	private static final Logger logger = LoggerFactory.getLogger(FtpTasklet.class);
	private String fileName;
	private String downloadedFileName;
	private String destination;
	private String archiveDestination;
	private String ftpDirectoryPath;
	
	public FtpTasklet() {}

	@Override
	public RepeatStatus execute(StepContribution arg0, ChunkContext arg1)
			throws Exception {
		
		ftpClient.login();
		String apolloSourcesFileLocation=getApolloSourcesFileLocation();
		InputStream inputStream=ftpClient.getFileAsInputStream(apolloSourcesFileLocation);
		copyFileToDestination(inputStream);
		ftpClient.quit();
		
		return RepeatStatus.FINISHED;
	}

	private void copyFileToDestination(InputStream inputStream) throws Exception{
		StringBuilder stringBuilder=new StringBuilder();
		stringBuilder.append(destination).append("/").append(fileName);
		FileUtils.copyInputStreamToFile(inputStream,new File(stringBuilder.toString()));
		if(RoviFileUtils.doesFileExist(stringBuilder.toString())){
			logger.info("File:{} download success and archived and located here",stringBuilder.toString());
			
		}else{
			ftpPullMbean.setErrorDownloading(true);
			ftpPullMbean.setErrorDescription("Copying to destination folder failed: " + stringBuilder.toString() + ".");
			logger.error("File:{} download FAILED!!!",stringBuilder.toString());
			throw new Exception("downloading file failed!!! :"+fileName);
		}
		logger.info("Now make a backup with date:{}",fileName);
		String archiveFileName=getDateWithTimeForFile(fileName);
		FileMover fm=new FileMover();
		fm.moveFile(fileName,archiveDestination , destination,archiveFileName, false);
		
	}
	
	private String getApolloSourcesFileLocation(){
		if(ftpDirectoryPath==null || ftpDirectoryPath.trim().equals("")){
			return this.fileName;
		}else{
			return ftpDirectoryPath+"/"+fileName; 
		}
	}
	
	private String getDateWithTimeForFile(String fileName){
		Date now=Calendar.getInstance().getTime();
		SimpleDateFormat sdf=new SimpleDateFormat("-MM-dd-YYYY-hh-mm-ss");
		return fileName+sdf.format(now);
	}
	
	public RoviFtpClient getFtpClient() {
		return ftpClient;
	}

	public void setFtpClient(RoviFtpClient ftpClient) {
		this.ftpClient = ftpClient;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getDownloadedFileName() {
		return downloadedFileName;
	}

	public void setDownloadedFileName(String downloadedFileName) {
		this.downloadedFileName = downloadedFileName;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}
	
	
	

}
