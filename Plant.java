import java.util.Random;
import java.util.List;

/**
 * This class represents a plant. Plants don't move.
 * Plants can spread, die of old age, or be eaten.
 *
 * @author Louis Mellac and Andrei Cinca
 * @version 2020.02.20
 */
public abstract class Plant extends Entity
{
    /**
     * Create a new plant and place it in the field.
     * @param randomAge Whether or not this plant should start with a random age.
     * @param location The location within the field.
     * @param field The field currently occupied.
     * @param weather The weather affecting the plant.
     */
    public Plant(boolean randomAge, Field field, Location location, Weather weather)
    {
        super(randomAge, location, field, weather);
    }
    
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
     * Attempts to create new plants and add them to the List. New plants
     * grow in adjacent locations.
     * @param newPlants The list to attempt to add new plants to.
     */
    private void spread(List<Entity> newPlants)
    {
        // Get a list of adjacent free locations.
        Field field = getField();
        Location location = getLocation();
        List<Location> free = field.getFreeAdjacentLocations(location);
        // Check how many new plants are created.
        int seeds = pollenationSuccess();
        // Create the appropriate number of new plants.
        for(int b = 0; b < seeds && free.size() > 0; b++) {
            Location loc = free.remove(0);
            Plant seed = buildSeed(false, field, loc, weather);
            newPlants.add(seed);
        }
    }
    
    /**
     * Returns an initialised object of a Plant subclass which can be used by the spread method.
     * @param randomAge Whether or not this plant should start with a random age.
     * @param location The location within the field.
     * @param field The field currently occupied.
     * @param weather The weather affecting the plant.
     * @returns an object of a subclass plant as a Plant object.
     */
    abstract protected Plant buildSeed(boolean randomAge, Field field, Location loc, Weather weather);
        
    /**
     * Generate a number representing the new seeds that sucessfully take hold
     * if the plant is old enough to spread.
     * @return The number of successfull seeds (may be zero).
     */
    private int pollenationSuccess()
    {
        int seeds = 0;
        if(rand.nextDouble() <= getSEEDING_PROBABILITY() && age >= getSEEDING_AGE())
            seeds = rand.nextInt(getMAX_SEEDLINGS()) + 1;
        return seeds;
    }
    
    /**
     * @return true if the plant has reached its edible age.
     */
    public boolean isEdible()
    {
        return (age > getEDIBLE_AGE());
    }
    
    // methods for aquiring the constants of the subclasses.
    protected abstract int getMAX_SEEDLINGS();
    protected abstract int getSEEDING_AGE();
    protected abstract int getEDIBLE_AGE();
    protected abstract double getSEEDING_PROBABILITY();
}
