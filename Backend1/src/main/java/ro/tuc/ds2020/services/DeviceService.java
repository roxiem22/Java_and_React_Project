package ro.tuc.ds2020.services;


import jakarta.persistence.EntityNotFoundException;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.tuc.ds2020.dtos.DeviceDTO;
import ro.tuc.ds2020.dtos.builders.DeviceBuilder;
import ro.tuc.ds2020.entities.Device;
import ro.tuc.ds2020.entities.UserId;
import ro.tuc.ds2020.repositories.DeviceRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class DeviceService {
    private static final Logger LOGGER = LoggerFactory.getLogger(DeviceService.class);
    private final DeviceRepository deviceRepository;
    @Autowired
    RabbitTemplate rabbitTemplate;

    @Autowired
    public DeviceService(DeviceRepository deviceRepository) {
        this.deviceRepository = deviceRepository;
    }

    public List<DeviceDTO> findDevice() {
        List<Device> userList = deviceRepository.findAll();
        return userList.stream()
                .map(DeviceBuilder::toDeviceDTO)
                .collect(Collectors.toList());
    }

    public UUID insert(DeviceDTO deviceDTO) throws JSONException {
        Device device = DeviceBuilder.toEntity(deviceDTO);
        System.out.println(device.getUser_id().getId());
        device = deviceRepository.save(device);
        LOGGER.debug("Device with id {} was inserted in db", device.getId());
        JSONObject jason = new JSONObject();
        jason.put("id",device.getId());
        jason.put("user_id",device.getUser_id().getId());
        jason.put("mhec",device.getMHEnergyC());
        rabbitTemplate.convertAndSend("spring-boot-exchange1","foo.bar.#1",jason.toString());
        return device.getId();
    }

    public UUID update(DeviceDTO deviceDTO) throws JSONException {
        Device device = deviceRepository.findDeviceById(deviceDTO.getId());
        if (device == null) {
            throw new EntityNotFoundException("Device not found for ID: " + deviceDTO.getId());
        }

        device.setDescription(deviceDTO.getDescription());
        device.setAddress(deviceDTO.getAddress());
        device.setMHEnergyC(deviceDTO.getMHEnergyC());

        UserId userId = new UserId();
        userId.setId(deviceDTO.getUser_id());
        device.setUser_id(userId);

        device = deviceRepository.save(device);
        LOGGER.debug("Device with id {} was updated in db", device.getId());

        JSONObject jason = new JSONObject();
        jason.put("id",device.getId());
        jason.put("user_id",device.getUser_id().getId());
        jason.put("mhec",device.getMHEnergyC());
        rabbitTemplate.convertAndSend("spring-boot-exchange1","foo.bar.#3",jason.toString());
        return device.getId();
    }

    public void delete(DeviceDTO deviceDTO) throws JSONException {
        Device device = deviceRepository.findById(deviceDTO.getId()).get();
        deviceRepository.delete(device);
        LOGGER.debug("Device with id {} was deleted from db", device.getId());
        JSONObject jason = new JSONObject();
        jason.put("id",device.getId());
        jason.put("user_id",device.getUser_id().getId());
        jason.put("mhec",device.getMHEnergyC());
        rabbitTemplate.convertAndSend("spring-boot-exchange1","foo.bar.#2",jason.toString());
    }

    public List<DeviceDTO> findDeviceByUserId(UUID user_id) {
        System.out.println("ok"+user_id);
        List<Device> userList = deviceRepository.findDeviceByUserId(user_id);
        return userList.stream()
                .map(DeviceBuilder::toDeviceDTO)
                .collect(Collectors.toList());
    }

}
