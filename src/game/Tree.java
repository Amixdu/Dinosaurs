package game;

import edu.monash.fit2099.engine.Actions;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.Location;

import java.util.Random;

public class Tree extends FruitProducer {
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
	 * grows a fruit with 50% chance
	 * drops a fruit at base of tree with 5% chance
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

		// grow fruit with 50% chance
		growFruit(50);

		// drop fruit with 5% chance at location of tree
		Fruit newFruit = dropFruit(5);
		if (newFruit != null)
			location.addItem(newFruit);
	}

	public Fruit dropFruit(int chance){
		Random rand = new Random();
		if (rand.nextInt(100) < chance && containsFruit()) {
			return new Fruit();
		}
		return null;
	}
}
