package com.rovicorp.tasklets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

public class EmptyTasklet implements Tasklet {

	private static final Logger logger=LoggerFactory.getLogger(EmptyTasklet.class);
	
	private String messageToLog;
	
	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
		
		logger.info("This tasklet will not do anything. Calling to have an empty step.");
		logger.info(""+getMessageToLog());
		return RepeatStatus.FINISHED;
	}

	public String getMessageToLog() {
		return messageToLog;
	}

	public void setMessageToLog(String messageToLog) {
		this.messageToLog = messageToLog;
	}

}
