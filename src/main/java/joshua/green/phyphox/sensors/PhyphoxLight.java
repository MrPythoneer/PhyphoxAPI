package joshua.green.phyphox.sensors;

import joshua.green.phyphox.PhyphoxClient;

public record PhyphoxLight(PhyphoxClient client) implements PhyphoxSensor {
    @Override
    public boolean include(String value) {
        value = value.toLowerCase();
        switch (value) {
            case "t", "time", "light_time" -> client.addQuery("light_time");
            default -> {
                return false;
            }
        }

        return true;
    }

    public double getLight() {
        return client.getBuffer().get("buffer").get("light").get("buffer").get(0).toDouble();
    }

    public double getTime() {
        return client.getBuffer().get("buffer").get("light_time").get("buffer").get(0).toDouble();
    }
}
