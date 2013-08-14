package com.mctoybox.toybox.exceptions;

public class NotEnoughArgumentsException extends Exception {
	private static final long serialVersionUID = 1L;
	
	public NotEnoughArgumentsException() {
		super();
	}
	
	public NotEnoughArgumentsException(String message) {
		super(message);
	}
	
	public NotEnoughArgumentsException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public NotEnoughArgumentsException(Throwable cause) {
		super(cause);
	}
}
