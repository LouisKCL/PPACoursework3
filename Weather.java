
/**
 * Write a description of class Weather here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Weather
{
    private boolean isNight = true;
    public Weather() {
        
    }
    
    public void updateWeather(int step) {
        if (step % 12 == 0) {
            isNight = !isNight;
        }
        System.out.println(isNight);
    }
}
