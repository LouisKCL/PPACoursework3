import java.util.List;

/**
 * A simple model of a student.
 * Students age, move, eat grades, catch S.A.D., reproduce, and die.
 * 
 * @author Louis Mellac, Andrei Cinca, David J. Barnes, and Michael Kölling.
 * @version 2020.02.20
 */
public class Student extends GenderedAnimal
{
    // Characteristics shared by all students(class variables).

    // Maximum age of a student .
    private static final int MAX_AGE = 40 * 24;
    // The likelihood of a student breeding.
    private static final double BREEDING_PROBABILITY = 0.03;
    // The maximum number of offspring a student can have.
    private static final int MAX_OFFSPRING = 5;
    // The food value of a student.
    private static final int FOOD_VALUE = 20;
    // The default starting food level of a student.
    private static final int DEFAULT_FOOD_LEVEL = 40;

    /**
     * Create a Student. A student can be created as a new born (age zero
     * and not hungry) or with a random age and food level.
     * @param randomAge If true, the student will have random age and hunger level.
     * @param field The field currently occupied.
     * @param location The location within the field.
     * @param isFemale Whether or not the student is female.
     * @param weather The weather affecting this student's behaviour.
     */
    public Student(boolean randomAge, Field field, Location location, boolean isFemale, Weather weather)
    {
        super(randomAge,location, field, isFemale, weather);
    }
    
    /**
     * Make the Student act. Students age, get hungrier, give birth if it is daytime and not hot, and move.
     * @param newStudents A list to return newly born students.
     */
    public void act(List<Entity> newStudents)
    {
        incrementAge();
        incrementHunger();
        developSAD();
        if(isAlive()) {
            fightSAD();
            if (isAlive()) {
               spreadSAD(this);
               if (!weather.isNight() && !weather.isHot()) {
                    giveBirth(newStudents);
               }
               move(); 
            }  
        }
    }
    
    /**
     * Create a Student but return it as an Animal object.
     * @param randomAge If true, the student will have random age and hunger level.
     * @param field The field currently occupied.
     * @param location The location within the field.
     * @param isFemale Whether or not the student is female.
     * @param weather The weather affecting this student's behaviour.
     */
    protected Animal makeOffspring(boolean randomAge, Field field, Location loc, Weather weather)
    {
        return new Student(randomAge, field, loc, rand.nextBoolean(), weather);
    }
    
    /**
     * @return an array of the types of entities students can eat (currently only grades).
     */
    public Class<?>[] getFoodSources()
    {
        Class<?>[] foodSource = {Grade.class};
        return foodSource;
    }
    
    // These methods return the constants specific to this class to any superclass.
    /**
     * @return the maximum age a student can have.
     */
    protected int getMAX_AGE() {return MAX_AGE;}
    /**
     * @return the probabbility that two students breed.
     */
    protected double getBREEDING_PROBABILITY() {return BREEDING_PROBABILITY;}
    /**
     * @return the maximum number of offspring a student can have.
     */
    protected int getMAX_OFFSPRING() {return MAX_OFFSPRING;}
    /**
     * @return the amount of food an animal would get from eating a student.
     */
    protected int getFOOD_VALUE() {return FOOD_VALUE;}
    /**
     * @return the default starting food level for a student.
     */
    protected int getDEFAULT_FOOD_LEVEL() {return DEFAULT_FOOD_LEVEL;}
}
