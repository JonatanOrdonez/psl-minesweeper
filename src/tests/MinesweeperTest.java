package tests;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.Test;

import exceptions.ActionInvalidException;
import exceptions.CoordinateInvalidException;
import models.Cell;
import models.Minesweeper;
import utilities.Utilities;

public class MinesweeperTest {

	/**
	 * Instance of Minesweeper class
	 */
	private Minesweeper minesweeper;
	
	/**
	 * Instance of Utilities class
	 */
	private Utilities utilities;
	
	/**
	 * Matrix with the cells that generate the game board
	 */
	public MinesweeperTest() {
		minesweeper = new Minesweeper();
		utilities = new Utilities();
	}
	
	/**
	 * This method generate a Minesweeper board with 3 rows, 3 columns and 2 mines
	 */
	public void executeEnvironment() {
		int x = 3;
		int y = 3;
		int m = 2;
		minesweeper.setRowsCount(x);
		minesweeper.setColumnsCount(y);
		minesweeper.setMinesCount(m);
		minesweeper.fillCells();
	}
	
	/**
	 * This method validate that the number of cells with adjacencies were generated 
	 * correctly and with the real values
	 */
	@Test
	public void flagACellTest() {
		// Execution of the generation of the environment
		executeEnvironment();
		
		// Print the board
		//print();
		
		// Get the HashMap mines
		HashMap<String, Cell> mineshm = minesweeper.getMines();
		
		// Initialization of an arraylist and storage of the hashmap mines in the arraylist
		ArrayList<Cell> mines = new ArrayList<Cell>();
		for (String key : mineshm.keySet()) {
			mines.add(mineshm.get(key));
		}
		
		// Get the first mine and it's coords
		Cell mine1 = mines.get(0);
		int x1 = mine1.getX() + 1;
		int y1 = mine1.getY() + 1;
		
		// Execution for flag a cell in the coords
		try {
			minesweeper.flagACell(x1, y1);
		} catch (CoordinateInvalidException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		} catch (ActionInvalidException e) {
			System.out.println(e.getMessage());
		}
		
		//Validation
		assertTrue("The number of mines marked with a flag was not increased", minesweeper.getFlaggedMines() == 1);
		
		// Get the second mine and it's coords
		Cell mine2 = mines.get(1);
		x1 = mine2.getX() + 1;
		y1 = mine2.getY() + 1;
		
		// Execution for flag a cell in the coords
		try {
			minesweeper.flagACell(x1, y1);
		} catch (CoordinateInvalidException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		} catch (ActionInvalidException e) {
			System.out.println(e.getMessage());
		}
		
		//Validation
		assertTrue("The number of mines marked with a flag was not increased", minesweeper.getFlaggedMines() == 2);
		
		// Get a non mine cell and it's coords
		String[] coords = getCoords(mines).split(" ");
		x1 = Integer.parseInt(coords[0]);
		y1 = Integer.parseInt(coords[1]);
		
		// Execution for flag a cell in the coords
		try {
			minesweeper.flagACell(x1, y1);
		} catch (CoordinateInvalidException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		} catch (ActionInvalidException e) {
			System.out.println(e.getMessage());
		}
		
		//Validation
		assertTrue("The number of mines marked with a flag was increased", minesweeper.getFlaggedMines() == 2);
	}
	
	@Test
	public void uncoverACellTest() {
		// Execution of the generation of the environment
		executeEnvironment();
		
		// Print the board
		//print();
		
		// Get the HashMap mines
		HashMap<String, Cell> mineshm = minesweeper.getMines();
		
		// Initialization of an arraylist and storage of the hashmap mines in the arraylist
		ArrayList<Cell> mines = new ArrayList<Cell>();
		for (String key : mineshm.keySet()) {
			mines.add(mineshm.get(key));
		}
		
		// Get a non mine cell and it's coords
		String[] coords = getCoords(mines).split(" ");
		int x = Integer.parseInt(coords[0]);
		int y = Integer.parseInt(coords[1]);
		
		// Execution for uncover a cell
		try {
			minesweeper.uncoverACell(x, y);
		} catch (CoordinateInvalidException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ActionInvalidException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// Validation
		String key = x + " " + y;
		if (!minesweeper.getMines().containsKey(key)) {
			assertTrue("The uncovered position is a mine", !minesweeper.isUncoverAMine());
		}
		else {
			assertTrue("The uncovered position is not a mine", minesweeper.isUncoverAMine());
		}
	
		// Get a mine cell and it's coords
		x = mines.get(0).getX() + 1;
		y =  mines.get(0).getY() + 1;
		
		// Execution for uncover a cell
		try {
			minesweeper.uncoverACell(x, y);
		} catch (CoordinateInvalidException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ActionInvalidException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// Validation
		assertTrue("The uncovered position is not a mine", minesweeper.isUncoverAMine());
	}
	
	/**
	 * This method print in console a minesweeper board
	 */
	private void print() {
		for (int i = 0; i < minesweeper.getRowsCount(); i++) {
			String cdn = "";
			for (int j = 0; j < minesweeper.getColumnsCount(); j++) {
				Cell cell = minesweeper.getCells()[i][j];
				if (cell.getCellType() == Cell.ADYACENT_MINE_CELL_TYPE) {
					cdn += cell.getAdyacentMines() + " ";
				} else if (cell.getCellType() == Cell.MINE_CELL_TYPE) {
					cdn += cell.getCellType() + " ";
				} else {
					cdn += "-  ";
				}
			}
			System.out.println(cdn);
		}
	}
	
	/**
	 * This method gets a coordinate on the map where there are no mines
	 * @param mines
	 * @return: String with the two coords separated by a blank space
	 */
	private String getCoords(ArrayList<Cell> mines) {
		boolean find = false;
		String coords = "";
		while(!find) {
			int xran = utilities.randomNumber(1, minesweeper.getRowsCount());
			int yran = utilities.randomNumber(1, minesweeper.getColumnsCount());
			boolean fn = false;
			for (int i = 0; i < mines.size() && !fn; i++) {
				Cell mine = mines.get(i);
				if((xran == mine.getX()) && (yran == mine.getY())) {
					fn = true;
				}
			}
			if (!fn) {
				coords = xran + " " + yran;
				find = true;
			}
		}
		return coords;
	}

}
