package com.mctoybox.toybox.exceptions;

public class NotPrimaryClassException extends Exception {
	private static final long serialVersionUID = 1L;
	
	public NotPrimaryClassException() {
		super();
	}
	
	public NotPrimaryClassException(String message) {
		super(message);
	}
	
	public NotPrimaryClassException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public NotPrimaryClassException(Throwable cause) {
		super(cause);
	}
	
}
