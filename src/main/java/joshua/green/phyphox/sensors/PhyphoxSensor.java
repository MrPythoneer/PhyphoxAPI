package joshua.green.phyphox.sensors;

public interface PhyphoxSensor {
    /**
     * Tells the client instance what data to fetch from the sensor, like x, y, z or time, etc.
     * @param value is the variable of the data the sensor captures
     * @return true if such variable exists in the sensor, otherwise, returns false
     */
    boolean include(String value);
}
