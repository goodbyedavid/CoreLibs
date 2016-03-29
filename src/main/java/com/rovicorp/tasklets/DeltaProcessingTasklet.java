package com.rovicorp.tasklets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

import com.rovicorp.deltaProcessing.DeltaProcessingForXMLFile;

public class DeltaProcessingTasklet implements Tasklet {
	
	private static final Logger logger=LoggerFactory.getLogger(DeltaProcessingTasklet.class);
	
	private String fullFile;
	private String currentRunFile;
	private String outputFile;
	private String keyColumnIndexes;
	private String rootTagName;
	private String deltaIdentifierColumnNamne;
	private String insertValue;
	private String updateValue;
	private String deleteValue;
	

	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {

		logger.info("Delta processing for file [{}]", getCurrentRunFile());
		DeltaProcessingForXMLFile deltaProcessingForXMLFile = new DeltaProcessingForXMLFile();
		deltaProcessingForXMLFile.prodessDeltaForXMLFiles(getFullFile(), getCurrentRunFile(), getOutputFile(), getKeyColumnIndexes(), getRootTagName(), getDeltaIdentifierColumnNamne(), getInsertValue(), getUpdateValue(), getDeleteValue());
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


	public String getRootTagName() {
		return rootTagName;
	}


	public void setRootTagName(String rootTagName) {
		this.rootTagName = rootTagName;
	}


	public String getDeltaIdentifierColumnNamne() {
		return deltaIdentifierColumnNamne;
	}


	public void setDeltaIdentifierColumnNamne(String deltaIdentifierColumnNamne) {
		this.deltaIdentifierColumnNamne = deltaIdentifierColumnNamne;
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

}
