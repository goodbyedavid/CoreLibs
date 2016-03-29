package com.rovicorp.tasklets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;

import com.rovicorp.core.file.FileMover;
import com.rovicorp.mbeans.FileMoverMbean;
import com.rovicorp.service.PackageDetailsService;

public class ZipFileMoverTasklet implements Tasklet {
    
	private static final Logger logger=LoggerFactory.getLogger(ZipFileMoverTasklet.class);
	private String sourceFile;
	private String sourceDirectory;
	private String destinationDirectory;
	private boolean isLarge=false;
	private String packageName;
	private boolean moveFileToFTPServer;

	@Autowired
	private FileMoverMbean fileMoverMbean;
	
	@Autowired
	PackageDetailsService packageDetailsService;
	
	@Override
	public RepeatStatus execute(StepContribution arg0, ChunkContext arg1) throws Exception {
		
		if(isMoveFileToFTPServer()) {
			if(getPackageName() == null && getSourceFile() != null) {
				logger.info("Moving file " + getSourceDirectory()+"/"+getSourceFile() + " to " + getDestinationDirectory());
				FileMover fileMover = new FileMover();
				fileMover.moveFile(getSourceFile(), getDestinationDirectory(), getSourceDirectory(), isLarge());
			} else {
				setSourceFile(packageDetailsService.getPackageDetails().get(getPackageName()).getPackageZipFileName());
				logger.info("Moving file " + getSourceDirectory()+"/"+getSourceFile() + " to " + getDestinationDirectory());
				FileMover fileMover = new FileMover();
				fileMover.moveFile(getSourceFile(), getDestinationDirectory(), getSourceDirectory(), isLarge());
			}
		} else
			logger.info("moveFileToFTPServer is false. So files were not posted to FTP server.");
		return RepeatStatus.FINISHED;
	}

	public String getSourceFile() {
		return sourceFile;
	}

	public void setSourceFile(String sourceFile) {
		this.sourceFile = sourceFile;
	}

	public String getSourceDirectory() {
		return sourceDirectory;
	}

	public void setSourceDirectory(String sourceDirectory) {
		this.sourceDirectory = sourceDirectory;
	}

	public String getDestinationDirectory() {
		return destinationDirectory;
	}

	public void setDestinationDirectory(String destinationDirectory) {
		this.destinationDirectory = destinationDirectory;
	}

	public boolean isLarge() {
		return isLarge;
	}

	public void setIsLarge(boolean isLarge) {
		this.isLarge = isLarge;
	}

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public FileMoverMbean getFileMoverMbean() {
		return fileMoverMbean;
	}

	public void setFileMoverMbean(FileMoverMbean fileMoverMbean) {
		this.fileMoverMbean = fileMoverMbean;
	}
	public boolean isMoveFileToFTPServer() {
		return moveFileToFTPServer;
	}
	public void setMoveFileToFTPServer(boolean moveFileToFTPServer) {
		this.moveFileToFTPServer = moveFileToFTPServer;
	}

}
