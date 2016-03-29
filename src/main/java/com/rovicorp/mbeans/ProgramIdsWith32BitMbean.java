package com.rovicorp.mbeans;

import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.stereotype.Component;

@Component
@ManagedResource(objectName="com.rovicorp.mbeans:name=ProgramIdsWith32BitMbean", description="32 Bit ProgramIds present: Checking for 32 Bit ProgramIds...")
public class ProgramIdsWith32BitMbean implements MbeansResetter {
	
	private boolean programIds32BitAvailable = false;
	private String listOfProgramIds;
	
	@ManagedAttribute
	public boolean isProgramIds32BitAvailable() {
		return programIds32BitAvailable;
	}
	
	public void setProgramIds32BitAvailable(boolean programIds32BitAvailable) {
		this.programIds32BitAvailable = programIds32BitAvailable;
	}
	
	@ManagedAttribute
	public String getListOfProgramIds() {
		return listOfProgramIds;
	}
	
	public void setListOfProgramIds(String listOfProgramIds) {
		this.listOfProgramIds = listOfProgramIds;
	}
	
	public void reset() {
		this.programIds32BitAvailable = false;
		this.listOfProgramIds = "";
	}

}
