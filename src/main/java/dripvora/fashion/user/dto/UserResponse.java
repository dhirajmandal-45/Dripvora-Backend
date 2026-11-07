package dripvora.fashion.user.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class UserResponse {

    private Long id;
    private String name;
    private String email;
    private String role;
    private boolean active;
    private boolean emailVerified;
    private String address;
    private String city;
    private String state;
    private String pincode;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public UserResponse(
            Long id,
            String name,
            String email,
            String role,
            boolean active,
            boolean emailVerified,
            String address,
            String city,
            String state,
            String pincode,
            LocalDateTime createdAt,
            LocalDateTime updatedAt) {

        this.id = id;
        this.name = name;
        this.email = email;
        this.role = role;
        this.active = active;
        this.emailVerified = emailVerified;
        this.address = address;
        this.city = city;
        this.state = state;
        this.pincode = pincode;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}