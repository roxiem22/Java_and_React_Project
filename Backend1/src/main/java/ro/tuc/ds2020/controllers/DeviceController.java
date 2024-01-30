package ro.tuc.ds2020.controllers;


import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.tuc.ds2020.dtos.DeviceDTO;
import ro.tuc.ds2020.entities.UserId;
import ro.tuc.ds2020.services.DeviceService;

import java.util.List;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping(value = "/device")
public class DeviceController {
    private final DeviceService deviceService;

    @Autowired
    public DeviceController(DeviceService deviceService) {
        this.deviceService = deviceService;
    }

    @GetMapping()
    public ResponseEntity<List<DeviceDTO>> getDevices() {
        List<DeviceDTO> dtos = deviceService.findDevice();
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<UUID> insertDevice( @RequestBody DeviceDTO deviceDTO, @RequestParam("userId") String userId) throws JSONException {
        UserId userId1 = new UserId();
        userId1.setId(UUID.fromString(userId));
        deviceDTO.setUser_id(userId1.getId());
        System.out.println(deviceDTO.getMHEnergyC());
        UUID deviceId = deviceService.insert(deviceDTO);
        return new ResponseEntity<>(deviceId, HttpStatus.CREATED);
    }

    @PutMapping()
    public ResponseEntity<UUID> updateDevice( @RequestBody DeviceDTO deviceDTO,@RequestParam("userId") String userId) throws JSONException {
        UserId userId1 = new UserId();
        userId1.setId(UUID.fromString(userId));
        deviceDTO.setUser_id(userId1.getId());
        System.out.println(deviceDTO.getUser_id());
        UUID deviceId = deviceService.update(deviceDTO);
        return new ResponseEntity<>(deviceId, HttpStatus.OK);
    }
    @DeleteMapping()
    public ResponseEntity<UUID> deleteDevice( @RequestBody DeviceDTO deviceDTO) throws JSONException {
        deviceService.delete(deviceDTO);
        return new ResponseEntity<>(deviceDTO.getId(), HttpStatus.OK);
    }
    @GetMapping(value = "/findBy/{user_id}")
    public ResponseEntity<List<DeviceDTO>> getDevicesByUserId(@PathVariable("user_id") String user_id) {
        System.out.println(user_id);
        String aux = user_id.substring(1,37);
        System.out.println(aux);
        UUID uid = UUID.fromString(aux);
        List<DeviceDTO> dtos = deviceService.findDeviceByUserId(uid);
       /* for (DeviceDTO dto : dtos) {
            Link deviceLink = linkTo(methodOn(DeviceController.class)
                    .getDevice(dto.getId())).withRel("deviceDetails");
            dto.add(deviceLink);
        }*/
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }

}
