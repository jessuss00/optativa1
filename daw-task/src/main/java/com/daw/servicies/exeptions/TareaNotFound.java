package com.daw.servicies.exeptions;

public class TareaNotFound extends RuntimeException {
	private static final long serialVersionUID =1L;
	
	public TareaNotFound(String message) {
		super(message);
	}

}
