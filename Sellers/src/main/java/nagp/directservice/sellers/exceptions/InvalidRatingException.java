package nagp.directservice.sellers.exceptions;

public class InvalidRatingException extends RuntimeException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6170196487063827224L;
	private static final String ERROR_MESSAGE = "Seller rating must be between 0 - 10.";
	public InvalidRatingException(String msg) {
		super(msg);
	}
	
	public InvalidRatingException() {
		super(ERROR_MESSAGE);
	}

}
