import java.util.List;

/**
 * A simple model of an admin.
 * Admins age, move, eat TAs, reproduce, and die.
 * 
 * @author Louis Mellac, Andrei Cinca, David J. Barnes, and Michael Kölling,
 * @version 2020.02.20
 */
public class Admin extends Animal
{
    // Characteristics shared by all admins (class variables).
    
    // The age to which a admin can live.
    private static final int MAX_AGE = 80 * 24;
    // The likelihood of a admin breeding.
    private static final double BREEDING_PROBABILITY = 0.03;
    // The maximum number of births.
    private static final int MAX_OFFSPRING = 2;
    // Default starting food.
    private static final int DEFAULT_FOOD_LEVEL = 30;
    // Food value for admins.
    private static final int FOOD_VALUE = 40;

    /**
     * Create an admin. An admin can be created as a new born (age zero
     * and not hungry) or with a random age and food level.
     * @param randomAge If true, the admin will have random age and hunger level.
     * @param field The field currently occupied.
     * @param location The location within the field.
     * @param weather The weather affecting this admin's behaviour.
     */
    public Admin(boolean randomAge, Field field, Location location, Weather weather)
    {
        super(randomAge, location, field, weather);
    }
    
    /**
     * Make the Admin act. Admins age, get hungrier, give birth (twice if it is hot), and move.
     * @param newAdmin A list to return newly born admin.
     */
    public void act(List<Entity> newAdmins)
    {
        incrementAge();
        incrementHunger();
        if(isAlive()) {
            if (weather.isNight()) {
                giveBirth(newAdmins);
                if (weather.isHot()) 
                    giveBirth(newAdmins);
            }
            move();
        }
    }
    
    /**
     * Create an admin but return it as an Animal object.
     * @param randomAge If true, the admin will have random age and hunger level.
     * @param field The field currently occupied.
     * @param location The location within the field.
     * @param weather The weather affecting this admin's behaviour.
     */
    protected Animal makeOffspring(boolean randomAge, Field field, Location loc, Weather weather)
    {
        return new Admin(randomAge, field, loc, weather);
    }

    /**
     * Checks if this admin can breed with the given animal.
     * @return true if the given animal is an admin
     */
    protected boolean canBreed(Animal animal)
    {
        return (animal instanceof Admin);
    }
    
    /**
     * @return an array of the types of entities admins can eat (currently lecturers and TAs).
     */
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
    protected int getMAX_OFFSPRING() {return MAX_OFFSPRING;}
    /**
     * @return the amount of food an animal would get from eating a admin.
     */
    protected int getFOOD_VALUE() {return FOOD_VALUE;}
    /**
     * @return the default starting food level for a admin.
     */
    protected int getDEFAULT_FOOD_LEVEL() {return DEFAULT_FOOD_LEVEL;}
}
