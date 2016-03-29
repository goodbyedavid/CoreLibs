package com.rovicorp.core.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.zeroturnaround.zip.commons.IOUtils;

import com.rovicorp.utils.RoviFileUtils;

@Component
public class FileMover {
	private static final Logger logger = LoggerFactory.getLogger(FileMover.class);
	
	//@Autowired
	//private FtpPullMbean ftpPullMbean;
	
	public void moveFile(String sourceFile,String destinationDirectory, String sourceDirectory, boolean isLarge)throws RoviFileException{
		
		Assert.notNull(sourceFile, "sourceFile can't be null!");
		Assert.notNull(destinationDirectory,"destination directory can't be null!");
		Assert.notNull(sourceDirectory,"sourceDirectory can't be null!");
		
		StringBuilder fullSourcePathBuilder=new StringBuilder();
		fullSourcePathBuilder.append(sourceDirectory).append(File.separator).append(sourceFile);
		StringBuilder fullDestinationPathBuilder=new StringBuilder();
		fullDestinationPathBuilder.append(destinationDirectory).append(File.separator).append(sourceFile);
		
		logger.info("Attempting to move {} to {}", fullSourcePathBuilder.toString(),destinationDirectory);
		FileInputStream fis;
		FileOutputStream fos;
		try {
			logger.debug("creating input and output streams...");
			fis = new FileInputStream(new File(fullSourcePathBuilder.toString()));
			fos = new FileOutputStream(new File(fullDestinationPathBuilder.toString()));
			logger.debug("is file over 2GB? {}",isLarge);
			if(isLarge){
			  logger.info("File {} is larger then 2GB using copyLarge method...");
			  IOUtils.copyLarge(fis, fos);
			}else{
			   IOUtils.copy(fis, fos);
			}
			fis.close();
			fos.close();
			logger.debug("File was copied now lets see if it really was copied........");
			if(!RoviFileUtils.doesFileExist(fullDestinationPathBuilder.toString())){
				logger.error("FILE {} WAS NOT COPIED TO DESTINATION {}",fullSourcePathBuilder.toString(),fullDestinationPathBuilder.toString());
				throw new RoviFileException("FILE "+fullSourcePathBuilder.toString()+" WAS NOT COPIED TO DESTINATION "+fullDestinationPathBuilder.toString());
			}
		    logger.info("Copy complete file located at {}",fullDestinationPathBuilder.toString());
		} catch (FileNotFoundException e) {
			logger.error("Problem copying file to destination!!!:",e);
		    throw new RoviFileException(e);
		} catch (IOException e){
			logger.error("Problem copying file to destination!!!:",e);
		    throw new RoviFileException(e);
		} catch(Exception e){
			logger.error("Problem copying file to destination!!!:",e);
		    throw new RoviFileException(e);
		}
	}
	
    public void moveFile(String sourceFile,String destinationDirectory, String sourceDirectory, String destinationFileName, boolean isLarge)throws RoviFileException{
		
		Assert.notNull(sourceFile, "sourceFile can't be null!");
		Assert.notNull(destinationDirectory,"destination directory can't be null!");
		Assert.notNull(sourceDirectory,"sourceDirectory can't be null!");
		
		StringBuilder fullSourcePathBuilder=new StringBuilder();
		fullSourcePathBuilder.append(sourceDirectory).append(File.separator).append(sourceFile);
		StringBuilder fullDestinationPathBuilder=new StringBuilder();
		fullDestinationPathBuilder.append(destinationDirectory).append(File.separator).append(destinationFileName);
		
		logger.info("Attempting to move {} to {}", fullSourcePathBuilder.toString(),destinationDirectory);
		FileInputStream fis;
		try {
			logger.debug("creating input and output streams...");
			fis = new FileInputStream(new File(fullSourcePathBuilder.toString()));
			FileOutputStream fos=new FileOutputStream(new File(fullDestinationPathBuilder.toString()));
			logger.debug("is file over 2GB? {}",isLarge);
			if(isLarge){
			  logger.info("File {} is larger then 2GB using copyLarge method...");
			  IOUtils.copyLarge(fis, fos);
			}else{
			   IOUtils.copy(fis, fos);
			}
			logger.debug("File was copied now lets see if it really was copied........");
			if(!RoviFileUtils.doesFileExist(fullDestinationPathBuilder.toString())){
				logger.error("FILE {} WAS NOT COPIED TO DESTINATION {}",fullSourcePathBuilder.toString(),fullDestinationPathBuilder.toString());
				throw new RoviFileException("FILE "+fullSourcePathBuilder.toString()+" WAS NOT COPIED TO DESTINATION "+fullDestinationPathBuilder.toString());
			}
			//ftpPullMbean.setFileArchived(true);
			//ftpPullMbean.setArchivedFileName(destinationFileName);
		    logger.info("Copy complete file located at {}",fullDestinationPathBuilder.toString());
		} catch (FileNotFoundException e) {
			//ftpPullMbean.setFileArchived(false);
			//ftpPullMbean.setFileAchiveErrorDescription("Problem copying file to destination!!!:" + e.getMessage());
			logger.error("Problem copying file to destination!!!:",e);
		    throw new RoviFileException(e);
		} catch (IOException e){
			//ftpPullMbean.setFileArchived(false);
			//ftpPullMbean.setFileAchiveErrorDescription("Problem copying file to destination!!!:" + e.getMessage());
			logger.error("Problem copying file to destination!!!:",e);
		    throw new RoviFileException(e);
		} catch(Exception e){
			//ftpPullMbean.setFileArchived(false);
			//ftpPullMbean.setFileAchiveErrorDescription("Problem copying file to destination!!!:" + e.getMessage());
			logger.error("Problem copying file to destination!!!:",e);
		    throw new RoviFileException(e);
		}
	}
	
	public static void main(String[] args) throws Exception{
		FileMover fm=new FileMover();
		fm.moveFile("big.zip", "/opt/test_dpi/final", "/opt/test_dpi/destination", false);
		
	}
}
