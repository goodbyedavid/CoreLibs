package com.rovicorp.tasklets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import com.rovicorp.core.file.FileMover;
import com.rovicorp.mbeans.FileMoverMbean;

public class FileMoverTasklet implements Tasklet {
    
	private static final Logger logger=LoggerFactory.getLogger(FileMoverTasklet.class);
	private String sourceFile;
	private String sourceDirectory;
	private String destinationDirectory;
	private boolean isLarge=false;

	@Autowired
	private FileMoverMbean fileMoverMbean;
	
	@Override
	public RepeatStatus execute(StepContribution arg0, ChunkContext arg1) throws Exception {
		
		Assert.notNull(getSourceFile(), "sourceFile can't be null!");
		Assert.notNull(getDestinationDirectory(),"destination directory can't be null!");
		Assert.notNull(getSourceDirectory(),"sourceDirectory can't be null!");
		
		FileMover fileMover = new FileMover();
		fileMover.moveFile(getSourceFile(), getDestinationDirectory(), getSourceDirectory(), isLarge());
		return RepeatStatus.FINISHED;
	}
	
	public void setIsLarge(boolean isLarge){
		logger.info("Configured for Large file use:{}",isLarge);
		this.isLarge=isLarge;
	}
	public boolean isLarge(){
		return isLarge;
	}
	public String getSourceFile() {
		return sourceFile;
	}
	public void setSourceFile(String sourceFile) {
		logger.info("Source File Name:{}",sourceFile);
		this.sourceFile = sourceFile;
	}
	public String getDestinationDirectory() {
		return destinationDirectory;
	}
	public void setDestinationDirectory(String destinationDirectory) {
		logger.info("Final File Destination:{}",destinationDirectory);
		this.destinationDirectory = destinationDirectory;
	}
	public String getSourceDirectory() {
		return sourceDirectory;
	}
	public void setSourceDirectory(String sourceDirectory) {
		logger.info("Source directory to find file:{}",sourceDirectory);
		this.sourceDirectory = sourceDirectory;
	}

}
