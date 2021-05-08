package game;

import edu.monash.fit2099.engine.Location;

public class Tuple {
    private Location first;
    private char second;

    public Tuple(Location first, char second) {
        this.first = first;
        this.second = second;
    }

    public Location getFirst() {
        return first;
    }

    public char getSecond() {
        return second;
    }

    public void setFirst(Location first) {
        this.first = first;
    }

    public void setSecond(char second) {
        this.second = second;
    }
}
