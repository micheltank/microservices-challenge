package br.com.micheltank.challenge.rest;

public class Error {

	private String message;
	
	public Error(String message) {
		this.message = message;	
	}
	
	public String getMessage() {
		return message;
	}
}
