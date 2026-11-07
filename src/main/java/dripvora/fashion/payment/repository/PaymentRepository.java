package dripvora.fashion.payment.repository;
import org.springframework.data.jpa.repository.JpaRepository; import java.util.*; import dripvora.fashion.payment.entity.Payment;
public interface PaymentRepository extends JpaRepository<Payment,Long>{Optional<Payment> findByOrderId(Long orderId);}
