package com.rovicorp.tasklets;

import java.io.File;
import java.util.ArrayList;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

import com.rovicorp.utils.RoviFileUtils;

public class RoviCreateFolderStructureTasklet implements Tasklet {
	
	private String baseFolder;
	ArrayList<String> foldersToBeCreated = new ArrayList<String>();
	private boolean createFolders;
	
	private static final Logger logger = LoggerFactory.getLogger(RoviCreateFolderStructureTasklet.class);

	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {

		if(isCreateFolders()) {
			for(String folder : getFoldersToBeCreated()) {
				String[] subFolders = StringUtils.split(folder, "|");
				for(int i=0;i<subFolders.length;i++) {
					RoviFileUtils.createDirectory(getBaseFolder() + File.separator + subFolders[i]);
				}
			}
		} else
			logger.info("create folders boolean flag is false. So folderes were nor created.");
		return RepeatStatus.FINISHED;
	}

	public String getBaseFolder() {
		return baseFolder;
	}
	public void setBaseFolder(String baseFolder) {
		this.baseFolder = baseFolder;
	}
	public ArrayList<String> getFoldersToBeCreated() {
		return foldersToBeCreated;
	}
	public void setFoldersToBeCreated(ArrayList<String> foldersToBeCreated) {
		this.foldersToBeCreated = foldersToBeCreated;
	}
	public boolean isCreateFolders() {
		return createFolders;
	}
	public void setCreateFolders(boolean createFolders) {
		this.createFolders = createFolders;
	}

}
