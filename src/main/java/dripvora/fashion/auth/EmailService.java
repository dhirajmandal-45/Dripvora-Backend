package dripvora.fashion.auth;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.resend.Resend;
import com.resend.services.emails.model.CreateEmailOptions;
import com.resend.services.emails.model.Template;

import dripvora.fashion.user.entity.User;
import dripvora.fashion.user.repository.UserRepository;

@Service
public class EmailService {

    private final Resend resend;
    private final UserRepository userRepository;

    @Value("${resend.from}")
    private String fromEmail;

    @Value("${resend.otp-template-id}")
    private String templateId;

    public EmailService(
            @Value("${resend.api.key}") String apiKey,
            UserRepository userRepository) {

        this.resend = new Resend(apiKey);
        this.userRepository = userRepository;
    }

    // ==========================================
    // SEND OTP EMAIL
    // ==========================================

    public void sendOtpEmail(
            String email,
            String otp) {

        try {

            User user = userRepository.findByEmail(email)
                    .orElseThrow(() -> new RuntimeException(
                            "User not found: " + email));

            String userName = user.getName();

            Template template = Template.builder()
                    .id(templateId)
                    .variables(
                            Map.of(
                                    "USER_NAME", userName,
                                    "OTP", otp))
                    .build();

            CreateEmailOptions options = CreateEmailOptions.builder()
                    .from(fromEmail)
                    .to(email)
                    .template(template)
                    .build();

            resend.emails().send(options);

        } catch (Exception e) {

            throw new RuntimeException(
                    "Failed to send OTP email: "
                            + e.getMessage(),
                    e);
        }
    }
}