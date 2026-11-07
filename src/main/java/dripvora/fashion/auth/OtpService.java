package dripvora.fashion.auth;

import java.security.SecureRandom;
import java.time.LocalDateTime;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OtpService {

    private final OtpVerificationRepository otpRepository;
    private final EmailService emailService;

    private final SecureRandom secureRandom = new SecureRandom();

    private static final int OTP_EXPIRY_MINUTES = 5;

    public OtpService(
            OtpVerificationRepository otpRepository,
            EmailService emailService) {

        this.otpRepository = otpRepository;
        this.emailService = emailService;
    }

    // ==========================================
    // GENERATE + SAVE OTP
    // ==========================================

    private String generateOtp(String email) {

        String otp = String.format(
                "%06d",
                secureRandom.nextInt(1_000_000)
        );

        // Remove previous OTP for this email
        otpRepository.deleteByEmail(email);

        OtpVerification verification =
                new OtpVerification();

        verification.setEmail(email);

        verification.setOtp(otp);

        verification.setExpiresAt(
                LocalDateTime.now()
                        .plusMinutes(OTP_EXPIRY_MINUTES)
        );

        verification.setVerified(false);

        otpRepository.save(verification);

        return otp;
    }

    // ==========================================
    // GENERATE + SEND OTP
    // ==========================================

    @Transactional
    public void generateAndSendOtp(String email) {

        email = email.trim().toLowerCase();

        String otp = generateOtp(email);

        // Send using your EXISTING Resend template
        emailService.sendOtpEmail(
                email,
                otp
        );
    }

    // ==========================================
    // VERIFY OTP
    // ==========================================

    @Transactional
    public boolean verifyOtp(
            String email,
            String otp) {

        email = email.trim().toLowerCase();

        if (otp == null || otp.isBlank()) {
            return false;
        }

        otp = otp.trim();

        OtpVerification verification =
                otpRepository
                        .findTopByEmailOrderByCreatedAtDesc(email)
                        .orElse(null);

        // OTP does not exist
        if (verification == null) {
            return false;
        }

        // OTP already used
        if (verification.isVerified()) {
            return false;
        }

        // OTP expired
        if (verification.getExpiresAt()
                .isBefore(LocalDateTime.now())) {

            return false;
        }

        // Wrong OTP
        if (!verification.getOtp().equals(otp)) {
            return false;
        }

        // OTP is correct
        verification.setVerified(true);

        otpRepository.save(verification);

        return true;
    }

    // ==========================================
    // RESEND OTP
    // ==========================================

    @Transactional
    public void resendOtp(String email) {

        email = email.trim().toLowerCase();

        String otp = generateOtp(email);

        // Send using your EXISTING Resend template
        emailService.sendOtpEmail(
                email,
                otp
        );
    }
}