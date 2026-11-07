package dripvora.fashion.category.entity;
import jakarta.persistence.*;
import lombok.Getter; import lombok.Setter;
import java.time.LocalDateTime;
@Entity @Table(name="categories",indexes=@Index(name="idx_category_name",columnList="name"))
@Getter @Setter
public class Category {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @Column(nullable=false,unique=true,length=100) private String name;
 @Column(length=500) private String description;
 @Column(nullable=false) private boolean active=true;
 @Column(nullable=false,updatable=false) private LocalDateTime createdAt;
 @PrePersist void create(){createdAt=LocalDateTime.now();}
}
