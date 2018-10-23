package com.moon.pf.exception;

public class PasswordMissMatchException extends Exception{
	private String message;
	
	public PasswordMissMatchException() {
		this.message = "비밀번호가 일치하지 않습니다.";
	}
	
	public PasswordMissMatchException(String message) {
		this.message = message;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}
}
