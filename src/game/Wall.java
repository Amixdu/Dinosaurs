package game;

import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.Ground;

/**
 * Wall Class to represent a wall
 */
public class Wall extends Ground {

	/**
	 * Constructor
	 */
	public Wall() {
		super('#');
	}

	/**
	 * To define if the actor can enter a wall
	 * @param actor the Actor to check
	 * @return whether an actor can go over walls
	 */
	@Override
	public boolean canActorEnter(Actor actor) {
		return false;
	}

	/**
	 * to define if a wall can block any thrown object
	 * @return true
	 */
	@Override
	public boolean blocksThrownObjects() {
		return true;
	}
}
