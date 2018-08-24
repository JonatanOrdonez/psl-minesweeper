package tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.Test;

import exceptions.InputInvalidException;
import utilities.Utilities;

public class UtilitiesTest {

	/**
	 * Instance of Utilities Class
	 */
	private Utilities utilities;
	
	/**
	 * Constructor of UtilitiesTest class
	 */
	public UtilitiesTest() {
		utilities = new Utilities();
	}
	
	/**
	 * This method generate a HashMap with inputs as keys and true or false as values
	 * @return: HashMap with inputs and responses
	 */
	public HashMap<String, Boolean> inputListOne() {
		HashMap<String, Boolean> values = new HashMap<String, Boolean>();
		values.put("123 123 123", true);
		values.put("-1 - 1 -1", false);
		values.put("@!#!#QWEQWEQW#!#", false);
		values.put("3 2 6", true);
		values.put("3 2 7", false);
		values.put("3 2 7", false);
		return values;
	}
	
	/**
	 * This method generate a HashMap with inputs as keys and true or false as values
	 * @return: HashMap with inputs and responses
	 */
	public HashMap<String, Boolean> inputListTwo() {
		HashMap<String, Boolean> values = new HashMap<String, Boolean>();
		values.put("123 123 325", false);
		values.put("-1 - 1 -1", false);
		values.put("@!#!#QWEQWEQW#!#", false);
		values.put("3 2 7", true);
		values.put("1 1 M", true);
		values.put("1 1 U", true);
		values.put("U 1 U", false);
		values.put("U U U", false);
		return values;
	}
	
	/**
	 * This test verifies that inputValidForTable(key) method response the correct answer to a list of inputs
	 * This test validate that  method response
	 */
	@Test
	public void testInputValidForTable() {
		HashMap<String, Boolean> values = inputListOne();
		for (String key : values.keySet()) {
			boolean value = values.get(key);
			if (value) {
				try {
					assertTrue("the input is not correctly validate", utilities.inputValidForTable(key));
				} catch (InputInvalidException e) {
					//System.out.println(e.getMessage());
				}
			}
			else {
				try {
					assertFalse("the input is not correctly validate", utilities.inputValidForTable(key));
				} catch (InputInvalidException e) {
					//System.out.println(e.getMessage());
				}
			}
		}
	}
	
	/**
	 * This test verifies that inputValidForPlay(key) method response the correct answer to a list of inputs
	 * This test validate that  method response
	 */
	@Test
	public void testInputValidForPlay() {
		HashMap<String, Boolean> values = inputListTwo();
		for (String key : values.keySet()) {
			boolean value = values.get(key);
			if (value) {
				try {
					assertTrue("the input is not correctly validate", utilities.inputValidForPlay(key));
				} catch (InputInvalidException e) {
					//System.out.println(e.getMessage());
				}
			}
			else {
				try {
					System.out.println(key);
					assertFalse("the input is not correctly validate", utilities.inputValidForPlay(key));
				} catch (InputInvalidException e) {
					System.out.println(e.getMessage());
				}
			}
		}
	}
}
