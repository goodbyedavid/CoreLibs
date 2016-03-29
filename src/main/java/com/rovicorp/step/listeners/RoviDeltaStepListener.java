package com.rovicorp.step.listeners;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.beans.factory.annotation.Autowired;

import com.rovicorp.dto.PackageDetails;
import com.rovicorp.service.PackageDetailsService;

public class RoviDeltaStepListener  implements StepExecutionListener {
	
	private String packageName;
	@Autowired
	PackageDetailsService packageDetailsService;

	@Override
	public void beforeStep(StepExecution stepExecution) {
		// TODO Auto-generated method stub
	}

	@Override
	public ExitStatus afterStep(StepExecution stepExecution) {
		PackageDetails packageDetails = packageDetailsService.getPackageDetails().get(getPackageName());
		if(packageDetails.isDeltaPackage() && packageDetails.isThisIsADeltaRun()) {
			return new ExitStatus("DELTARUN");
		} else if(!packageDetails.isDeltaPackage() && packageDetails.isThisIsADeltaRun())
			return new ExitStatus("NORUN");
		return new ExitStatus("FULLRUN");
	}

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

}
