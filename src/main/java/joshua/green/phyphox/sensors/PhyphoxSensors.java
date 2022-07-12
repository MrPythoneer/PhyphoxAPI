package joshua.green.phyphox.sensors;

public enum PhyphoxSensors {
    ACCELEROMETER, GYROSCOPE, LIGHT, PROXIMITY, LINEAR_ACCELERATION, MAGNETIC_FIELD;

    public String phyphoxName() {
        return this.name().toLowerCase();
    }
}
