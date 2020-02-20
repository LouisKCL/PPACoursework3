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
     * Create a new gendered animal at location in field.
     * 
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
    
    /**
     * Generate a number representing the number of births,
     * if it can breed.
     * @return The number of births (may be zero).
     */
    protected int genderedBreed()
    {
        int births = 0;
        if(canBreed(this) && rand.nextDouble() <= getBREEDING_PROBABILITY()) {
            births = rand.nextInt(getMAX_OFFSPRING()) + 1;
        }
        return births;
    }
    
    /**
     * Checks all adjacent locations to see if there is another animal of the same
     * species and of the oposite gender.
     * 
     * @param currentAnimal The animal to check compatibility with.
     * @return true if the current animal can breed with.
     */
    protected boolean canBreed(GenderedAnimal currentAnimal)
    {
        // Gets the locations of all surrounding animals.
        List<Location> potentialMates = field.getFullAdjacentLocations(location);
        for (Location potentialMate : potentialMates) {
            Object mate = field.getObjectAt(potentialMate); 
            // Checks to see if the object in the adjacent location is of the same class.
            if (mate.getClass() == currentAnimal.getClass()) {
                GenderedAnimal castedMate = (GenderedAnimal) mate;
                // If it is, check to see if it is female.
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
