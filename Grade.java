import java.util.List;

/**
 * A model of a grade plant.
 * Grades age, spread, and die.
 * 
 * @author Louis Mellac and Andrei Cinca
 * @version 2020.02.20
 */
public class Grade extends Plant
{
    //The maximum age of a grade plant.
    private static final int MAX_AGE = 35 * 24;
    // The likelihood of a grade seed successfully taking hold.
    private static final double SEEDING_PROBABILITY = 0.005;
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
     * @param randomAge Whether or not this grade's age should be random.
     * @param field The field to place the grade in.
     * @param locaion The location in the field to put the grade.
     * @param weather The weather affecting the grade's behaviour.
     */
    public Grade(boolean randomAge, Field field, Location location, Weather weather)
    {
        super(randomAge, field, location, weather);
    }
    
    /**
     * Create a Grade but return it as a Plant object.
     * @param randomAge If true, the grade will have random age.
     * @param field The field currently occupied.
     * @param loc The location within the field.
     * @param weather The weather affecting this grade's behaviour.
     */
    protected Plant buildSeed(boolean randomAge, Field field, Location loc, Weather weather)
    {
        return new Grade(randomAge, field, loc, weather);
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
