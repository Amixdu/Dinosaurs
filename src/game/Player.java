package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actions;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.Display;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Menu;

/**
 * Class representing the Player.
 */
public class Player extends Actor {

	/**
	 * An instance of menu class, to show the user a menu of aactions
	 */
	private Menu menu = new Menu();
	/**
	 * amount of eco points
	 */
	private int ecoPoints;

	/**
	 * Constructor.
	 *
	 * @param name        Name to call the player in the UI
	 * @param displayChar Character to represent the player in the UI
	 * @param hitPoints   Player's starting number of hitpoints
	 */
	public Player(String name, char displayChar, int hitPoints) {
		super(name, displayChar, hitPoints);
		ecoPoints = 0;
	}

	/**
	 * Select and return an action to perform on the current turn.
	 * @param actions    collection of possible Actions for this Actor
	 * @param lastAction The Action this Actor took last turn. Can do interesting things in conjunction with Action.getNextAction()
	 * @param map        the map containing the Actor
	 * @param display    the I/O object to which messages may be written
	 * @return the Action to be performed
	 */
	@Override
	public Action playTurn(Actions actions, Action lastAction, GameMap map, Display display) {
		// Handle multi-turn Actions
		if (lastAction.getNextAction() != null)
			return lastAction.getNextAction();
		return menu.showMenu(this, actions, display);
	}

	/**
	 * Getter method for retrieving current eco points
	 * @return current eco points
	 */
	public int getEcoPoints() {
		return ecoPoints;
	}

	/**
	 * Method to add eco points
	 * @param ecoPointsToAdd number of eco points to add to current amount
	 */
	public void increaseEcoPoints(int ecoPointsToAdd){
		this.ecoPoints = this.ecoPoints + ecoPointsToAdd;
	}

	/**
	 * Method to reduce eco points
	 * @param ecoPointsToReduce ecoPointsToAdd number of eco points to add to current amount
	 */
	public void reduceEcoPoints(int ecoPointsToReduce){
		this.ecoPoints = this.ecoPoints - ecoPointsToReduce;
	}
}
