import java.util.Random;

public class MockSensorData {

    private final Random random = new Random();

    private double temp = 20.0;
    private double humidity = 50.0;
    private double pressure = 1010.0;

    public double getTemp(){
        return temp;
    }
    public double getHumidity(){
        return humidity;
    }
    public double getPressure(){
        return pressure;
    }

    public void tick() {
        if (random.nextDouble() < 0.15) {
            temp     = -10 + random.nextDouble() * 55;
            humidity = 20  + random.nextDouble() * 80;
            pressure = 985 + random.nextDouble() * 40;
        } else {
            temp     += (random.nextGaussian() * 2);
            humidity += (random.nextGaussian() * 3);
            pressure += (random.nextGaussian() * 1.5);

            temp     = Math.max(-20, Math.min(50, temp));
            humidity = Math.max(0,   Math.min(100, humidity));
            pressure = Math.max(970, Math.min(1040, pressure));
        }
    }
}