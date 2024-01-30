package ro.tuc.ds2020.dtos;

import java.util.Objects;
import java.util.UUID;

public class UserIdDTO {
    private UUID id;

    public UserIdDTO() {
    }

    public UserIdDTO(UUID id) {
        this.id = id;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserIdDTO userIdDTO = (UserIdDTO) o;
        return Objects.equals(id, userIdDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "UserIdDTO{" +
                "id=" + id +
                '}';
    }
}
