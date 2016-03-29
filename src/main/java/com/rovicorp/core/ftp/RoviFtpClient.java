package com.rovicorp.core.ftp;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.FileUtils;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import com.rovicorp.mbeans.FtpPullMbean;

@Component
public class RoviFtpClient {
	private static final Logger logger = LoggerFactory.getLogger(RoviFtpClient.class);

	private @Value("${ftp.hostname.name}") String hostName;
	private @Value("${ftp.user.name}")String userName;
	private @Value("${ftp.password}")String password;
	private @Value("${ftp.port}")int portNumber=21;
	
	private FTPClient ftpClient;
	
	@Autowired
	private FtpPullMbean ftpPullMbean;
	
	public RoviFtpClient(){
		ftpClient=new FTPClient();
	}
	
	public void login() throws RoviFtpClientException{
		
		if(ftpClient.isConnected()){
			logger.info("Login called when already connected to host {}",ftpClient.getRemoteAddress().getHostAddress());
			logger.info("Disconnecting and Reconnecting");
			quit();
		}
		
		try {
			logger.info("Attempting to connect to host:{}",hostName);
			
			ftpClient.connect(this.hostName,this.portNumber);
			
			logFtpReplies(ftpClient);
			int replyCode = ftpClient.getReplyCode();
			
            if (!FTPReply.isPositiveCompletion(replyCode)) {
            	ftpClient.disconnect();
            	throw new RoviFtpClientException("Failed to connect to host:"+hostName);
            }
            
            boolean success = ftpClient.login(userName, password);
           
            logFtpReplies(ftpClient);
            
            if (!success) {
                logger.error("Failed to log into host:[{}]",hostName);
                throw new RoviFtpClientException("Failed to log into host:"+hostName);
            } else {
                logger.info("Logged into host:[{}] successfull!!",hostName);
            }
		} catch (Exception e) {
			logger.error("Failed connecting to [{}]: Reason:{}",hostName,e);
		    throw new RoviFtpClientException(e);
		}
	}
	
	public InputStream getFileAsInputStream(String fileName)throws RoviFtpClientException{
		try {
			Assert.notNull("File name can't be null",fileName);
			logger.info("Attempting to retrieve file as input stream from ftp:{}",fileName);
			InputStream inputStream=ftpClient.retrieveFileStream(fileName);
			logger.info("Downloaded to file as input stream:{}",fileName);
			return inputStream;
		} catch (IOException e) {
			ftpPullMbean.setErrorDownloading(true);
			ftpPullMbean.setErrorDescription("Failed to retreive file: " + fileName + ". Error details are: " + e.getMessage());
			logger.error("Failed to retreive file [{}]: Reason:{}",fileName,e);
		    throw new RoviFtpClientException(e);
		}
	}
	
	public void putFileOnFtp(String fileName, String sourceFileLocation,String destinationFileLocation) throws RoviFtpClientException{
		Assert.notNull("File name can't be null",fileName);
		Assert.notNull("sourceFileLocation name can't be null",sourceFileLocation);
		Assert.notNull("destinationFileLocation name can't be null",destinationFileLocation);
		if(ftpClient.isConnected()){
			try{
				logger.info("Attempting to upload file to ftp!!");
				logger.info("Going into passive mode");
				ftpClient.setFileType( FTP.BINARY_FILE_TYPE);
				ftpClient.enterLocalPassiveMode();
				FileInputStream fis=FileUtils.openInputStream(new File(sourceFileLocation+"/"+fileName));
				logger.info("ftp'n {} to destination: {}",sourceFileLocation+"/"+fileName, destinationFileLocation);
				ftpClient.storeFile(destinationFileLocation+"/"+fileName, fis);
			}catch (Exception e) {
				  logger.error("Failed disconnecting from [{}]: Reason:{}",hostName,e);
			      throw new RoviFtpClientException(e);
			}
			finally{
				  try{
					  ftpClient.disconnect();
				  }catch(Exception e){
					  logger.error("[Last Attempt] Failed to disconnect Reason:{}",e);
				  } 
			}
			}
		 else{
			logger.error("FTP Client was never connected");
		}
		
	}
	
	public void quit() throws RoviFtpClientException{
		logger.info("Attempting to disconnect from {}",hostName);
		if(ftpClient.isConnected()){
		  try{
			  
			  ftpClient.logout();
			  logger.info("FTPClient disconnected from host {}",hostName);
		  } catch (IOException e) {
			  logger.error("Failed disconnecting from [{}]: Reason:{}",hostName,e);
		      throw new RoviFtpClientException(e);
		  }
		  finally{
			  try{
				  ftpClient.disconnect();
			  }catch(IOException e){
				  logger.error("[Last Attempt] Failed to disconnect Reason:{}",e);
			  }
		  }
		}else{
			logger.info("FTPClient was never connected....");
		}
	}
	private void logFtpReplies(FTPClient ftpClient) {
        String[] allReplyStrings = ftpClient.getReplyStrings();
        if (allReplyStrings != null && allReplyStrings.length > 0) {
            for (String reply : allReplyStrings) {
                logger.info("{}:{}",hostName,reply);
            }
        }else{
        	logger.warn("No Response from:{}",hostName);
        }
    }
}
