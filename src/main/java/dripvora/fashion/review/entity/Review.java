package dripvora.fashion.review.entity;
import jakarta.persistence.*; import lombok.Getter; import lombok.Setter; import java.time.LocalDateTime; import dripvora.fashion.product.entity.Product; import dripvora.fashion.user.entity.User;
@Entity @Table(name="reviews",uniqueConstraints=@UniqueConstraint(columnNames={"user_id","product_id"})) @Getter @Setter
public class Review{@Id @GeneratedValue(strategy=GenerationType.IDENTITY) Long id;@ManyToOne(optional=false)@JoinColumn(name="user_id") User user;@ManyToOne(optional=false)@JoinColumn(name="product_id") Product product;@Column(nullable=false) int rating;@Column(length=1000) String comment;LocalDateTime createdAt;@PrePersist void c(){createdAt=LocalDateTime.now();}}
