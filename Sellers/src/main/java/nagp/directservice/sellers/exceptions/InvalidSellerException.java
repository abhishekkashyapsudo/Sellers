package nagp.directservice.sellers.exceptions;

public class InvalidSellerException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6170196487063827224L;
	private static final String ERROR_MESSAGE = "No seller exists with the passed seller id";
	public InvalidSellerException(String msg) {
		super(ERROR_MESSAGE +":" + msg);
	}
	
	public InvalidSellerException() {
		super(ERROR_MESSAGE);
	}
}
