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

    public int getFruits() {
        return fruits;
    }

    public void removeFruit(){
        this.fruits -= 1;
    }
}
