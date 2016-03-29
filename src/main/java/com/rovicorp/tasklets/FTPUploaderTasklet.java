package com.rovicorp.tasklets;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;

import com.rovicorp.core.ftp.RoviFtpClient;
import com.rovicorp.core.ftp.RoviFtpClientException;

public class FTPUploaderTasklet  implements Tasklet {

	@Autowired
	private RoviFtpClient ftpClient;

	private static final Logger logger = LoggerFactory.getLogger(FtpTasklet.class);
	
	private String zipFileName;
	private boolean copyAllFiles;
	private String sourceFileLocation;
	private String destinationFileLocation;
	private boolean postFilestoFTP = false;


	public FTPUploaderTasklet() {
	}

	@Override
	public RepeatStatus execute(StepContribution arg0, ChunkContext arg1) throws Exception {
       
		if(isPostFilestoFTP()) {
			if(isCopyAllFiles()) {
				File destinationFolder = new File(getSourceFileLocation());
				for(File file: destinationFolder.listFiles()) {
					copyFile(file.getName(), getSourceFileLocation(), getDestinationFileLocation());
				}
			} else
				copyFile(getZipFileName(), getSourceFileLocation(), getDestinationFileLocation());
		}
		return RepeatStatus.FINISHED;
	}
	
	public boolean copyFile(String fileName, String sourceDirectory, String destinationDirectory) throws RoviFtpClientException {
		ftpClient.login();
		ftpClient.putFileOnFtp(fileName, sourceDirectory, destinationDirectory);
		   try{
		    ftpClient.quit();
		   }catch(Exception e){
			   logger.warn("FTP Client did not disconnect!!");
			   logger.warn("FtpClient will try to disconnect and reconnect on next job execution!");
		   }
		return true;
	}

	public String getZipFileName() {
		return zipFileName;
	}

	public void setZipFileName(String zipFileName) {
		this.zipFileName = zipFileName;
	}

	public boolean isCopyAllFiles() {
		return copyAllFiles;
	}

	public void setCopyAllFiles(boolean copyAllFiles) {
		this.copyAllFiles = copyAllFiles;
	}

	public String getSourceFileLocation() {
		return sourceFileLocation;
	}

	public void setSourceFileLocation(String sourceFileLocation) {
		this.sourceFileLocation = sourceFileLocation;
	}

	public String getDestinationFileLocation() {
		return destinationFileLocation;
	}

	public void setDestinationFileLocation(String destinationFileLocation) {
		this.destinationFileLocation = destinationFileLocation;
	}

	public boolean isPostFilestoFTP() {
		return postFilestoFTP;
	}

	public void setPostFilestoFTP(boolean postFilestoFTP) {
		this.postFilestoFTP = postFilestoFTP;
	}
}
