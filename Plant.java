import java.util.Random;
import java.util.List;

/**
 * This class represents a plant. Plants don't move. They attempt to spread at
 * each step and die when they are eaten or get too old.
 *
 * @author Louis Mellac and Andrei Cinca
 * @version 2020.02.20
 */
public abstract class Plant extends Entity
{
    /**
     * Creates a plant and places it in the field.
     * @param field The plant's field.
     * @param location The plant's location in the field.
     * @param weather The weather affecting the plant's behaviour.
     */
    public Plant(Field field, Location location, Weather weather)
    {
        super(location, field, weather);
    }
    
    // methods for aquiring the constants of the subclasses.
    protected abstract int getMAX_SEEDLINGS();
    protected abstract int getSEEDING_AGE();
    protected abstract int getEDIBLE_AGE();
    protected abstract double getSEEDING_PROBABILITY();
    
    /**
     * Makes this plant act (spread and grow).
     * @param newPlants A list to receive newly created plants.
     */
    public void act(List<Entity> newPlants)
    {
        if (isAlive()) {
            spread(newPlants);
            incrementAge();
        }
    }
    
    /**
     * Attempts to create new plants and add them to the List.
     * @param newPlants The list to attempt to add new plants to.
     */
    private void spread(List<Entity> newPlants)
    {
        // New Plants grow in adjacent locations.
        // Get a list of adjacent free locations.
        Field field = getField();
        List<Location> free = field.getFreeAdjacentLocations(getLocation());
        // Check how many new plants are created.
        int seeds = pollenationSuccess();
        // Create the appropriate number of new plants.
        for(int b = 0; b < seeds && free.size() > 0; b++) {
            Location loc = free.remove(0);
            Grade seed = new Grade(field, loc, weather);
            newPlants.add(seed);
        }
    }
        
    /**
     * Generate a number representing the new seeds that sucessfully take hold
     * if the plant is old enough to spread.
     * @return The number of successfull seeds (may be zero).
     */
    private int pollenationSuccess()
    {
        int seeds = 0;
        if(rand.nextDouble() <= getSEEDING_PROBABILITY() && age >= getSEEDING_AGE()) {
            seeds = rand.nextInt(getMAX_SEEDLINGS()) + 1;
        }
        return seeds;
    }
    
    /**
     * @return true if the plant has reached its edible age.
     */
    public boolean isEdible()
    {
        return (age > getEDIBLE_AGE());
    }
}
