package game;

import edu.monash.fit2099.engine.*;

public abstract class Dinosaur extends Actor {
    /**
     * AgeGroup of the dinosaur -> adult or baby
     */
    private AgeGroup ageGroup;
    /**
     * Sex of the Dinosaur (Male or Female)
     */
    private Sex sex;

    /**
     * Number of rounds that dinosaur has been unconscious for
     */
    private int unconsciousCount;

    /**
     * Maximum number of rounds the dinosaur can go without dying
     */
    private int maxUnconsciousRounds;

    /**
     * Amount of hit points below which dinosaur starts to feel hunger
     */
    private int hungerAmount;

    /**
     * Wander Behavior that all dinosaurs have
     */
    private Behaviour wBehaviour;
    /**
     * Mate Behavior that all dinosaurs have
     */
    private Behaviour mBehavior;
    /**
     * Number of turns since this dinosaur mated (only for females)
     */
    private int turnsSinceMate;
    /**
     * To tell if dinosaur has mated or not (only for females)
     */
    private boolean hasMated;

    /**
     * Turns it takes to lay eggs after mating (only for females)
     */
    private int turnsToLayEgg;

    /**
     * food level above which dinosaur can mate
     */
    private int mateAmount;

    /**
     * Constructor
     * @param name Name of the Dinosaur
     * @param displayChar Display char of the dinosaur
     * @param sex Sex of the dinosaur
     * @param startingHitPoints starting hitpoints for the dinosaur
     * @param maxHitPoints max hitpoints for the dinosaur
     * @param maxUnconsciousRounds number of rounds a dinosaur can stay unconscious without dying
     * @param hungerAmount Amount of health points below which a dinosaur feels hunger
     * @param turnsToLayEgg turns it takes to lay eggs
     * @param mateAmount food level above which mating is possible
     */
    public Dinosaur(String name, char displayChar, Sex sex,  int startingHitPoints, int maxHitPoints, int maxUnconsciousRounds,
                    int hungerAmount, int turnsToLayEgg, int mateAmount) {
        super(name, displayChar, maxHitPoints);
        // Sets the starting level to value indicated by startingHitPoints
        this.hurt(maxHitPoints - startingHitPoints);
        this.maxUnconsciousRounds = maxUnconsciousRounds;
        this.hungerAmount = hungerAmount;
        this.sex = sex;
        this.unconsciousCount = 0;
        this.hasMated = false;
        this.turnsSinceMate = 0;
        this.turnsToLayEgg = turnsToLayEgg;
        this.mateAmount = mateAmount;

        //behaviors
        mBehavior = new MateBehavior();
        wBehaviour = new WanderBehaviour();
    }

    /**
     * Select and return action to perform in current turn
     * @param actions    collection of possible Actions for this Actor
     * @param lastAction The Action this Actor took last turn. Can do interesting things in conjunction with Action.getNextAction()
     * @param map        the map containing the Actor
     * @param display    the I/O object to which messages may be written
     * @return Action - most appropriate action to perform in current turn
     */
    @Override
    public Action playTurn(Actions actions, Action lastAction, GameMap map, Display display) {
        if (isConscious()){
            // reduce food level each turn
            this.hurt(1);

            if (hasMated){
                turnsSinceMate++;
            }
            if (hitPoints > mateAmount){
                // can mate
                Action mateAction = mBehavior.getAction(this, map);
                if (mateAction != null){
                    return mateAction;
                }
                // if null is returned, no dinosaur of opposite sex is close by -> wanders
                else {
                    Action wander = wBehaviour.getAction(this, map);
                    if (wander != null){
                        return wander;
                    } else {
                        return new DoNothingAction();
                    }
                }
            } else {
                // cannot mate - just wanders
                Action wander = wBehaviour.getAction(this, map);
                if (wander != null){
                    return wander;
                } else {
                    return new DoNothingAction();
                }
            }
        } else {
            // if unconscious, update counter
            if (unconsciousCount < this.getMaxUnconsciousRounds()){
                this.unconsciousCount += 1;
                System.out.println(this.name + " at (" + map.locationOf(this).x() + "," + map.locationOf(this).y() + ") is unconscious!");
            }
            else {
                System.out.println(this.name + " at (" + map.locationOf(this).x() + "," + map.locationOf(this).y() + ") died  due to lack of food!");
                Corpse corpse = new Corpse("Corpse", false, this.getDisplayChar());
                map.locationOf(this).addItem(corpse);
                map.removeActor(this);
            }
            return new DoNothingAction();
        }
    }

    /**
     * @return turns since mate (for females)
     */
    public int getTurnsSinceMate() {
        return turnsSinceMate;
    }

    /**
     *
     * @return turns to lay eggs
     */
    public int getTurnsToLayEgg() {
        return turnsToLayEgg;
    }

    /**
     *
     * @return whether this FEMALE dinosaur has mated or not
     */
    public boolean getHasMated() {
        return hasMated;
    }

    /**
     *
     * @return sex of the dinosaur
     */
    public Sex getSex() {
        return sex;
    }

    /**
     *
     * @return number of rounds this dinosaur has been unconscious for
     */
    public int getUnconsciousCount() {
        return unconsciousCount;
    }


    /**
     * Sets turns since mate. Usually used to turn to 0 once female lays egg
     * @param turnsSinceMate
     */
    public void setTurnsSinceMate(int turnsSinceMate) {
        this.turnsSinceMate = turnsSinceMate;
    }

    /**
     * sets whether FEMALE has mated or not
     * @param hasMated
     */
    public void setHasMated(boolean hasMated) {
        this.hasMated = hasMated;
    }

    /**
     * Getter method to retrieve the name of current dinosaur
     * @return name of dinosaur
     */
    public String getName(){
        return this.name;
    }

    /**
     * Getter method to retrieve the current hit points of dinosaur
     * @return current hit points of dinosaur
     */
    public int getHitPoints(){
        return this.hitPoints;
    }

    /**
     * Getter method to retrieve the max hit points of dinosaur
     * @return max hit points of dinosaur
     */
    public int getMaxHitPoints(){
        return this.maxHitPoints;
    }

    /**
     * Getter method to retrieve the max unconscious rounds of dinosaur before dying
     * @return max unconscious rounds of dinosaur before dying
     */
    public int getMaxUnconsciousRounds() {
        return maxUnconsciousRounds;
    }

    /**
     * Getter method to retrieve the hit points below which the dinosaur feels hunger
     * @return hit points below which the dinosaur feels hunger
     */
    public int getHungerAmount() {
        return hungerAmount;
    }
}
