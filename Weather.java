import java.util.Random;

/**
 * This is the class responsible for handling weather condititions.
 * The currently implemented conditions are day, night, too hot, and too cold.
 * Conditions are updated at every step. Every 12 steps day/night switch, and every 24 hours
 * there is a chance of it becoming too hot, too cold, or neither.
 *
 * @author Louis Mellac,Andrei Cinca
 * @version 2020.02.21
 */

public class Weather
{
    // Whether is it nighttime.
    private boolean isNight;
    // Whether is it too cold.
    private boolean isCold;
    // Whether is it too hot.
    private boolean isHot;
    
    // Default conditions if none are specified in the constructor.
    private final boolean DEFAULT_TIME = true;
    private final boolean DEFAULT_COLD = false;
    private final boolean DEFAULT_HOT = false;
    
    // Probability that the weather changes to hot every 24 hours.
    private final double  PROBABILITY_OF_HOT = 0.25;
    // Probability that the weather changes to cold every 24 hours.
    private final double  PROBABILITY_OF_COLD = 0.25;
    // Probability that the weather changes back to normal every 24 hours.
    private final double PROBABILITY_OF_NORMAL = 0.5;
    
    private final Random rand=new Random();
    /**
     * The default weather constructor. Uses the default values for the conditions.
     */
    public Weather() 
    {
        isNight = DEFAULT_TIME;
        isCold = DEFAULT_COLD;
        isHot = DEFAULT_HOT;
    }
    
    /**
     * Weather constructor for custom conditions.
     * @param night boolean for night
     * @param cold boolean for cold weather
     * @param hot boolean for hot weather
     */
    public Weather(boolean night, boolean cold, boolean hot) 
    {
        isNight = night;
        isCold = cold;
        isHot = hot;
    }
    
    /**
     * This method updates the weather and time of day every at every step.
     * @param step the step the simulation is currently on.
     */
    public void updateWeather(int step) {
        // Every 12 hours, change from day to night or vice-versa.
        if (step % 12 == 0) {
            isNight = !isNight;
        }
        // Every 24 hours, attempt to change the other weather conditions or revert to normal.
        if(step% 24 == 0)
        {   if(rand.nextDouble() <= PROBABILITY_OF_HOT)
            {
                isHot=true;
                isCold = false;
            }
            if(rand.nextDouble()<=PROBABILITY_OF_COLD)
            {
                isCold=true;
                isHot = false;
            }
            if(rand.nextDouble() <= PROBABILITY_OF_NORMAL)
            {
               isHot = false;
               isCold = false;
            }
        }
    }
    
    /**
     * @return true if is night.
     */
    public boolean isNight() {
        return isNight;
    }
    
    /**
     * @return true if it is cold.
     */
    public boolean isCold() {
        return isCold;
    }
    
    /**
     * @return true if it is hot.
     */
    public boolean isHot() {
        return isHot;
    }
}
