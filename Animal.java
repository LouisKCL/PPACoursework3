import java.util.List;
import java.util.Random;

/**
 * A class representing shared characteristics of animals.
 * 
 * @author Louis Mellac, Andrei Cinca, David J. Barnes, and Michael Kölling
 * @version 2020.02.18
 */
public abstract class Animal
{
    // Maximum amount of food an animal can have.
    protected static final int MAX_FOOD_LEVEL = 100;
    
    protected static final double PROBABILITY_OF_SAD = 0.1;
    
    protected static final double PROBABILITY_OF_SAD_SPREAD = 0.2;
    
    protected static final double LETHALITY_OF_SAD = 0.08;
    
    protected static final Random rand = Randomizer.getRandom();
    
    // Whether the animal is alive or not.
    protected boolean alive;
    // The animal's field.
    protected Field field;
    // The animal's position in the field.
    protected Location location;
    
    protected Weather weather;
    
    protected int age;
    
    protected boolean hasSAD;
    
    /**
     * Create a new animal at location in field.
     * 
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public Animal(Field field, Location location, Weather weather)
    {
        alive = true;
        this.field = field;
        this.weather = weather;
        setLocation(location);
    }
    
    /**
     * Make this animal act - that is: make it do
     * whatever it wants/needs to do.
     * @param newAnimals A list to receive newly born animals.
     */
    abstract public void act(List<Animal> newAnimals);
    
    /**
     * Check whether the animal is alive or not.
     * @return true if the animal is still alive.
     */
    protected boolean isAlive()
    {
        return alive;
    }

    /**
     * Indicate that the animal is no longer alive.
     * It is removed from the field.
     */
    protected void setDead()
    {
        alive = false;
        if(location != null) {
            field.clear(location);
            location = null;
            field = null;
        }
    }

    /**
     * Return the animal's location.
     * @return The animal's location.
     */
    protected Location getLocation()
    {
        return location;
    }
    
    /**
     * Place the animal at the new location in the given field.
     * @param newLocation The animal's new location.
     */
    protected void setLocation(Location newLocation)
    {
        if(location != null) {
            field.clear(location);
        }
        location = newLocation;
        field.place(this, newLocation);
    }
    
    /**
     * Return the animal's field.
     * @return The animal's field.
     */
    protected Field getField()
    {
        return field;
    }
    
    /**
     * Check to see if it catches S.A.D.
     */
    protected void getInfected()
    {
        if (rand.nextDouble() <= PROBABILITY_OF_SAD) {
            hasSAD = true;
        }
    }
    
    /**
     * Recieves infection
     */
    protected void makeSAD()
    {
        hasSAD = true;
    }
    
}