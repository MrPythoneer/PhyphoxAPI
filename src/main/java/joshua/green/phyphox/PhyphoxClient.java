package joshua.green.phyphox;

import com.jsoniter.JsonIterator;
import com.jsoniter.any.Any;
import joshua.green.phyphox.sensors.*;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.rmi.ConnectException;
import java.rmi.NoSuchObjectException;

public class PhyphoxClient {
    private final String uri;
    private final Any config;

    private String getRequest;

    private Any buffer;

    public PhyphoxClient(String uri, Any config) {
        this.uri = uri;
        this.config = config;
        getRequest = uri + "get?";
    }

    public static PhyphoxClient connect(String addr, int port) throws ConnectException {
        try {
            final String uri = "http://" + addr + ":" + port + "/";
            final URL url = new URL(uri + "config?input");
            final URLConnection conn = url.openConnection();
            final byte[] resp = conn.getInputStream().readAllBytes();
            Any config = JsonIterator.deserialize(resp);

            return new PhyphoxClient(uri, config);
        } catch (IOException e) {
            e.printStackTrace();
            throw new ConnectException("Cannot retrieve configuration");
        }
    }

    public void addQuery(String query) {
        getRequest += query + "&";
    }

    public PhyphoxSensor getSensor(PhyphoxSensors sensor) throws NoSuchObjectException {
        final String sensorName = sensor.phyphoxName();

        boolean found = false;
        for (Any input : config.get("inputs")) {
            if (input.get("source").toString().equals(sensorName)) {
                found = true;
                break;
            }
        }

        if (!found)
            throw new NoSuchObjectException("There's no such a sensor in the experiment");

        return switch (sensor) {
            case ACCELEROMETER -> new PhyphoxAccelerometer(this);
            case GYROSCOPE -> new PhyphoxGyroscope(this);
            case LIGHT -> {
                addQuery("light");
                yield new PhyphoxLight(this);
            }
            case PROXIMITY -> {
                addQuery("prox");
                yield new PhyphoxProximity(this);
            }
            case MAGNETIC_FIELD -> new PhyphoxMagneticField(this);
            case LINEAR_ACCELERATION -> new PhyphoxLinearAcceleration(this);
        };
    }

    public boolean start() {
        try {
            final URL url = new URL(uri + "control?cmd=start");
            final URLConnection conn = url.openConnection();
            final byte[] resp = conn.getInputStream().readAllBytes();
            return JsonIterator.deserialize(resp).get("result").toBoolean();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean stop() {
        try {
            final URL url = new URL(uri + "control?cmd=stop");
            final URLConnection conn = url.openConnection();
            final byte[] resp = conn.getInputStream().readAllBytes();
            return JsonIterator.deserialize(resp).get("result").toBoolean();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean clear() {
        try {
            final URL url = new URL(uri + "control?cmd=clear");
            final URLConnection conn = url.openConnection();
            final byte[] resp = conn.getInputStream().readAllBytes();
            return JsonIterator.deserialize(resp).get("result").toBoolean();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean update() {
        try {
            final var url = new URL(getRequest);
            final URLConnection conn = url.openConnection();
            final var is = conn.getInputStream();
            final byte[] data = is.readAllBytes();
            buffer = JsonIterator.deserialize(data);

            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Any getBuffer() {
        return buffer;
    }
}
