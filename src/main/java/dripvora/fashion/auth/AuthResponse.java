package dripvora.fashion.auth;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthResponse {

    private String message;
    private Long userId;
    private String name;
    private String email;

    private String token;

    public AuthResponse(
            String message,
            Long userId,
            String name,
            String email,

            String token) {

        this.message = message;
        this.userId = userId;
        this.name = name;
        this.email = email;

        this.token = token;
    }
}
