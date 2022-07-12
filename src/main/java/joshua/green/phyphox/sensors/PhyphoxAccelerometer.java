package joshua.green.phyphox.sensors;

import joshua.green.phyphox.PhyphoxClient;

public record PhyphoxAccelerometer(PhyphoxClient client) implements PhyphoxSensor {
    @Override
    public boolean include(String value) {
        value = value.toLowerCase();
        switch (value) {
            case "x", "accx" -> client.addQuery("accX");
            case "y", "accy" -> client.addQuery("accY");
            case "z", "accz" -> client.addQuery("accZ");
            case "t", "time", "acc_time" -> client.addQuery("acc_time");
            default -> {
                return false;
            }
        }
        return true;
    }

    public double getX() {
        return client.getBuffer().get("buffer").get("accX").get("buffer").get(0).toDouble();
    }

    public double getY() {
        return client.getBuffer().get("buffer").get("accY").get("buffer").get(0).toDouble();
    }

    public double getZ() {
        return client.getBuffer().get("buffer").get("accZ").get("buffer").get(0).toDouble();
    }

    public double getTime() {
        return client.getBuffer().get("buffer").get("acc_time").get("buffer").get(0).toDouble();
    }
}
