import java.util.List;
import java.util.Random;

/**
 * This class describes represents the characteristics of a documentation plant.
 *
 * @author Louis Mellac and Andrei Cinca
 * @version 2020.02.20
 */
public class Documentation extends Plant
{
    // The maximum age of a documentation plant.
    private static final int MAX_AGE = 35* 24;
    // The likelihood of a documentation plant spreading successfully.
    private static final double SEEDING_PROBABILITY = 0.04;
    // The maximum number of new seeds a documentation plant can make.
    private static final int MAX_SEEDLINGS = 5;
    // The age at which a documentation plant can start spreading seeds.
    private static final int SEEDING_AGE = 30;
    // The age at which a documentation plant can be eaten.
    private static final int EDIBLE_AGE = 20;
    // The amount of food an animal gets when eating a documentation plant.
    private static final int FOOD_VALUE = 10;

    public Documentation(Field field, Location location, Weather weather)
    {
        super(field,location,weather);
    }
    
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
