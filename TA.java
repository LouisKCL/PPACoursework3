import java.util.List;

/**
 * A simple model of a teaching assistant (TA).
 * TAs age, move, eat documentation, reproduce, and die.
 * 
 * @author Louis Mellac, Andrei Cinca, David J. Barnes, and Michael Kölling.
 * @version 2020.02.20
 */
public class TA extends GenderedAnimal
{
    // Maximum age of a TA.
    private static final int MAX_AGE = 40 * 24;
    // The likelihood of a TA breeding.
    private static final double BREEDING_PROBABILITY = 0.04;
    // The maximum number of births.
    private static final int MAX_OFFSPRING = 3;
    // Food value of a TA
    private static final int FOOD_VALUE = 20;
    // Default starting food level of a TA.
    private static final int DEFAULT_FOOD_LEVEL = 35;

    /**
     * Create a TA. A TA can be created as a new born (age zero
     * and not hungry) or with a random age and food level.
     * @param randomAge If true, the TA will have random age and hunger level.
     * @param field The field currently occupied.
     * @param location The location within the field.
     * @param isFemale Whether or not the TA is female.
     * @param weather The weather affecting this TA's behaviour.
     */
    public TA(boolean randomAge, Field field, Location location, boolean isFemale, Weather weather)
    {
        super(randomAge, location, field, isFemale, weather);
    }
    
    /**
     * Make the TA act. TAs age, get hungrier, give birth if it not cold, and move.
     * @param newTAs A list to return newly born TAs.
     */
    public void act(List<Entity> newTAs)
    {
        incrementAge();
        incrementHunger();
        if(isAlive()) {
            if (!weather.isCold())
                giveBirth(newTAs);       
            move();
        }
    }

    /**
     * Create a TA but return it as an Animal object.
     * @param randomAge If true, the TA will have random age and hunger level.
     * @param field The field currently occupied.
     * @param location The location within the field.
     * @param isFemale Whether or not the TA is female.
     * @param weather The weather affecting this TA's behaviour.
     */
    protected Animal makeOffspring(boolean randomAge, Field field, Location loc, Weather weather)
    {
        return new TA(randomAge, field, loc, rand.nextBoolean(), weather);
    }
    
    /**
     * @return an array of the types of entities students can eat (currently only grades).
     */
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
    protected int getMAX_OFFSPRING() {return MAX_OFFSPRING;}
    /**
     * @return the amount of food an animal would get from eating a TA.
     */
    protected int getFOOD_VALUE() {return FOOD_VALUE;}
    /**
     * @return the default starting food level for a TA.
     */
    protected int getDEFAULT_FOOD_LEVEL() {return DEFAULT_FOOD_LEVEL;}
}
