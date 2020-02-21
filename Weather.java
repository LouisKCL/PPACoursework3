import java.util.Random;

/**
 * This is class weather,it contains the details about the current weather(hot or cold), and about the time of day
 * which are being calculated by with the number of steps
 *
 * @author Louis Mellac,Andrei Cinca
 * @version 
 */

public class Weather
{
    private boolean isNight;
    private boolean isCold;
    private boolean isHot;
    
    private final boolean DEFAULT_TIME = true;
    private final boolean DEFAULT_COLD = false;
    private final boolean DEFAULT_HOT = false;

    private final double  Probability_for_hot = 0.25;
    private final double  Probability_for_cold = 0.25;
    private final double PROBABLITY_FOR_NORMAL = 0.5;
    private final Random rand=new Random();
    /**
     * The default weather constructor. Uses the default values for time of day, whether it is 
     * hot, and whether it is cold.
     */
    public Weather() 
    {
        isNight = DEFAULT_TIME;
        isCold = DEFAULT_COLD;
        isHot = DEFAULT_HOT;
    }
    
    /**
     * Weather constructor for custom time of day, whether it is 
     * hot, and whether it is cold.
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
     * this method updates the weather and time of day at every single act call
     * if there is the case it will change the weather to hot/cold/none, or time of day to night/day
     */
    public void updateWeather(int step) {
        if (step % 12 == 0) {
            isNight = !isNight;
        }
        if(step% 24 ==0)
        {   if(rand.nextDouble()<=Probability_for_hot)
            {
                isHot=true;
                isCold = false;
            }
            if(rand.nextDouble()<=Probability_for_cold)
            {
                isCold=true;
                isHot = false;
            }
            if(rand.nextDouble() <= PROBABLITY_FOR_NORMAL)
            {
               isHot = false;
               isCold = false;
            }
        }
    }
    
    /**
     * checks if the time is night
     * @return true if is night
     */
    public boolean isNight() {
        return isNight;
    }
    
    /**
     * checks if the weather is cold
     * @return true if it's cold
     */
    public boolean isCold() {
        return isCold;
    }
    
    /**
     * checks if the weather is hot
     * @return true if it's hot
     */
    public boolean isHot() {
        return isHot;
    }
}
