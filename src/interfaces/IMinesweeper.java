package interfaces;

import java.io.IOException;

import exceptions.ActionInvalidException;
import exceptions.CoordinateInvalidException;
import models.Cell;

public interface IMinesweeper {

	/**
	 * This method executes the reading of the user input to generate the board, fills the board with 
	 * random mines, adjacencies and disabled cells. In addition, print the initial board state and 
	 * finally execute a method to read the user's actions.
	 */
	public void startGame();
	
	/**
	 * This method prints on the console a message with the name of the game and the author
	 */
	public void initialMessage();
	
	/**
	 * This method is responsible for reading the user input to generate the board,
	 * then validates if it is correct and finally initializes the variables for
	 * rows, columns and number of mines
	 */
	public void readRCMInput() throws IOException;
	
	/**
	 * This method is responsible for filling the board with mines in random coordinates. In addition,
	 * for each generated mine, the calculateAdyNumbers method is executed to generate the values of the 
	 * numbers adjacent to the mine.
	 */
	public void fillCells();
	
	/**
	 * This method calculates the number of mines adjacent to the cells
	 * @param mine: Initial mine from which to calculate the adjacent cells and their numbers
	 */
	public void calculateAdyNumbers(Cell mine);
	
	/**
	 * This method prints the current state of the board in console
	 */
	public void printCells();
	
	/**
	 * This method executes a while to read the commands that the user enters to
	 * execute some action on the board. In each attempt validates that the user has
	 * entered the command with the correct structure with the method.
	 */
	public void play() throws IOException;
	
	/**
	 * This method generates cells that will not contain mines or numbers with adjacencies
	 */
	public void fillDisableCells();
	
	/**
	 * This method marks a cell with a flag from the user input
	 * @param x: Coordinate in the row
	 * @param y: Coordinate in the column
	 * @throws CoordinateInvalidException: Exception if the coordinate is outside the board
	 * @throws ActionInvalidException: Exception if the cell can not be marked with a flag
	 */
	public void flagACell(int x, int y) throws CoordinateInvalidException, ActionInvalidException;
	
	/**
	 * This method uncover a cell from the user input
	 * @param x: Coordinate in the row
	 * @param y: Coordinate in the column
	 * @throws CoordinateInvalidException: Exception if the coordinate is outside the board
	 * @throws ActionInvalidException: Exception if the cell can not be uncovered
	 */
	public void uncoverACell(int x, int y) throws CoordinateInvalidException, ActionInvalidException;
	
	/**
	 * this method makes all mines visible when the user has uncovered a mine
	 */
	public void uncoverMines();
	
	/**
	 * When the cell that is being uncovered is an empty or adjacency cell type, this method makes 
	 * visible all the cells around that are not mines until reaching a cell that is an adjacency
	 * @param x: Coordinate in the row
	 * @param y: Coordinate in the column
	 */
	public void uncoverCells(int x, int y);
	
}
