import java.util.List;

/**
 * This is class lecturer
 * they eat,breed,die of old age or hunger
 * 
 * @author David J. Barnes and Michael Kölling
 * @version 2016.02.29 (2)
 */
public class Lecturer extends GenderedAnimal
{
    // Characteristics shared by all foxes (class variables).
    
    // The age to which a Lecturer can live.
    private static final int MAX_AGE = 60 * 24;
    // The likelihood of a fox breeding.
    private static final double BREEDING_PROBABILITY = 0.02;
    // The maximum number of births.
    private static final int MAX_OFFSPRING = 2;
    // Default starting food.
    private static final int DEFAULT_FOOD_LEVEL = 40;
    // Food value for lecturers.
    private static final int FOOD_VALUE = 40;

    /**
     * Create a lecturer. A lecturer can be created as a new born (age zero
     * and not hungry) or with a random age and food level.
     * 
     * @param randomAge If true, the lecturer will have random age and hunger level.
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public Lecturer(boolean randomAge, Field field, Location location, boolean isFemale, Weather weather)
    {
        super(location, field, isFemale, weather);
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
     * This is what the lecturer does most of the time: it hunts for
     * students. In the process, it might breed, die of hunger,
     * or die of old age.
     * @param field The field currently occupied.
     * @param newLecturers A list to return newly born students.
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
     * Creates a new lecturer but returns it in an Entity variable.
     * @param randomAge If true, the lecturer will have a random age.
     * @param field The field currently occupied.
     * @param location The location within the field.
     * @param gender Whether or not this is a female.
     * @param weather The weather affecting this lecturer's behaviour.
     */
    protected Entity buildGenderedOffspring(boolean randomAge, Field field, Location loc, boolean gender, Weather weather)
    {
        return new Lecturer(randomAge, field, loc, gender, weather);
    }
    
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
    protected int getMAX_OFFSPRING() {return MAX_AGE;}
    /**
     * @return the amount of food an animal would get from eating a lecturer.
     */
    protected int getFOOD_VALUE() {return FOOD_VALUE;}
    /**
     * @return the default starting food level for a lecturer.
     */
    protected int getDEFAULT_FOOD_LEVEL() {return DEFAULT_FOOD_LEVEL;}
}
