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
public class Stegosaur extends Actor {
	// Will need to change this to a collection if Stegosaur gets additional Behaviours.
	private Behaviour wBehaviour;
	private Behaviour hBehaviour;
	int foodLevel;
	int unconsciousCount;

	/**
	 * Constructor.
	 * All Stegosaurs are represented by a 'd' and have 100 hit points.
	 *
	 * @param name the name of this Stegosaur
	 */
	public Stegosaur(String name) {
		super(name, 'd', 100);

		wBehaviour = new WanderBehaviour();
		hBehaviour = new SeekFruitBehaviour();
		foodLevel = 50;
		unconsciousCount = 0;
	}

	@Override
	public Actions getAllowableActions(Actor otherActor, String direction, GameMap map) {
		Actions actions = new Actions();
		actions.add(new AttackAction(this));
		actions.add(new FeedStegosaurAction(this));
		actions.add(new FeedStegasourMealKitAction(this));
		return actions;
	}

	/**
	 * Figure out what to do next.
	 *
	 * FIXME: Stegosaur wanders around at random, or if no suitable MoveActions are available, it
	 * just stands there.  That's boring.
	 *
	 * @see edu.monash.fit2099.engine.Actor#playTurn(Actions, Action, GameMap, Display)
	 */
	@Override
	public Action playTurn(Actions actions, Action lastAction, GameMap map, Display display) {

//		System.out.println(this.foodLevel);

		// reduce food level each turn
		if (this.foodLevel > 0){
			this.foodLevel = this.foodLevel - 1;
			if (this.foodLevel < 7){
				System.out.println("Stegosaur at (" + map.locationOf(this).x() + "," + map.locationOf(this).y() + ") is hungry!");
				Action wander = hBehaviour.getAction(this, map);
				if (wander != null)
					return wander;
				else{
					return new DoNothingAction();
				}
			}
			else{
				Action wander = wBehaviour.getAction(this, map);
				if (wander != null)
					return wander;
				else{
					return new DoNothingAction();
				}
			}
		}
		else {
			if (unconsciousCount < 5){
				this.unconsciousCount += 1;
				System.out.println("Stegosaur at (" + map.locationOf(this).x() + "," + map.locationOf(this).y() + ") is unconscious!");
			}
			else {
				System.out.println("Stegosaur at (" + map.locationOf(this).x() + "," + map.locationOf(this).y() + ") died  due to lack of food!");
				map.removeActor(this);
			}

			return new DoNothingAction();
		}



//		return new DoNothingAction();
	}

	public String getName(){
		return this.name;
	}

	public int getFoodLevel() {
		return foodLevel;
	}

	public void increaseFoodLevel(int amount){
		this.foodLevel += amount;
		if (this.foodLevel > 100){
			this.foodLevel = 100;
		}
	}

}
