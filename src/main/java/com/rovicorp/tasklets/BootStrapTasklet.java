package com.rovicorp.tasklets;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContextAware;

import com.rovicorp.utils.RoviParameters;

public abstract class BootStrapTasklet implements Tasklet, ApplicationContextAware {

	@Autowired
	private RoviParameters roviParameters;
	
	private boolean resetStartEndTime;
	private int extractScheduleDuration;
	
	public boolean isResetStartEndTime() {
		return resetStartEndTime;
	}
	
	public void setResetStartEndTime(boolean resetStartEndTime) {
		this.resetStartEndTime = resetStartEndTime;
	}
	
	public int getExtractScheduleDuration() {
		return extractScheduleDuration;
	}

	public void setExtractScheduleDuration(int extractScheduleDuration) {
		this.extractScheduleDuration = extractScheduleDuration;
	}

	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
		
		roviParameters.setResetDate(isResetStartEndTime(), getExtractScheduleDuration());
		resetMbeans();
		return RepeatStatus.FINISHED;
	}
	
	public abstract void resetMbeans();

}
