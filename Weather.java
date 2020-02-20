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
    private boolean isNight = true;
    private boolean isCold = false;
    private boolean isHot = false;
    private final double  Probability_for_hot=0.25;
    private final double  Probability_for_cold=0.25;
    private final double PROBABLITY_FOR_NORMAL = 0.5;
    private final Random rand=new Random();
    /**
     * the constructor for the weather is not supposed to do anything,
     * as we check the weather at every step 
     */
    public Weather() {
        
    }
    
    /**
     * this method updates the weather and time of day at every singke act call
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
