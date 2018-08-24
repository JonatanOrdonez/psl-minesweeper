package models;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Stack;

import exceptions.ActionInvalidException;
import exceptions.CoordinateInvalidException;
import exceptions.InputInvalidException;
import interfaces.IMinesweeper;
import utilities.Utilities;

public class Minesweeper implements IMinesweeper {

	/**
	 * Matrix with the cells that generate the game board
	 */
	private Cell[][] cells;
	
	/**
	 * Instance of Utilities class
	 */
	private Utilities utilities;

	/**
	 * Number of rows in the board
	 */
	private int rowsCount;

	/**
	 * Number of columns in the board
	 */
	private int columnsCount;
	
	/**
	 * Number of mines in the board
	 */
	private int minesCount;
	
	/**
	 * Number of flagged mines that have been correctly identified
	 */
	private int flaggedMines;

	/**
	 * HashMap with the mines in the board
	 */
	private HashMap<String, Cell> mines;
	
	/**
	 * HashMap with the numbers in the board
	 */
	private HashMap<String, Cell> numbers;
	
	/**
	 * HashMap with the disable cells in the board
	 */
	private HashMap<String, Cell> disableCells;
	
	/**
	 * State of the game
	 */
	private boolean isPlaying;
	
	/**
	 * Indicates if the user has uncovered a mine
	 */
	private boolean uncoverAMine;

	/**
	 * Constants with the actions that the user can enter to play
	 */
	public final static String UNCOVER_ACTION = "U";
	public final static String MARK_ACTION = "M";

	/**
	 * Constructor of Minesweeper class
	 */
	public Minesweeper() {
		utilities = new Utilities();
		rowsCount = columnsCount = minesCount = flaggedMines = 0;
		mines = new HashMap<String, Cell>();
		numbers = new HashMap<String, Cell>();
		disableCells = new HashMap<String, Cell>();
		isPlaying = uncoverAMine = false;
	}

	/**
	 * This method executes the reading of the user input to generate the board,
	 * fills the board with random mines, adjacencies and disabled cells. In
	 * addition, print the initial board state and finally execute a method to read
	 * the user's actions.
	 */
	@Override
	public void startGame() {
		readRCMInput();
		fillCells();
		printCells();
		play();
	}

	/**
	 * This method prints on the console a message with the name of the game and the
	 * author
	 */
	@Override
	public void initialMessage() {
		System.out.println("----------------------------------------------");
		System.out.println("/         Welcome to Minesweeper 1.0         /");
		System.out.println("/         Autor: Jonatan Ordonez             /");
		System.out.println("----------------------------------------------");
	}


	/**
	 * This method is responsible for reading the user input to generate the board,
	 * then validates if it is correct and finally initializes the variables for
	 * rows, columns and number of mines
	 */
	public void readRCMInput() {
		boolean tryAgain = true;
		BufferedReader br = null;
		String input = "";
		br = new BufferedReader(new InputStreamReader(System.in));
		try {
			initialMessage();
			System.out.println(
					"To start the game, enter the number of rows, columns and mines in the following format (r c m):");
			do {
				input = br.readLine();
				try {
					boolean valid = utilities.inputValidForTable(input);
					if (valid) {
						String[] values = input.split(" ");
						rowsCount = Integer.parseInt(values[0]);
						columnsCount = Integer.parseInt(values[1]);
						minesCount = Integer.parseInt(values[2]);
						tryAgain = false;
					}
				} catch (InputInvalidException e) {
					System.out.println(e.getMessage()
							+ ". Enter the number of rows, columns and mines in the following format (r c m):");
				}
			} while (tryAgain);
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * This method is responsible for filling the board with mines in random
	 * coordinates. In addition, for each generated mine, the calculateAdyNumbers
	 * method is executed to generate the values of the numbers adjacent to the mine.
	 */
	@Override
	public void fillCells() {
		cells = new Cell[rowsCount][columnsCount];
		boolean isGenerated = false;
		while (!isGenerated) {
			int xrand = utilities.randomNumber(0, rowsCount - 1);
			int yrand = utilities.randomNumber(0, columnsCount - 1);
			String key = xrand + " " + yrand;
			if (!mines.containsKey(key)) {
				Cell mine = new Cell(Cell.MINE_CELL_TYPE, 0, xrand, yrand);
				mines.put(key, mine);
				cells[xrand][yrand] = mine;
				calculateAdyNumbers(mine);
			}
			if (mines.size() == minesCount) {
				isGenerated = true;
			}
		}
		fillDisableCells();
	}

	/**
	 * This method calculates the number of mines adjacent to the cells
	 * @param mine: Initial mine from which to calculate the adjacent cells and their numbers
	 */
	@Override
	public void calculateAdyNumbers(Cell mine) {
		int xini = mine.getX() - 1;
		int yini = mine.getY() - 1;
		for (int i = xini; i < xini + 3; i++) {
			for (int j = yini; j < yini + 3; j++) {
				String key = i + " " + j;
				if ((i >= 0 && i < rowsCount) && (j >= 0 && j < columnsCount) && !mines.containsKey(key)) {
					if (numbers.containsKey(key)) {
						numbers.get(key).incrementAdyMines();
					} else {
						Cell ady = new Cell(Cell.ADYACENT_MINE_CELL_TYPE, 1, i, j);
						numbers.put(key, ady);
						cells[i][j] = ady;
					}
				}
			}
		}
	}

	/**
	 * This method prints the current state of the board in console
	 */
	@Override
	public void printCells() {
		String lineOne = "     ";
		String lineTwo = "   --";
		for (int i = 1; i < columnsCount + 1; i++) {
			if (i >= 0 && i < 10) {
				lineOne += i + "   ";
			} else if (i >= 10 && i < 100) {
				lineOne += i + "  ";
			}
			lineTwo += "----";
		}
		System.out.println(lineOne);
		System.out.println(lineTwo);
		for (int i = 0; i < rowsCount; i++) {
			String cdn = "  |  ";
			for (int j = 0; j < columnsCount; j++) {
				Cell cell = cells[i][j];
				if (cell.isFlagged()) {
					cdn += Cell.FLAG_CELL_TYPE + "   ";
				} else if (cell.isVisible() && cell.getCellType() == Cell.ADYACENT_MINE_CELL_TYPE) {
					cdn += cell.getAdyacentMines() + "   ";
				} else if (cell.isVisible() && cell.getCellType() == Cell.DISABLE_CELL_TYPE) {
					cdn += cell.getCellType() + "   ";
				} else if (cell.isVisible() && cell.getCellType() == Cell.MINE_CELL_TYPE) {
					cdn += cell.getCellType() + "   ";
				} else {
					cdn += Cell.UNSELECTED_CELL_TYPE + "   ";
				}
			}
			cdn += "|  " + (i + 1);
			System.out.println(cdn);
		}
		lineOne = "     ";
		lineTwo = "   --";
		for (int i = 1; i < columnsCount + 1; i++) {
			if (i >= 0 && i < 10) {
				lineOne += i + "   ";
			} else if (i >= 10 && i < 100) {
				lineOne += i + "  ";
			}
			lineTwo += "----";
		}
		System.out.println(lineTwo);
		System.out.println(lineOne);
	}

	/**
	 * This method executes a while to read the commands that the user enters to
	 * execute some action on the board. In each attempt validates that the user has
	 * entered the command with the correct structure with the method.
	 */
	@Override
	public void play() {
		isPlaying = true;
		BufferedReader br = null;
		String input = "";
		br = new BufferedReader(new InputStreamReader(System.in));
		try {
			do {
				System.out.println(
						"Enter the row, column and action to play in the following format (r c a). For the action 'a' you can enter 'U' to discover a cell or 'M' to mark a mine:");

				input = br.readLine();
				try {
					boolean valid = utilities.inputValidForPlay(input);
					if (valid) {
						String[] values = input.split(" ");
						int row = Integer.parseInt(values[0]);
						int column = Integer.parseInt(values[1]);
						String action = values[2].toUpperCase();
						if (action.equals(MARK_ACTION)) {
							try {
								flagACell(row, column);
								printCells();
							} catch (CoordinateInvalidException | ActionInvalidException e) {
								System.out.println(e.getMessage()
										+ ". Enter the row, column and action to play in the following format (r c a). For the action 'a' you can enter 'U' to discover a cell or 'M' to mark a mine:");
							}
						} else if (action.equals(UNCOVER_ACTION)) {
							try {
								uncoverACell(row, column);
								printCells();
							} catch (CoordinateInvalidException | ActionInvalidException e) {
								System.out.println(e.getMessage()
										+ ". Enter the row, column and action to play in the following format (r c a). For the action 'a' you can enter 'U' to discover a cell or 'M' to mark a mine:");
							}
						}
						if (flaggedMines == mines.size()) {
							isPlaying = false;
							System.out.println("/////////////////////////////////////////////////////////////");
							System.out.println("//  CONGRATULATIONS, YOU HAVE DISCOVERED ALL THE MINES!!!  //");
							System.out.println("/////////////////////////////////////////////////////////////");
						} else if (uncoverAMine) {
							isPlaying = false;
							System.out.println("/////////////////////////////////////////////////////////////");
							System.out.println("//           YOU LOST, GOOD LUCK FOR THE NEXT!!!           //");
							System.out.println("/////////////////////////////////////////////////////////////");
						}
					}
				} catch (InputInvalidException e) {
					System.out.println(e.getMessage()
							+ ". Enter the row, column and action to play in the following format (r c a). For the action 'a' you can enter 'U' to discover a cell or 'M' to mark a mine:");
				}
			} while (isPlaying);
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * This method generates cells that will not contain mines or numbers with
	 * adjacencies
	 */
	@Override
	public void fillDisableCells() {
		for (int i = 0; i < rowsCount; i++) {
			for (int j = 0; j < columnsCount; j++) {
				Cell cell = cells[i][j];
				if (cell == null) {
					String key = i + " " + j;
					Cell newDisableCell = new Cell(Cell.DISABLE_CELL_TYPE, 0, i, j);
					disableCells.put(key, newDisableCell);
					cells[i][j] = newDisableCell;
				}
			}
		}
	}

	/**
	 * This method marks a cell with a flag from the user input
	 * @param x: Coordinate in the row
	 * @param y: Coordinate in the column
	 * @throws CoordinateInvalidException: Exception if the coordinate is outside the board
	 * @throws ActionInvalidException: Exception if the cell can not be marked with a flag
	 */
	@Override
	public void flagACell(int x, int y) throws CoordinateInvalidException, ActionInvalidException {
		if ((x > 0 && x <= rowsCount) && (y > 0 && y <= columnsCount)) {
			int realx = x - 1;
			int realy = y - 1;
			String key = realx + " " + realy;
			Cell cell = cells[realx][realy];
			if (cell.isFlagged()) {
				throw new ActionInvalidException("The cell has already been marked with a flag");
			} else if (mines.containsKey(key)) {
				flaggedMines++;
				mines.get(key).setFlagged(true);
			} else if (numbers.containsKey(key) && numbers.get(key).isVisible()) {
				throw new ActionInvalidException("The number can not be marked with a flag");
			} else if (disableCells.containsKey(key) && disableCells.get(key).isVisible()) {
				throw new ActionInvalidException("The cell is disabled and can not be marked with a flag");
			} else {
				cells[realx][realy].setFlagged(true);
			}
		} else {
			throw new CoordinateInvalidException("The coordinate is out of range");
		}
	}

	/**
	 * This method uncover a cell from the user input
	 * @param x: Coordinate in the row
	 * @param y: Coordinate in the column
	 * @throws CoordinateInvalidException: Exception if the coordinate is outside the board
	 * @throws ActionInvalidException: Exception if the cell can not be uncovered
	 */
	@Override
	public void uncoverACell(int x, int y) throws CoordinateInvalidException, ActionInvalidException {
		if ((x > 0 && x <= rowsCount) && y > 0 && y <= columnsCount) {
			int realx = x - 1;
			int realy = y - 1;
			String key = realx + " " + realy;
			Cell cell = cells[realx][realy];
			if (mines.containsKey(key)) {
				uncoverAMine = true;
				uncoverMines();
			} else if (cell.isVisible() && numbers.containsKey(key)) {
				throw new ActionInvalidException("The cell is already uncovered");
			} else if (cell.isVisible() && disableCells.containsKey(key)) {
				throw new ActionInvalidException("The cell is already uncovered");
			} else {
				uncoverCells(realx, realy);
			}
		} else {
			throw new CoordinateInvalidException("The coordinate is out of range");
		}
	}

	/**
	 * this method makes all mines visible when the user has uncovered a mine
	 */
	@Override
	public void uncoverMines() {
		for (String key : mines.keySet()) {
			mines.get(key).setVisible(true);
		}
	}

	/**
	 * When the cell that is being uncovered is an empty or adjacency cell type,
	 * this method makes visible all the cells around that are not mines until
	 * reaching a cell that is an adjacency
	 * @param x: Coordinate in the row
	 * @param y: Coordinate in the column
	 */
	@Override
	public void uncoverCells(int x, int y) {
		Stack<Cell> stack = new Stack<Cell>();
		HashMap<String, String> trace = new HashMap<String, String>();
		stack.push(cells[x][y]);
		while (!stack.isEmpty()) {
			Cell cell = stack.pop();
			int xini = cell.getX() - 1;
			int yini = cell.getY() - 1;
			for (int i = xini; i < xini + 3; i++) {
				for (int j = yini; j < yini + 3; j++) {
					String key = i + " " + j;
					if ((i >= 0 && i < rowsCount) && (j >= 0 && j < columnsCount)) {
						Cell selectedCell = cells[i][j];
						if (!mines.containsKey(key)) {
							if (!trace.containsKey(key)) {
								if (selectedCell.isFlagged()) {
									selectedCell.setFlagged(false);
									selectedCell.setVisible(true);
								} else if (selectedCell.getCellType() == Cell.ADYACENT_MINE_CELL_TYPE) {
									selectedCell.setVisible(true);
								} else {
									selectedCell.setVisible(true);
									stack.push(selectedCell);
								}
								trace.put(key, key);
							}
						}
					}
				}
			}
		}
	}
	
	/**
	 * Get the value of cells attribute
	 * @return visible: Value of cells attribute
	 */
	public Cell[][] getCells() {
		return cells;
	}

	/**
	 * This method set the value of cells attribute
	 * @param visible: Value of the new value of cells attribute
	 */
	public void setCells(Cell[][] cells) {
		this.cells = cells;
	}

	/**
	 * Get the value of utilities attribute
	 * @return visible: Value of utilities attribute
	 */
	public Utilities getUtilities() {
		return utilities;
	}

	/**
	 * This method set the value of utilities attribute
	 * @param visible: Value of the new value of utilities attribute
	 */
	public void setUtilities(Utilities utilities) {
		this.utilities = utilities;
	}
	
	/**
	 * Get the value of rowsCount attribute
	 * @return visible: Value of rowsCount attribute
	 */
	public int getRowsCount() {
		return rowsCount;
	}

	/**
	 * This method set the value of rowsCount attribute
	 * @param visible: Value of the new value of rowsCount attribute
	 */
	public void setRowsCount(int rowsCount) {
		this.rowsCount = rowsCount;
	}

	/**
	 * Get the value of columnsCount attribute
	 * @return visible: Value of columnsCount attribute
	 */
	public int getColumnsCount() {
		return columnsCount;
	}

	/**
	 * This method set the value of columnsCount attribute
	 * @param visible: Value of the new value of columnsCount attribute
	 */
	public void setColumnsCount(int columnsCount) {
		this.columnsCount = columnsCount;
	}

	/**
	 * Get the value of minesCount attribute
	 * @return visible: Value of minesCount attribute
	 */
	public int getMinesCount() {
		return minesCount;
	}

	/**
	 * This method set the value of minesCount attribute
	 * @param visible: Value of the new value of minesCount attribute
	 */
	public void setMinesCount(int minesCount) {
		this.minesCount = minesCount;
	}
	
	/**
	 * Get the value of flaggedMines attribute
	 * @return visible: Value of flaggedMines attribute
	 */
	public int getFlaggedMines() {
		return flaggedMines;
	}

	/**
	 * This method set the value of flaggedMines attribute
	 * @param visible: Value of the new value of flaggedMines attribute
	 */
	public void setFlaggedMines(int flagedMines) {
		this.flaggedMines = flagedMines;
	}

	/**
	 * Get the value of mines attribute
	 * @return visible: Value of mines attribute
	 */
	public HashMap<String, Cell> getMines() {
		return mines;
	}

	/**
	 * This method set the value of mines attribute
	 * @param visible: Value of the new value of mines attribute
	 */
	public void setMines(HashMap<String, Cell> mines) {
		this.mines = mines;
	}

	/**
	 * Get the value of numbers attribute
	 * @return visible: Value of numbers attribute
	 */
	public HashMap<String, Cell> getNumbers() {
		return numbers;
	}

	/**
	 * This method set the value of numbers attribute
	 * @param visible: Value of the new value of numbers attribute
	 */
	public void setNumbers(HashMap<String, Cell> numbers) {
		this.numbers = numbers;
	}

	/**
	 * Get the value of disableCells attribute
	 * @return visible: Value of disableCells attribute
	 */
	public HashMap<String, Cell> getDisableCells() {
		return disableCells;
	}

	/**
	 * This method set the value of disableCells attribute
	 * @param visible: Value of the new value of disableCells attribute
	 */
	public void setDisableCells(HashMap<String, Cell> disableCells) {
		this.disableCells = disableCells;
	}

	/**
	 * Get the value of isPlaying attribute
	 * @return visible: Value of isPlaying attribute
	 */
	public boolean isPlaying() {
		return isPlaying;
	}

	/**
	 * This method set the value of isPlaying attribute
	 * @param visible: Value of the new value of isPlaying attribute
	 */
	public void setPlaying(boolean isPlaying) {
		this.isPlaying = isPlaying;
	}

	/**
	 * Get the value of uncoverAMine attribute
	 * @return visible: Value of uncoverAMine attribute
	 */
	public boolean isUncoverAMine() {
		return uncoverAMine;
	}

	/**
	 * This method set the value of uncoverAMine attribute
	 * @param visible: Value of the new value of uncoverAMine attribute
	 */
	public void setUncoverAMine(boolean uncoverAMine) {
		this.uncoverAMine = uncoverAMine;
	}
}
