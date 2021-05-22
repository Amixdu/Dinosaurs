package game;

import edu.monash.fit2099.engine.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Class Representing ParkGameMap
 * @author Abhishek Shrestha
 */
public class ParkGameMap extends GameMap {
    /**
     * Random instance
     */
    private Random rand = new Random();
    /**
     * Number of turns
     */
    private int noOfTurns;

    /**
     * Constructor that creates a map from a sequence of ASCII strings.
     *
     * @param groundFactory Factory to create Ground objects
     * @param lines         List of Strings representing rows of the map
     */
    public ParkGameMap(GroundFactory groundFactory, List<String> lines) {
        super(groundFactory, lines);
        noOfTurns = 0;
    }

    /**
     * Constructor.
     *
     * @param groundFactory Factory to create Ground objects
     * @param groundChar    Symbol that will represent empty Ground in this map
     * @param width         width of the GameMap, in characters
     * @param height        height of the GameMap, in characters
     */
    public ParkGameMap(GroundFactory groundFactory, char groundChar, int width, int height) {
        super(groundFactory, groundChar, width, height);
        noOfTurns = 0;
    }

    /**
     * Constructor that reads a map from file.
     *
     * @param groundFactory Factory to create Ground objects
     * @param mapFile       Name of a file containing an ASCII representation of a
     *                      level
     * @throws IOException when file I/O fails
     */
    public ParkGameMap(GroundFactory groundFactory, String mapFile) throws IOException {
        super(groundFactory, mapFile);
        noOfTurns = 0;
    }

    /**
     * Method to add exits to a location in another map
     * Access level set to public
     * @param here the current location
     * @param x X coordinate
     * @param y Y coordinate
     * @param name name of the Exit
     * @param hotKey the hotkey for the appropiate Action
     */
    public void addExitFromHereToAnotherMap(Location here, int x, int y, String name, String hotKey, GameMap newMap){
        NumberRange newMapWidth = newMap.getXRange();
        NumberRange newMapHeight = newMap.getYRange();
        if (newMapWidth.contains(x) && newMapHeight.contains(y)){
            here.addExit(new Exit(name, newMap.at(x,y), hotKey));
        }
    }

    /**
     * map can experience the passage of time
     * Rains every 10 turns with a probability of 20%
     * When it rains, it hydrates unconscious dinosaurs and fills lakes
     */
    @Override
    public void tick() {
        noOfTurns++;
        double rainfall = 0.0;
        boolean rain = false;

        // 20% probability of raining every 10 turns
        if (noOfTurns % 10 == 0 && rand.nextInt(100) < 20){
            rain = true;
            System.out.println("It started to rain!");

            // amount of water added to lake => rainfall
            int rainfallInt = rand.nextInt(6) + 1;
            rainfall = rainfallInt / 10.0;
            rainfall = rainfall * 20; // calculation according to specifications
        }

        // Tick over all the items in inventories.
        for (Actor actor : actorLocations) {
            if (this.contains(actor)) {
                for (Item item : new ArrayList<Item>(actor.getInventory())) { // Copy the list in case the item wants to leave
                    item.tick(actorLocations.locationOf(actor), actor);
                }

                // if it's raining and actor is dino and dino is unconscious due to lack of water
                // set water points to 10 and reset boolean tracker
                if (rain){
                    if (actor instanceof Dinosaur){
                        Dinosaur dino = (Dinosaur) actor;
                        if (dino.isUnconsciousDueToWater()){
                            dino.setWaterLevel(10);
                            dino.setUnconsciousDueToWater(false);
                            dino.setUnconsciousCount(0);
                        }
                    }
                }

            }
        }

        for (int y : heights) {
            for (int x : widths) {
                // if it's raining, increment sips in lake
                if (rain && this.at(x, y).getGround().getDisplayChar() == '~'){
                    Lake thisLake = (Lake) this.at(x, y).getGround();
                    thisLake.setSips(thisLake.getSips() + (int) rainfall);
                }
                this.at(x, y).tick();
            }
        }
    }
}
