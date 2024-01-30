package ro.tuc.ds2020.entities;
import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

import java.io.Serializable;
import java.util.UUID;

@Entity
public class Device implements Serializable{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;


    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "MHEnergyC", nullable = false)
    private int MHEnergyC;

    @ManyToOne(fetch = FetchType.LAZY)
    private UserId user;

    public Device() {
    }

    public Device(String description, String address, int MHEnergyC, UserId user) {
        this.description = description;
        this.address = address;
        this.MHEnergyC = MHEnergyC;
        this.user=user;
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

    public UserId getUser_id() {
        return user;
    }

    public void setUser_id(UserId user_id) {
        this.user = user_id;
    }
}
