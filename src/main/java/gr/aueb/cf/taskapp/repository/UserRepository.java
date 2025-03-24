package gr.aueb.cf.taskapp.repository;

import gr.aueb.cf.taskapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Optional<User> findByLastname(String lastname);
    Optional<User> findByUsernameAndLastname(String username, String lastname);
}
