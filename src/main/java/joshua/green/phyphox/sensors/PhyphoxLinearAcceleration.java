package joshua.green.phyphox.sensors;

import joshua.green.phyphox.PhyphoxClient;

public record PhyphoxLinearAcceleration(PhyphoxClient client) implements PhyphoxSensor {
    @Override
    public boolean include(String value) {
        value = value.toLowerCase();
        switch (value) {
            case "x", "linx" -> client.addQuery("linX");
            case "y", "liny" -> client.addQuery("linY");
            case "z", "linz" -> client.addQuery("linZ");
            case "t", "time", "lin_time" -> client.addQuery("lin_time");
            default -> {
                return false;
            }
        }
        return true;
    }

    public double getX() {
        return client.getBuffer().get("buffer").get("linX").get("buffer").get(0).toDouble();
    }

    public double getY() {
        return client.getBuffer().get("buffer").get("linY").get("buffer").get(0).toDouble();
    }

    public double getZ() {
        return client.getBuffer().get("buffer").get("linZ").get("buffer").get(0).toDouble();
    }

    public double getTime() {
        return client.getBuffer().get("buffer").get("lin_time").get("buffer").get(0).toDouble();
    }

}
