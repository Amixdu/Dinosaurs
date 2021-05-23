package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actions;
import edu.monash.fit2099.engine.Display;
import edu.monash.fit2099.engine.GameMap;
import game.AgeGroup;
import game.CarnivorousDinosaur;
import game.Sex;

/**
 * @author Amindu Kumarasinghe
 */
public class Pterodactyl extends CarnivorousDinosaur {

    /**
     * Counter of how many rounds a pterodactyl can fly before landing
     */
    int fuel;

    /**
     * Behaviour to seek for a tree either for laying egg/resting
     */
    Behaviour seekTreeBehaviour;

    /**
     * Constructor
     *
     * @param name                 Name of the Dinosaur
     * @param sex                  Sex of the dinosaur
     * @param startingHitPoints    starting hitpoints for the dinosaur
     * @param ageGroup             Age group of the dino (Baby or Adult)
     */
    public Pterodactyl(String name, char displayChar, Sex sex, int startingHitPoints, AgeGroup ageGroup, int fuel){
        super(name, displayChar, sex, startingHitPoints, 100, 20, 90,
                10, 50, ageGroup, 30, 100, 30);
        this.addCapability(Flight.YES);
        this.fuel = 30 - (30 - fuel);
        seekTreeBehaviour = new PterodactylSeekTreeBehaviour("resting");
    }

    /**
     * Setter method for fuel
     * @param fuel
     */
    public void setFuel(int fuel) {
        this.fuel = fuel;
    }

    /**
     * Select and return an action to perform on the current turn and process the amount of fuel.
     * @param actions    collection of possible Actions for this Actor
     * @param lastAction The Action this Actor took last turn. Can do interesting things in conjunction with Action.getNextAction()
     * @param map        the map containing the Actor
     * @param display    the I/O object to which messages may be written
     * @return the final action to perform next
     */
    @Override
    public Action playTurn(Actions actions, Action lastAction, GameMap map, Display display) {
        Action superAction = super.playTurn(actions, lastAction, map, display);
        Action finalAction = superAction;
        boolean superActionSuccess = false;
        if (superAction instanceof MateAction || superAction instanceof MoveActorToMateAction || superAction instanceof LayEggAction){
            superActionSuccess = true;
        }
        if (fuel <= 0){
            // does not land on water
            if (map.locationOf(this).getGround().getDisplayChar() != '~'){
                this.removeCapability(Flight.YES);
                if (this.isConscious()){
                    if (!superActionSuccess){
                        // start looking for a tree
                        finalAction = seekTreeBehaviour.getAction(this, map);
                    }
                }

            }
        }
        fuel = fuel - 1;
        return finalAction;
    }

}
