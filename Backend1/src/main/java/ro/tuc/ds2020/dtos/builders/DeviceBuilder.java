package ro.tuc.ds2020.dtos.builders;

import ro.tuc.ds2020.dtos.DeviceDTO;
import ro.tuc.ds2020.entities.Device;
import ro.tuc.ds2020.entities.UserId;

public class DeviceBuilder {

    private DeviceBuilder() {
    }

    public static DeviceDTO toDeviceDTO(Device device) {
        return new DeviceDTO(device.getId(), device.getDescription(), device.getAddress(),device.getMHEnergyC(), device.getUser_id().getId());
    }

    public static Device toEntity(DeviceDTO deviceDTO) {
        return new Device(deviceDTO.getDescription(), deviceDTO.getAddress(),deviceDTO.getMHEnergyC(), new UserId(deviceDTO.getUser_id()));
    }
}
