package ro.tuc.ds2020.entities;
import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Entity
public class UserId implements Serializable{
    private static final long serialVersionUID = 1L;
    @Id
    @Column(columnDefinition = "BINARY(16)",name="id",nullable = false)
    private UUID id;
    @OneToMany(cascade = CascadeType.REMOVE,fetch = FetchType.LAZY,mappedBy = "user")
    private List<Device> deviceList;

    public UserId() {

    }

    public UserId(UUID id) {
        this.id = id;
    }


    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public List<Device> getDeviceList() {
        return deviceList;
    }

    public void setDeviceList(List<Device> deviceList) {
        this.deviceList = deviceList;
    }
}
