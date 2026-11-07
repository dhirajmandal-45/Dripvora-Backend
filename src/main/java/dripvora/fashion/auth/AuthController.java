package dripvora.fashion.auth;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import dripvora.fashion.user.entity.User;

@RestController
@RequestMapping("/dripvora")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    // ==========================================
    // SIGNUP
    // ==========================================

    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> signup(
            @Valid @RequestBody SignupRequest request) {

        User user = authService.signup(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new AuthResponse(
                        "Signup successful. OTP sent to email.",
                        user.getId(),
                        user.getName(),
                        user.getEmail(),
                        null));
    }

    // ==========================================
    // LOGIN
    // ==========================================

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(
            @Valid @RequestBody LoginRequest request) {

        User user = authService.login(request);

        return ResponseEntity.ok(
                new AuthResponse(
                        "OTP sent successfully",
                        user.getId(),
                        user.getName(),
                        user.getEmail(),
                        null));
    }

    // ==========================================
    // VERIFY OTP
    // ==========================================

    @PostMapping("/verify-otp")
    public ResponseEntity<AuthResponse> verify(
            @Valid @RequestBody OtpVerifyRequest request) {

        AuthResponse result = authService.verifyLoginOtp(
                request.getEmail(),
                request.getOtp());

        return ResponseEntity.ok(result);
    }

    // ==========================================
    // RESEND OTP
    // ==========================================

    @PostMapping("/resend-otp")
    public ResponseEntity<String> resend(
            @Valid @RequestBody ResendOtpRequest request) {

        authService.resendLoginOtp(request.getEmail());

        return ResponseEntity.ok("OTP resent successfully");
    }
}