import java.util.Random;
import java.util.List;

/**
 * Write a description of class Plant here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Plant extends Animal
{
    
    // A shared random number generator to control breeding.
    private static final Random rand = Randomizer.getRandom();
    //the plant's age
    protected int age;
    
    /**
     * Constructor for objects of class Plant
     */
    public Plant(Field field, Location location, Weather weather)
    {
        super(field,location,weather);
    }
    
    /**
     * Make this animal act - that is: make it do
     * whatever it wants/needs to do.
     * @param newAnimals A list to receive newly born animals.
     */
    public void act(List<Animal> newAnimals){
        
    }
    
    /**
     * Place the animal at the new location in the given field.
     * @param newLocation The animal's new location.
     */
    protected void setLocation(Location newLocation)
    {
        location = newLocation;
        field.place(this, newLocation);
    }
    
    
    
}
