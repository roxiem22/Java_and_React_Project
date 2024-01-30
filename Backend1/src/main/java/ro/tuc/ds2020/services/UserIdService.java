package ro.tuc.ds2020.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.tuc.ds2020.dtos.UserIdDTO;
import ro.tuc.ds2020.dtos.builders.UserIdBuilder;
import ro.tuc.ds2020.entities.UserId;
import ro.tuc.ds2020.repositories.DeviceRepository;
import ro.tuc.ds2020.repositories.UserIdRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserIdService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserIdService.class);
    private final UserIdRepository userIdRepository;

     private final DeviceRepository deviceRepository;

    @Autowired
    public UserIdService(UserIdRepository userIdRepository, DeviceRepository deviceRepository) {
        this.userIdRepository = userIdRepository;
        this.deviceRepository = deviceRepository;
    }

     public List<UserIdDTO> findUserId() {
            List<UserId> userIdList = userIdRepository.findAll();
            return userIdList.stream()//public List<UserIdDTO>
                    .map(UserIdBuilder::toUserIdDTO)
                    .collect(Collectors.toList());
     }

      public UserIdDTO findUserIdById(UUID id) {
             Optional<UserId> prosumerOptional = userIdRepository.findById(id);
             if (!prosumerOptional.isPresent()) {
                 throw new RuntimeException(UserId.class.getSimpleName() + " with id: " + id);
             }
             return UserIdBuilder.toUserIdDTO(prosumerOptional.get());
      }

      public UUID insert(UserIdDTO userIdDTO) {
              UserId userId = UserIdBuilder.toEntity(userIdDTO);
              userId = userIdRepository.save(userId);
              LOGGER.debug("Device with id {} was inserted in db", userId.getId());
              return userId.getId();
      }

       public void delete(UserIdDTO userIdDTO) {
              UserId userId = userIdRepository.findById(userIdDTO.getId()).get();
              userIdRepository.delete(userId);
              deviceRepository.deleteDeviceByUserId(userId.getId());
              LOGGER.debug("Device with id {} was deleted from db", userId.getId());
       }

}
