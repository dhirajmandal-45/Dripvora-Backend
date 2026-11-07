package dripvora.fashion.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import dripvora.fashion.user.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    // Find user by email
    Optional<User> findByEmail(String email);

    // Check whether email already exists
    boolean existsByEmail(String email);

    // Admin/dashboard statistics
    long countByRole(String role);

    long countByActive(boolean active);

    long countByEmailVerified(boolean emailVerified);
}