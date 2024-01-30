package ro.tuc.ds2020.repositories;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ro.tuc.ds2020.entities.Device;

import java.beans.Transient;
import java.util.List;
import java.util.UUID;

public interface DeviceRepository extends JpaRepository<Device, UUID> {
    @Transactional
    @Query(value = "SELECT p " +
            "FROM Device p " +
            "WHERE p.device_id = :id ")
    Device findDeviceById(@Param("id") UUID id);
}
