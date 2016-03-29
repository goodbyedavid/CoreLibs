package com.rovicorp.tasklets;

import java.io.File;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;

import com.rovicorp.core.file.FileMover;
import com.rovicorp.mbeans.FileMoverMbean;
import com.rovicorp.service.PackageDetailsService;

public class ZipFileMoverToMultipleDestinationsTasklet implements Tasklet {
    
	private static final Logger logger = LoggerFactory.getLogger(ZipFileMoverToMultipleDestinationsTasklet.class);
	private String sourceFile;
	private String sourceDirectory;
	private String destinationDirectory;
	private boolean isLarge=false;
	private String packageName;
	private boolean moveFileToFTPServer;
	private String destinatioSubFolders;
	private String specificZipFileNames;
	private String defaultZipFileName;

	@Autowired
	private FileMoverMbean fileMoverMbean;
	
	@Autowired
	PackageDetailsService packageDetailsService;
	
	@Override
	public RepeatStatus execute(StepContribution arg0, ChunkContext arg1) throws Exception {
		
		if(isMoveFileToFTPServer()) {
			if(getPackageName() != null && getSourceFile() == null) {
				setSourceFile(packageDetailsService.getPackageDetails().get(getPackageName()).getPackageZipFileName());
			}
			FileMover fileMover = new FileMover();
			String[] subFolders = StringUtils.split(getDestinatioSubFolders(), "|");
			String[] specificZipFileNames = StringUtils.split(getSpecificZipFileNames(), "|");
			for(int i=0;i<subFolders.length;i++) {
				logger.info("Moving file " + getSourceDirectory()+"/"+getSourceFile() + " to " + getDestinationDirectory() + File.separator + subFolders[i]);
				if(specificZipFileNames != null && i<specificZipFileNames.length)
					fileMover.moveFile(getSourceFile(), getDestinationDirectory() + File.separator + subFolders[i], getSourceDirectory(), StringUtils.replace(getSourceFile(), getDefaultZipFileName(), specificZipFileNames[i]), isLarge());
				else
					fileMover.moveFile(getSourceFile(), getDestinationDirectory() + File.separator + subFolders[i], getSourceDirectory(), isLarge());
			}
		} else
			logger.info("moveFileToFTPServer is false. So files were not posted to FTP server.");
		return RepeatStatus.FINISHED;
	}

	public String getSourceFile() {
		return sourceFile;
	}

	public void setSourceFile(String sourceFile) {
		this.sourceFile = sourceFile;
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

	public boolean isLarge() {
		return isLarge;
	}

	public void setIsLarge(boolean isLarge) {
		this.isLarge = isLarge;
	}

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public FileMoverMbean getFileMoverMbean() {
		return fileMoverMbean;
	}

	public void setFileMoverMbean(FileMoverMbean fileMoverMbean) {
		this.fileMoverMbean = fileMoverMbean;
	}
	public boolean isMoveFileToFTPServer() {
		return moveFileToFTPServer;
	}
	public void setMoveFileToFTPServer(boolean moveFileToFTPServer) {
		this.moveFileToFTPServer = moveFileToFTPServer;
	}
	public String getDestinatioSubFolders() {
		return destinatioSubFolders;
	}
	public void setDestinatioSubFolders(String destinatioSubFolders) {
		this.destinatioSubFolders = destinatioSubFolders;
	}
	public String getSpecificZipFileNames() {
		return specificZipFileNames;
	}
	public void setSpecificZipFileNames(String specificZipFileNames) {
		this.specificZipFileNames = specificZipFileNames;
	}
	public String getDefaultZipFileName() {
		return defaultZipFileName;
	}
	public void setDefaultZipFileName(String defaultZipFileName) {
		this.defaultZipFileName = defaultZipFileName;
	}

}