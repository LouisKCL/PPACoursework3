import java.util.List;
import java.util.Random;
/**
 * This is the class for Grades (a type plant).
 * It currently only holds and returns constants specific to the Grade plant.
 *
 * @author Louis Mellac, Andrei Cinca
 * @version 2020.02.21
 */
public class Grade extends Plant
{
    //The maximum age of a grade plant.
    private static final int MAX_AGE = 35 * 24;
    // The likelihood of a grade seed successfully taking hold.
    private static final double SEEDING_PROBABILITY = 0.03;
    // The maximum number of seedlings of a grade.
    private static final int MAX_SEEDLINGS = 5;
    // The age at which a grade plant can start making seeds.
    private static final int SEEDING_AGE = 35;
    // The age at which a grade plant is edible.
    private static final int EDIBLE_AGE = 25;
    // The amount of food that eating a grade plant provides.
    private static final int FOOD_VALUE = 15;
    
    /**
     * Creates a new grade plant and places it a field.
     * @param field The field to place the gradde in.
     * @param locaion The location in the field to put the grade.
     * @param weather The weather affecting the grade's behaviour.
     */
    public Grade(Field field, Location location, Weather weather)
    {
        super(field,location,weather);
    }
    
    //Methods used to return constants to superclasses
    /**
     * @return the maximum age a grade plant can have.
     */
    protected int getMAX_AGE() {return MAX_AGE;}
    /**
     * @return the maximum number of seedlings a grade plant can create.
     */
    protected int getMAX_SEEDLINGS() {return MAX_SEEDLINGS;}
    /**
     * @return the age at which a grade plant can start creating seeds.
     */
    protected int getSEEDING_AGE() {return SEEDING_AGE;}
    /**
     * @return the age at which a grade plant is edible.
     */
    protected int getEDIBLE_AGE() {return EDIBLE_AGE;}
    /**
     * @return the amount of food an animal gets when eating a grade plant.
     */
    protected int getFOOD_VALUE() {return FOOD_VALUE;}
    /**
     * @return the probability that a grade plant will create a successfull seed.
     */
    protected double getSEEDING_PROBABILITY() {return SEEDING_PROBABILITY;}
}
