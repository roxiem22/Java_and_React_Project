package ro.tuc.ds2020.repositories;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ro.tuc.ds2020.entities.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
   // User findUserByName(String name);
    Optional<User> findByNameAndPassword(String username, String password);
    @Transactional
    @Query(value = "SELECT p " +
            "FROM User p " +
            "WHERE p.id = :id ")
    User findUserById(@Param("id") UUID id);

    @Transactional
    @Modifying
    @Query(value = "DELETE " +
            "FROM User u " +
            "WHERE u.name = :name " +
            "AND u.password = :password  ")
    void deleteByNameAndPass(@Param("name") String name, @Param("password") String password);

  Optional<User> findByName(String name);
}
