package ro.tuc.ds2020.repositories;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ro.tuc.ds2020.entities.Consumer;
import ro.tuc.ds2020.entities.Device;

import java.util.UUID;

public interface ConsumerRepository extends JpaRepository<Consumer, UUID> {
    @Transactional
    @Query(value = "SELECT p " +
            "FROM Consumer p " +
            "WHERE p.id = :id ")
    Consumer findConsumerById(@Param("id") UUID id);
}
