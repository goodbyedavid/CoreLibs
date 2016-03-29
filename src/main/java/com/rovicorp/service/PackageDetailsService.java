package com.rovicorp.service;

import java.util.HashMap;

import org.springframework.stereotype.Component;

import com.rovicorp.dto.PackageDetails;

@Component
public class PackageDetailsService {
	
	private HashMap<String, PackageDetails> packageDetails =  new HashMap<String, PackageDetails>();

	public HashMap<String, PackageDetails> getPackageDetails() {
		return packageDetails;
	}

	public void setPackageDetails(HashMap<String, PackageDetails> packageDetails) {
		this.packageDetails = packageDetails;
	}
	
	public void addPackageDetails(PackageDetails pakageDetails) {
		this.packageDetails.put(pakageDetails.getPackageName(), pakageDetails);
	}

}
