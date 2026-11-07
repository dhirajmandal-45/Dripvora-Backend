package dripvora.fashion.admin.dto;

import java.time.LocalDateTime;

public class AdminUserResponse {

    public Long id;
    public String name;
    public String email;
    public String role;
    public boolean active;
    public boolean emailVerified;
    public String address;
    public String city;
    public String state;
    public String pincode;
    public LocalDateTime createdAt;
    public LocalDateTime updatedAt;

    public AdminUserResponse(
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