import java.util.List;
import java.util.Iterator;

/**
 * A class representing the shared characteristics of all animals in the
 * simulation. Animals move, hunt, mate to give birth to other animals of their type,
 * and can catch the Seasonal Affective Dissorder disease.
 * 
 * @author Louis Mellac, Andrei Cinca, David J. Barnes, and Michael Kölling
 * @version 2020.02.18
 */
public abstract class Animal extends Entity
{
    // Maximum amount of food an animal can have.
    protected static final int MAX_FOOD_LEVEL = 100;
    // Constants relating to the S.A.D. disease (Seasonal Affective Dissorder).
    protected static final double PROBABILITY_OF_SAD = 0.003;
    protected static final double PROBABILITY_OF_SPREADING_SAD = 0.10;
    protected static final double PROBABILITY_OF_SURVIVING_SAD = 0.08;
    protected static final double LETHALITY_OF_SAD = 0.05;
    // The current amount of food an animal has.
    protected int foodLevel;
    // Whether or not the animal is infected with S.A.D.
    protected boolean hasSAD = false;
    
    /**
     * Creates a new animal and places it in a field.
     * @param randomAge Whether or not this animal's age should be random.
     * @param location The location within the field.
     * @param field The field currently occupied.
     * @param weather The weather affecting the animal.
     */
    public Animal(boolean randomAge, Location location, Field field, Weather weather) 
    {
        super(randomAge, location, field, weather);
        // If the age is random, then so is the food level.
        if(randomAge)
            foodLevel = rand.nextInt(getFOOD_VALUE());
        else
            foodLevel = getDEFAULT_FOOD_LEVEL();
    }
    

    /**
     * Check whether or not this student is to give birth at this step.
     * New births will be made into free adjacent locations.
     * @param newStudents A list to return newly born students.
     */
    protected void giveBirth(List<Entity> newAnimals)
    {
        // New rabbits are born into adjacent locations.
        // Get a list of adjacent free locations.
        Field field = getField();
        List<Location> free = field.getFreeAdjacentLocations(getLocation());
        int births = breed();
        for(int b = 0; b < births && free.size() > 0; b++) {
            Location loc = free.remove(0);
            Animal newAnimal = makeOffspring(false, field, loc, weather);
            newAnimals.add(newAnimal);
        }
    }
    
    /**
     * Returns an initialised object as an Animal variable to use in the giveBirth method.
     * @param randomAge Whether or not this animal's age should be random.
     * @param location The location within the field.
     * @param field The field currently occupied.
     * @param weather The weather affecting the animal.
     */
    abstract protected Animal makeOffspring(boolean randomAge, Field field, Location loc, Weather weather);


    /**
     * Moves the animal to an adjacent location.
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
     * Looks through adjacent tiles for an entity the animal can eat.
     * @return The location of the first entity it finds. Null if none was found.
     */
    protected Location findFood()
    {
        // Get a list of adjacent locations with entities in them.
        Field field = getField();
        List<Location> adjacent = field.adjacentLocations(getLocation());
        Iterator<Location> it = adjacent.iterator();
        while(it.hasNext()) {
            Location where = it.next();
            Entity food = field.getEntityAt(where);
            // See if the entity is an instance of a class type this animal can eat.
            for (Class<?> foodSource : getFoodSources()) {
                if(foodSource.isInstance(food) && food.isEdible()) {
                    // If it is, kill the entity and return its location.
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
    
    /**
     * @return An array of the different types of entity this animal can eat.
     */
    abstract protected Class<?>[] getFoodSources();

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
     * @return true if the animal is edible (currently, all animals are always edible)
     */
    public boolean isEdible()
    {
        return true;
    }
    
    /**
     * Generate a number representing the number of births, if the animal can breed.
     * @return The number of births (may be zero).
     */
    protected int breed()
    {
        int births = 0;
        if (canBreed(this) && rand.nextDouble() <= getBREEDING_PROBABILITY())
                births = rand.nextInt(getMAX_OFFSPRING()) + 1;
        return births;
    }
    
    /**
     * Checks if an animal can breed with another.
     * @param animal The animal to check breeding compatibility with.
     * @return true if the animal is able to create offspring.
     */
    abstract protected boolean canBreed(Animal animal);

    /**
     * This method determines whether or not the current animal will develop the disease
     * by itself.
     */
    protected void developSAD()
    {
        if (rand.nextDouble() <= PROBABILITY_OF_SAD)
            hasSAD = true;
    }
    
    /**
     * This method enables the infected animals to spread the disease to other animals (of the same class) around them.
     * @param currentAnimal the animal with the disease attempting to spread it.
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
    
    /**
     * Infects an animal with the disease.
     */
    protected void infect()
    {
        hasSAD = true;
    }
    
    // Methods for getting the constants of the subclasses (and ensuring they have those constants).
    /**
     * @return the probability that an animal can breed.
     */
    protected abstract double getBREEDING_PROBABILITY();
    /**
     * @return the maximum number of offspring this animal can have.
     */
    protected abstract int getMAX_OFFSPRING();
    /**
     * @return the default food level this animal starts with.
     */
    protected abstract int getDEFAULT_FOOD_LEVEL();
}