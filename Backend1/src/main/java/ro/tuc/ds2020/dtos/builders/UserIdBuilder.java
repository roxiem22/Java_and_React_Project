package ro.tuc.ds2020.dtos.builders;

import ro.tuc.ds2020.dtos.UserIdDTO;
import ro.tuc.ds2020.entities.UserId;

public class UserIdBuilder {

    public static UserIdDTO toUserIdDTO(UserId userId) {
        return new UserIdDTO(userId.getId());
    }

    public static UserId toEntity(UserIdDTO userIdDTO) {
        return new UserId(userIdDTO.getId());
    }

}
