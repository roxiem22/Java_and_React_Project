package ro.tuc.ds2020.controllers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.tuc.ds2020.dtos.UserDTO;
import ro.tuc.ds2020.entities.User;
import ro.tuc.ds2020.services.UserService;

import java.util.List;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping(value = "/user")
public class UserController {
    private final UserService userService;
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public ResponseEntity<List<UserDTO>> getUsers() {
        List<UserDTO> dtos = userService.findUser();
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<UUID> insertUser( @RequestBody UserDTO userDTO) {
        UUID userId = userService.insert(userDTO);
        return new ResponseEntity<>(userId, HttpStatus.CREATED);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<UserDTO> getUser(@PathVariable("id") UUID userId) {
        UserDTO dto = userService.findUserById(userId);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    //TODO: UPDATE, DELETE per resource
    @PutMapping()
    public ResponseEntity<UUID> updateUser( @RequestBody UserDTO userDTO)
    {
        UUID userId = userService.update(userDTO);
        return new ResponseEntity<>(userId, HttpStatus.OK);
    }

    @DeleteMapping()
    public ResponseEntity<UUID> deleteUser( @RequestBody UserDTO userDTO) {
        userService.delete(userDTO);
        return new ResponseEntity<>(userDTO.getId(), HttpStatus.OK);
    }


    @PostMapping(value = "/login")
    public ResponseEntity<UserDTO> login( @RequestBody UserDTO userDTO) {
        if(userDTO.getPassword().equals("")){
            System.out.println("Parola invalida");
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);

        }
        if(userDTO.getName().equals("")){
            System.out.println("Username invalid");
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }

        UserDTO user1 = userService.login(userDTO.getName(), userDTO.getPassword());
        if(user1!=null)
            return new ResponseEntity<>(user1, HttpStatus.OK);
        else return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }
    @PostMapping(value = "/register")
    public ResponseEntity<UserDTO> register (@RequestBody UserDTO user) {

        UserDTO user1 = userService.register(user);
        if(user1 != null)
            return new ResponseEntity<>(user1, HttpStatus.OK);
        else
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

}
