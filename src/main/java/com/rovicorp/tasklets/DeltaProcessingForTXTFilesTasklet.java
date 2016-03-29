package com.rovicorp.tasklets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

import com.rovicorp.deltaProcessing.DeltaProcessingForTextFile;

public class DeltaProcessingForTXTFilesTasklet implements Tasklet {
	
	private static final Logger logger=LoggerFactory.getLogger(DeltaProcessingForTXTFilesTasklet.class);
	
	private String fullFile;
	private String currentRunFile;
	private String outputFile;
	private String keyColumnIndexes;
	private String deltaIdentifierColumnIndex;
	private String insertValue;
	private String updateValue;
	private String deleteValue;
	private String newLineSeperator;
	

	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {

		logger.info("Delta processing for file [{}]", getCurrentRunFile());
		DeltaProcessingForTextFile deltaProcessingForTextFile = new DeltaProcessingForTextFile();
		deltaProcessingForTextFile.processDeltaForTXTFiles(getFullFile(), getCurrentRunFile(), getOutputFile(), getKeyColumnIndexes(), getDeltaIdentifierColumnIndex(), getInsertValue(), getUpdateValue(), getDeleteValue(), getNewLineSeperator());
		return RepeatStatus.FINISHED;
	}


	public String getFullFile() {
		return fullFile;
	}


	public void setFullFile(String fullFile) {
		this.fullFile = fullFile;
	}


	public String getCurrentRunFile() {
		return currentRunFile;
	}


	public void setCurrentRunFile(String currentRunFile) {
		this.currentRunFile = currentRunFile;
	}


	public String getOutputFile() {
		return outputFile;
	}


	public void setOutputFile(String outputFile) {
		this.outputFile = outputFile;
	}


	public String getKeyColumnIndexes() {
		return keyColumnIndexes;
	}


	public void setKeyColumnIndexes(String keyColumnIndexes) {
		this.keyColumnIndexes = keyColumnIndexes;
	}
	
	
	public String getDeltaIdentifierColumnIndex() {
		return deltaIdentifierColumnIndex;
	}


	public void setDeltaIdentifierColumnIndex(String deltaIdentifierColumnIndex) {
		this.deltaIdentifierColumnIndex = deltaIdentifierColumnIndex;
	}


	public String getInsertValue() {
		return insertValue;
	}


	public void setInsertValue(String insertValue) {
		this.insertValue = insertValue;
	}


	public String getUpdateValue() {
		return updateValue;
	}


	public void setUpdateValue(String updateValue) {
		this.updateValue = updateValue;
	}


	public String getDeleteValue() {
		return deleteValue;
	}


	public void setDeleteValue(String deleteValue) {
		this.deleteValue = deleteValue;
	}

	public String getNewLineSeperator() {
		return newLineSeperator;
	}

	public void setNewLineSeperator(String newLineSeperator) {
		this.newLineSeperator = newLineSeperator;
	}

}
