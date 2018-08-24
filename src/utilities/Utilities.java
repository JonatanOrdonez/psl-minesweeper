package utilities;

import java.util.Random;

import exceptions.InputInvalidException;
import interfaces.IUtilities;

public class Utilities implements IUtilities {

	/**
	 * Constructor of Utilities class
	 */
	public Utilities() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * This method validates that the input entered by the user to generate the game is correct
	 * @param input: String entered by the user to generate the rows, columns and the number of mines
	 * @return: True if the string is in the correct format,  otherwise it returns false
	 * @throws InputInvalidException: Exception if the string does not comply with the correct format
	 */
	@Override
	public boolean inputValidForTable(String input) throws InputInvalidException {
		boolean valid = false;
		if (input == null || input.equals("")) {
			throw new InputInvalidException("The entered string does not have a valid structure");
		} else {
			String[] values = input.split(" ");
			if (values.length != 3) {
				throw new InputInvalidException("The entered string does not have a valid structure");
			} else if (!isNumber(values[0]) || !isNumber(values[1]) || !isNumber(values[2])) {
				throw new InputInvalidException("One of the arguments is not a valid number");
			} else {
				int rows = Integer.parseInt(values[0]);
				int columns = Integer.parseInt(values[1]);
				int mines = Integer.parseInt(values[2]);
				if (rows <= 0 || columns <= 0 || mines <= 0) {
					throw new InputInvalidException("Values for rows, columns and mines must be positive numbers");
				} else if ((rows * columns) < mines) {
					throw new InputInvalidException(
							"The number of mines must be less than the number of rows per columns");
				} else {
					valid = true;
				}
			}
		}
		return valid;
	}

	/**
	 * This method validates if the number of a string is an integer
	 * @param number: String with the number you want to validate
	 * @return: True if the number param is an integer, otherwise it returns false
	 */
	@Override
	public boolean isNumber(String number) {
		try {
			int num = Integer.parseInt(number);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	/**
	 * This method generates a random number between two intervals including both
	 * @param min: Lower range from which the number can be generated
	 * @param max: Higher range from which the number can be generated
	 * @return: Integer type number between the minimum and maximum range
	 */
	@Override
	public int randomNumber(int min, int max) {
		Random rand = new Random();
		return rand.nextInt((max - min) + 1) + min;
	}

	/**
	 * This method validates that the input entered by the user to execute an action on the board is correct
	 * @param input: String entered by the user to mark a mine with a flag or uncover a cell
	 * @return: True if the string is in the correct format,  otherwise it returns false
	 * @throws InputInvalidException: Exception if the string does not comply with the correct format
	 */
	@Override
	public boolean inputValidForPlay(String input) throws InputInvalidException {
		boolean valid = false;
		if (input == null || input.equals("")) {
			throw new InputInvalidException("The entered string does not have a valid structure");
		}
		else {
			String[] values = input.split(" ");
			if (values.length != 3) {
				throw new InputInvalidException("The entered string does not have a valid structure");
			}
			else if (!isNumber(values[1]) && !isNumber(values[1]) && isNumber(values[2])) {
				throw new InputInvalidException("The first and second arguments must be numbers and the third argument must be a 'U' or 'M' character");
			}
			else if (!isNumber(values[0]) || !isNumber(values[1]) || !(values[2].toUpperCase().equals("M") || values[2].toUpperCase().equals("U"))) {
				throw new InputInvalidException("The first and second arguments must be numbers and the third argument must be a 'U' or 'M' character");
			}
			else {
				int row = Integer.parseInt(values[0]);
				int column = Integer.parseInt(values[1]);
				if (row <= 0 || column <= 0) {
					throw new InputInvalidException("Values for row, column must be positive numbers");
				}
				else {
					valid = true;
				}
			}
		}
		return valid;
	}

}
