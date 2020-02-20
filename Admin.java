import java.util.List;
import java.util.Iterator;

/**
 * A simple model of a admin.
 * admines age, move, eat TAs, and die.
 * 
 * @author David J. Barnes and Michael Kölling,Louis Mellac, Andrei Cinca
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
    private static final int MAX_OFFSPRING = 2;
    // Default starting food.
    private static final int DEFAULT_FOOD_LEVEL = 30;
    // Food value for admins.
    private static final int FOOD_VALUE = 40;

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
        super(location, field, weather);
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
     * these methods are used for providing the superclass with the specific constants of the subclass
     */
    public int getMAX_AGE()
    {
        return MAX_AGE;
    }
    public double getBREEDING_PROBABILITY()
    {
        return BREEDING_PROBABILITY;
    }
    public int getMAX_OFFSPRING()
    {
        return MAX_OFFSPRING;
    }
    public int getFOOD_VALUE()
    {
        return FOOD_VALUE;
    }
    public int getDEFAULT_FOOD_LEVEL()
    {
        return DEFAULT_FOOD_LEVEL;
    }
    
    /**
     * This is what the admin does most of the time: it hunts for
     * rabbits. In the process, it might breed, die of hunger,
     * or die of old age
     * @param field The field currently occupied.
     * @param newadmines A list to return newly born admins.
     */
    public void act(List<Entity> newAdmin)
    {
        incrementAge();
        incrementHunger();
        if(isAlive()) {
            if (weather.isNight()) {
                giveBirth(newAdmin);
                if (weather.isCold()) 
                    giveBirth(newAdmin);
            }
            move();
        }
    }
    
    /**
     * Look for TAs adjacent to the current location.
     * Only the first live TA is eaten.
     * @return Where food was found, or null if it wasn't.
     */
    protected Location findFood()
    {
        Field field = getField();
        List<Location> adjacent = field.adjacentLocations(getLocation());
        Iterator<Location> it = adjacent.iterator();
        while(it.hasNext()) {
            Location where = it.next();
            Object entity = field.getObjectAt(where);
            if(entity instanceof Lecturer) {
                Lecturer lecturer = (Lecturer) entity;
                if(lecturer.isAlive()) { 
                    lecturer.setDead();
                    if (foodLevel < MAX_FOOD_LEVEL)
                        foodLevel = foodLevel + lecturer.getFOOD_VALUE();
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
    protected void giveBirth(List<Entity> newAdmins)
    {
        // New admins are born into adjacent locations.
        // Get a list of adjacent free locations.
        Field field = getField();
        List<Location> free = field.getFreeAdjacentLocations(getLocation());
        int births = breed();
        for(int b = 0; b < births && free.size() > 0; b++) {
            Location loc = free.remove(0);
            Admin young = new Admin(false, field, loc, weather);
            newAdmins.add(young);
        }
    }
}
