package game;

import edu.monash.fit2099.engine.*;

public class VegetarianDinosaur extends Actor {
    private int unconsciousCount;
    private int maxUconsciousRounds;
    private int hungerAmount;
    private Behaviour wBehaviour;
    private Behaviour hBehaviour;
    /**
     * Constructor.
     *
     * @param name        the name of the Actor
     * @param displayChar the character that will represent the Actor in the display
     * @param startingHitPoints   the Actor's starting hit points
     * @param hungerAmount amount below which a dinosaur is hungry
     * @param maxHitPoints maximum hit points
     * @param maxUnconsciousRounds number of rounds the dinosaur can stay unconscjious before dying
     *
     */
    public VegetarianDinosaur(String name, char displayChar, int startingHitPoints, int maxHitPoints, int maxUnconsciousRounds, int hungerAmount) {
        super(name, displayChar, maxHitPoints);
        // Sets the starting level to value indicated by startingHitPoints
        this.hurt(maxHitPoints - startingHitPoints);
        this.maxUconsciousRounds = maxUnconsciousRounds;
        this.hungerAmount = hungerAmount;
        wBehaviour = new WanderBehaviour();
        hBehaviour = new SeekFruitBehaviour(displayChar);

    }

    /**
     * Returns a collection of the Actions that the otherActor can do to the current Actor.
     * Currently adds two allowable actions, for attacking and feeding.
     *
     * @param otherActor the Actor that might be performing attack
     * @param direction  String representing the direction of the other Actor
     * @param map        current GameMap
     * @return A collection of Actions (For attacking and feeding)
     */
    @Override
    public Actions getAllowableActions(Actor otherActor, String direction, GameMap map) {
        Actions actions = new Actions();
        actions.add(new AttackAction(this));
        actions.add(new FeedVegetarianAction(this));
        return actions;

    }

    /**
     * Select and return an action to perform on the current turn
     *
     * @param actions    collection of possible Actions for this Actor
     * @param lastAction The Action this Actor took last turn. Can do interesting things in conjunction with Action.getNextAction()
     * @param map        the map containing the Actor
     * @param display    the I/O object to which messages may be written
     * @return the Action to be performed.
     */
    @Override
    public Action playTurn(Actions actions, Action lastAction, GameMap map, Display display) {
        if (this.isConscious()){
            // reduce food level each turn
            this.hurt(1);
            if (this.hitPoints < hungerAmount){
                System.out.println(this.name + "at (" + map.locationOf(this).x() + "," + map.locationOf(this).y() + ") is hungry!");
                Action hungerMovement = hBehaviour.getAction(this, map);
                if (hungerMovement != null)
                    return hungerMovement;
                else{
                    // If null is returned, it means no food in map, so dinosaur just wanders
                    Action wander = wBehaviour.getAction(this, map);
                    if (wander != null)
                        return wander;
                    else{
                        return new DoNothingAction();
                    }
                }
            }
            // if not hungry, wander
            else{
                Action wander = wBehaviour.getAction(this, map);
                if (wander != null)
                    return wander;
                else{
                    return new DoNothingAction();
                }
            }
        }
        // if not conscious check and update the number of turns the dinosaur has been unconscious for
        else {
            if (unconsciousCount < maxUconsciousRounds){
                this.unconsciousCount += 1;
                System.out.println(this.name + "at (" + map.locationOf(this).x() + "," + map.locationOf(this).y() + ") is unconscious!");
            }
            else {

                System.out.println(this.name + "at (" + map.locationOf(this).x() + "," + map.locationOf(this).y() + ") died  due to lack of food!");
                Corpse corpse = new Corpse("Corpse", false, this.getDisplayChar());
                map.locationOf(this).addItem(corpse);
                map.removeActor(this);
            }

            return new DoNothingAction();
        }
    }

    /**
     *
     * @return name of the dinosaur
     */
    public String getName(){
        return this.name;
    }

    /**
     *
     * @return current hitpoints of the dinosaur
     */
    public int getHitPoints(){
        return this.hitPoints;
    }

    /**
     *
     * @return maximum hit points of the dinosaur
     */
    public int getMaxHitPoints(){
        return this.maxHitPoints;
    }
}
