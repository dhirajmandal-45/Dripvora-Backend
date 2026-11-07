package dripvora.fashion.admin;
import org.springframework.security.access.prepost.PreAuthorize; import org.springframework.web.bind.annotation.*; import java.util.*; import dripvora.fashion.order.entity.Order; import dripvora.fashion.order.service.OrderService;
@RestController @RequestMapping("/dripora/admin/orders") @PreAuthorize("hasAuthority('ADMIN')")
public class AdminOrderController{private final OrderService s;public AdminOrderController(OrderService s){this.s=s;}@GetMapping public List<Order> all(){return s.all();}@PutMapping("/{id}/status") public Order status(@PathVariable Long id,@RequestParam String status){return s.status(id,status);}}
