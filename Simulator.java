import java.util.Random;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import java.awt.Color;

/**
 * A simple predator-prey simulator based on a rectangular field
 * containing students, lecturers, KCLSU, admin staff, and TAs.
 * 
 * @author Louis Mellac, Andrei Cinca, David J. Barnes, and Michael Kölling
 * @version 2020.02.18
 */
public class Simulator
{
    // Constants representing configuration information for the simulation.
    // The default width for the grid.
    private static final int DEFAULT_WIDTH = 120;
    // The default depth of the grid.
    private static final int DEFAULT_DEPTH = 80;
    // The probability that a lecturer will be created in any given grid position.
    private static final double LECTURER_CREATION_PROBABILITY = 0.03;
    // The probability that a student will be created in any given grid position.
    private static final double STUDENT_CREATION_PROBABILITY = 0.05;
    // The probability that an admin will be created in any given grid position.
    private static final double ADMIN_CREATION_PROBABILITY = 0.04;
    // The probability that a KCLSU will be created in any given grid position.
    private static final double KCLSU_CREATION_PROBABILITY = 0.009;
    // The probability that a TA will be created in any given grid position.
    private static final double TA_CREATION_PROBABILITY = 0.05;
    // The probability that a grade will be created in any given grid position.
    private static final double GRADE_CREATION_PROBABILITY = 0.10;
    // The probability that a documentation will be created in any given grid position.
    private static final double DOCUMENTATION_CREATION_PROBABILITY = 0.10;


    // List of animals in the field.
    private List<Animal> animals;
    // The current state of the field.
    private Field field;
    // The current step of the simulation.
    private int step;
    // A graphical view of the simulation.
    private SimulatorView view;
    // The current state of the weather.
    private Weather weather;
    
    
    /**
     * Construct a simulation field with default size.
     */
    public Simulator()
    {
        this(DEFAULT_DEPTH, DEFAULT_WIDTH);
    }
    
    /**
     * Create a simulation field with the given size.
     * @param depth Depth of the field. Must be greater than zero.
     * @param width Width of the field. Must be greater than zero.
     */
    public Simulator(int depth, int width)
    {
        if(width <= 0 || depth <= 0) {
            System.out.println("The dimensions must be greater than zero.");
            System.out.println("Using default values.");
            depth = DEFAULT_DEPTH;
            width = DEFAULT_WIDTH;
        }
        
        animals = new ArrayList<>();
        field = new Field(depth, width);
        weather = new Weather();

        // Create a view of the state of each location in the field.
        view = new SimulatorView(depth, width);
        view.setColor(Student.class, Color.ORANGE);
        view.setColor(Lecturer.class, Color.BLUE);
        view.setColor(Admin.class, Color.CYAN);
        view.setColor(KCLSU.class, Color.RED);
        view.setColor(TA.class, Color.BLACK);
        view.setColor(Grade.class, Color.GREEN);
        view.setColor(Documentation.class, Color.GREEN);
        
        // Setup a valid starting point.
        reset();
    }
    
    /**
     * Run the simulation from its current state for a reasonably long period,
     * (4000 steps).
     */
    public void runLongSimulation()
    {
        simulate(1000);
    }
    
    /**
     * Run the simulation from its current state for the given number of steps.
     * Stop before the given number of steps if it ceases to be viable.
     * @param numSteps The number of steps to run for.
     */
    public void simulate(int numSteps)
    {
        for(int step = 1; step <= numSteps && view.isViable(field); step++) {
            simulateOneStep();
            // delay(60);   // uncomment this to run more slowly
        }
    }
    
    /**
     * Run the simulation from its current state for a single step.
     * Iterate over the whole field updating the state of each
     * lecturer and student.
     */
    public void simulateOneStep()
    {
        step++;
        weather.updateWeather(step);

        // Provide space for newborn animals.
        List<Animal> newAnimals = new ArrayList<>();        
        // Let all students act.
        for(Iterator<Animal> it = animals.iterator(); it.hasNext(); ) {
            Animal animal = it.next();
            animal.act(newAnimals);
            if(! animal.isAlive()) {
                it.remove();
            }
        }
               
        // Add the newly born animals to the main lists.
        animals.addAll(newAnimals);

        view.showStatus(step, field);
    }
        
    /**
     * Reset the simulation to a starting position.
     */
    public void reset()
    {
        step = 0;
        animals.clear();
        populate();
        
        // Show the starting state in the view.
        view.showStatus(step, field);
    }
    
    /**
     * Randomly populate the field with animals.
     */
    private void populate()
    {
        Random rand = Randomizer.getRandom();
        field.clear();
        for(int row = 0; row < field.getDepth(); row++) {
            for(int col = 0; col < field.getWidth(); col++) {
                if(rand.nextDouble() <= LECTURER_CREATION_PROBABILITY) {
                    Location location = new Location(row, col);
                    Lecturer lecturer = new Lecturer(true, field, location, rand.nextBoolean(), weather);
                    animals.add(lecturer);
                }
                else if(rand.nextDouble() <= STUDENT_CREATION_PROBABILITY) {
                    Location location = new Location(row, col);
                    Student student = new Student(true, field, location, rand.nextBoolean(), weather);
                    animals.add(student);
                }
                else if(rand.nextDouble() <= ADMIN_CREATION_PROBABILITY) {
                    Location location = new Location(row, col);
                    Admin admin = new Admin(true, field, location, weather);
                    animals.add(admin);
                }
                else if(rand.nextDouble() <= KCLSU_CREATION_PROBABILITY) {
                    Location location = new Location(row, col);
                    KCLSU kclsu = new KCLSU(true, field, location, weather);
                    animals.add(kclsu);
                }
                else if(rand.nextDouble() <= TA_CREATION_PROBABILITY) {
                    Location location = new Location(row, col);
                    TA ta = new TA(true, field, location, rand.nextBoolean(), weather);
                    animals.add(ta);
                }
                else if(rand.nextDouble() <=  GRADE_CREATION_PROBABILITY) {
                    Location location = new Location(row, col);
                    Grade  grade = new Grade(field, location, weather);
                    animals.add(grade);
                }
                else if(rand.nextDouble() <=  DOCUMENTATION_CREATION_PROBABILITY) {
                    Location location = new Location(row, col);
                    Documentation  doc = new Documentation(field, location, weather);
                    animals.add(doc);
                }
                // else leave the location empty.
            }
        }
    }
    
    /**
     * Pause for a given time.
     * @param millisec  The time to pause for, in milliseconds
     */
    private void delay(int millisec)
    {
        try {
            Thread.sleep(millisec);
        }
        catch (InterruptedException ie) {
            // wake up
        }
    }
}
