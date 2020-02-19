    import java.util.List;
    import java.util.Random;
    import java.util.Iterator;
    
    /**
     * A simple model of a rabbit.
     * Rabbits age, move, breed, and die.
     * We will all die eventually.
     * 
     * @author David J. Barnes, Michael Kölling, Louis Mellac, Andrei Cinca
     * @version 2020.02.11
     */
    public class Student extends GenderedAnimal
    {
        // Characteristics shared by all rabbits (class variables).
    
        // Maximum age of a student (100 days).
        private static final int MAX_AGE = 40 * 24;
        // The likelihood of a student breeding.
        private static final double BREEDING_PROBABILITY = 0.1;
        // The maximum number of births.
        private static final int MAX_OFFSPRINGS = 5;
        // A shared random number generator to control breeding.
        private static final Random rand = Randomizer.getRandom();
        // Food value of a student
        private static final int FOOD_VALUE = 20;
        // Default starting food level of a student.
        private static final int DEFAULT_FOOD_LEVEL = 30;
        
        // Instance fields:
        private int foodLevel;
        // The rabbit's age.
        private int age;
    
        /**
         * Create a new rabbit. A rabbit may be created with age
         * zero (a new born) or with a random age.
         * 
         * @param randomAge If true, the rabbit will have a random age.
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
         * This is what the rabbit does most of the time - it runs 
         * around. Sometimes it will breed or die of old age.
         * @param newRabbits A list to return newly born rabbits.
         */
        public void act(List<Animal> newStudents)
        {
            incrementAge();
            incrementHunger();
            getInfected();
            if(isAlive()) {
                if (!weather.isNight() && !weather.isHot()) {
                    giveBirth(newStudents);
                }
                // Try to move into a free location.
                move();
                if (hasSAD) {
                infect();
                if (rand.nextDouble() <= LETHALITY_OF_SAD)
                    setDead();
                }  
            }
        }
        
        /**
         * Spread
         */
        private void infect()
        {
            Field field = getField();
            List<Location> adjacent = field.adjacentLocations(getLocation());
            Iterator<Location> it = adjacent.iterator();
            while(it.hasNext()) {
                Location where = it.next();
                Object animal = field.getObjectAt(where);
                if(animal instanceof Student) {
                    Animal student = (Animal) animal;
                    if(student.isAlive()) { 
                        if(rand.nextDouble() <= PROBABILITY_OF_SAD_SPREAD)
                            student.makeSAD();
                    }
                }
            }
            
        }
        
        private void incrementHunger()
        {
            foodLevel--;
            if(foodLevel <= 0) {
                setDead();
            }
        }
        
        /**
         * Look for rabbits adjacent to the current location.
         * Only the first live rabbit is eaten.
         * @return Where food was found, or null if it wasn't.
         */
        private Location findFood()
        {
            Field field = getField();
            List<Location> adjacent = field.adjacentLocations(getLocation());
            Iterator<Location> it = adjacent.iterator();
            while(it.hasNext()) {
                Location where = it.next();
                Object food = field.getObjectAt(where);
                if(food instanceof Grade) {
                    Grade grade = (Grade) food;
                    if(grade.isAlive() && grade.isEdible()) { 
                        grade.setDead();
                        if (foodLevel < MAX_FOOD_LEVEL)
                            foodLevel = foodLevel + grade.getFoodValue();
                        return where;
                    }
                }
            }
            return null;
        }

        /**
         * Increase the age.
         * This could result in the rabbit's death.
         */
        private void incrementAge()
        {
            age++;
            if(age > MAX_AGE) {
                setDead();
            }
        }
        
        public void move()
        {
            Location newLocation = findFood();
            if(newLocation == null) { 
                // No food found - try to move to a free location.
                newLocation = getField().freeAdjacentLocation(getLocation());
            }
            // See if it was possible to move.
            if(newLocation != null) {
                setLocation(newLocation);
            }
            else {
                // Overcrowding.
                setDead();
            }
        }
        
        /**
         * Check whether or not this rabbit is to give birth at this step.
         * New births will be made into free adjacent locations.
         * @param newRabbits A list to return newly born rabbits.
         */
        private void giveBirth(List<Animal> newStudents)
        {
            // New rabbits are born into adjacent locations.
            // Get a list of adjacent free locations.
            Field field = getField();
            List<Location> free = field.getFreeAdjacentLocations(getLocation());
            int births = breed();
            for(int b = 0; b < births && free.size() > 0; b++) {
                Location loc = free.remove(0);
                Student young = new Student(false, field, loc, rand.nextBoolean(), weather);
                newStudents.add(young);
            }
        }
            
        /**
         * Generate a number representing the number of births,
         * if it can breed.
         * @return The number of births (may be zero).
         */
        private int breed()
        {
            int births = 0;
            if(canBreed(this) && rand.nextDouble() <= BREEDING_PROBABILITY) {
                births = rand.nextInt(MAX_OFFSPRINGS) + 1;
            }
            return births;
        }
        
        public int getFoodValue()
        {
            return FOOD_VALUE;
        }
    
}
