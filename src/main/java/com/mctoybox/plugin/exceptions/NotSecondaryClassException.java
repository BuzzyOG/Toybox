package com.mctoybox.plugin.exceptions;

public class NotSecondaryClassException extends Exception {
	private static final long serialVersionUID = 1L;
	
	public NotSecondaryClassException() { super(); }
	public NotSecondaryClassException(String message) { super(message); }
	public NotSecondaryClassException(String message, Throwable cause) { super(message, cause); }
	public NotSecondaryClassException(Throwable cause) { super(cause); }

}
