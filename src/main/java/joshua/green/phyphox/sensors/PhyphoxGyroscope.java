package joshua.green.phyphox.sensors;

import joshua.green.phyphox.PhyphoxClient;

public record PhyphoxGyroscope(PhyphoxClient client) implements PhyphoxSensor {
    @Override
    public boolean include(String value) {
        value = value.toLowerCase();
        switch (value) {
            case "x", "gyrx" -> client.addQuery("gyrX");
            case "y", "gyry" -> client.addQuery("gyrY");
            case "z", "gyrz" -> client.addQuery("gyrZ");
            case "t", "time", "gyr_time" -> client.addQuery("gyr_time");
            default -> {
                return false;
            }
        }
        return true;
    }

    public double getX() {
        return client.getBuffer().get("buffer").get("gyrX").get("buffer").get(0).toDouble();
    }

    public double getY() {
        return client.getBuffer().get("buffer").get("gyrY").get("buffer").get(0).toDouble();
    }

    public double getZ() {
        return client.getBuffer().get("buffer").get("gyrZ").get("buffer").get(0).toDouble();
    }

    public double getTime() {
        return client.getBuffer().get("buffer").get("gyr_time").get("buffer").get(0).toDouble();
    }

}
