import java.util.List;
import java.util.Random;
import java.util.Iterator;
/**
 * A simple model of a rabbit.
 * Rabbits age, move, breed, and die.
 * We will all die eventually.
 * 
 * @author David J. Barnes, Michael Kölling, Louis Mellac, Andrei Cinca
 * @version 2020.02.11
 */
public class TA extends GenderedAnimal
{
    // Characteristics shared by all rabbits (class variables).

    // Maximum age of a TA.
    private static final int MAX_AGE = 40 * 24;
    // The likelihood of a TA breeding.
    private static final double BREEDING_PROBABILITY = 0.10;
    // The maximum number of births.
    private static final int MAX_OFFSPRINGS = 3;
    // A shared random number generator to control breeding.
    private static final Random rand = Randomizer.getRandom();
    // Food value of a TA
    private static final int FOOD_VALUE = 15;
    // Default starting food level of a TA.
    private static final int DEFAULT_FOOD_LEVEL = 25;
    
    private int foodLevel;
    
    // The rabbit's age.
    private int age;
    

    /**
     * Create a new rabbit. A rabbit may be created with age
     * zero (a new born) or with a random age.
     * 
     * @param randomAge If true, the rabbit will have a random age.
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public TA(boolean randomAge, Field field, Location location, boolean isFemale, Weather weather)
    {
        super(location, field, isFemale, weather);
        age = 0;
        if(randomAge) {
            age = rand.nextInt(MAX_AGE);
            foodLevel = rand.nextInt(MAX_FOOD_LEVEL);
        }
        else {
            age = 0;
            foodLevel = DEFAULT_FOOD_LEVEL;
        }
    }
    
        public void move()
            {
                Location newLocation = findFood();
                if(newLocation == null) { 
                    // No food found - try to move to a free location.
                    newLocation = getField().freeAdjacentLocation(getLocation());
                }
                // See if it was possible to move.
                if(newLocation != null) {
                    setLocation(newLocation);
                }
                else {
                    // Overcrowding.
                    setDead();
                }
            }
        
        private void incrementHunger()
            {
                foodLevel--;
                if(foodLevel <= 0) {
                    setDead();
                }
            }
        
        /**
         * Look for rabbits adjacent to the current location.
         * Only the first live rabbit is eaten.
         * @return Where food was found, or null if it wasn't.
         */
        private Location findFood()
        {
            Field field = getField();
            List<Location> adjacent = field.adjacentLocations(getLocation());
            Iterator<Location> it = adjacent.iterator();
            while(it.hasNext()) {
                Location where = it.next();
                Object food = field.getObjectAt(where);
                if(food instanceof Documentation) {
                    Documentation doc = (Documentation) food;
                    if(doc.isAlive() && doc.isEdible()) { 
                        doc.setDead();
                        if(foodLevel<MAX_FOOD_LEVEL)
                            foodLevel = foodLevel + doc.getFoodValue();
                        return where;
                    }
                }
            }
            return null;
        }

    
    /**
     * This is what the rabbit does most of the time - it runs 
     * around. Sometimes it will breed or die of old age.
     * @param newRabbits A list to return newly born rabbits.
     */
    public void act(List<Animal> newTA)
    {
        incrementAge();
        incrementHunger();
        if(isAlive()) {
            if (!weather.isCold())
                giveBirth(newTA);       
            // Try to move into a free location.
            move();
            
        }
        
    }

    /**
     * Increase the age.
     * This could result in the rabbit's death.
     */
    private void incrementAge()
    {
        age++;
        if(age > MAX_AGE) {
            setDead();
        }
    }
    
    /**
     * Check whether or not this rabbit is to give birth at this step.
     * New births will be made into free adjacent locations.
     * @param newRabbits A list to return newly born rabbits.
     */
    private void giveBirth(List<Animal> newTA)
    {
        // New rabbits are born into adjacent locations.
        // Get a list of adjacent free locations.
        Field field = getField();
        List<Location> free = field.getFreeAdjacentLocations(getLocation());
        int births = breed();
        for(int b = 0; b < births && free.size() > 0; b++) {
            Location loc = free.remove(0);
            TA young = new TA(false, field, loc, rand.nextBoolean(), weather);
            newTA.add(young);
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
        if(canBreed(this) && rand.nextDouble() <= BREEDING_PROBABILITY) {
            births = rand.nextInt(MAX_OFFSPRINGS) + 1;
        }
        return births;
    }
    
    public int getFoodValue()
    {
        return FOOD_VALUE;
    }    
    
}
