package ro.tuc.ds2020.entities;

import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Entity
public class Device implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(columnDefinition = "BINARY(16)",name="id",nullable = false)
    private UUID device_id;

    @Column(columnDefinition = "BINARY(16)",name="user_id",nullable = false)
    private UUID user_id;

    @Column(name = "mhec", nullable = false)
    private int mhec;

    @OneToMany(mappedBy = "device")
    private List<Consumer> consumerList;

    public Device(UUID device_id, UUID user_id, int mhec) {
        this.device_id = device_id;
        this.user_id = user_id;
        this.mhec = mhec;
    }


    public Device() {

    }

    public UUID getDevice_id() {
        return device_id;
    }

    public void setDevice_id(UUID device_id) {
        this.device_id = device_id;
    }

    public UUID getUser_id() {
        return user_id;
    }

    public void setUser_id(UUID user_id) {
        this.user_id = user_id;
    }

    public int getMhec() {
        return mhec;
    }

    public void setMhec(int mhec) {
        this.mhec = mhec;
    }

    public List<Consumer> getConsumerList() {
        return consumerList;
    }

    public void setConsumerList(List<Consumer> consumerList) {
        this.consumerList = consumerList;
    }
}
