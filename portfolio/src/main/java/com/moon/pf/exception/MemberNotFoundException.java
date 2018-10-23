package com.moon.pf.exception;

public class MemberNotFoundException extends Exception{
	private String message;
	
	public MemberNotFoundException() {
		this.message = "사용자가 없습니다.";
	}
	
	public MemberNotFoundException(String message) {
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
