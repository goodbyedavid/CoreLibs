package com.rovicorp.tasklets;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

import com.rovicorp.utils.RoviFileUtils;

public class MoveFilesFromOneDirectoryToAnother implements Tasklet {
	
	private static final Logger logger=LoggerFactory.getLogger(MoveFilesFromOneDirectoryToAnother.class);
	private String sourceDirectory;
	private String destinationDirectory;
	private List<String> filesToBeMoved = new ArrayList<String>();
		
	@Override
	public RepeatStatus execute(StepContribution arg0, ChunkContext arg1) throws Exception {
		logger.info("Moving files under directory " + getSourceDirectory() + " to " + getDestinationDirectory());
		RoviFileUtils.cleanDirectory(getDestinationDirectory());
		for(String fileName: getFilesToBeMoved()) {
			FileUtils.moveFileToDirectory(new File(getSourceDirectory()+ File.separator + fileName), new File(getDestinationDirectory()), true);
		}
		return RepeatStatus.FINISHED;
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

	public List<String> getFilesToBeMoved() {
		return filesToBeMoved;
	}

	public void setFilesToBeMoved(List<String> filesToBeMoved) {
		this.filesToBeMoved = filesToBeMoved;
	}

}
