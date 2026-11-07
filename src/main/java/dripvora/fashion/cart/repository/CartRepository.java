package dripvora.fashion.cart.repository;
import org.springframework.data.jpa.repository.JpaRepository; import java.util.*; import dripvora.fashion.cart.entity.Cart;
public interface CartRepository extends JpaRepository<Cart,Long>{Optional<Cart> findByUserEmail(String email);}
