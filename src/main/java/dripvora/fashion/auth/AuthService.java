package dripvora.fashion.auth;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import dripvora.fashion.security.JwtService;
import dripvora.fashion.user.entity.User;
import dripvora.fashion.user.repository.UserRepository;

@Service
public class AuthService {

        private final UserRepository userRepository;
        private final PasswordEncoder passwordEncoder;
        private final OtpService otpService;
        private final JwtService jwtService;

        public AuthService(
                        UserRepository userRepository,
                        PasswordEncoder passwordEncoder,
                        OtpService otpService,
                        JwtService jwtService) {

                this.userRepository = userRepository;
                this.passwordEncoder = passwordEncoder;
                this.otpService = otpService;
                this.jwtService = jwtService;
        }

        // ==========================================
        // SIGNUP
        // ==========================================

        public User signup(SignupRequest request) {

                String email = request.getEmail()
                                .trim()
                                .toLowerCase();

                // Check duplicate email
                if (userRepository.existsByEmail(email)) {
                        throw new RuntimeException(
                                        "Email is already registered");
                }

                User user = new User();

                user.setName(request.getName().trim());

                user.setEmail(email);

                // Never save plain password
                user.setPassword(
                                passwordEncoder.encode(
                                                request.getPassword()));

                // Public signup always creates USER
                user.setRole("USER");

                user.setActive(true);

                user.setEmailVerified(false);

                user.setAddress(
                                request.getAddress().trim());

                user.setCity(
                                request.getCity().trim());

                user.setState(
                                request.getState().trim());

                user.setPincode(
                                request.getPincode().trim());

                user = userRepository.save(user);
                otpService.generateAndSendOtp(email);
                return user;
        }

        // ==========================================
        // LOGIN
        // ==========================================

        public User login(LoginRequest request) {

                String email = request.getEmail()
                                .trim()
                                .toLowerCase();

                // Find user
                User user = userRepository.findByEmail(email)
                                .orElseThrow(() -> new RuntimeException(
                                                "Invalid email or password"));

                // Check account status
                if (!user.isActive()) {
                        throw new RuntimeException(
                                        "Your account is inactive");
                }

                // Check password
                if (!passwordEncoder.matches(
                                request.getPassword(),
                                user.getPassword())) {

                        throw new RuntimeException(
                                        "Invalid email or password");
                }

                // Password is correct.
                // Generate and send OTP to email.
                otpService.generateAndSendOtp(email);

                // IMPORTANT:
                // Do NOT generate JWT here.
                // JWT is generated only after OTP verification.
                return user;
        }

        // ==========================================
        // VERIFY LOGIN OTP
        // ==========================================

        public AuthResponse verifyLoginOtp(
                        String email,
                        String otp) {

                email = email.trim().toLowerCase();

                // Find user
                User user = userRepository.findByEmail(email)
                                .orElseThrow(() -> new RuntimeException(
                                                "User not found"));

                // Check account status
                if (!user.isActive()) {
                        throw new RuntimeException(
                                        "Your account is inactive");
                }

                // Verify OTP
                boolean verified = otpService.verifyOtp(
                                email,
                                otp);

                if (!verified) {
                        throw new RuntimeException(
                                        "Invalid or expired OTP");
                }

                user.setEmailVerified(true);
                userRepository.save(user);

                // ==========================================
                // OTP CORRECT
                // NOW GENERATE JWT
                // ==========================================

                String token = jwtService.generateToken(
                                user.getEmail(),
                                user.getRole());

                return new AuthResponse(
                                "Login successful",
                                user.getId(),
                                user.getName(),
                                user.getEmail(),
                                token);
        }

        // ==========================================
        // RESEND LOGIN OTP
        // ==========================================

        public void resendLoginOtp(String email) {

                email = email.trim().toLowerCase();

                // Check user
                User user = userRepository.findByEmail(email)
                                .orElseThrow(() -> new RuntimeException(
                                                "User not found"));

                // Check account
                if (!user.isActive()) {
                        throw new RuntimeException(
                                        "Your account is inactive");
                }

                // Generate new OTP and send email
                otpService.resendOtp(email);
        }
}