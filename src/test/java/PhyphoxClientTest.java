import joshua.green.phyphox.PhyphoxClient;
import joshua.green.phyphox.sensors.PhyphoxSensors;
import joshua.green.phyphox.sensors.PhyphoxAccelerometer;
import joshua.green.phyphox.sensors.PhyphoxGyroscope;

import java.io.IOException;

public class PhyphoxClientTest {
   public static void main(String[] args) throws IOException {
       PhyphoxClient client = PhyphoxClient.connect("192.168.0.1", 8080);

       var acc = (PhyphoxAccelerometer) client.getSensor(PhyphoxSensors.ACCELEROMETER);
       acc.include("x");

       var gyr = (PhyphoxGyroscope) client.getSensor(PhyphoxSensors.GYROSCOPE);
       gyr.include("x");
       gyr.include("y");
       gyr.include("z");

       client.start();
       if (client.update()) {
           System.out.println(
                   "\naccX: " + acc.getX() +
                   "\naccY: " + acc.getY() +
                   "\naccZ: " + acc.getZ() +
                   "\ngyrX: " + gyr.getX() +
                   "\ngyrY: " + gyr.getY() +
                   "\ngyrZ: " + gyr.getZ()
           );
       }
       client.stop();
   }
}