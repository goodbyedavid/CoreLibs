package com.rovicorp.tasklets;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import com.rovicorp.service.PackageDetailsService;

 

/**
 * @author ABajaj
 *
 */
public class MergeMultipleFilesTasklet implements Tasklet {

 	private static final Logger logger = LoggerFactory.getLogger(MergeMultipleFilesTasklet.class);
	
	private String XMLDECLARATION ="<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
	private String outPutFilePath;
	private List<String> inputFilesPath;
	private String isfileFullorDelta;	
	private String fileType;
	private String xmlHeaderTag = null;
	private String XMLHEADTAG = "<header>";
	private String XMLFOOTTAG = "</footer>";
	private String packageName;
	private boolean large;
	
	@Autowired
	PackageDetailsService packageDetailsService;

	public String getIsfileFullorDelta() {
		return isfileFullorDelta;
	}

	public void setIsfileFullorDelta(String isfileFullorDelta) {
		this.isfileFullorDelta = isfileFullorDelta;
	}

	public String getFileType() {
		return fileType;
	}

	public String getXmlHeaderTag() {
		return xmlHeaderTag;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public void setXmlHeaderTag(String xmlHeaderTag) {
		this.xmlHeaderTag = xmlHeaderTag;
	}

	public List<String> getInputFilesPath() {
		return inputFilesPath;
	}

	public void setInputFilesPath(List<String> inputFilesPath) {
		this.inputFilesPath = inputFilesPath;
	}

	public String getOutPutFilePath() {
		return outPutFilePath;
	}

	public void setOutPutFilePath(String outPutFilePath) {
		this.outPutFilePath = outPutFilePath;
	}

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public boolean isLarge() {
		return large;
	}

	public void setLarge(boolean large) {
		this.large = large;
	}

	/**
	 * This method is merge multiple xml files into one file file.
	 **/
	public void MergeXMLFiles() throws IOException
	{
		Assert.notNull(outPutFilePath,"File output path cannot be null");
		
		StringBuilder outFilePath = new StringBuilder();
		outFilePath.append(outPutFilePath)
					.append(File.separator)										  
					.append(packageDetailsService.getPackageDetails().get(getPackageName()).getZipFileNameByJobNameWithoutFileExtension())
					.append(".xml");
		
		 
		 try
		 {
			 FileOutputStream outFile =new FileOutputStream(outFilePath.toString(),true);
			 outFile.write(XMLDECLARATION.getBytes());
			 outFile.write("\r\n".getBytes());
			 outFile.write(XMLHEADTAG.replace("header", xmlHeaderTag).getBytes());
			 outFile.write("\r\n".getBytes());
			 //Loop through each file and copy the content of the file.
			 for(String filePath:inputFilesPath)
			 {
				 if(isLarge()) {
					 FileInputStream inFile = new FileInputStream(filePath);
					 IOUtils.copyLarge(inFile, outFile);
					 inFile.close();
				 } else {
					 BufferedReader inFile = new BufferedReader(new FileReader(filePath));
					 //Skip the first line.
					 //inFile.readLine();
					 IOUtils.copy(inFile,outFile);
					 inFile.close();
				 }
			 }
			 outFile.write("\r\n".getBytes());
			 outFile.write(XMLFOOTTAG.replace("footer", xmlHeaderTag).getBytes());
			 outFile.close();
		 } catch(Exception e) {
			 logger.info("Error occured while Merging the XML files.");
			 throw new IOException(e);
		 }
    }  
	
	/**
	 * Merge list of text files into one file.
	 * */
	public void MergeTextFiles() throws IOException
	{
		Assert.notNull(outPutFilePath,"File output path cannot be null");
		     	
		 try
		 {
			 FileOutputStream outFile =new FileOutputStream(outPutFilePath,true);
			 //Loop through each file and copy the content of the file.
			 for(String filePath:inputFilesPath)
			 {
				 FileInputStream inFile = new FileInputStream(filePath);
				 if(isLarge())
					 IOUtils.copyLarge(inFile, outFile);
				 else
					 IOUtils.copy(inFile, outFile);
				 inFile.close();
			 }
			 outFile.close();
		 } catch(Exception e) {
			 logger.info("Error occured while Merging the XML files.");
			 throw new IOException(e);
		 }
    }
		 
	@Override
	public RepeatStatus execute(StepContribution contribution,
			ChunkContext chunkContext) throws Exception {
		
		try {
			
			//based on file type call respective methods
			if(fileType.equalsIgnoreCase("XML"))
				MergeXMLFiles();
			else
				MergeTextFiles();
		} catch (Exception ex) {
			logger.error("Combined file generation failed. " + ex.getMessage() + ex.getStackTrace());
			throw ex;
		}
		 
		return   RepeatStatus.FINISHED;
	}

}