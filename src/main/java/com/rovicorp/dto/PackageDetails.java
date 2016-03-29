package com.rovicorp.dto;

public class PackageDetails {
	
	private String packageName;
	private String packageZipFileName;
	private boolean deltaPackage;
	private boolean thisIsADeltaRun;
	
	public String getPackageName() {
		return packageName;
	}
	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}
	public String getPackageZipFileName() {
		return packageZipFileName;
	}
	public void setPackageZipFileName(String packageZipFileName) {
		this.packageZipFileName = packageZipFileName;
	}
	public boolean isDeltaPackage() {
		return deltaPackage;
	}
	public void setDeltaPackage(boolean deltaPackage) {
		this.deltaPackage = deltaPackage;
	}
	public boolean isThisIsADeltaRun() {
		return thisIsADeltaRun;
	}
	public void setThisIsADeltaRun(boolean thisIsADeltaRun) {
		this.thisIsADeltaRun = thisIsADeltaRun;
	}
	
	public String getZipFileNameByJobNameWithoutFileExtension() {
		return getPackageZipFileName().substring(0, getPackageZipFileName().lastIndexOf("."));
	}

}
