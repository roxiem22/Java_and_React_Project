package ro.tuc.ds2020.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.tuc.ds2020.entities.Device;
import ro.tuc.ds2020.repositories.DeviceRepository;

import java.util.Optional;
import java.util.UUID;

@Service
public class DeviceService {
    private static final Logger LOGGER = LoggerFactory.getLogger(DeviceService.class);
    private final DeviceRepository deviceRepository;

    @Autowired
    public DeviceService(DeviceRepository deviceRepository) {
        this.deviceRepository = deviceRepository;
    }

    public UUID insert(Device device) {
        device = deviceRepository.save(device);
        return device.getDevice_id();
    }

   public UUID update(Device device) {
        Device device1 = deviceRepository.findDeviceById(device.getDevice_id());

        device1.setMhec(device.getMhec());
        System.out.println(device1.getMhec());
        device1 = deviceRepository.save(device1);
        LOGGER.debug("Device with id {} was updated in db", device1.getDevice_id());
        return device1.getDevice_id();
    }

    public void delete(Device device) {
        deviceRepository.delete(device);
        LOGGER.debug("Device with id {} was deleted from db", device.getDevice_id());
    }

    public Device findById(UUID uuid) {
        Device device = deviceRepository.findById(uuid).get();
        return device;
    }
}
