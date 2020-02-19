import java.util.List;
import java.util.Iterator;
import java.util.Random;

/**
 * A simple model of a admin.
 * admines age, move, eat rabbits, and die.
 * 
 * @author David J. Barnes and Michael Kölling
 * @version 2016.02.29 (2)
 */
public class Admin extends Animal
{
    // Characteristics shared by all admins (class variables).
    
    // The age to which a admin can live.
    private static final int MAX_AGE = 80 * 24;
    // The likelihood of a admin breeding.
    private static final double BREEDING_PROBABILITY = 0.06;
    // The maximum number of births.
    private static final int MAX_LITTER_SIZE = 2;
    // A shared random number generator to control breeding.
    private static final Random rand = Randomizer.getRandom();
    // Default starting food.
    private static final int DEFAULT_FOOD_LEVEL = 30;
    // Food value for admins.
    private static final int FOOD_VALUE = 40;

    // Individual characteristics (instance fields).
    // The admin's age.
    private int age;
    // The admin's food level, which is increased by eating rabbits.
    private int foodLevel;
    

    /**
     * Create a admin. A admin can be created as a new born (age zero
     * and not hungry) or with a random age and food level.
     * 
     * @param randomAge If true, the admin will have random age and hunger level.
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public Admin(boolean randomAge, Field field, Location location, Weather weather)
    {
        super(field, location, weather);
        if(randomAge) {
            age = rand.nextInt(MAX_AGE);
            foodLevel = rand.nextInt(MAX_FOOD_LEVEL);
        }
        else {
            age = 0;
            foodLevel = DEFAULT_FOOD_LEVEL;
        }
    }
    
    /**
     * This is what the admin does most of the time: it hunts for
     * rabbits. In the process, it might breed, die of hunger,
     * or die of old age.
     * @param field The field currently occupied.
     * @param newadmines A list to return newly born admines.
     */
    public void act(List<Animal> newAdmin)
    {
        incrementAge();
        incrementHunger();
        if(isAlive()) {
            if (weather.isNight()) {
                giveBirth(newAdmin);
                if (weather.isCold()) 
                    giveBirth(newAdmin);
            }
            
            // Move towards a source of food if found.
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
    }

    /**
     * Increase the age. This could result in the admin's death.
     */
    private void incrementAge()
    {
        age++;
        if(age > MAX_AGE) {
            setDead();
        }
    }
    
    /**
     * Make this admin more hungry. This could result in the admin's death.
     */
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
            Object animal = field.getObjectAt(where);
            if(animal instanceof Lecturer) {
                Lecturer lecturer = (Lecturer) animal;
                if(lecturer.isAlive()) { 
                    lecturer.setDead();
                    if (foodLevel < MAX_FOOD_LEVEL)
                        foodLevel = foodLevel + lecturer.getFoodValue();
                    return where;
                }
            }
        }
        return null;
    }
    
    /**
     * Check whether or not this admin is to give birth at this step.
     * New births will be made into free adjacent locations.
     * @param newadmins A list to return newly born admines.
     */
    private void giveBirth(List<Animal> newadmins)
    {
        // New admins are born into adjacent locations.
        // Get a list of adjacent free locations.
        Field field = getField();
        List<Location> free = field.getFreeAdjacentLocations(getLocation());
        int births = breed();
        for(int b = 0; b < births && free.size() > 0; b++) {
            Location loc = free.remove(0);
            Admin young = new Admin(false, field, loc, weather);
            newadmins.add(young);
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
        if(rand.nextDouble() <= BREEDING_PROBABILITY) {
            births = rand.nextInt(MAX_LITTER_SIZE) + 1;
        }
        return births;
    }
    
    public int getFoodValue()
    {
        return FOOD_VALUE;
    }
}