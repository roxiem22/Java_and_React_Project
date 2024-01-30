package ro.tuc.ds2020.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.tuc.ds2020.dtos.UserIdDTO;
import ro.tuc.ds2020.services.UserIdService;

import java.util.UUID;

@RestController
@CrossOrigin("*")
@RequestMapping(value = "/userId")
public class UserIdController {
    private final UserIdService userIdService;

    @Autowired
    public UserIdController(UserIdService userIdService) {
        this.userIdService = userIdService;
    }

    @PostMapping()
    public ResponseEntity<String> insertUserId( @RequestBody String id) {
        //System.out.println(id);
        UserIdDTO userIdDTO = new UserIdDTO();
        userIdDTO.setId(UUID.fromString(id));
        //System.out.println(userIdDTO.getId());
        UUID idu = userIdService.insert(userIdDTO);
        return new ResponseEntity<>(idu.toString(), HttpStatus.CREATED);
    }

    @DeleteMapping()
    public ResponseEntity<String> deleteUserId( @RequestBody String id) {
        //System.out.println(id);
        UserIdDTO userIdDTO = new UserIdDTO();
        userIdDTO.setId(UUID.fromString(id));
        //System.out.println(userIdDTO.getId());
        userIdService.delete(userIdDTO);
        return new ResponseEntity<>(userIdDTO.getId().toString(), HttpStatus.OK);
    }

}
