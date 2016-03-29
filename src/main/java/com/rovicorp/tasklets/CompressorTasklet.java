package com.rovicorp.tasklets;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.zeroturnaround.zip.ZipUtil;

import com.rovicorp.service.PackageDetailsService;


public class CompressorTasklet implements Tasklet {

	
	private static final Logger logger=LoggerFactory.getLogger(CompressorTasklet.class);
	
	private String sourceDirectory;
	private String destinationDirectory;
	private String packageName;
	
	@Autowired
	PackageDetailsService packageDetailsService;
	
	@Override
	public RepeatStatus execute(StepContribution arg0, ChunkContext arg1) throws Exception {

		Assert.notNull(sourceDirectory,"source Directory can't be null!");
		Assert.notNull(destinationDirectory,"destination directory can't be null!");
		
		StringBuilder destinationBuilder=new StringBuilder();
		destinationBuilder.append(destinationDirectory).append(File.separator);
		destinationBuilder.append(packageDetailsService.getPackageDetails().get(getPackageName()).getPackageZipFileName());
				
		File sourceDirectoryObject=new File(sourceDirectory);
		File destinationDirectoryObject=new File(destinationBuilder.toString());
		
		logger.info("Attempting to compress and possibly move files in directory:[{}] to name:[{}]",sourceDirectory,destinationBuilder.toString());
		ZipUtil.pack(sourceDirectoryObject,destinationDirectoryObject);
		logger.info("Compressed file should be ready and located here:{}",destinationBuilder.toString());
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

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

}
