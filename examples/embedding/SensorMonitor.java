import io.github.seanieStack.embed.RJScriptEngine;

public class SensorMonitor {

    private final MockSensorData sensor = new MockSensorData();

    private double currentTemp;
    private double currentHumidity;
    private double currentPressure;
    private int currentIndex;

    public double getCurrentTemp(){
        return currentTemp;
    }

    public double getCurrentHumidity(){
        return currentHumidity;
    }

    public double getCurrentPressure(){
        return currentPressure;
    }

    public int getCurrentIndex(){
        return currentIndex;
    }

    public void addAlert(String level, String message) {
        System.out.println("  [" + level.toUpperCase() + "] " + message);
    }

    public void run(String[] args) throws Exception {
        String rulesFile = "./rules/rules.rjs";
        if (args.length > 0) {
            rulesFile = args[0];
        }

        System.out.println("Rules file: " + rulesFile);
        System.out.println("Reading sensors every 5 seconds (edit " + rulesFile + " while running)");
        System.out.println("Press Ctrl+C to stop\n");

        RJScriptEngine engine = new RJScriptEngine();
        engine.registerModule(new SensorModule(this));

        int reading = 0;
        while (true) {
            reading++;
            sensor.tick();

            currentIndex = reading;
            currentTemp = Math.round(sensor.getTemp() * 10.0) / 10.0;
            currentHumidity = Math.round(sensor.getHumidity() * 10.0) / 10.0;
            currentPressure = Math.round(sensor.getPressure() * 10.0) / 10.0;

            System.out.printf("Reading #%d: temp=%.1f°C  humidity=%.1f%%  pressure=%.1fhPa%n", currentIndex, currentTemp, currentHumidity, currentPressure);

            try {
                engine.executeFile(rulesFile);
            } catch (Exception e) {
                System.out.println("[ERROR] Script failed: " + e.getMessage());
            }

            System.out.println();
            Thread.sleep(5000);
        }
    }

    public static void main(String[] args) throws Exception {
        new SensorMonitor().run(args);
    }
}