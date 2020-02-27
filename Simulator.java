import java.util.Random;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import java.awt.Color;

/**
 * A simple simulator based on a rectangular field for simulating interactions between entities.
 * The entities in this simulation are students, lecturers, KCLSU, admin staff, TAs, 
 * and the grades and documentation plants.
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
    private static final double LECTURER_CREATION_PROBABILITY = 0.015;
    // The probability that a student will be created in any given grid position.
    private static final double STUDENT_CREATION_PROBABILITY = 0.04;
    // The probability that an admin will be created in any given grid position.
    private static final double ADMIN_CREATION_PROBABILITY = 0.02;
    // The probability that a KCLSU will be created in any given grid position.
    private static final double KCLSU_CREATION_PROBABILITY = 0.009;
    // The probability that a TA will be created in any given grid position.
    private static final double TA_CREATION_PROBABILITY = 0.1;
    // The probability that a grade will be created in any given grid position.
    private static final double GRADE_CREATION_PROBABILITY = 0.15;
    // The probability that a documentation will be created in any given grid position.
    private static final double DOCUMENTATION_CREATION_PROBABILITY = 0.2;

    // List of entities in the field.
    private List<Entity> entities;
    // The current state of the field.
    private Field field;
    // The current step of the simulation.
    private int step;
    // A graphical view of the simulation.
    private SimulatorView view;
    // The current state of the weather.
    private Weather weather;
    // Boolean to check if the stop button has been pressed.
    private boolean stopRequested = false;
    
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
        
        entities = new ArrayList<>();
        field = new Field(depth, width);
        weather = new Weather();

        // Create a view of the state of each location in the field.
        view = new SimulatorView(depth, width, this);
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
     * (2000 steps).
     */
    public void runLongSimulation()
    {
        simulate(2000);
    }
    
    /**
     * Run the simulation from its current state for the given number of steps.
     * Stop before the given number of steps if it ceases to be viable.
     * @param numSteps The number of steps to run for.
     */
    public void simulate(int numSteps)
    {
        for(int step = 1; step <= numSteps && view.isViable(field); step++) {
            if (!stopRequested) {
                simulateOneStep();
                //delay(60);   // uncomment this to run more slowly
            }
        }
    }
    
    /**
     * Run the simulation from its current state for a single step.
     * Iterate over the whole field updating the state of each entity and plant.
     */
    public void simulateOneStep()
    {
        step++;
        weather.updateWeather(step);
        
        // Provide space for newly made entities.
        List<Entity> newEntities = new ArrayList<>();
        
        // Make all entities act and remove dead entities from the list.
        for(Iterator<Entity> it = entities.iterator(); it.hasNext(); ) {
            Entity entity = it.next();
            entity.act(newEntities);
            if(!entity.isAlive()) {
                it.remove();
            }
        }

        // Add the newly born entities to the main list.
        entities.addAll(newEntities);
        view.showStatus(step, field);
    }
        
    /**
     * Reset the simulation to a starting position.
     */
    public void reset()
    {
        step = 0;
        entities.clear();
        populate();
        stopRequested = false;
        
        // Show the starting state in the view.
        view.showStatus(step, field);
    }
    
    /**
     * Randomly populate the field with entities.
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
                    entities.add(lecturer);
                }
                else if(rand.nextDouble() <= STUDENT_CREATION_PROBABILITY) {
                    Location location = new Location(row, col);
                    Student student = new Student(true, field, location, rand.nextBoolean(), weather);
                    entities.add(student);
                }
                else if(rand.nextDouble() <= ADMIN_CREATION_PROBABILITY) {
                    Location location = new Location(row, col);
                    Admin admin = new Admin(true, field, location, weather);
                    entities.add(admin);
                }
                else if(rand.nextDouble() <= KCLSU_CREATION_PROBABILITY) {
                    Location location = new Location(row, col);
                    KCLSU kclsu = new KCLSU(true, field, location, weather);
                    entities.add(kclsu);
                }
                else if(rand.nextDouble() <= TA_CREATION_PROBABILITY) {
                    Location location = new Location(row, col);
                    TA ta = new TA(true, field, location, rand.nextBoolean(), weather);
                    entities.add(ta);
                }
                else if(rand.nextDouble() <=  GRADE_CREATION_PROBABILITY) {
                    Location location = new Location(row, col);
                    Grade  grade = new Grade(true, field, location, weather);
                    entities.add(grade);
                }
                else if(rand.nextDouble() <=  DOCUMENTATION_CREATION_PROBABILITY) {
                    Location location = new Location(row, col);
                    Documentation  doc = new Documentation(true, field, location, weather);
                    entities.add(doc);
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
    
    /**
     * @return true if a stop has been requested is true.
     */
    public boolean getStopRequested()
    {
        return stopRequested;
    }
    
    /**
     * Allows changing whether or not a stop has been requested.
     * @param value The boolean value to set stopRequested to.
     */
    public void setStopRequested(boolean value)
    {
        stopRequested = value;
    }
}
