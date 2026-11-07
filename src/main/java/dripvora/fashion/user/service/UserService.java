package dripvora.fashion.user.service;

import dripvora.fashion.exception.ResourceNotFoundException;
import dripvora.fashion.user.dto.UserProfileUpdateRequest;
import dripvora.fashion.user.dto.UserResponse;
import dripvora.fashion.user.entity.User;
import dripvora.fashion.user.repository.UserRepository;

import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // ==========================================
    // GET USER PROFILE
    // ==========================================

    public UserResponse getProfile(String email) {

        User user = findUserByEmail(email);

        return toUserResponse(user);
    }

    // ==========================================
    // UPDATE USER PROFILE
    // ==========================================

    public UserResponse updateProfile(
            String email,
            UserProfileUpdateRequest request) {

        User user = findUserByEmail(email);

        // Name
        if (request.getName() != null
                && !request.getName().isBlank()) {

            user.setName(request.getName().trim());
        }

        // Address
        if (request.getAddress() != null) {

            user.setAddress(
                    request.getAddress().trim());
        }

        // City
        if (request.getCity() != null) {

            user.setCity(
                    request.getCity().trim());
        }

        // State
        if (request.getState() != null) {

            user.setState(
                    request.getState().trim());
        }

        // Pincode
        if (request.getPincode() != null) {

            user.setPincode(
                    request.getPincode().trim());
        }

        User updatedUser = userRepository.save(user);

        return toUserResponse(updatedUser);
    }

    // ==========================================
    // DEACTIVATE PROFILE
    // ==========================================

    public void deactivateProfile(String email) {

        User user = findUserByEmail(email);

        user.setActive(false);

        userRepository.save(user);
    }

    // ==========================================
    // FIND USER
    // ==========================================

    private User findUserByEmail(String email) {

        return userRepository.findByEmail(
                email.trim().toLowerCase()).orElseThrow(
                        () -> new ResourceNotFoundException(
                                "User not found"));
    }

    // ==========================================
    // ENTITY -> RESPONSE DTO
    // ==========================================

    private UserResponse toUserResponse(User user) {

        return new UserResponse(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getRole(),
                user.isActive(),
                user.isEmailVerified(),
                user.getAddress(),
                user.getCity(),
                user.getState(),
                user.getPincode(),
                user.getCreatedAt(),
                user.getUpdatedAt());
    }
}