package joshua.green.phyphox.sensors;

import joshua.green.phyphox.PhyphoxClient;

public record PhyphoxMagneticField(PhyphoxClient client) implements PhyphoxSensor {
    @Override
    public boolean include(String value) {
        value = value.toLowerCase();
        switch (value) {
            case "x", "magx" -> client.addQuery("magX");
            case "y", "magy" -> client.addQuery("magY");
            case "z", "magz" -> client.addQuery("magZ");
            case "t", "time", "mag_time" -> client.addQuery("mag_time");
            default -> {
                return false;
            }
        }
        return true;
    }

    public double getX() {
        return client.getBuffer().get("buffer").get("magX").get("buffer").get(0).toDouble();
    }

    public double getY() {
        return client.getBuffer().get("buffer").get("magY").get("buffer").get(0).toDouble();
    }

    public double getZ() {
        return client.getBuffer().get("buffer").get("magZ").get("buffer").get(0).toDouble();
    }

    public double getTime() {
        return client.getBuffer().get("buffer").get("mag_time").get("buffer").get(0).toDouble();
    }

}
