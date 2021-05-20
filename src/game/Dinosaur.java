package game;

import edu.monash.fit2099.engine.*;

/**
 * Base Class Dinosaur
 * @author Abhishek Shrestha
 */
public abstract class Dinosaur extends Actor {
    /**
     * AgeGroup of the dinosaur -> adult or baby
     */
    private AgeGroup ageGroup;
    /**
     * Turns needed to grow from baby to adult
     */
    private int timeToGrow;
    /**
     * Age of the dinosaur
     */
    private int age;
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
     * Hunger Behavior that all dinosaurs have
     */
    private Behaviour hBehaviour;

    /**
     * Wander Behavior that all dinosaurs have
     */
    private Behaviour wBehaviour;
    /**
     * Thirst Behavior that all dinosaurs have
     */
    private Behaviour tBehaviour;
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
     * Water level of the dinosaur
     */
    private int waterLevel;

    /**
     * Maximum water level a dinosaur can have
     */
    private int maxWaterLevel;

    /**
     * Counter for unconsciousness due to lack of rain
     */
    private int rainfallUnconsciousCounter;

    /**
     * To know whether the dinosaur is unconscious due to lack of rain
     */
    private boolean unconsciousDueToRain;


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
     * @param ageGroup Age group of the dino (Baby or Adult)
     */
    public Dinosaur(String name, char displayChar, Sex sex,  int startingHitPoints, int maxHitPoints, int maxUnconsciousRounds,
                    int hungerAmount, int turnsToLayEgg, int mateAmount, AgeGroup ageGroup, int timeToGrow, int maxWaterLevel) {
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
        this.ageGroup = ageGroup;
        this.timeToGrow = timeToGrow;
        this.age = 0;
        this.waterLevel = 60;
        this.maxWaterLevel = maxWaterLevel;
        this.unconsciousDueToRain = false;

        //behaviors
        mBehavior = new MateBehavior();
        wBehaviour = new WanderBehaviour();
        tBehaviour = new SeekWaterBehaviour(displayChar);
        hBehaviour = new SeekFoodBehaviour(displayChar);
    }

    /**
     *
     * @return ageGroup
     */
    public AgeGroup getAgeGroup() {
        return ageGroup;
    }

    /**
     * sets Age Gruop for the dinosaur
     * @param ageGroup Age group of the dino (Baby or Adult)
     */
    public void setAgeGroup(AgeGroup ageGroup) {
        this.ageGroup = ageGroup;
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
        Action resultAction = null;
        if (isConscious()){
            // reduce food level each turn
            this.hurt(1);

            // increase age each turn
            age++;

            // reduce water level each turn
            waterLevel -= 1;

            // check if adult and update age group
            if (age == timeToGrow && ageGroup == AgeGroup.BABY){
                ageGroup = AgeGroup.ADULT;
                System.out.printf("Baby %s at (%d,%d) grew to become an adult.\n", toString(),
                        map.locationOf(this).x(), map.locationOf(this).y() );
            }

            // increment turnsSinceMate if has already mated (for adult female)
            if (hasMated && ageGroup == AgeGroup.ADULT){
                turnsSinceMate++;
            }

            // getting the dinos next action
            // check if mating is possible
            if (hitPoints > mateAmount && ageGroup == AgeGroup.ADULT){
                // can mate
                Action mateAction = mBehavior.getAction(this, map);
                if (mateAction != null){
                    resultAction = mateAction;
                }
                else{
                    // if mating isn't possible, check for thirst
                    resultAction = thirst(map);
                    // if not thirsty, check for hunger
                    if (resultAction == null){
                        resultAction = hunger(map);
                    }
                }
            } else {
                // cannot mate - so check thirst
                resultAction = thirst(map);
                // if not thirsty, check for hunger
                if (resultAction == null){
                    resultAction = hunger(map);
                }
            }
        } else {
            // if unconscious because of lack of rain, update correct counter
            if (unconsciousDueToRain){
                if (rainfallUnconsciousCounter < 15){
                    this.rainfallUnconsciousCounter += 1;
                    // increase normal counter as well
                    this.unconsciousCount += 1;
                    System.out.println(this.name + " at (" + map.locationOf(this).x() + "," + map.locationOf(this).y() + ") is unconscious due to lack of water!");
                }
                else {
                    System.out.println(this.name + " at (" + map.locationOf(this).x() + "," + map.locationOf(this).y() + ") died due to lack of water!");
                    Corpse corpse = new Corpse("Corpse", this.getDisplayChar());
                    map.locationOf(this).addItem(corpse);
                    map.removeActor(this);
                }
            }
            // if unconscious, but not because of rain, update counter
            else{
                if (unconsciousCount < this.getMaxUnconsciousRounds()){
                    this.unconsciousCount += 1;
                    System.out.println(this.name + " at (" + map.locationOf(this).x() + "," + map.locationOf(this).y() + ") is unconscious!");
                }
                else {
                    System.out.println(this.name + " at (" + map.locationOf(this).x() + "," + map.locationOf(this).y() + ") died!");
                    Corpse corpse = new Corpse("Corpse", this.getDisplayChar());
                    map.locationOf(this).addItem(corpse);
                    map.removeActor(this);
                }
            }
        }

        if (isConscious()){
            // return appropriate action
            if (resultAction != null){
                return resultAction;
            }
            // if no other action wander
            else{
                Action wander = wBehaviour.getAction(this, map);
                if (wander != null){
                    return wander;
                }
                else{
                    return new DoNothingAction();
                }
            }
        }
        // if not conscious, do nothing
        else{
            return new DoNothingAction();
        }

    }

    /**
     * a method to check whether a dinosaur is thirsty, and if so return the correct action
     * @param map the map that the actor is currently on
     * @return a MoveActorForConsumption action in the direction of the closest lake if thirsty
     */
    private Action thirst(GameMap map){
        // check if thirsty
        if (waterLevel < 40){
            System.out.println(this.name + " at (" + map.locationOf(this).x() + "," + map.locationOf(this).y() + ") is thirsty!");
            if (waterLevel > 0){
                // return thirsty behaviour
                Action thirstyAction = tBehaviour.getAction(this, map);
                if (thirstyAction != null){
                    return thirstyAction;
                }
            }
            else{
                // if no water, make the dinosaur unconscious
                if (waterLevel <= 0){
                    this.hurt(maxHitPoints);
                    unconsciousDueToRain = true;
                }
            }
        }
        return null;
    }

    /**
     * a method to check whether a dinosaur is hungry, and if so return the correct action
     * @param map the map that the actor is currently on
     * @return a MoveActorForConsumption action in the direction of the closest food if hungry
     */
    private Action hunger(GameMap map){
        // if hungry
        if (hitPoints < hungerAmount){
            System.out.println(this.name + " at (" + map.locationOf(this).x() + "," + map.locationOf(this).y() + ") is hungry!");
            Action hungerMovement = hBehaviour.getAction(this, map);
            if (hungerMovement != null){
                return hungerMovement;
            }
        }
        return null;
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

    public int getWaterLevel() {
        return waterLevel;
    }

    public int getMaxWaterLevel() {
        return maxWaterLevel;
    }

    public void setWaterLevel(int waterLevel) {
        this.waterLevel = waterLevel;
    }
}
