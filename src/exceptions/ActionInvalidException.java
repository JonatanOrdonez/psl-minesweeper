package exceptions;

public class ActionInvalidException extends Exception{

	/**
	 * Message of the exception
	 */
	private String message;
	
	/**
	 * Constructor of ActionInvalidException class
	 * @param msg: message of the exception
	 */
	public ActionInvalidException(String msg) {
		super(msg);
		this.message = msg;
	}
}
