package com.rovicorp.tasklets;

import java.util.List;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

import com.rovicorp.utils.RoviFileUtils;

public class RoviEmptyFileGenerator implements Tasklet {

	private List<String> filesNamesToBeCreated;
	private String directory;
	
	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
	
		for(String fileName : getFilesNamesToBeCreated()) {
			RoviFileUtils.createEmptyFile(getDirectory(), fileName);
		}
		return RepeatStatus.FINISHED;
	}
	
	public List<String> getFilesNamesToBeCreated() {
		return filesNamesToBeCreated;
	}
	public void setFilesNamesToBeCreated(List<String> filesNamesToBeCreated) {
		this.filesNamesToBeCreated = filesNamesToBeCreated;
	}
	public String getDirectory() {
		return directory;
	}
	public void setDirectory(String directory) {
		this.directory = directory;
	}

}
