import java.util.List;
/**
 * A class representing animals with gender.
 * Gendered animals can only breed with an animal of the same class 
 * but of opposite gender.
 *
 * @author Louis Mellac and Andrei Cinca
 * @version 2020.02.18
 */
public abstract class GenderedAnimal extends Animal
{
    // Gender of the animal. True means female.
    protected boolean isFemale;
    
    /**
     * Create a new gendered animal at a location in a field.
     * @param location The location within the field.
     * @param field The field currently occupied.
     * @param isFemale Whether or not this animal is female.
     * @param weather The weather affecting the animal.
     */
    public GenderedAnimal(Location location, Field field, boolean isFemale, Weather weather) 
    {
        super(location, field, weather);
        this.isFemale = isFemale;
    }

    protected Entity buildOffspring(boolean randomAge, Field field, Location loc, Weather weather)
    {
        return buildGenderedOffspring(randomAge, field, loc, rand.nextBoolean(), weather);
    }
    
    abstract protected Entity buildGenderedOffspring(boolean randomAge, Field field, Location loc, boolean gender, Weather weather);
    
    /**
     * Checks all adjacent locations to see if there is another animal of the same
     * species and of the oposite gender.
     * @param currentAnimal The animal to check compatibility with.
     * @return true if the current animal can breed with.
     */
    protected boolean canBreed(Animal currentAnimal)
    {
        // Gets the locations of all surrounding animals.
        List<Location> potentialMates = field.getFullAdjacentLocations(location);
        for (Location potentialMate : potentialMates) {
            Entity mate = field.getEntityAt(potentialMate); 
            // Checks to see if this can be mated with (same class and opposite gender).
            if (mate.getClass() == currentAnimal.getClass()) {
                GenderedAnimal castedMate = (GenderedAnimal) mate;
                if (castedMate.isFemale() != isFemale)
                    return true;
            }
        }
        // No animal of the same class or opposite gender.
        return false;
    }
    
    /**
     * @return true if the current animal is female.
     */
    protected boolean isFemale()
    {
        return isFemale;
    } 
}
