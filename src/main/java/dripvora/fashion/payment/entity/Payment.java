package dripvora.fashion.payment.entity;
import jakarta.persistence.*; import lombok.Getter; import lombok.Setter; import java.math.BigDecimal; import java.time.LocalDateTime; import dripvora.fashion.order.entity.Order;
@Entity @Table(name="payments") @Getter @Setter
public class Payment{@Id @GeneratedValue(strategy=GenerationType.IDENTITY) Long id;@OneToOne(optional=false)@JoinColumn(name="order_id",unique=true) Order order;@Column(nullable=false,precision=12,scale=2) BigDecimal amount;@Column(nullable=false) String status="PENDING";String transactionId;LocalDateTime createdAt;@PrePersist void c(){createdAt=LocalDateTime.now();}}
