package exceptions;

public class CoordinateInvalidException extends Exception{

	/**
	 * Message of the exception
	 */
	private String message;
	
	/**
	 * Constructor of CoordinateInvalidException class
	 * @param msg: message of the exception
	 */
	public CoordinateInvalidException(String msg) {
		super(msg);
		this.message = msg;
	}
}
