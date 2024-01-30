package ro.tuc.ds2020.rabbitMQ;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ro.tuc.ds2020.entities.Consumer;
import ro.tuc.ds2020.entities.Device;
import ro.tuc.ds2020.services.ConsumerService;
import ro.tuc.ds2020.services.DeviceService;
import ro.tuc.ds2020.ws.WSController;

import java.util.HashMap;
import java.util.UUID;

@Component
public class ReceiverMessage {
    @Autowired
    DeviceService deviceService;
    @Autowired
    ConsumerService consumerService;
    @Autowired
    WSController wsController;
    HashMap<String, Long> id_time = new HashMap<String, Long>();
    HashMap<String, Float> id_mv = new HashMap<String, Float>();
    int x = 0;
    Long diff = Long.valueOf(0);

    @RabbitListener(queues = "spring-boot")
    public void receiveMessage(String message) throws JSONException, InterruptedException {
        System.out.println("Received xxx <" + message + ">");
        JSONObject jsonObject = new JSONObject(message);
        float measurementValue = (float) jsonObject.getDouble("measurement_value");

        String deviceId = jsonObject.getString("deviceId");
        Long timestamp = jsonObject.getLong("timestamp");

        Device device = deviceService.findById(UUID.fromString(deviceId));
        //System.out.println(device.getDevice_id());
        Consumer consumer = new Consumer(device,timestamp,measurementValue);
        consumerService.insert(consumer);

        if(x == 0){
            id_time.put(deviceId,timestamp);
            id_mv.put(deviceId,measurementValue);
            x++;
        } else{
            diff = timestamp - id_time.get(deviceId);
            if(diff/60000 >= 1){
                if(measurementValue - id_mv.get(deviceId) >= device.getMhec())
                {
                    wsController.sendNotification("you just passed the value with: "+(measurementValue - id_mv.get(deviceId)),
                            device.getUser_id().toString());
                    x++;
                    id_time.put(deviceId,timestamp);
                    id_mv.put(deviceId,measurementValue);
                }
            }
        }
    }

}
