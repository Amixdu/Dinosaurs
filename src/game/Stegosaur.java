package game;


import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actions;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.Display;
import edu.monash.fit2099.engine.DoNothingAction;
import edu.monash.fit2099.engine.GameMap;

/**
 * A herbivorous dinosaur.
 *
 */
public class Stegosaur extends VegetarianDinosaur {
	/**
	 * A counter to make sure that once this Stegosaur has been attacked, it cannot be attacked again for 20 more rounds
	 */
	private int attackCoolDown;


	/**
	 * Constructor.
	 * All Stegosaurs are represented by a 'S' and have 100 hit points.
	 * @param name the name of this Stegosaur
	 */
	public Stegosaur(String name) {
		super(name, 'S', 50, 100, 20, 90);
		// starting with 20, so initial attack will be possible
		attackCoolDown = 20;

	}

	/**
	 * Updates attackCoolDown counter and calls super
	 *
	 * @param actions    collection of possible Actions for this Actor
	 * @param lastAction The Action this Actor took last turn. Can do interesting things in conjunction with Action.getNextAction()
	 * @param map        the map containing the Actor
	 * @param display    the I/O object to which messages may be written
	 * @return the Action to be performed
	 */
	@Override
	public Action playTurn(Actions actions, Action lastAction, GameMap map, Display display) {
		attackCoolDown = attackCoolDown + 1;
		return super.playTurn(actions, lastAction, map, display);
	}

	@Override
	public Actions getAllowableActions(Actor otherActor, String direction, GameMap map) {
		Actions actions = super.getAllowableActions(otherActor, direction, map);
		actions.add(new AttackAction(this));
		return actions;
	}

	/**
	 * Getter method to obtain current cool down counter
	 * @return
	 */
	public int getAttackCoolDown() {
		return attackCoolDown;
	}

	/**
	 * Method to reset attack cool down
	 */
	public void resetAttackCoolDown() {
		this.attackCoolDown = 0;
	}
}
