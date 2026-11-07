package dripvora.fashion.auth;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface OtpVerificationRepository
        extends JpaRepository<OtpVerification, Long> {

    @Transactional
    void deleteByEmail(String email);

    Optional<OtpVerification> findTopByEmailOrderByCreatedAtDesc(
            String email);
}