package com.rovicorp.tasklets;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

import com.rovicorp.core.file.RoviFileException;
import com.rovicorp.utils.RoviFileUtils;

public class RemoveFilesUnderDirectoryTasklet implements Tasklet {

	private List<String> directory;
	
	private static final Logger logger = LoggerFactory.getLogger(RemoveFilesUnderDirectoryTasklet.class);
	
	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
		try {
			for(String dir:getDirectory())
			{
				RoviFileUtils.cleanDirectory(dir);
			}
		} catch(RoviFileException e) {
			logger.debug("Not able to clean/remove files under directory {}", getDirectory());
		}
		return RepeatStatus.FINISHED;
	}

	public List<String> getDirectory() {
		return directory;
	}

	public void setDirectory(List<String> directory) {
		this.directory = directory;
	}

}
