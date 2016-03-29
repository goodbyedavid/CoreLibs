package com.rovicorp.tasklets;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;

import com.rovicorp.dto.PackageDetails;
import com.rovicorp.service.JobDetailsService;
import com.rovicorp.service.PackageDetailsService;
import com.rovicorp.utils.RoviDeltaUtil;

public class RoviResetPackageDetails implements Tasklet {
	
	private String zipFileNameBeforeDateTime;
	private String zipFileNameDateTimeFormat;
	private String zipFileNameAfterDateTime;
	private String zipFileFormat;
	private String zipFileAppendFullForFullFile;
	private String zipFileAppendDeltaForDeltaFile;
	
	private String packageName;
	private boolean deltaPackage;
	private boolean thisADeltaRun;
	private String deltaExpression;
	private String zipFileDirectory;
	private String jobName;
	
	@Autowired
	PackageDetailsService packageDetailsService;
	@Autowired
	JobDetailsService jobDetailsService;

	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {

		PackageDetails packageDetails = new PackageDetails();
		packageDetails.setPackageName(getPackageName());
		packageDetails.setDeltaPackage(isDeltaPackage());
		
		if(getJobName() != null && jobDetailsService.getJobDetails().containsKey(getJobName()) && jobDetailsService.getJobDetails().get(getJobName()).isDoesItHaveDeltaPackages()) {
			if(isDeltaPackage())
				packageDetails.setThisIsADeltaRun(RoviDeltaUtil.isThisADeltaRun(getDeltaExpression(), getZipFileDirectory(), getZipFileNameBeforeDateTime(), getZipFileAppendFullForFullFile()));
			else
				packageDetails.setThisIsADeltaRun(RoviDeltaUtil.isDailyFullRan(getZipFileDirectory(), getZipFileNameBeforeDateTime(), getZipFileAppendFullForFullFile()));
		}
				
		String zipFileName = "";
		
		if(isDeltaPackage()) {
			if(packageDetails.isThisIsADeltaRun())
				zipFileName = getZipFileNameBeforeDateTime() + new SimpleDateFormat(getZipFileNameDateTimeFormat()).format(new Date()) + getZipFileNameAfterDateTime() + getZipFileAppendDeltaForDeltaFile() + "." + getZipFileFormat();
			else
				zipFileName = getZipFileNameBeforeDateTime() + new SimpleDateFormat(getZipFileNameDateTimeFormat()).format(new Date()) + getZipFileNameAfterDateTime() + getZipFileAppendFullForFullFile() + "." + getZipFileFormat();
		} else
			zipFileName = getZipFileNameBeforeDateTime() + new SimpleDateFormat(getZipFileNameDateTimeFormat()).format(new Date()) + getZipFileNameAfterDateTime() + getZipFileAppendFullForFullFile() + "." + getZipFileFormat();
		
		packageDetails.setPackageZipFileName(zipFileName);
		
		packageDetailsService.addPackageDetails(packageDetails);
		
		return RepeatStatus.FINISHED;
	}
	
	public String getZipFileNameBeforeDateTime() {
		if(this.zipFileNameBeforeDateTime == null)
			this.zipFileNameBeforeDateTime = "";
		return this.zipFileNameBeforeDateTime;
	}
	public void setZipFileNameBeforeDateTime(String zipFileNameBeforeDateTime) {
		this.zipFileNameBeforeDateTime = zipFileNameBeforeDateTime;
	}
	public String getZipFileNameDateTimeFormat() {
		if(this.zipFileNameDateTimeFormat == null)
			this.zipFileNameDateTimeFormat = "yyyyMMddHHmm";
		return this.zipFileNameDateTimeFormat;
	}
	public void setZipFileNameDateTimeFormat(String zipFileNameDateTimeFormat) {
		this.zipFileNameDateTimeFormat = zipFileNameDateTimeFormat;
	}
	public String getZipFileNameAfterDateTime() {
		if(this.zipFileNameAfterDateTime == null)
			this.zipFileNameAfterDateTime = "";
		return this.zipFileNameAfterDateTime;
	}
	public void setZipFileNameAfterDateTime(String zipFileNameAfterDateTime) {
		this.zipFileNameAfterDateTime = zipFileNameAfterDateTime;
	}
	public String getZipFileFormat() {
		if(this.zipFileFormat == null)
			this.zipFileFormat = "zip";
		return this.zipFileFormat;
	}
	public void setZipFileFormat(String zipFileFormat) {
		this.zipFileFormat = zipFileFormat;
	}
	public String getZipFileAppendFullForFullFile() {
		return zipFileAppendFullForFullFile;
	}
	public void setZipFileAppendFullForFullFile(String zipFileAppendFullForFullFile) {
		this.zipFileAppendFullForFullFile = zipFileAppendFullForFullFile;
	}
	public String getZipFileAppendDeltaForDeltaFile() {
		return zipFileAppendDeltaForDeltaFile;
	}
	public void setZipFileAppendDeltaForDeltaFile(String zipFileAppendDeltaForDeltaFile) {
		this.zipFileAppendDeltaForDeltaFile = zipFileAppendDeltaForDeltaFile;
	}
	public String getPackageName() {
		return packageName;
	}
	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}
	public boolean isDeltaPackage() {
		return deltaPackage;
	}
	public void setDeltaPackage(boolean deltaPackage) {
		this.deltaPackage = deltaPackage;
	}
	public boolean isThisADeltaRun() {
		return thisADeltaRun;
	}
	public void setThisADeltaRun(boolean thisADeltaRun) {
		this.thisADeltaRun = thisADeltaRun;
	}
	public String getDeltaExpression() {
		return deltaExpression;
	}
	public void setDeltaExpression(String deltaExpression) {
		this.deltaExpression = deltaExpression;
	}
	public String getZipFileDirectory() {
		return zipFileDirectory;
	}
	public void setZipFileDirectory(String zipFileDirectory) {
		this.zipFileDirectory = zipFileDirectory;
	}
	public String getJobName() {
		return jobName;
	}
	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

}
