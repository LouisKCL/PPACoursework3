import java.util.List;
/**
 * Abstract class GenderedAnimal - write a description of the class here
 *
 * @author (your name here)
 * @version (version number or date here)
 */
public abstract class GenderedAnimal extends Animal
{
    // Gender of the animal. True means female.
    protected boolean isFemale;
    
    public GenderedAnimal(Location location, Field field, boolean isFemale) 
    {
        super(field, location);
        this.isFemale = isFemale;
    }
    
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
