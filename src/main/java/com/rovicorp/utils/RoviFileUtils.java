package com.rovicorp.utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.TrueFileFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rovicorp.core.file.RoviFileException;
import com.rovicorp.dto.DirectoryFileStats;


public class RoviFileUtils {

	private static final Logger logger=LoggerFactory.getLogger(RoviFileUtils.class);
	
	
	public static String convertLine(String line){
		//At the start of the apollo file there are lines that start with = and APOLLO_ID. 
		//The following lines can be skipped.
		if(line.startsWith("=")|| line.startsWith("APOLLO_ID")){
			return null;
		}
		//remove the tab/spaces at the start of each line.
		String convertedLine=line.replaceAll("^[ \t]*", "");
		//now replace all the consecutive white space with a comma.
		return convertedLine.replaceAll("\\s+", ",");
	}
	
	public static String getFirstToken(String line){
		//At the start of the apollo file there are lines that start with = and APOLLO_ID. 
		//The following lines can be skipped.
		if(line.startsWith("=")|| line.startsWith("APOLLO_ID")){
			return null;
		}
		String convertedLine=line.replaceAll("^[ \t]*", "");
		
		//return convertedLine=convertedLine.replaceAll("\\s+", ",");
		
		return convertedLine.substring(0, convertedLine.indexOf(" ")+1);
		
	}
	
	public static boolean doesFileExist(String pathToFile){
		return new File(pathToFile).isFile();
	}
	
	public static boolean doesDirectoryExist(String directoryName){
		return new File(directoryName).isDirectory();
	}
	
	public static boolean createDirectory(String directoryName) throws RoviFileException{
		logger.info("Creating directory with name:{}",directoryName);
		try {
			FileUtils.forceMkdir(new File(directoryName));
		} catch (IOException e) {
			logger.error("Failed to create directory:",e);
			throw new RoviFileException(e);
		}
		return doesDirectoryExist(directoryName);
	}
	
	public static boolean removeDirectory(String directoryName) throws RoviFileException{
		logger.info("Removing directory:{}",directoryName);
		try{
		  FileUtils.forceDelete(new File(directoryName));
		}catch(IOException e){
			logger.error("Failed to remove directory:{}",e);
			throw new RoviFileException(e);
		}
		return doesDirectoryExist(directoryName);
	}
	
	public static List<DirectoryFileStats> getDirectoryStats(String directory){
		
		List<DirectoryFileStats> directoryFileStats=new ArrayList<DirectoryFileStats>();
		Collection<File> files = FileUtils.listFiles(FileUtils.getFile(directory), TrueFileFilter.INSTANCE, TrueFileFilter.INSTANCE);
		
		int i = 1;
		for(File f: files){
			logger.info("File name:{} size:{}",f.getName(),FileUtils.sizeOf(f));
			DirectoryFileStats dfs=new DirectoryFileStats();
			dfs.setRowId(i++);
			dfs.setFileName(f.getName());
			dfs.setFileSize(FileUtils.sizeOf(f));
			dfs.setGeneratedTime(f.lastModified());
			f.lastModified();
			directoryFileStats.add(dfs);
		}
		return directoryFileStats;
		
	}
	
	public static boolean cleanDirectory(String directory) throws RoviFileException {
		logger.info("Cleaning/removing files under directory:{}", directory);
		try {
			FileUtils.cleanDirectory(new File(directory));
		} catch(IOException e) {
			logger.error("Failed to clean/remove files under directory: {}, Exception is {}", directory, e);
			throw new RoviFileException(e);
		}
		return true;
	}
	
	public static boolean createEmptyFile(String directory, String fileName) throws RoviFileException {
		try {
			File f = new File(directory + File.separator + fileName);
			return f.createNewFile();
		} catch (IOException e) {
			logger.error("Failed to craete empty file under directory: {}, Exception is {}", directory, e);
			throw new RoviFileException(e);
		}
	}
}
