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
    private static final double BREEDING_PROBABILITY = 0.04;
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
                if (weather.isHot()) 
                    giveBirth(newAdmin);
            }
            move();
        }
    }
    
     /**
     * Creates a new lecturer but returns it in an Entity variable.
     * @param randomAge If true, the lecturer will have a random age.
     * @param field The field currently occupied.
     * @param location The location within the field.
     * @param gender Whether or not this is a female.
     * @param weather The weather affecting this lecturer's behaviour.
     */
    protected Entity buildOffspring(boolean randomAge, Field field, Location loc, Weather weather)
    {
        return new Admin(randomAge, field, loc, weather);
    }

    protected boolean canBreed(Animal animal)
    {
        return true;
    }
    
    public Class<?>[] getFoodSources()
    {
        Class<?>[] foodSource = {Lecturer.class, TA.class};
        return foodSource;
    }
    
    // These methods return the constants specific to this class to any superclass.
    /**
     * @return the maximum age a admin can have.
     */
    protected int getMAX_AGE() {return MAX_AGE;}
    /**
     * @return the probabbility that two admins breed.
     */
    protected double getBREEDING_PROBABILITY() {return BREEDING_PROBABILITY;}
    /**
     * @return the maximum number of offspring a admin can have.
     */
    protected int getMAX_OFFSPRING() {return MAX_AGE;}
    /**
     * @return the amount of food an animal would get from eating a admin.
     */
    protected int getFOOD_VALUE() {return FOOD_VALUE;}
    /**
     * @return the default starting food level for a admin.
     */
    protected int getDEFAULT_FOOD_LEVEL() {return DEFAULT_FOOD_LEVEL;}
}
