import java.util.List;
import java.util.Iterator;

/**
 * This is class lecturer
 * they eat,breed,die of old age or hunger
 * 
 * @author David J. Barnes and Michael Kölling
 * @version 2016.02.29 (2)
 */
public class Lecturer extends GenderedAnimal
{
    // Characteristics shared by all foxes (class variables).
    
    // The age to which a Lecturer can live (200 days).
    private static final int MAX_AGE = 80 * 24;
    // The likelihood of a fox breeding.
    private static final double BREEDING_PROBABILITY = 0.08;
    // The maximum number of births.
    private static final int MAX_OFFSPRING = 2;
    // Default starting food.
    private static final int DEFAULT_FOOD_LEVEL = 30;
    // Food value for lecturers.
    private static final int FOOD_VALUE = 20;

    /**
     * Create a lecturer. A lecturer can be created as a new born (age zero
     * and not hungry) or with a random age and food level.
     * 
     * @param randomAge If true, the lecturer will have random age and hunger level.
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public Lecturer(boolean randomAge, Field field, Location location, boolean isFemale, Weather weather)
    {
        super(location, field, isFemale, weather);
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
     * Look for students adjacent to the current location.
     * Only the first live student is eaten.
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
            if(animal instanceof Student) {
                Student prey = (Student) animal;
                if(prey.isAlive()) { 
                    prey.setDead();
                    if (foodLevel < MAX_FOOD_LEVEL)
                        foodLevel = foodLevel + prey.getFOOD_VALUE();
                    return where;
                }
            }
        }
        return null;
    }
    
    /**
     * This is what the lecturer does most of the time: it hunts for
     * students. In the process, it might breed, die of hunger,
     * or die of old age.
     * @param field The field currently occupied.
     * @param newLecturers A list to return newly born students.
     */
    public void act(List<Entity> newLecturer)
    {
        incrementAge();
        incrementHunger();
        if(isAlive()) {
            if (weather.isNight()) {
                giveBirth(newLecturer); 
            }          
            move();
            if(isAlive() && weather.isCold())
                move();
        }
    }
    
    /**
     * Check whether or not this lecturers is to give birth at this step.
     * New births will be made into free adjacent locations.
     * @param newLecturers A list to return newly born lecturers.
     */
    protected void giveBirth(List<Entity> newLecturers)
    {
        // New lecturers are born into adjacent locations.
        // Get a list of adjacent free locations.
        Field field = getField();
        List<Location> free = field.getFreeAdjacentLocations(getLocation());
        int births = genderedBreed();
        for(int b = 0; b < births && free.size() > 0; b++) {
            Location loc = free.remove(0);
            Lecturer young = new Lecturer(false, field, loc, rand.nextBoolean(), weather);
            newLecturers.add(young);
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
}
