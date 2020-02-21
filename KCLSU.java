import java.util.List;
import java.util.Iterator;

/**
 * A simple model of a KCLSU.
 * KCLSU age, move, eat admin, and die,and they can get a disease
 * 
 * @author David J. Barnes and Michael Kölling,Lousi Mellac,Andrei Cinca
 * @version 2016.02.29 (2)
 */
public class KCLSU extends Animal
{
    // Characteristics shared by all KCLSUes (class variables).
    
    // The age to which a KCLSU can live 
    private static final int MAX_AGE = 50 * 24;
    // The likelihood of a KCLSU breeding.
    private static final double BREEDING_PROBABILITY = 0.007;
    // The maximum number of births.
    private static final int MAX_OFFSPRING = 3;
    // Default starting food.
    private static final int DEFAULT_FOOD_LEVEL = 20;
    // The food value of a KCLSU.
    private static final int FOOD_VALUE = 50;
    
    /**
     * Create a KCLSU. A KCLSU can be created as a new born (age zero
     * and not hungry) or with a random age and food level.
     * 
     * @param randomAge If true, the KCLSU will have random age and hunger level.
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public KCLSU(boolean randomAge, Field field, Location location, Weather weather)
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
     * The KCLSU eats,moves,breeds,can die of old age or hunger,or from a disease
     * @param field The field currently occupied.
     * @param newKCLSUes A list to return newly born KCLSUes.
     */
    public void act(List<Entity> newKCLSU)
    {
        incrementAge();
        incrementHunger();
        developSAD();
        if(isAlive()) {
            fightSAD();
            if (isAlive()) {
               spreadSAD(this);
               giveBirth(newKCLSU);            
               move();  
            }     
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
        return new KCLSU(randomAge, field, loc, weather);
    }

    protected boolean canBreed(Animal animal)
    {
        return true;
    }
    
    public Class<?>[] getFoodSources()
    {
        Class<?>[] foodSource = {Admin.class};
        return foodSource;
    }
    
    // These methods return the constants specific to this class to any superclass.
    /**
     * @return the maximum age a KCLSU can have.
     */
    protected int getMAX_AGE() {return MAX_AGE;}
    /**
     * @return the probabbility that two KCLSUs breed.
     */
    protected double getBREEDING_PROBABILITY() {return BREEDING_PROBABILITY;}
    /**
     * @return the maximum number of offspring a KCLSU can have.
     */
    protected int getMAX_OFFSPRING() {return MAX_AGE;}
    /**
     * @return the amount of food an animal would get from eating a KCLSU.
     */
    protected int getFOOD_VALUE() {return FOOD_VALUE;}
    /**
     * @return the default starting food level for a KCLSU.
     */
    protected int getDEFAULT_FOOD_LEVEL() {return DEFAULT_FOOD_LEVEL;}
}
