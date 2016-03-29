package com.rovicorp.mbeans;

import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.stereotype.Component;

@Component
@ManagedResource(objectName="com.rovicorp.mbeans:name=FtpPullMbean", description="FtpPullMbean: Pulling files from FTP to local...")
public class FtpPullMbean implements MbeansResetter {

	private boolean errorDownloading = false;
	private String errorDescription;
	private String downloadedFileName;
	private boolean fileArchived = true;
	private String fileAchiveErrorDescription;
	private String archivedFileName;
	
	@ManagedAttribute
	public boolean isErrorDownloading() {
		return errorDownloading;
	}

	public void setErrorDownloading(boolean errorDownloading) {
		this.errorDownloading = errorDownloading;
	}

	@ManagedAttribute
	public String getErrorDescription() {
		return errorDescription;
	}

	public void setErrorDescription(String errorDescription) {
		this.errorDescription = errorDescription;
	}
	
	@ManagedAttribute
	public String getDownloadedFileName() {
		return downloadedFileName;
	}
	
	public void setDownloadedFileName(String downloadedFileName) {
		this.downloadedFileName = downloadedFileName;
	}
	
	@ManagedAttribute
	public boolean isFileArchived() {
		return fileArchived;
	}
	
	public void setFileArchived(boolean fileArchived) {
		this.fileArchived = fileArchived;
	}
	
	@ManagedAttribute
	public String getFileAchiveErrorDescription() {
		return fileAchiveErrorDescription;
	}

	public void setFileAchiveErrorDescription(String fileAchiveErrorDescription) {
		this.fileAchiveErrorDescription = fileAchiveErrorDescription;
	}

	@ManagedAttribute
	public String getArchivedFileName() {
		return archivedFileName;
	}
	
	public void setArchivedFileName(String archivedFileName) {
		this.archivedFileName = archivedFileName;
	}
	
	public void reset() {
		this.errorDownloading = false;
		this.errorDescription = "";
		this.downloadedFileName = "";
		this.fileArchived = true;
		this.fileAchiveErrorDescription = "";
		this.archivedFileName = "";
	}

}
