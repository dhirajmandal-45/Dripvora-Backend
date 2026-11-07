package dripvora.fashion.admin;
import org.springframework.security.access.prepost.PreAuthorize; import org.springframework.web.bind.annotation.*; import java.util.*; import dripvora.fashion.product.repository.ProductRepository; import dripvora.fashion.user.repository.UserRepository; import dripvora.fashion.order.repository.OrderRepository;
@RestController @RequestMapping("/dripora/admin/dashboard") @PreAuthorize("hasAuthority('ADMIN')")
public class AdminDashboardController{
 private final UserRepository users;private final ProductRepository products;private final OrderRepository orders;
 public AdminDashboardController(UserRepository u,ProductRepository p,OrderRepository o){users=u;products=p;orders=o;}
 @GetMapping public Map<String,Object> dashboard(){Map<String,Object> m=new LinkedHashMap<>();m.put("totalUsers",users.count());m.put("activeUsers",users.countByActive(true));m.put("verifiedUsers",users.countByEmailVerified(true));m.put("totalAdmins",users.countByRole("ADMIN"));m.put("totalProducts",products.count());m.put("activeProducts",products.findByActiveTrue().size());m.put("lowStockProducts",products.findByStockQuantityLessThanEqual(5).size());m.put("totalOrders",orders.count());m.put("pendingOrders",orders.countByOrderStatus("PENDING"));m.put("deliveredOrders",orders.countByOrderStatus("DELIVERED"));m.put("cancelledOrders",orders.countByOrderStatus("CANCELLED"));return m;}
}
