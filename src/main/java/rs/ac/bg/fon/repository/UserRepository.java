package rs.ac.bg.fon.repository;

import jakarta.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import rs.ac.bg.fon.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    Optional<User> findByCustomerId(@Param("id") Long customerId);

    @Modifying
    @Transactional
    @Query(value = "UPDATE user u SET u.membership_expiration = :expirationDate WHERE u.id = :id", nativeQuery = true)
    void updateMembershipExpiration(@Param("id") Long id, @Param("expirationDate") LocalDate expirationDate);

    @Query("SELECT u FROM User u WHERE u.membershipExpiration = :targetDate")
    List<User> findUsersWithMembershipExpiringOn(LocalDate targetDate);

}
