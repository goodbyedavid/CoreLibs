package com.rovicorp.mbeans;

import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.stereotype.Component;

@Component
@ManagedResource(objectName="com.rovicorp.mbeans:name=DuplicateCheckerMbean", description="DuplicateCheckerMbean: Checking for duplcates...")
public class DuplicateCheckerMbean implements MbeansResetter {
	
	private boolean failed = false;
	private String errorName;
	private String errorDescription;
	
	@ManagedAttribute
	public boolean isFailed() {
		return failed;
	}

	public void setFailed(boolean failed) {
		this.failed = failed;
	}

	@ManagedAttribute
	public String getErrorName() {
		return errorName;
	}
	
	public void setErrorName(String errorName) {
		this.errorName = errorName;
	}
	
	@ManagedAttribute
	public String getErrorDescription() {
		return errorDescription;
	}
	
	public void setErrorDescription(String errorDescription) {
		this.errorDescription = errorDescription;
	}
	
	public void reset() {
		this.failed = false;
		this.errorName = "";
		this.errorDescription = "";
	}

}
