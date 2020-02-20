import java.util.List;

/**
 * A class representing the shared characteristics of all animals in the
 * simulation.
 * 
 * @author Louis Mellac, Andrei Cinca, David J. Barnes, and Michael Kölling
 * @version 2020.02.18
 */
public abstract class Animal extends Entity
{
    // Maximum amount of food an animal can have.
    protected static final int MAX_FOOD_LEVEL = 100;
    // The amount of food the animal currently has.
    protected static final double PROBABILITY_OF_SAD = 0.005;
    protected static final double PROBABILITY_OF_SPREADING_SAD = 0.10;
    protected static final double PROBABILITY_OF_SURVIVING_SAD = 0.08;
    protected static final double LETHALITY_OF_SAD = 0.05;
    
    protected int foodLevel;
    
    protected boolean hasSAD = false;
    
    /**
     * Creates a new animal and places it in a field.
     * 
     * @param location The location within the field.
     * @param field The field currently occupied.
     * @param weather The weather affecting the animal.
     */
    public Animal(Location location, Field field, Weather weather) 
    {
        super(location, field, weather);
    }
    
    // Methods for getting the constants of the subclasses (and ensuring they have those constants).
    protected abstract double getBREEDING_PROBABILITY();
    protected abstract int getMAX_OFFSPRING();
    protected abstract int getDEFAULT_FOOD_LEVEL();
    
    protected abstract Location findFood();

    /**
     * enables the animal classes to move,eat,infect and breed
     */
    protected void move()
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
     * Make this animal more hungry. This could result in the animal's death.
     */
    protected void incrementHunger()
    {
        foodLevel--;
        if(foodLevel <= 0) {
            setDead();
        }
    }
    
    /**
     * Generate a number representing the number of births,
     * if it can breed.
     * @return The number of births (may be zero).
     */
    protected int breed()
    {
        int births = 0;
        if(rand.nextDouble() <= getBREEDING_PROBABILITY()) {
            births = rand.nextInt(getMAX_OFFSPRING()) + 1;
        }
        return births;
    }
    
    /**
     * this method determines whether or not the current animal will catch the disease
     */
    protected void developSAD()
    {
        if (rand.nextDouble() <= PROBABILITY_OF_SAD)
            hasSAD = true;
    }
    
    /**
     * This method enables the infected animals to spread the disease to other animals(of the same class) around them.
     * There is a probability for the spreading as well
     */
    protected void spreadSAD(Animal currentAnimal)
    {
        if (hasSAD) {
            List<Location> potentialSpreads = field.getFullAdjacentLocations(location);
            for (Location potentialSpread : potentialSpreads) {
                Object spread = field.getObjectAt(potentialSpread); 
                // Checks to see if the object in the adjacent location is of the same class.
                if (spread.getClass() == currentAnimal.getClass()) {
                    Animal castedSpread = (Animal) spread;
                    if(rand.nextDouble() <= PROBABILITY_OF_SPREADING_SAD) {
                        castedSpread.infect();
                    }
                }
            }
        }
            
    }
    
    /**
     * this method changes the value of the infected state to true
     */
    protected void infect()
    {
        hasSAD = true;
    }
    
    /**
     * This method checks whether or not the infected animal dies or not.If they don't die, there is a chance that they can be cured.
     */
    protected void fightSAD()
    {
        if (hasSAD) {
            if (rand.nextDouble() <= LETHALITY_OF_SAD) {
               
                setDead();
            }
            if (rand.nextDouble() <= PROBABILITY_OF_SURVIVING_SAD) {
                
                hasSAD = false; 
            }
        }
    }
}