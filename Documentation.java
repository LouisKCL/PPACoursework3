import java.util.List;

/**
 * A model of a documentation plant.
 * Documentations age, spread, and die.
 * 
 * @author Louis Mellac and Andrei Cinca
 * @version 2020.02.20
 */
public class Documentation extends Plant
{
    // The maximum age of a documentation plant.
    private static final int MAX_AGE = 35* 24;
    // The likelihood of a documentation plant spreading successfully.
    private static final double SEEDING_PROBABILITY = 0.003;
    // The maximum number of new seeds a documentation plant can make.
    private static final int MAX_SEEDLINGS = 5;
    // The age at which a documentation plant can start spreading seeds.
    private static final int SEEDING_AGE = 30;
    // The age at which a documentation plant can be eaten.
    private static final int EDIBLE_AGE = 20;
    // The amount of food an animal gets when eating a documentation plant.
    private static final int FOOD_VALUE = 10;
    
    /**
     * Creates a new documentation plant and places it a field.
     * @param randomAge Whether or not this documentation's age should be random.
     * @param field The field to place the documentation in.
     * @param locaion The location in the field to put the documentation.
     * @param weather The weather affecting the documentation's behaviour.
     */
    public Documentation(boolean randomAge, Field field, Location location, Weather weather)
    {
        super(randomAge, field, location, weather);
    }
    
    /**
     * Create a Documentation but return it as a Plant object.
     * @param randomAge If true, the documentation will have random age.
     * @param field The field currently occupied.
     * @param loc The location within the field.
     * @param weather The weather affecting this documentation's behaviour.
     */
    protected Plant buildSeed(boolean randomAge, Field field, Location loc, Weather weather)
    {
        return new Documentation(randomAge, field, loc, weather);
    }
        
    //Methods used to return constants to superclasses
    /**
     * @return the maximum age a documentation plant can have.
     */
    protected int getMAX_AGE() {return MAX_AGE;}
    /**
     * @return the maximum number of seedlings a documentation plant can create.
     */
    protected int getMAX_SEEDLINGS() {return MAX_SEEDLINGS;}
    /**
     * @return the age at which a documentation plant can start creating seeds.
     */
    protected int getSEEDING_AGE() {return SEEDING_AGE;}
    /**
     * @return the age at which a documentation plant is edible.
     */
    protected int getEDIBLE_AGE() {return EDIBLE_AGE;}
    /**
     * @return the amount of food an animal gets when eating a documentation plant.
     */
    protected int getFOOD_VALUE() {return FOOD_VALUE;}
    /**
     * @return the probability that a documentation plant will create a successfull seed.
     */
    protected double getSEEDING_PROBABILITY() {return SEEDING_PROBABILITY;}
}
