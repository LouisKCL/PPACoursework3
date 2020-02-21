import java.util.List;
import java.util.Iterator;

/**
 * This is class student
 * they eat grades(plant),die,breed,move,age
 * they re being eaten by lecturers
 * 
 * @author David J. Barnes, Michael Kölling, Louis Mellac, Andrei Cinca
 * @version 2020.02.11
 */
public class Student extends GenderedAnimal
{
    // Characteristics shared by all students(class variables).

    // Maximum age of a student .
    private static final int MAX_AGE = 40 * 24;
    // The likelihood of a student breeding.
    private static final double BREEDING_PROBABILITY = 0.04;
    // The maximum number of births.
    private static final int MAX_OFFSPRING = 5;
    // Food value of a student
    private static final int FOOD_VALUE = 20;
    // Default starting food level of a student.
    private static final int DEFAULT_FOOD_LEVEL = 30;

    /**
     * Create a new student. A student may be created with age
     * zero (a new born) or with a random age.
     * 
     * @param randomAge If true, the student will have a random age.
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public Student(boolean randomAge, Field field, Location location, boolean isFemale, Weather weather)
    {
        super(location, field, isFemale, weather);
        age = 0;
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
     * This is how the student acts.They can eat if they find food around them
     * they can breed,die,get a disease,age
     * @param newRabbits A list to return newly born students.
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
     * Creates a new student but returns it in an Entity variable.
     * @param randomAge If true, the student will have a random age.
     * @param field The field currently occupied.
     * @param location The location within the field.
     * @param gender Whether or not this is a female.
     * @param weather The weather affecting this student's behaviour.
     */
    protected Entity buildGenderedOffspring(boolean randomAge, Field field, Location loc, boolean gender, Weather weather)
    {
        return new Student(randomAge, field, loc, gender, weather);
    }
    
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
    protected int getMAX_OFFSPRING() {return MAX_AGE;}
    /**
     * @return the amount of food an animal would get from eating a student.
     */
    protected int getFOOD_VALUE() {return FOOD_VALUE;}
    /**
     * @return the default starting food level for a student.
     */
    protected int getDEFAULT_FOOD_LEVEL() {return DEFAULT_FOOD_LEVEL;}
}
