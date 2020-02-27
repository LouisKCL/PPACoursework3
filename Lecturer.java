import java.util.List;

/**
 * A simple model of a Lecturer.
 * Lecturers age, move, eat grades, catch S.A.D., reproduce, and die.
 * 
 * @author Louis Mellac, Andrei Cinca, David J. Barnes, and Michael Kölling.
 * @version 2020.02.20
 */
public class Lecturer extends GenderedAnimal
{
    // Characteristics shared by all foxes (class variables).
    
    // The age to which a Lecturer can live.
    private static final int MAX_AGE = 60 * 24;
    // The likelihood of a fox breeding.
    private static final double BREEDING_PROBABILITY = 0.04;
    // The maximum number of births.
    private static final int MAX_OFFSPRING = 2;
    // Default starting food.
    private static final int DEFAULT_FOOD_LEVEL = 40;
    // Food value for lecturers.
    private static final int FOOD_VALUE = 40;

    /**
     * Create a Lecturer. A lecturer can be created as a new born (age zero
     * and not hungry) or with a random age and food level.
     * @param randomAge If true, the lecturer will have random age and hunger level.
     * @param field The field currently occupied.
     * @param location The location within the field.
     * @param isFemale Whether or not the lecturer is female.
     * @param weather The weather affecting this lecturer's behaviour.
     */
    public Lecturer(boolean randomAge, Field field, Location location, boolean isFemale, Weather weather)
    {
        super(randomAge, location, field, isFemale, weather);
    }
    
    /**
     * Make the Lecturer act. Lecturers age, get hungrier, give birth if it is night, and move (twice if it is cold).
     * @param newLecturers A list to return newly born lecturers.
     */
    public void act(List<Entity> newLecturer)
    {
        incrementAge();
        incrementHunger();
        if(isAlive()) {
            if (weather.isNight()) {
                giveBirth(newLecturer); 
            }          
            move();
            if(isAlive() && weather.isCold())
                move();
        }
    }
    
    /**
     * Create a Lecturer but return it as an Animal object.
     * @param randomAge If true, the lecturer will have random age and hunger level.
     * @param field The field currently occupied.
     * @param location The location within the field.
     * @param isFemale Whether or not the lecturer is female.
     * @param weather The weather affecting this lecturer's behaviour.
     */
    protected Animal makeOffspring(boolean randomAge, Field field, Location loc, Weather weather)
    {
        return new Lecturer(randomAge, field, loc, rand.nextBoolean(), weather);
    }
    
    /**
     * @return an array of the types of entities lecturers can eat (currently only grades).
     */
    public Class<?>[] getFoodSources()
    {
        Class<?>[] foodSource = {TA.class, Student.class};
        return foodSource;
    }
    
    // These methods return the constants specific to this class to any superclass.
    /**
     * @return the maximum age a lecturer can have.
     */
    protected int getMAX_AGE() {return MAX_AGE;}
    /**
     * @return the probabbility that two lecturers breed.
     */
    protected double getBREEDING_PROBABILITY() {return BREEDING_PROBABILITY;}
    /**
     * @return the maximum number of offspring a lecturer can have.
     */
    protected int getMAX_OFFSPRING() {return MAX_OFFSPRING;}
    /**
     * @return the amount of food an animal would get from eating a lecturer.
     */
    protected int getFOOD_VALUE() {return FOOD_VALUE;}
    /**
     * @return the default starting food level for a lecturer.
     */
    protected int getDEFAULT_FOOD_LEVEL() {return DEFAULT_FOOD_LEVEL;}
}
