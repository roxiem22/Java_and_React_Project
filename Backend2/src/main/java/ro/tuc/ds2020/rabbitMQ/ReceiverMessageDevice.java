package ro.tuc.ds2020.rabbitMQ;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ro.tuc.ds2020.entities.Device;
import ro.tuc.ds2020.services.DeviceService;

import java.util.UUID;

@Component
public class ReceiverMessageDevice {
    @Autowired
    DeviceService deviceService;

    @RabbitListener(queues = "spring-boot1")
    public void receiveMessage1(String message) throws JSONException {
        System.out.println("Received <" + message + ">");

        JSONObject jsonObject = new JSONObject(message);

        String deviceId = jsonObject.getString("id");
        String userId = jsonObject.getString("user_id");
        int mhec = jsonObject.getInt("mhec");

        Device device = new Device(UUID.fromString(deviceId),UUID.fromString(userId),mhec);
        deviceService.insert(device);

    }

    @RabbitListener(queues = "spring-boot3")
    public void receiveMessage2(String message) throws JSONException {
        System.out.println("Received <" + message + ">");

        JSONObject jsonObject = new JSONObject(message);

        String deviceId = jsonObject.getString("id");
        String userId = jsonObject.getString("user_id");
        int mhec = jsonObject.getInt("mhec");

        Device device = new Device(UUID.fromString(deviceId),UUID.fromString(userId),mhec);
        deviceService.update(device);

    }

    @RabbitListener(queues = "spring-boot2")
    public void receiveMessage3(String message) throws JSONException {
        System.out.println("Received <" + message + ">");

        JSONObject jsonObject = new JSONObject(message);

        String deviceId = jsonObject.getString("id");
        String userId = jsonObject.getString("user_id");
        int mhec = jsonObject.getInt("mhec");

        Device device = new Device(UUID.fromString(deviceId),UUID.fromString(userId),mhec);
        deviceService.delete(device);
    }
}
