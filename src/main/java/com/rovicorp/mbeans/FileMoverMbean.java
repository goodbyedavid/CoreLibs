package com.rovicorp.mbeans;

import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.stereotype.Component;

@Component
@ManagedResource(objectName="com.rovicorp.mbeans:name=FileMoverMbean", description="FileMoverMbean: Moving zipped file to FTP location...")
public class FileMoverMbean implements MbeansResetter {
	
	private boolean zipFileMoved = false;
	private String zipFileName;
	private boolean zipFilePostingError;
	private String zipFilePostingErrorDescription;
	
	@ManagedAttribute
	public boolean isZipFileMoved() {
		return zipFileMoved;
	}

	public void setZipFileMoved(boolean zipFileMoved) {
		this.zipFileMoved = zipFileMoved;
	}

	@ManagedAttribute
	public String getZipFileName() {
		return zipFileName;
	}
	
	public void setZipFileName(String zipFileName) {
		this.zipFileName = zipFileName;
	}
	
	@ManagedAttribute
	public boolean isZipFilePostingError() {
		return zipFilePostingError;
	}
	
	public void setZipFilePostingError(boolean zipFilePostingError) {
		this.zipFilePostingError = zipFilePostingError;
	}
	
	@ManagedAttribute
	public String getZipFilePostingErrorDescription() {
		return zipFilePostingErrorDescription;
	}
	
	public void setZipFilePostingErrorDescription(
			String zipFilePostingErrorDescription) {
		this.zipFilePostingErrorDescription = zipFilePostingErrorDescription;
	}
	
	public void reset() {
		this.zipFileMoved = false;
		this.zipFileName = "";
		this.zipFilePostingError = false;
		this.zipFilePostingErrorDescription = "";
	}

}
