package com.rovicorp.core.file;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.zeroturnaround.zip.ZipUtil;

import com.rovicorp.utils.RoviFileUtils;

@Component
public class FileCompressor {
	private static final Logger logger = LoggerFactory.getLogger(FileCompressor.class);
	
	public FileCompressor(){
		
	}
	
	public void compress(String sourceDirectory, String destinationDirectory, String compressedFileName) throws RoviFileException{
		logger.debug("checking to see if all the inputs are not null");
		Assert.notNull(sourceDirectory,"source Directory can't be null!");
		Assert.notNull(destinationDirectory,"destination directory can't be null!");
		Assert.notNull(compressedFileName,"compressedFileName can't be null!");
		
		logger.debug("checking to see if the source directory and destination directory exist");
		if(!RoviFileUtils.doesDirectoryExist(sourceDirectory)){
			logger.error("source directory {} does not exist. ",sourceDirectory);
			throw new RoviFileException("source Directory Does Not Exist... please create.."+sourceDirectory);
		}
		
		if(!RoviFileUtils.doesDirectoryExist(destinationDirectory)){
			logger.error("destination directory {} does not exist. ",destinationDirectory);
			throw new RoviFileException("Destination Directory Does Not Exist... please create..");
		}
		
		StringBuilder destinationBuilder=new StringBuilder();
		destinationBuilder.append(destinationDirectory).append(File.separator).append(compressedFileName);
		logger.debug("set up the source and destination directories...");
		File sourceDirectoryObject=new File(sourceDirectory);
		File destinationDirectoryObject=new File(destinationBuilder.toString());
		
		logger.info("Attempting to compress and possibly move files in directory:[{}] to name:[{}]",sourceDirectory,destinationBuilder.toString());
		ZipUtil.pack(sourceDirectoryObject,destinationDirectoryObject);
		
		logger.debug("checking to see if file is really there.....");
		if(!RoviFileUtils.doesFileExist(destinationBuilder.toString())){
			logger.error("VERY BAD. File did not get moved to the final destination",destinationDirectory);
			throw new RoviFileException("File did not get moved to the final destination: VERY BAD.");
		}
		logger.info("Compressed file moved to destination directory {}",destinationBuilder.toString());
	}
	
	public static void main (String[] args) throws Exception{
		FileCompressor fileCompressor=new FileCompressor();
		fileCompressor.compress("/opt/test_dpi/source", "/opt/test_dpi/destination", "big.zip");
	}

}
