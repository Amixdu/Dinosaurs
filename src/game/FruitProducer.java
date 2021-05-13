package game;

import edu.monash.fit2099.engine.Ground;

import java.util.Random;

public abstract class FruitProducer extends Ground {
    /**
     * Number of fruits this fruit producer holds
     */
    private int fruits;
    /**
     * Constructor.
     *
     * @param displayChar character to display for this type of terrain
     */
    public FruitProducer(char displayChar) {
        super(displayChar);
    }

    /**
     * Method that checks if this fruit producer has any fruits
     * @return true if it contains any fruits
     */
    public boolean containsFruit(){
        Boolean hasFruit = false;
        if (fruits > 0)
            hasFruit = true;
        return hasFruit;
    }

    /**
     * Method that gets number of fruits on this fruit producer
     * @return int
     */
    public int getFruits(){
        return fruits;
    }

    /**
     * Method that removes a fruit from this fruit producer
     */
    public void removeFruit(){
        fruits--;
    }

    /**
     * Method that sets the number of fruits at the beginning for this fruit producer
     * @param count (int) - number of fruits this will hold
     */
    public void setFruits(int count){
        fruits = count;
    }

    /**
     * Method that increases fruit count by 1 (given certain chance)
     * @param chance (int) - chance to grow a fruit
     */
    public void growFruit(int chance){
        Random rand = new Random();
        if (rand.nextInt(100) < chance){
            fruits++;
        }
    }
}
