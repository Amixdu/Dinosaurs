package game;

import edu.monash.fit2099.engine.Ground;
import edu.monash.fit2099.engine.Location;

public class Tree extends Ground {
	/**
	 * age of tree
	 */
	private int age = 0;

	/**
	 * Constructor
	 */
	public Tree() {
		super('+');
	}

	/**
	 * Passage of time for the tree
	 * @param location The location of the Ground
	 */
	@Override
	public void tick(Location location) {
		super.tick(location);

		age++;
		if (age == 10)
			displayChar = 't';
		if (age == 20)
			displayChar = 'T';
	}
}
