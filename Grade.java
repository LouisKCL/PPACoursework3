import java.util.List;
import java.util.Random;
/**
 * This is the class for Grades(a type of class Plant)
 *
 * @author Louis Mellac, Andrei Cinca
 * 
 */
public class Grade extends Plant
{
    //The maximum age of a grade plant.
    private static final int MAX_AGE = 35 * 24;
    // The likelihood of a seed successfully taking hold.
    private static final double SEEDING_PROBABILITY = 0.05;
    // The maximum number of seedlings of a grade.
    private static final int MAX_SEEDLINGS = 6;
    // The age at which a grade plant can start making seeds.
    private static final int SEEDING_AGE=35;
    // The age at which a grade plant is edible.
    private static final int EDIBLE_AGE = 25;
    // The amount of food that eating a grade plant provides.
    private static final int FOOD_VALUE = 15;
    
    /**
     * Creates a new grade plant.
     * @param field The field to place the plant in.
     * @param locaion The location in the field to put the plant.
     * @param weather The weather affecting the plant's behaviour.
     */
    public Grade(Field field, Location location, Weather weather)
    {
        super(field,location,weather);
    }
    
    /**
     * All of the methods below ar being used to return constants to the superclass
     */
    public int getMAX_AGE()
    {
        return MAX_AGE;
    }
    public int getMAX_SEEDLINGS()
    {
        return MAX_SEEDLINGS;
    }
    public int getSEEDING_AGE()
    {
        return SEEDING_AGE;
    }
    public int getEDIBLE_AGE()
    {
        return EDIBLE_AGE;
    }
    public int getFOOD_VALUE()
    {
        return FOOD_VALUE;
    }
    public double getSEEDING_PROBABILITY()
    {
        return SEEDING_PROBABILITY;
    }
}
