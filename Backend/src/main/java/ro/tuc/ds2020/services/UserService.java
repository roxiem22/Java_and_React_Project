package ro.tuc.ds2020.services;

import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ro.tuc.ds2020.dtos.UserDTO;
import ro.tuc.ds2020.dtos.builders.UserBuilder;
import ro.tuc.ds2020.entities.User;
import ro.tuc.ds2020.repositories.UserRepository;
import ro.tuc.ds2020.security.JwtService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;



    @Autowired
    public UserService(UserRepository userRepository, AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public List<UserDTO> findUser() {
        List<User> userList = userRepository.findAll();
        return userList.stream()
                .map(UserBuilder::toUserDTO)
                .collect(Collectors.toList());
    }

    public UserDTO findUserById(UUID id) {
        Optional<User> prosumerOptional = userRepository.findById(id);
        if (!prosumerOptional.isPresent()) {
            LOGGER.error("User with id {} was not found in db", id);
            throw new RuntimeException(User.class.getSimpleName() + " with id: " + id);
        }
        return UserBuilder.toUserDTO(prosumerOptional.get());
    }

    public UUID insert(UserDTO userDTO) {
        User user = UserBuilder.toEntity(userDTO);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user = userRepository.save(user);
        LOGGER.debug("User with id {} was inserted in db", user.getId());
        return user.getId();
    }

    public UUID update(UserDTO userDTO) {
        System.out.println(userDTO.getName());
        System.out.println(userDTO.getId());
        User user = userRepository.findUserById(userDTO.getId());

        if (user == null) {
            LOGGER.error("User with id {} not found in the database", userDTO.getId());
            throw new EntityNotFoundException("User with id " + userDTO.getId() + " not found.");
        }

        System.out.println(user);
        user.setName(userDTO.getName());
        user.setRole(userDTO.getRole());
        //user.setPassword(userDTO.getPassword());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setMail(userDTO.getMail());
        user = userRepository.save(user);
        LOGGER.debug("User with id {} was updated in db", user.getId());
        return user.getId();
    }

    public void delete(UserDTO userDTO) {
        userRepository.deleteByNameAndPass(userDTO.getName(),userDTO.getPassword());
        LOGGER.debug("User was deleted from db");
    }

    public UserDTO login(String username, String password) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username,password)
        );

        User user = userRepository.findByName(username).get();
        String jwtToken = jwtService.generateToken(user);
        UserDTO dto = UserBuilder.toUserDTO(user);
        dto.setToken(jwtToken);
        return dto;
    }

    public UserDTO register(UserDTO userDTO) {

        User user = UserBuilder.toEntity(userDTO);
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        String jwtToken = jwtService.generateToken(user);
        User u = userRepository.save(user);
        UserDTO dto = UserBuilder.toUserDTO(u);
        dto.setToken(jwtToken);

        return dto;
    }
}
