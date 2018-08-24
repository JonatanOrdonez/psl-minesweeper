package exceptions;

public class InputInvalidException extends Exception{

	/**
	 * Message of the exception
	 */
	private String message;
	
	/**
	 * Constructor of InputInvalidException class
	 * @param msg: message of the exception
	 */
	public InputInvalidException(String msg) {
		super(msg);
		this.message = msg;
	}
}
