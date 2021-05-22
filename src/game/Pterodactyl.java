package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actions;
import edu.monash.fit2099.engine.Display;
import edu.monash.fit2099.engine.GameMap;
import game.AgeGroup;
import game.CarnivorousDinosaur;
import game.Sex;

public class Pterodactyl extends CarnivorousDinosaur {

    int fuel;
    Behaviour seekTreeBehaviour;
    /**
     * Constructor
     *
     * @param name                 Name of the Dinosaur
     * @param sex                  Sex of the dinosaur
     * @param startingHitPoints    starting hitpoints for the dinosaur
     * @param ageGroup             Age group of the dino (Baby or Adult)
     */
//    public Pterodactyl(String name, Sex sex, int startingHitPoints, int maxHitPoints, int maxUnconsciousRounds, int hungerAmount, int turnsToLayEgg, int mateAmount, AgeGroup ageGroup, int timeToGrow, int maxWaterLevel) {
//        super(name, 'P', sex, startingHitPoints, maxHitPoints, maxUnconsciousRounds, hungerAmount, turnsToLayEgg, mateAmount, ageGroup, timeToGrow, maxWaterLevel, 30);
//        this.addCapability(Flight.YES);
//    }

    public Pterodactyl(String name, Sex sex, int startingHitPoints, AgeGroup ageGroup, int fuel){
        super(name, 'P', sex, startingHitPoints, 100, 20, 90,
                10, 50, ageGroup, 30, 100, 30);
        this.addCapability(Flight.YES);
        this.fuel = 30 - (30 - fuel);
        seekTreeBehaviour = new PterodactylSeekTreeBehaviour("resting");
    }

    public void setFuel(int fuel) {
        this.fuel = fuel;
    }

    @Override
    public Action playTurn(Actions actions, Action lastAction, GameMap map, Display display) {
        Action superAction = super.playTurn(actions, lastAction, map, display);
        Action finalAction = superAction;
        boolean superActionSuccess = (superAction instanceof MateAction || superAction instanceof MoveActorToMateAction || superAction instanceof LayEggAction);
        if (fuel <= 0){
            // does not land on water
            if (map.locationOf(this).getGround().getDisplayChar() != '~'){
                this.removeCapability(Flight.YES);
                if (!superActionSuccess){
                    // start looking for a tree
                    finalAction = seekTreeBehaviour.getAction(this, map);
                }
            }
        }
        fuel = fuel - 1;
        return finalAction;
    }

}
