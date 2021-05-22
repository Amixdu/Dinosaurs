package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Location;

public class DrinkWaterAction extends Action {
    Location waterLocation;

    public DrinkWaterAction(Location waterLocation) {
        this.waterLocation = waterLocation;
    }

    @Override
    public String execute(Actor actor, GameMap map) {
        String outputMessage = "";
        Dinosaur dino = (Dinosaur) actor;
        Lake lake = (Lake) waterLocation.getGround();
        lake.setSips(lake.getSips() - 1);
        // if pterodactyls are flying they can drink water by dipping their beaks in
        // and also has a chance of catching fish
        if (dino.getDisplayChar() == 'P' && dino.hasCapability(Flight.YES)){
            if (lake.getFishCount() >= 2){
                // chance based system : 60% chance of catching only one fish,
                // 60% chance of catching two and 20% chance of catching none.
                double random = Math.random();
                int fish = lake.getFishCount();
                // chance for eating one fish
                if (random > 0.4) {
                    lake.setFishCount(fish - 1);
                    actor.heal(5);
                    dino.setWaterLevel(dino.getWaterLevel() + 30);
                    outputMessage = (actor.toString() + " at location (" + waterLocation.x() + "," +
                            waterLocation.y() + ") eats " + "1 fish and increases water level by 30");
                }
                // chance for eating two fish
                else if (random >= 0.2 && random <= 0.4){
                    lake.setFishCount(fish - 2);
                    dino.heal(5 * 2);
                    dino.setWaterLevel(dino.getWaterLevel() + 60);
                    outputMessage = (actor.toString() + " at location (" + waterLocation.x() + "," +
                            waterLocation.y() + ") eats " + "2 fish and increases water level by 60");
                }
                // chance for eating no fish
                else if (random < 0.2) {
                    outputMessage = (actor.toString() + " at location (" + waterLocation.x() + "," +
                            waterLocation.y() + ") doesn't catch any fish but increases water level by 30");
                    dino.setWaterLevel(dino.getWaterLevel() + 30);
                }
            }
            // theres only one fish
            else if (lake.getFishCount() == 1){
                lake.setFishCount(lake.getFishCount() - 1);
                dino.heal(5);
                dino.setWaterLevel(dino.getWaterLevel() + 30);
                outputMessage = (actor.toString() + " at location (" + waterLocation.x() + "," +
                        waterLocation.y() + ") eats " + "1 fish and increases water level by 30");
            }
            // no fish
            else{
                outputMessage = (actor.toString() + " at location (" + waterLocation.x() + "," +
                        waterLocation.y() + ") doesn't catch any fish but increases water level by 30");
                dino.setWaterLevel(dino.getWaterLevel() + 30);
            }
        }
        if (actor.getDisplayChar() == 'R') {
            // if brachiosaur
            Brachiosaur brachiosaur = (Brachiosaur) actor;
            if (brachiosaur.getWaterLevel() + 80 < brachiosaur.getMaxWaterLevel()){
                brachiosaur.setWaterLevel(brachiosaur.getWaterLevel() + 80);
            }
            else {
                brachiosaur.setWaterLevel(brachiosaur.getMaxWaterLevel());
            }
            outputMessage = (actor.toString() + " at location (" + waterLocation.x() + "," +
                    waterLocation.y() + ") drinks water");

        }
        // if Allosaur, Stegosaur or Pterodactyl
        else{
            // if allosaur or stegosaur
            Dinosaur dinosaur = (Dinosaur) actor;
            if (dinosaur.getWaterLevel() + 30 < dinosaur.getMaxWaterLevel()){
                dinosaur.setWaterLevel(dinosaur.getWaterLevel() + 30);
            }
            else {
                dinosaur.setWaterLevel(dinosaur.getMaxWaterLevel());
            }
            outputMessage = (actor.toString() + " at location (" + waterLocation.x() + "," +
                    waterLocation.y() + ") drinks water");
        }
        return outputMessage;
    }

    @Override
    public String menuDescription(Actor actor) {
        return null;
    }
}
