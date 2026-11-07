package dripvora.fashion.wishlist.repository;
import org.springframework.data.jpa.repository.JpaRepository; import java.util.*; import dripvora.fashion.wishlist.entity.WishlistItem;
public interface WishlistRepository extends JpaRepository<WishlistItem,Long>{List<WishlistItem> findByUserEmail(String email);Optional<WishlistItem> findByUserEmailAndProductId(String email,Long productId);}
