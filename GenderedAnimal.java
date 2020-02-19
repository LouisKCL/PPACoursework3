import java.util.List;
/**
 * A class representing animals with gender.
 *
 * @author Louis Mellac and Andrei Cinca
 * @version 2020.02.18
 */
public abstract class GenderedAnimal extends Animal
{
    // Gender of the animal. True means female.
    protected boolean isFemale;
    
    /**
     * Create a new gendered animal at location in field.
     * 
     * @param location The location within the field.
     * @param field The field currently occupied.
     * @param isFemale Whether or not this animal a female.
     * @param weather The weather affecting the animal.
     */
    public GenderedAnimal(Location location, Field field, boolean isFemale, Weather weather) 
    {
        super(field, location, weather);
        this.isFemale = isFemale;
    }
    
    /**
     * Checks to see if the current animal can breed with another.
     * 
     * @param currentAnimal The animal to check compatibility with.
     * @return true if the current animal can breed with.
     */
    protected boolean canBreed(GenderedAnimal currentAnimal)
    {
        List<Location> potentialMates = field.getFullAdjacentLocations(location);
        for (Location potentialMate : potentialMates) {
            Object mate = field.getObjectAt(potentialMate); 
            if (mate.getClass() == currentAnimal.getClass()) {
                GenderedAnimal castedMate = (GenderedAnimal) mate;
                if (castedMate.isFemale() != isFemale) {
                    return true;
                }
            }
        }
        return false;
    }
    
    protected boolean isFemale()
    {
        return isFemale;
    }
}
