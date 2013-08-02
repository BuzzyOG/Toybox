package com.mctoybox.toybox.exceptions;

public class PlayerNotAllowedClassException extends Exception {
	private static final long serialVersionUID = 1L;
	
	public PlayerNotAllowedClassException() {
		super();
	}
	
	public PlayerNotAllowedClassException(String message) {
		super(message);
	}
	
	public PlayerNotAllowedClassException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public PlayerNotAllowedClassException(Throwable cause) {
		super(cause);
	}
}
