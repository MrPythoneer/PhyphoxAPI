package joshua.green.phyphox.sensors;

import joshua.green.phyphox.PhyphoxClient;

public record PhyphoxProximity(PhyphoxClient client) implements PhyphoxSensor {
    @Override
    public boolean include(String value) {
        value = value.toLowerCase();
        switch (value) {
            case "t", "time", "prox_time" -> client.addQuery("prox_time");
            default -> {
                return false;
            }
        }

        return true;
    }

    public double getX() {
        return client.getBuffer().get("buffer").get("prox").get("buffer").get(0).toDouble();
    }

    public double getTime() {
        return client.getBuffer().get("buffer").get("prox_time").get("buffer").get(0).toDouble();
    }

}
