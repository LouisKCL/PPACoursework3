import java.util.List;
import java.util.Iterator;

/**
 * A simple model of a rabbit.
 * Rabbits age, move, breed, and die.
 * We will all die eventually.
 * 
 * @author David J. Barnes, Michael Kölling, Louis Mellac, Andrei Cinca
 * @version 2020.02.11
 */
public class Student extends GenderedAnimal
{
    // Characteristics shared by all rabbits (class variables).

    // Maximum age of a student (100 days).
    private static final int MAX_AGE = 40 * 24;
    // The likelihood of a student breeding.
    private static final double BREEDING_PROBABILITY = 0.1;
    // The maximum number of births.
    private static final int MAX_OFFSPRING = 5;
    // Food value of a student
    private static final int FOOD_VALUE = 20;
    // Default starting food level of a student.
    private static final int DEFAULT_FOOD_LEVEL = 30;

    /**
     * Create a new rabbit. A rabbit may be created with age
     * zero (a new born) or with a random age.
     * 
     * @param randomAge If true, the rabbit will have a random age.
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public Student(boolean randomAge, Field field, Location location, boolean isFemale, Weather weather)
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
     * This is what the rabbit does most of the time - it runs 
     * around. Sometimes it will breed or die of old age.
     * @param newRabbits A list to return newly born rabbits.
     */
    public void act(List<Entity> newStudents)
    {
        incrementAge();
        incrementHunger();
        developSAD();
        if(isAlive()) {
            fightSAD();
            if (isAlive()) {
               spreadSAD(this);
               if (!weather.isNight() && !weather.isHot()) {
                    giveBirth(newStudents);
               }
               move();  
            }  
        }
    }
    
    /**
     * Look for rabbits adjacent to the current location.
     * Only the first live rabbit is eaten.
     * @return Where food was found, or null if it wasn't.
     */
    protected Location findFood()
    {
        Field field = getField();
        List<Location> adjacent = field.adjacentLocations(getLocation());
        Iterator<Location> it = adjacent.iterator();
        while(it.hasNext()) {
            Location where = it.next();
            Object food = field.getObjectAt(where);
            if(food instanceof Grade) {
                Grade grade = (Grade) food;
                if(grade.isAlive() && grade.isEdible()) { 
                    grade.setDead();
                    if (foodLevel < MAX_FOOD_LEVEL)
                        foodLevel = foodLevel + grade.getFOOD_VALUE();
                    return where;
                }
            }
        }
        return null;
    }
    
    /**
     * Check whether or not this rabbit is to give birth at this step.
     * New births will be made into free adjacent locations.
     * @param newRabbits A list to return newly born rabbits.
     */
    protected void giveBirth(List<Entity> newStudents)
    {
        // New rabbits are born into adjacent locations.
        // Get a list of adjacent free locations.
        Field field = getField();
        List<Location> free = field.getFreeAdjacentLocations(getLocation());
        int births = genderedBreed();
        for(int b = 0; b < births && free.size() > 0; b++) {
            Location loc = free.remove(0);
            Student young = new Student(false, field, loc, rand.nextBoolean(), weather);
            newStudents.add(young);
        }
    }

}
