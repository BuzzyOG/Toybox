package com.mctoybox.toybox.exceptions;

public class ArgumentIsNullException extends Exception {
	private static final long serialVersionUID = 1L;
	
	public ArgumentIsNullException() { super(); }
	public ArgumentIsNullException(String message) { super(message); }
	public ArgumentIsNullException(String message, Throwable cause) { super(message, cause); }
	public ArgumentIsNullException(Throwable cause) { super(cause); }

}
