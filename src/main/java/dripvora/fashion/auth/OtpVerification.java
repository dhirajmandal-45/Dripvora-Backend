package dripvora.fashion.auth;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "otp_verifications")
@Getter
@Setter
public class OtpVerification {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(nullable = false)
        private String email;

        @Column(nullable = false, length = 6)
        private String otp;

        @Column(nullable = false)
        private LocalDateTime expiresAt;

        @Column(nullable = false)
        private boolean verified = false;

        @Column(nullable = false)
        private LocalDateTime createdAt;

        @PrePersist
        protected void onCreate() {

                if (createdAt == null) {
                        createdAt = LocalDateTime.now();
                }
        }
}