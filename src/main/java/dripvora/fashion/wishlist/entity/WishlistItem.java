package dripvora.fashion.wishlist.entity;
import jakarta.persistence.*; import lombok.Getter; import lombok.Setter; import dripvora.fashion.product.entity.Product; import dripvora.fashion.user.entity.User;
@Entity @Table(name="wishlist_items",uniqueConstraints=@UniqueConstraint(columnNames={"user_id","product_id"})) @Getter @Setter
public class WishlistItem{@Id @GeneratedValue(strategy=GenerationType.IDENTITY) Long id; @ManyToOne(optional=false) @JoinColumn(name="user_id") User user; @ManyToOne(optional=false) @JoinColumn(name="product_id") Product product;}
