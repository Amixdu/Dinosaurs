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
	// Will need to change this to a collection if Stegosaur gets additional Behaviours.


	/**
	 * Constructor.
	 * All Stegosaurs are represented by a 'd' and have 100 hit points.
	 *
	 * @param name the name of this Stegosaur
	 */
	public Stegosaur(String name, char displayChar) {
		super(name, displayChar, 50, 100, 20, 90, 20);

	}





}
