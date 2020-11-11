package com.mybank.app.exception;

public class AccountInfoNotFoundException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public AccountInfoNotFoundException(String message) {
		super(message);
	}

}
