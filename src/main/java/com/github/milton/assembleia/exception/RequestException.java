package com.github.milton.assembleia.exception;

public class RequestException extends RuntimeException{

	private static final long serialVersionUID = -3913849328177463713L;

	public RequestException(String message) {
		super(message);
	}
	
	public RequestException(String message, Throwable cause) {
		
	} 
}
