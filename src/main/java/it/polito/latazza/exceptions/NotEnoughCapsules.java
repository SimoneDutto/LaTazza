package it.polito.latazza.exceptions;

public class NotEnoughCapsules extends Exception {

	private static final long serialVersionUID = 1L;
    
    public NotEnoughCapsules(String message) {
		super(message);
	}
}
