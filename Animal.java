import java.util.List;
import java.util.Iterator;

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
    // Constants relating to the S.A.D. disease (Seasonal Affective Dissorder).
    protected static final double PROBABILITY_OF_SAD = 0.005;
    protected static final double PROBABILITY_OF_SPREADING_SAD = 0.10;
    protected static final double PROBABILITY_OF_SURVIVING_SAD = 0.08;
    protected static final double LETHALITY_OF_SAD = 0.05;
    // The current amount of food an animal has.
    protected int foodLevel;
    // Whether or not the animal is infected with S.A.D.
    protected boolean hasSAD = false;
    
    /**
     * Creates a new animal and places it in a field.
     * @param location The location within the field.
     * @param field The field currently occupied.
     * @param weather The weather affecting the animal.
     */
    public Animal(Location location, Field field, Weather weather) 
    {
        super(location, field, weather);
    }
    
    abstract protected Entity buildOffspring(boolean randomAge, Field field, Location loc, Weather weather);

    /**
     * Check whether or not this student is to give birth at this step.
     * New births will be made into free adjacent locations.
     * @param newStudents A list to return newly born students.
     */
    protected void giveBirth(List<Entity> newEntities)
    {
        // New rabbits are born into adjacent locations.
        // Get a list of adjacent free locations.
        Field field = getField();
        List<Location> free = field.getFreeAdjacentLocations(getLocation());
        int births = breed();
        for(int b = 0; b < births && free.size() > 0; b++) {
            Location loc = free.remove(0);
            Entity newEntity = buildOffspring(false, field, loc, weather);
            newEntities.add(newEntity);
        }
    }

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
     * Look for grades adjacent to the current location.
     * Only the first live grade is eaten.
     * @return Where food was found, or null if it wasn't.
     */
    protected Location findFood()
    {
        Field field = getField();
        List<Location> adjacent = field.adjacentLocations(getLocation());
        Iterator<Location> it = adjacent.iterator();
        while(it.hasNext()) {
            Location where = it.next();
            Entity food = field.getEntityAt(where);
            for (Class<?> foodSource : getFoodSources()) {
                if(foodSource.isInstance(food) && food.isEdible()) {
                    if(food.isAlive()) { 
                        food.setDead();
                        if (foodLevel < MAX_FOOD_LEVEL)
                            foodLevel = foodLevel + food.getFOOD_VALUE();
                        return where;
                    }
                }   
            }
        }
        return null;
    }
    
    abstract public Class<?>[] getFoodSources();

    /**
     * Make this animal more hungry. This could result in the animal's death.
     */
    protected void incrementHunger()
    {
        foodLevel--;
        if(foodLevel <= 0)
            setDead();
    }
    
    /**
     * @return true if the animal is edible (in this case, all animals are always edible)
     */
    public boolean isEdible()
    {
        return true;
    }
    
    /**
     * Generate a number representing the number of births,
     * if it can breed.
     * @return The number of births (may be zero).
     */
    protected int breed()
    {
        int births = 0;
        if (canBreed(this) && rand.nextDouble() <= getBREEDING_PROBABILITY())
                births = rand.nextInt(getMAX_OFFSPRING()) + 1;
        return births;
    }
    
    abstract protected boolean canBreed(Animal animal);

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
                Entity spread = field.getEntityAt(potentialSpread); 
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
     * This method checks whether or not the infected animal dies of SAD.
     * If they don't die, there is a chance they can be cured.
     */
    protected void fightSAD()
    {
        if (hasSAD) {
            if (rand.nextDouble() <= LETHALITY_OF_SAD)
                setDead();
            else if (rand.nextDouble() <= PROBABILITY_OF_SURVIVING_SAD)
                hasSAD = false; 
        }
    }
    
    // Methods for getting the constants of the subclasses (and ensuring they have those constants).
    protected abstract double getBREEDING_PROBABILITY();
    protected abstract int getMAX_OFFSPRING();
    protected abstract int getDEFAULT_FOOD_LEVEL();
}