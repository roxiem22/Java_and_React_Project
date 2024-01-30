package ro.tuc.ds2020.dtos;

import java.util.Objects;
import java.util.UUID;

public class DeviceDTO {
    private UUID id;
    private String description;
    private String address;
    private int MHEnergyC;
    private UUID user_id;

    public DeviceDTO() {
    }

    public DeviceDTO(UUID id, String description, String address, int MHEnergyC, UUID user_id) {
        this.id = id;
        this.description = description;
        this.address = address;
        this.MHEnergyC = MHEnergyC;
        this.user_id = user_id;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getMHEnergyC() {
        return MHEnergyC;
    }

    public void setMHEnergyC(int MHEnergyC) {
        this.MHEnergyC = MHEnergyC;
    }

    public UUID getUser_id() {
        return user_id;
    }

    public void setUser_id(UUID user_id) {
        this.user_id = user_id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DeviceDTO deviceDTO = (DeviceDTO) o;
        return MHEnergyC == deviceDTO.MHEnergyC && Objects.equals(id, deviceDTO.id) && Objects.equals(description, deviceDTO.description) && Objects.equals(address, deviceDTO.address) && Objects.equals(user_id, deviceDTO.user_id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, description, address, MHEnergyC, user_id);
    }

    @Override
    public String toString() {
        return "DeviceDTO{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", address='" + address + '\'' +
                ", MHEnergyC=" + MHEnergyC +
                ", user_id=" + user_id +
                '}';
    }
}
