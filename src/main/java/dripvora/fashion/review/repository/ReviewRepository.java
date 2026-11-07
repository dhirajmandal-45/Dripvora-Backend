package dripvora.fashion.review.repository;
import org.springframework.data.jpa.repository.JpaRepository; import java.util.*; import dripvora.fashion.review.entity.Review;
public interface ReviewRepository extends JpaRepository<Review,Long>{List<Review> findByProductId(Long productId);}
