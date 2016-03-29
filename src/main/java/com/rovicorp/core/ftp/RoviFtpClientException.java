package com.rovicorp.core.ftp;

public class RoviFtpClientException extends Exception {

	private static final long serialVersionUID = 1L;

	public RoviFtpClientException() {
		super();
		
	}

	public RoviFtpClientException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		
	}

	public RoviFtpClientException(String message, Throwable cause) {
		super(message, cause);
	}

	public RoviFtpClientException(String message) {
		super(message);
		
	}

	public RoviFtpClientException(Throwable cause) {
		super(cause);
		
	}
	

}
