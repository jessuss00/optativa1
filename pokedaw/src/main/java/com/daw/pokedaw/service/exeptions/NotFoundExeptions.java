package com.daw.pokedaw.service.exeptions;

public class NotFoundExeptions extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public NotFoundExeptions(String message) {
		super(message);
	}

}
