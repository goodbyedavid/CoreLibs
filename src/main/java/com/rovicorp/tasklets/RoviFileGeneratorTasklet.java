package com.rovicorp.tasklets;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;

import com.rovicorp.dto.DirectoryFileStats;
import com.rovicorp.mbeans.FileStatsMbean;
import com.rovicorp.utils.RoviFileUtils;

public class RoviFileGeneratorTasklet implements Tasklet {
	
	private static final Logger logger=LoggerFactory.getLogger(RoviFileGeneratorTasklet.class);
	private String directory;
	private String fileName;
	private List<String> filesToSkip;
	private String columnNames;
	private String orderByFieldName;
	
	//private @Value("${dpi21.source.directory}") String directory;
	
	@Autowired
	private FileStatsMbean fileStatsMbean;

	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
		createFile();
		return RepeatStatus.FINISHED;
	}

	public void createFile() {
		PrintWriter writer;
		try {
			
			String[] allColumnNames = getColumnNames().split(",");
			
			writer = new PrintWriter(getDirectory() + File.separatorChar + getFileName());
			List<DirectoryFileStats> directoryFileStats = RoviFileUtils.getDirectoryStats(getDirectory());
			
			if(getOrderByFieldName() == "fileName")
				Collections.sort(directoryFileStats, DirectoryFileStats.fileNameComparator);
			if(getOrderByFieldName() == "fileSize")
				Collections.sort(directoryFileStats, DirectoryFileStats.fileSizeComparator);
			if(getOrderByFieldName() == "generatedTime")
				Collections.sort(directoryFileStats, DirectoryFileStats.fileGeneratedTimeComparator);
			
			for(DirectoryFileStats dfs: directoryFileStats) {
				BeanWrapper dfsWrapper = new BeanWrapperImpl(dfs);
				if((!getFilesToSkip().contains(dfs.getFileName())) && !dfs.getFileName().equals(getFileName())) {
					for(int i =0; i < allColumnNames.length; i++) {
						if((i+1) == allColumnNames.length)
							writer.println(dfsWrapper.getPropertyValue(allColumnNames[i].trim()));
						else
							writer.print(dfsWrapper.getPropertyValue(allColumnNames[i].trim()) + ",");
					}
					fileStatsMbean.setDirectoryFileStats(dfs);
				}
			}
			writer.close();
		} catch (FileNotFoundException e) {
			logger.error("File {} not found under directory {}", getFileName(), getDirectory(), e);
			e.printStackTrace();
		}
		catch (Exception e) {
			logger.error("Exception while creating file {} under directory {}", getFileName(), getDirectory(), e);
			e.printStackTrace();
		}
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getDirectory() {
		return directory;
	}

	public void setDirectory(String directory) {
		this.directory = directory;
	}

	public List<String> getFilesToSkip() {
		if(filesToSkip == null)
			filesToSkip = new ArrayList<String>();
		return filesToSkip;
	}

	public void setFilesToSkip(List<String> filesToSkip) {
		this.filesToSkip = filesToSkip;
	}

	public String getColumnNames() {
		if(columnNames == null || columnNames.length() < 1)
			columnNames = "fileName";
		return columnNames;
	}

	public void setColumnNames(String columnNames) {
		this.columnNames = columnNames;
	}

	public String getOrderByFieldName() {
		if(orderByFieldName == null || orderByFieldName.length() < 1)
			orderByFieldName = "fileName";
		return orderByFieldName;
	}

	public void setOrderByFieldName(String orderByFieldName) {
		this.orderByFieldName = orderByFieldName;
	}
	
}
