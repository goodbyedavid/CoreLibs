package com.rovicorp.core.file;

public class RoviFileException extends Exception{

	private static final long serialVersionUID = 1L;

	public RoviFileException() {
		super();
	}

	public RoviFileException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public RoviFileException(String message, Throwable cause) {
		super(message, cause);
	}

	public RoviFileException(String message) {
		super(message);
	}

	public RoviFileException(Throwable cause) {
		super(cause);
	}

}
