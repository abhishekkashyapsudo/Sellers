package nagp.directservice.sellers.exceptions;

public class InvalidCategoryException extends RuntimeException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6170196487063827224L;
	private static final String ERROR_MESSAGE = "Service type do not belongs to the selected category.";
	public InvalidCategoryException(String msg) {
		super(msg);
	}
	
	public InvalidCategoryException() {
		super(ERROR_MESSAGE);
	}

}
