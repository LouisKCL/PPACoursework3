import java.util.List;
import java.util.Random;

/**
 * A class representing the shared characteristics and methods of all entities (plants and animals)
 * in the simulation. Entities get older and die, and can be affected by weather.
 * 
 * @author Louis Mellac, Andrei Cinca, David J. Barnes, and Michael Kölling
 * @version 2020.02.18
 */
public abstract class Entity
{
    protected static final Random rand = Randomizer.getRandom();
    
    // Whether the entity is alive or not.
    protected boolean alive = true;
    // The entity's field.
    protected Field field;
    // The entity's position in the field.
    protected Location location;
    // The weather affecting the entity.
    protected Weather weather;
    // The entity's age.
    protected int age;
    
    /**
     * Create a new entity and place it in the field.
     * @param randomAge Whether or not this entity should start with a random age.
     * @param location The location within the field.
     * @param field The field currently occupied.
     * @param weather The weather affecting the entity.
     */
    public Entity(boolean randomAge, Location location, Field field, Weather weather) 
    {
        this.field = field;
        this.weather = weather;
        setLocation(location);
        if(randomAge)
            age = rand.nextInt(getMAX_AGE());
        else
            age = 0;
    }
    
    /**
     * Places the entity at the new location in the given field.
     * @param newLocation The entity's new location.
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
     * Makes this entity act.
     * @param newEntities A list to receive newly made entities.
     */
    abstract public void act(List<Entity> newEntities);
    
    /**
     * Increase the entity's age. This could result in its death.
     */
    protected void incrementAge()
    {
        age++;
        if(age > getMAX_AGE()) {
            setDead();
        }
    }
    
    /**
     * Indicates that the entity is no longer alive.
     * It is removed from the field.
     */
    protected void setDead()
    {
        // System.out.println("Killed: "+super.toString()); 
        alive = false;
        if(location != null) {
            field.clear(location);
            location = null;
            field = null;
        }
    }
    
    /**
     * @return true if the entity is still alive.
     */
    protected boolean isAlive()
    {
        return alive;
    }
    
    /**
     * @return true if the entity can be eaten.
     */
    abstract public boolean isEdible();

    /**
     * @return The entity's location.
     */
    protected Location getLocation()
    {
        return location;
    }
    
    /**
     * @return The entity's field.
     */
    protected Field getField()
    {
        return field;
    }
     
    // Methods for ensuring access to the MAX_AGE and FOOD_VALUE constants of the subclasses.
    /**
     * @return The entity's maximum age.
     */
    abstract protected int getMAX_AGE();
    /**
     * @return How much food the entity is worth.
     */
    abstract protected int getFOOD_VALUE();
}
