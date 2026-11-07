package dripvora.fashion.admin;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import dripvora.fashion.admin.dto.AdminUserResponse;
import dripvora.fashion.user.entity.User;
import dripvora.fashion.user.repository.UserRepository;

@RestController
@RequestMapping("/dripvora/admin")
@PreAuthorize("hasAuthority('ADMIN')")
public class AdminController {

        private final UserRepository userRepository;

        public AdminController(UserRepository userRepository) {
                this.userRepository = userRepository;
        }

        // ==========================================
        // GET ALL USERS
        // ==========================================

        @GetMapping("/users")
        public ResponseEntity<List<AdminUserResponse>> getAllUsers() {

                List<AdminUserResponse> users = userRepository.findAll()
                                .stream()
                                .map(this::toResponse)
                                .toList();

                return ResponseEntity.ok(users);
        }

        // ==========================================
        // UPDATE USER STATUS
        // ==========================================

        @PatchMapping("/users/{id}/status")
        public ResponseEntity<String> updateUserStatus(
                        @PathVariable Long id,
                        @RequestParam boolean active) {

                User user = userRepository.findById(id)
                                .orElseThrow(() -> new RuntimeException(
                                                "User not found with id: " + id));

                user.setActive(active);

                userRepository.save(user);

                return ResponseEntity.ok(
                                "User status updated successfully");
        }

        // ==========================================
        // PROMOTE USER TO ADMIN
        // ==========================================

        @PatchMapping("/users/{id}/promote")
        public ResponseEntity<String> promoteToAdmin(
                        @PathVariable Long id) {

                User user = userRepository.findById(id)
                                .orElseThrow(() -> new RuntimeException(
                                                "User not found with id: " + id));

                user.setRole("ADMIN");

                userRepository.save(user);

                return ResponseEntity.ok(
                                "User promoted to ADMIN successfully");
        }

        // ==========================================
        // ENTITY -> RESPONSE
        // ==========================================

        private AdminUserResponse toResponse(User user) {

                return new AdminUserResponse(
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