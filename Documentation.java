import java.util.List;
import java.util.Random;

/**
 * Write a description of class Grade here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Documentation extends Plant
{
    private static final int MAX_AGE = 35* 24;
    // The likelihood of a admin breeding.
    private static final double SEEDING_PROBABILITY = 0.04;
    // The maximum number of births.
    private static final int MAX_SEEDLINGS = 5;
    
    private static final Random rand = Randomizer.getRandom();
    
    private static final int SEEDING_AGE=30;
    
    private static final int EDIBLE_AGE = 20;
    // Food value for admins.
    private static final int FOOD_VALUE = 10;

    public Documentation(Field field, Location location, Weather weather)
    {
        super(field,location,weather);
    }
    
    public int getFoodValue()
    {
        return FOOD_VALUE;
    }
    
    /**
     * Make this animal act - that is: make it do
     * whatever it wants/needs to do.
     * @param newAnimals A list to receive newly born animals.
     */
    public void act(List<Animal> newDocs){
        if (isAlive()) {
            giveBirth(newDocs);
        }
    }
    
    private void giveBirth(List<Animal> newDocs)
    {
        // New KCLSUs are born into adjacent locations.
        // Get a list of adjacent free locations.
        Field field = getField();
        List<Location> free = field.getFreeAdjacentLocations(getLocation());
        int births = breed();
        for(int b = 0; b < births && free.size() > 0; b++) {
            Location loc = free.remove(0);
            Documentation doc = new Documentation(field, loc, weather);
            newDocs.add(doc);
        }
    }
        
    /**
     * Generate a number representing the number of births,
     * if it can breed.
     * @return The number of births (may be zero).
     */
    private int breed()
    {
        int births = 0;
        if(rand.nextDouble() <= SEEDING_PROBABILITY && age>=SEEDING_AGE) {
            births = rand.nextInt(MAX_SEEDLINGS) + 1;
        }
        return births;
    }
    
    public boolean isEdible()
    {
        return (age > EDIBLE_AGE);
    }
}
