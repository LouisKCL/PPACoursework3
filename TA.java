import java.util.List;
import java.util.Iterator;

/**
 * A model of a Teaching Assistant (TA)
 * TAs eat plants,die of hunger or old ages,move and breed
 * 
 * @author David J. Barnes, Michael Kölling, Louis Mellac, Andrei Cinca
 * @version 2020.02.11
 */
public class TA extends GenderedAnimal
{
    // Maximum age of a TA.
    private static final int MAX_AGE = 40 * 24;
    // The likelihood of a TA breeding.
    private static final double BREEDING_PROBABILITY = 0.05;
    // The maximum number of births.
    private static final int MAX_OFFSPRING = 3;
    // Food value of a TA
    private static final int FOOD_VALUE = 20;
    // Default starting food level of a TA.
    private static final int DEFAULT_FOOD_LEVEL = 35;

    /**
     * Create a new rabbit. A TA may be created with age
     * zero (a new born) or with a random age.
     * 
     * @param randomAge If true, the TA will have a random age.
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public TA(boolean randomAge, Field field, Location location, boolean isFemale, Weather weather)
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
    
    /**
     * TAs can move,breed,eat,die of old age or hunger
     * @param newTAs A list to return newly born TA.
     */
    public void act(List<Entity> newTA)
    {
        incrementAge();
        incrementHunger();
        if(isAlive()) {
            if (!weather.isCold())
                giveBirth(newTA);       
            move();
        }
    }

    /**
     * Creates a new TA but returns it in an Entity variable.
     * @param randomAge If true, the TA will have a random age.
     * @param field The field currently occupied.
     * @param location The location within the field.
     * @param gender Whether or not this is a female.
     * @param weather The weather affecting this TA's behaviour.
     */
    protected Entity buildGenderedOffspring(boolean randomAge, Field field, Location loc, boolean gender, Weather weather)
    {
        return new TA(randomAge, field, loc, gender, weather);
    }
    
    public Class<?>[] getFoodSources()
    {
        Class<?>[] foodSource = {Documentation.class};
        return foodSource;
    }
    
    // These methods return the constants specific to this class to any superclass.
    /**
     * @return the maximum age a TA can have.
     */
    protected int getMAX_AGE() {return MAX_AGE;}
    /**
     * @return the probabbility that two TAs breed.
     */
    protected double getBREEDING_PROBABILITY() {return BREEDING_PROBABILITY;}
    /**
     * @return the maximum number of offspring a TA can have.
     */
    protected int getMAX_OFFSPRING() {return MAX_AGE;}
    /**
     * @return the amount of food an animal would get from eating a TA.
     */
    protected int getFOOD_VALUE() {return FOOD_VALUE;}
    /**
     * @return the default starting food level for a TA.
     */
    protected int getDEFAULT_FOOD_LEVEL() {return DEFAULT_FOOD_LEVEL;}
}
