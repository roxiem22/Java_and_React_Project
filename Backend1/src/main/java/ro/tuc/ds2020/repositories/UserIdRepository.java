package ro.tuc.ds2020.repositories;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ro.tuc.ds2020.entities.UserId;

import java.beans.Transient;
import java.util.UUID;

public interface UserIdRepository extends JpaRepository<UserId, UUID> {
    @Transactional
    @Query(value = "SELECT p " +
            "FROM UserId p " +
            "WHERE p.id = :id ")
    UserId findUserIdById(@Param("id") UUID id);
}
