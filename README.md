# PhyphoxAPI
Provides API for fetching data from remote experiments

If you don't know what Phyphox is, find it out on its [official website](https://phyphox.org/)

***

The API provieds an easy way to access remote experiment sensors data

### Example
```Java
String host = "address";
int port = 8080;
PhyphoxClient client = PhyphoxClient.connect(host, port);

// Registering sensors from which we want to fetch data
PhyphoxLight light = (PhyphoxLight) client.registerSensor(PhyphoxSensors.LIGHT);

client.start();
while (client.update()) {
  System.out.println("Light: " + light.getLight());
}
client.stop();
```

See another one [example](https://github.com/MrPythoneer/PhyphoxAPI/blob/main/src/example/java/Example.java) from the example folder
