import java.util.List;
import java.util.Iterator;
import java.util.Random;

/**
 * A simple model of a KCLSU.
 * KCLSU age, move, eat admin, and die.
 * 
 * @author David J. Barnes and Michael Kölling
 * @version 2016.02.29 (2)
 */
public class KCLSU extends Animal
{
    // Characteristics shared by all KCLSUes (class variables).
    
    // The age to which a KCLSU can live (90 days).
    private static final int MAX_AGE = 70 * 24;
    // The likelihood of a KCLSU breeding.
    private static final double BREEDING_PROBABILITY = 0.06;
    // The maximum number of births.
    private static final int MAX_LITTER_SIZE = 3;
    // A shared random number generator to control breeding.
    private static final Random rand = Randomizer.getRandom();
    // Default starting food.
    private static final int DEFAULT_FOOD_LEVEL = 20;
    

    // Individual characteristics (instance fields).
    // The KCLSU's age.
    private int age;
    // The KCLSU's food level, which is increased by eating rabbits.
    private int foodLevel;
    
    private boolean hasSAD = false;

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
    
    private void move()
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
    
    /**
     * This is what the KCLSU does most of the time: it hunts for
     * rabbits. In the process, it might breed, die of hunger,
     * or die of old age.
     * @param field The field currently occupied.
     * @param newKCLSUes A list to return newly born KCLSUes.
     */
    public void act(List<Animal> newKCLSU)
    {
        incrementAge();
        incrementHunger();
        getInfected();
        if(isAlive()) {
            giveBirth(newKCLSU);            
            // Move towards a source of food if found.
            move();
            if (hasSAD) {
                System.out.println("Is infected");
                infect();
                if (rand.nextDouble() <= LETHALITY_OF_SAD) {
                    setDead();
                    System.out.println("Is dead.");
                }
            }           
        }
    }
    
    /**
     * Spread
     */
    private void infect()
    {
        Field field = getField();
        List<Location> adjacent = field.adjacentLocations(getLocation());
        Iterator<Location> it = adjacent.iterator();
        while(it.hasNext()) {
            Location where = it.next();
            Object animal = field.getObjectAt(where);
            if(animal instanceof KCLSU) {
                Animal kclsu = (Animal) animal;
                if(kclsu.isAlive()) { 
                    if(rand.nextDouble() <= PROBABILITY_OF_SAD_SPREAD)
                        kclsu.makeSAD();
                }
            }
        } 
    }
    
    /**
     * Increase the age. This could result in the KCLSU's death.
     */
    private void incrementAge()
    {
        age++;
        if(age > MAX_AGE) {
            setDead();
        }
    }
    
    /**
     * Make this KCLSU more hungry. This could result in the KCLSU's death.
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
            if(animal instanceof Admin) {
                Admin admin = (Admin) animal;
                if(admin.isAlive()) { 
                    admin.setDead();
                    if (foodLevel < MAX_FOOD_LEVEL)
                        foodLevel = foodLevel + admin.getFoodValue();
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
    private void giveBirth(List<Animal> newKCLSUs)
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
}
