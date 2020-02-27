import java.util.List;

/**
 * A simple model of a KCLSU student.
 * KCLSU students age, move, eat admin, catch S.A.D., reproduce, and die.
 * 
 * @author Louis Mellac, Andrei Cinca, David J. Barnes, and Michael Kölling.
 * @version 2020.02.20
 */
public class KCLSU extends Animal
{
    // Characteristics shared by all KCLSU (class variables).
    
    // The age to which a KCLSU can live 
    private static final int MAX_AGE = 50 * 24;
    // The likelihood of a KCLSU breeding.
    private static final double BREEDING_PROBABILITY = 0.009;
    // The maximum number of births.
    private static final int MAX_OFFSPRING = 3;
    // Default starting food.
    private static final int DEFAULT_FOOD_LEVEL = 20;
    // The food value of a KCLSU.
    private static final int FOOD_VALUE = 50;
    
    /**
     * Create a KCLSU. A KCLSU can be created as a new born (age zero
     * and not hungry) or with a random age and food level.
     * @param randomAge If true, the KCLSU will have random age and hunger level.
     * @param field The field currently occupied.
     * @param location The location within the field.
     * @param weather The weather affecting this KCLSU's behaviour.
     */
    public KCLSU(boolean randomAge, Field field, Location location, Weather weather)
    {
        super(randomAge, location, field, weather);
    }
    
    /**
     * Make the KCLSU act. KCLSUs age, get hungrier, can catch disease, give birth, and move.
     * @param newKCLSU A list to return newly born KCLSU.
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
     * Create a KCLSU but return it as an Animal object.
     * @param randomAge If true, the KCLSU will have random age and hunger level.
     * @param field The field currently occupied.
     * @param location The location within the field.
     * @param weather The weather affecting this KCLSU's behaviour.
     */
    protected Animal makeOffspring(boolean randomAge, Field field, Location loc, Weather weather)
    {
        return new KCLSU(randomAge, field, loc, weather);
    }
    
    /**
     * Checks if this KCLSU can breed with the given animal.
     * @return true if the given animal is KCLSU
     */
    protected boolean canBreed(Animal animal)
    {
        return (animal instanceof KCLSU);
    }
    
    /**
     * @return an array of the types of entities KCLSU can eat (currently only admin).
     */
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
    protected int getMAX_OFFSPRING() {return MAX_OFFSPRING;}
    /**
     * @return the amount of food an animal would get from eating a KCLSU.
     */
    protected int getFOOD_VALUE() {return FOOD_VALUE;}
    /**
     * @return the default starting food level for a KCLSU.
     */
    protected int getDEFAULT_FOOD_LEVEL() {return DEFAULT_FOOD_LEVEL;}
}
