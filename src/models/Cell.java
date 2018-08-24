package models;

public class Cell {

	/**
	 * Make the cell visible in the board
	 */
	private boolean visible;
	
	/**
	 * Mark when the cell has been marked with a flag
	 */
	private boolean flagged;
	
	/**
	 * value with the number of mines adjacent to the cell
	 */
	private int adyacentMines;
	
	/**
	 * Type of cell. The cell can be a mine, adjacency, disabled, not selected or marked with a flag
	 */
	private char cellType;
	
	/**
	 * Coordinate of the cell in the row
	 */
	private int x;
	
	/**
	 * Coordinate of the cell in the column
	 */
	private int y;

	/**
	 * Constants to indicate what type of cell the instance is
	 */
	public final static char DISABLE_CELL_TYPE = '-';
	public final static char MINE_CELL_TYPE = '*';
	public final static char  FLAG_CELL_TYPE = 'P';
	public final static char  UNSELECTED_CELL_TYPE = '.';
	public final static char  ADYACENT_MINE_CELL_TYPE = '#';
	
	/**
	 * Constructor of Cell class
	 * @param cellType: Type of cell. The cell can be a mine, adjacency, disabled, not selected or marked with a flag
	 * @param adyacentMines: Value with the number of mines adjacent to the cel
	 * @param x: Coordinate of the cell in the row
	 * @param y: Coordinate of the cell in the column
	 */
	public Cell(char cellType, int adyacentMines, int x, int y) {
		this.cellType = cellType;
		this.adyacentMines = adyacentMines;
		visible = false;
		flagged = false;
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Get the value of visible attribute
	 * @return visible: Value of visible attribute
	 */
	public boolean isVisible() {
		return visible;
	}

	/**
	 * This method set the value of visible attribute
	 * @param visible: Value of the new value of visible attribute
	 */
	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	/**
	 * Get the value of adyacentMines attribute
	 * @return visible: Value of adyacentMines attribute
	 */
	public int getAdyacentMines() {
		return adyacentMines;
	}

	/**
	 * This method set the value of adyacentMines attribute
	 * @param visible: Value of the new value of adyacentMines attribute
	 */
	public void setAdyacentMines(int adyacentMines) {
		this.adyacentMines = adyacentMines;
	}

	/**
	 * Get the value of cellType attribute
	 * @return visible: Value of cellType attribute
	 */
	public char getCellType() {
		return cellType;
	}

	/**
	 * This method set the value of cellType attribute
	 * @param visible: Value of the new value of cellType attribute
	 */
	public void setCellType(char cellType) {
		this.cellType = cellType;
	}
	
	/**
	 * This method increases by one unit the value of adyacentMines
	 */
	public void incrementAdyMines() {
		adyacentMines++;
	}
	
	/**
	 * Get the value of x attribute
	 * @return visible: Value of x attribute
	 */
	public int getX() {
		return x;
	}

	/**
	 * This method set the value of x attribute
	 * @param visible: Value of the new value of x attribute
	 */
	public void setX(int x) {
		this.x = x;
	}

	/**
	 * Get the value of y attribute
	 * @return visible: Value of y attribute
	 */
	public int getY() {
		return y;
	}

	/**
	 * This method set the value of y attribute
	 * @param visible: Value of the new value of y attribute
	 */
	public void setY(int y) {
		this.y = y;
	}

	/**
	 * Get the value of flagged attribute
	 * @return visible: Value of flagged attribute
	 */
	public boolean isFlagged() {
		return flagged;
	}

	/**
	 * This method set the value of flagged attribute
	 * @param visible: Value of the new value of flagged attribute
	 */
	public void setFlagged(boolean flaged) {
		this.flagged = flaged;
	}
}
