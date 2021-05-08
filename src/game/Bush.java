package game;

import edu.monash.fit2099.engine.Ground;

public class Bush extends Ground {
    int fruits;
    /**
     * Constructor.
     *
     *
     */
    public Bush() {
        super('b');
        this.fruits = 1;
    }

    /**
     * Getter method for the number of fruits
     * @return number of fruits in bush
     */
    public int getFruits() {
        return fruits;
    }

    /**
     * Method for removing fruits off a bush one at a time
     */
    public void removeFruit(){
        this.fruits -= 1;
    }
}
