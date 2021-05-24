package game;

import edu.monash.fit2099.engine.*;

/**
 * Class representing the Player.
 * @author Amindu Kaushal Kumarasinghe
 * @author Abhishek Shrestha
 */
public class Player extends Actor {

	/**
	 * An instance of menu class, to show the user a menu of aactions
	 */
	private Menu menu = new Menu();
	/**
	 * amount of eco points
	 */
	private static int ecoPoints = 0;

	/**
	 * Target Ecopoints - Only for Challenge Mode
	 */
	private int targetEcoPoints;

	/**
	 * Target number of moves
	 * Only For Challenge Mode
	 */
	private int targetMoves;

	/**
	 * Number of turns
	 */
	private int noOfMoves;

	/**
	 * Constructor.
	 *
	 * @param name        Name to call the player in the UI
	 * @param displayChar Character to represent the player in the UI
	 * @param hitPoints   Player's starting number of hitpoints
	 */
	public Player(String name, char displayChar, int hitPoints) {
		super(name, displayChar, hitPoints);
	}

	/**
	 * Select and return an action to perform on the current turn.
	 * allows player to quit game
	 * if challenge mode and target moves has been reached, the game ends (with win/lose msg)
	 * @param actions    collection of possible Actions for this Actor
	 * @param lastAction The Action this Actor took last turn. Can do interesting things in conjunction with Action.getNextAction()
	 * @param map        the map containing the Actor
	 * @param display    the I/O object to which messages may be written
	 * @return the Action to be performed
	 */
	@Override
	public Action playTurn(Actions actions, Action lastAction, GameMap map, Display display) {
		noOfMoves++;
		// check if number of turns reached in challenge mode
		if (hasCapability(GameMode.CHALLENGE) && noOfMoves == targetMoves + 1){
			System.out.printf("Target ecoPoints: %d\nAcquired ecoPoints: %d\nSet number of moves: %d\n",
					targetEcoPoints, ecoPoints, noOfMoves-1);
			if (targetEcoPoints <= ecoPoints){
				System.out.println("You won the Challenge!");
			} else {
				System.out.println("You lost the Challenge");
			}
			return new QuitGameAction();
		}

		// Let player quit game
		actions.add(new QuitGameAction());

		// Handle multi-turn Actions
		if (lastAction.getNextAction() != null)
			return lastAction.getNextAction();
		// if player is on top of bush or tree
		Ground currentGround = map.locationOf(this).getGround();
		if (currentGround.hasCapability(ProduceFruit.YES)) {
			actions.add(new SearchFruitAction((FruitProducer) currentGround));
		}
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
	public static void increaseEcoPoints(int ecoPointsToAdd){
		Player.ecoPoints = Player.ecoPoints + ecoPointsToAdd;
//		System.out.printf("%d ecoPoints have been added\n", ecoPointsToAdd);
	}

	/**
	 * Method to reduce eco points
	 * eco points cannot be negative
	 * @param ecoPointsToReduce ecoPointsToAdd number of eco points to add to current amount
	 */
	public static void reduceEcoPoints(int ecoPointsToReduce){
		int newEcoPoints = Player.ecoPoints - ecoPointsToReduce;
		Player.ecoPoints = Math.min(newEcoPoints, 0);
		System.out.printf("%d ecoPoints have been reduced\n", ecoPointsToReduce);
	}

	public void setTargetEcoPoints(int targetEcoPoints) {
		if (targetEcoPoints > 0)
			this.targetEcoPoints = targetEcoPoints;
	}

	public void setTargetMoves(int targetMoves) {
		this.targetMoves = targetMoves;
	}
}
