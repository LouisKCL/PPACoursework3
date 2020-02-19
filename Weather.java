import java.util.Random;

/**
 * Write a description of class Weather here.
 *
 * @author (your name)
 * @version (a version number or a date)
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
    public Weather() {
        
    }
    
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
    
    public boolean isNight() {
        return isNight;
    }
    
    public boolean isCold() {
        return isCold;
    }
    
    public boolean isHot() {
        return isHot;
    }
}
