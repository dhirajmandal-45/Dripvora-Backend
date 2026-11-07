package dripvora.fashion.admin;
import org.springframework.security.access.prepost.PreAuthorize; import org.springframework.web.bind.annotation.*; import java.util.*; import dripvora.fashion.user.entity.User; import dripvora.fashion.user.repository.UserRepository; import dripvora.fashion.exception.ResourceNotFoundException;
@RestController @RequestMapping("/dripora/admin/users") @PreAuthorize("hasAuthority('ADMIN')")
public class AdminUserController{
 private final UserRepository repo;public AdminUserController(UserRepository r){repo=r;}
 @GetMapping public List<User> all(){return repo.findAll().stream().peek(u->u.setPassword(null)).toList();}
 @GetMapping("/{id}") public User get(@PathVariable Long id){User u=repo.findById(id).orElseThrow(()->new ResourceNotFoundException("User not found"));u.setPassword(null);return u;}
 @PutMapping("/{id}/active") public User active(@PathVariable Long id,@RequestParam boolean value){User u=repo.findById(id).orElseThrow(()->new ResourceNotFoundException("User not found"));u.setActive(value);u.setPassword(null);return repo.save(u);}
}
