package dripvora.fashion.wishlist.service;
import org.springframework.stereotype.Service; import org.springframework.transaction.annotation.Transactional; import java.util.*; import dripvora.fashion.wishlist.entity.*; import dripvora.fashion.wishlist.repository.*; import dripvora.fashion.user.repository.UserRepository; import dripvora.fashion.product.repository.ProductRepository; import dripvora.fashion.exception.*;
@Service public class WishlistService{
 private final WishlistRepository repo; private final UserRepository users; private final ProductRepository products;
 public WishlistService(WishlistRepository r,UserRepository u,ProductRepository p){repo=r;users=u;products=p;}
 public List<WishlistItem> get(String email){return repo.findByUserEmail(email);}
 @Transactional public WishlistItem add(String email,Long pid){if(repo.findByUserEmailAndProductId(email,pid).isPresent())throw new BadRequestException("Already in wishlist");WishlistItem i=new WishlistItem();i.setUser(users.findByEmail(email).orElseThrow(()->new ResourceNotFoundException("User not found")));i.setProduct(products.findById(pid).orElseThrow(()->new ResourceNotFoundException("Product not found")));return repo.save(i);}
 public void remove(String email,Long pid){repo.findByUserEmailAndProductId(email,pid).ifPresent(repo::delete);}
}
