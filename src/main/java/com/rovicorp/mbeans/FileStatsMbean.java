package com.rovicorp.mbeans;

import java.util.ArrayList;
import java.util.List;

import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.stereotype.Component;

import com.rovicorp.dto.DirectoryFileStats;

@Component
@ManagedResource(objectName="com.rovicorp.mbeans:name=FileStatsMbean", description="FileStatsMbean: Status of all generated files...")
public class FileStatsMbean implements MbeansResetter {
	
	private List<DirectoryFileStats> directoryFileStats;
	
	public FileStatsMbean() {
		directoryFileStats=new ArrayList<DirectoryFileStats>();
	}
	
	@ManagedAttribute
	public String getDirectoryFileStats() {
		StringBuilder fileStats = new StringBuilder("");
		for(DirectoryFileStats dfs: this.directoryFileStats) {
			fileStats.append("FileName: " + dfs.getFileName() + ", FileSize: " + dfs.getFileSize() + ", Filegenerated at: " + dfs.getGeneratedTime() + "\r\n");
		}
		return fileStats.toString();
	}

	public void setDirectoryFileStats(DirectoryFileStats directoryFileStats) {
		this.directoryFileStats.add(directoryFileStats);
	}
	
	public void reset() {
		directoryFileStats.clear();
	}

}
