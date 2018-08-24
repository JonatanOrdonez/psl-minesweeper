package logic;

import interfaces.IGame;
import models.Minesweeper;

public class Game implements IGame{

	/**
	 * Instance of Minesweeper
	 */
	private Minesweeper mw;
	
	/**
	 * Constructor of Game class
	 */
	public Game() {
		mw = new Minesweeper();
	}
	
	/**
	 * Start a minesweeper instance and run the game
	 */
	@Override
	public void startMinesweeper() {
		mw.startGame();
	}
	
	public static void main(String[] args) {
		Game gm = new Game();
		gm.startMinesweeper();
	}

}
