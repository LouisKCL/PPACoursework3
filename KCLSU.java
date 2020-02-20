import java.util.List;
import java.util.Iterator;

/**
 * A simple model of a KCLSU.
 * KCLSU age, move, eat admin, and die,and they can get a disease
 * 
 * @author David J. Barnes and Michael Kölling,Lousi Mellac,Andrei Cinca
 * @version 2016.02.29 (2)
 */
public class KCLSU extends Animal
{
    // Characteristics shared by all KCLSUes (class variables).
    
    // The age to which a KCLSU can live 
    private static final int MAX_AGE = 70 * 24;
    // The likelihood of a KCLSU breeding.
    private static final double BREEDING_PROBABILITY = 0.06;
    // The maximum number of births.
    private static final int MAX_OFFSPRING = 3;
    // Default starting food.
    private static final int DEFAULT_FOOD_LEVEL = 20;
    // The food value of a KCLSU.
    private static final int FOOD_VALUE = 50;
    
    /**
     * Create a KCLSU. A KCLSU can be created as a new born (age zero
     * and not hungry) or with a random age and food level.
     * 
     * @param randomAge If true, the KCLSU will have random age and hunger level.
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public KCLSU(boolean randomAge, Field field, Location location, Weather weather)
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
        return MAX_AGE;
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
     * The KCLSU eats,moves,breeds,can die of old age or hunger,or from a disease
     * @param field The field currently occupied.
     * @param newKCLSUes A list to return newly born KCLSUes.
     */
    public void act(List<Entity> newKCLSU)
    {
        incrementAge();
        incrementHunger();
        developSAD();
        if(isAlive()) {
            fightSAD();
            if (isAlive()) {
               spreadSAD(this);
               giveBirth(newKCLSU);            
               move();  
            }     
        }
    }
    
    /**
     * Look for admins adjacent to the current location.
     * Only the first live admin is eaten.
     * @return Where food was found, or null if it wasn't.
     */
    protected Location findFood()
    {
        Field field = getField();
        List<Location> adjacent = field.adjacentLocations(getLocation());
        Iterator<Location> it = adjacent.iterator();
        while(it.hasNext()) {
            Location where = it.next();
            Object animal = field.getObjectAt(where);
            if(animal instanceof Admin) {
                Admin admin = (Admin) animal;
                if(admin.isAlive()) { 
                    admin.setDead();
                    if (foodLevel < MAX_FOOD_LEVEL)
                        foodLevel = foodLevel + admin.getFOOD_VALUE();
                    return where;
                }
            }
        }
        return null;
    }
    
    /**
     * Check whether or not this KCLSU is to give birth at this step.
     * New births will be made into free adjacent locations.
     * @param newKCLSUs A list to return newly born KCLSUes.
     */
    protected void giveBirth(List<Entity> newKCLSUs)
    {
        // New KCLSUs are born into adjacent locations.
        // Get a list of adjacent free locations.
        Field field = getField();
        List<Location> free = field.getFreeAdjacentLocations(getLocation());
        int births = breed();
        for(int b = 0; b < births && free.size() > 0; b++) {
            Location loc = free.remove(0);
            KCLSU young = new KCLSU(false, field, loc, weather);
            newKCLSUs.add(young);
        }
    }
}
