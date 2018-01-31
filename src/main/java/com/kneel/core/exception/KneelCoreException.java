package com.kneel.core.exception;

/**
 * Kneel Core Runtime Exception.
 * 
 * @author e557400
 *
 */
@SuppressWarnings("serial")
public class KneelCoreException extends RuntimeException { 
 
	public KneelCoreException() {
		super();  
	}
 
	public KneelCoreException(String message) {
		super(message);  
	}

	 
	public KneelCoreException(Throwable cause) {
		super(cause);  
	}
 
	public KneelCoreException(
		String message,
		Throwable cause) {
		super(message, cause); 
	} 
}

