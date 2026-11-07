package dripvora.fashion.user.controller;

import jakarta.validation.Valid;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dripvora.fashion.user.dto.UserProfileUpdateRequest;
import dripvora.fashion.user.dto.UserResponse;
import dripvora.fashion.user.service.UserService;

@RestController
@RequestMapping("/dripvora/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // ==========================================
    // GET CURRENT USER
    // ==========================================

    @GetMapping("/profile")
    public UserResponse getCurrentUser(
            Authentication authentication) {

        return userService.getProfile(
                authentication.getName());
    }

    // ==========================================
    // UPDATE CURRENT USER PROFILE
    // ==========================================

    @PutMapping("/profile-update")
    public UserResponse updateProfile(
            Authentication authentication,
            @Valid @RequestBody UserProfileUpdateRequest request) {

        return userService.updateProfile(
                authentication.getName(),
                request);
    }

    // ==========================================
    // DEACTIVATE CURRENT USER
    // ==========================================

    @DeleteMapping("/profile-delete")
    public String deactivateProfile(
            Authentication authentication) {

        userService.deactivateProfile(
                authentication.getName());

        return "Account deactivated successfully";
    }
}