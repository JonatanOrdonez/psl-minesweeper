package interfaces;

import exceptions.InputInvalidException;

public interface IUtilities {

	/**
	 * This method validates that the input entered by the user to generate the game is correct
	 * @param input: String entered by the user to generate the rows, columns and the number of mines
	 * @return: True if the string is in the correct format,  otherwise it returns false
	 * @throws InputInvalidException: Exception if the string does not comply with the correct format
	 */
	public boolean inputValidForTable(String input) throws InputInvalidException;
	
	/**
	 * This method validates if the number of a string is an integer
	 * @param number: String with the number you want to validate
	 * @return: True if the number param is an integer, otherwise it returns false
	 */
	public boolean isNumber(String number);
	
	/**
	 * This method generates a random number between two intervals including both
	 * @param min: Lower range from which the number can be generated
	 * @param max: Higher range from which the number can be generated
	 * @return: Integer type number between the minimum and maximum range
	 */
	public int randomNumber(int min, int max);
	
	/**
	 * This method validates that the input entered by the user to execute an action on the board is correct
	 * @param input: String entered by the user to mark a mine with a flag or uncover a cell
	 * @return: True if the string is in the correct format,  otherwise it returns false
	 * @throws InputInvalidException: Exception if the string does not comply with the correct format
	 */
	public boolean inputValidForPlay(String input) throws InputInvalidException;
}
