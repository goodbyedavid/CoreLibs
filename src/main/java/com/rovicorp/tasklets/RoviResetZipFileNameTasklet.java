package com.rovicorp.tasklets;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

import com.rovicorp.utils.RoviZipFileUtils;

public class RoviResetZipFileNameTasklet  implements Tasklet {
	
	private String fileNameBeforeDateTime;
	private String fileNameDateTimeFormat;
	private String fileNameAfterDateTime;
	private String fileFormat;
	private boolean appendFullOrDelta;
	private String jobName;
	
	
	public String getFileNameBeforeDateTime() {
		if(this.fileNameBeforeDateTime == null)
			this.fileNameBeforeDateTime = "";
		return this.fileNameBeforeDateTime;
	}

	public void setFileNameBeforeDateTime(String fileNameBeforeDateTime) {
		this.fileNameBeforeDateTime = fileNameBeforeDateTime;
	}

	public String getFileNameDateTimeFormat() {
		if(this.fileNameDateTimeFormat == null)
			this.fileNameDateTimeFormat = "yyyyMMddHHmm";
		return this.fileNameDateTimeFormat;
	}

	public void setFileNameDateTimeFormat(String fileNameDateTimeFormat) {
		this.fileNameDateTimeFormat = fileNameDateTimeFormat;
	}

	public String getFileNameAfterDateTime() {
		if(this.fileNameAfterDateTime == null)
			this.fileNameAfterDateTime = "";
		return this.fileNameAfterDateTime;
	}

	public void setFileNameAfterDateTime(String fileNameAfterDateTime) {
		this.fileNameAfterDateTime = fileNameAfterDateTime;
	}

	public String getFileFormat() {
		if(this.fileFormat == null)
			this.fileFormat = "zip";
		return this.fileFormat;
	}

	public void setFileFormat(String fileFormat) {
		this.fileFormat = fileFormat;
	}

	public boolean isAppendFullOrDelta() {
		return appendFullOrDelta;
	}

	public void setAppendFullOrDelta(boolean appendFullOrDelta) {
		this.appendFullOrDelta = appendFullOrDelta;
	}
	
	public String getJobName() {
		return jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
		
		/*RoviZipFileUtils.setFileNameBeforeDateTime(getFileNameBeforeDateTime());
		RoviZipFileUtils.setFileNameDateTimeFormat(getFileNameDateTimeFormat());
		RoviZipFileUtils.setFileNameAfterDateTime(getFileNameAfterDateTime());
		RoviZipFileUtils.setFileFormat(getFileFormat());
		RoviZipFileUtils.setAppendFullOrDelta(isAppendFullOrDelta());
		RoviZipFileUtils.setZipFileName(null);*/
		
		RoviZipFileUtils.addZipFileName(getJobName(), getFileNameBeforeDateTime(), getFileNameDateTimeFormat(), getFileNameAfterDateTime(), getFileFormat(), isAppendFullOrDelta());
		
		return RepeatStatus.FINISHED;
	}

}
