package game;

import edu.monash.fit2099.engine.Exit;
import edu.monash.fit2099.engine.Ground;
import edu.monash.fit2099.engine.Location;

import java.util.Random;

/**
 * A class that represents bare dirt.
 * @author Abhishek Shreshta
 */
public class Dirt extends Ground {

	/**
	 * Constructor
	 */
	public Dirt() {
		super('.');
	}

	/**
	 * Passage of time for dirt
	 * If current Dirt is next not next to any Tree, turns current Dirt into Bush with 0.5% chance
	 * If current Dirt is next to at least 2 bushes, turns current Dirt into Bush with 5% chance
	 * @param location The location of the Ground
	 */
	@Override
	public void tick(Location location) {
		Random random = new Random();
		super.tick(location);
		int bushCount = 0, treeCount = 0;

		// count the number of trees and bushes next to current dirt
		for (Exit exit : location.getExits()){
			char c = exit.getDestination().getGround().getDisplayChar();
			if ((c == '+') || (c == 't') || (c == 'T')){
				treeCount++;
			} else if ((c == 'b')){
				bushCount++;
			}
		}
		// if next to a tree, no chance to grow bush
		// if next to at least 2 bushes, 5% chance to grow bush
		if (bushCount> 1 && treeCount == 0){
			if (random.nextInt(100) < 5 ){
				location.setGround(new Bush());
			}
			// if not next to at least 2 bushes, 0.5% chance to grow bush
		} else if (bushCount <= 1 && treeCount == 0) {
			if (random.nextDouble() * 100 <= 0.5) {
				location.setGround(new Bush());
			}
		}
	}
}
