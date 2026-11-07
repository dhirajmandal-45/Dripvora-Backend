package dripvora.fashion.order.repository;
import org.springframework.data.jpa.repository.JpaRepository; import java.util.*; import dripvora.fashion.order.entity.Order;
public interface OrderRepository extends JpaRepository<Order,Long>{List<Order> findByUserEmailOrderByCreatedAtDesc(String email);Optional<Order> findByIdAndUserEmail(Long id,String email);long countByOrderStatus(String status);List<Order> findTop10ByOrderByCreatedAtDesc();}
