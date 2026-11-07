package dripvora.fashion.cart.service;
import org.springframework.stereotype.Service; import org.springframework.transaction.annotation.Transactional;
import dripvora.fashion.cart.dto.CartItemRequest; import dripvora.fashion.cart.entity.*; import dripvora.fashion.cart.repository.CartRepository; import dripvora.fashion.product.entity.Product; import dripvora.fashion.product.repository.ProductRepository; import dripvora.fashion.user.entity.User; import dripvora.fashion.user.repository.UserRepository; import dripvora.fashion.exception.*;
@Service public class CartService{
 private final CartRepository carts; private final ProductRepository products; private final UserRepository users;
 public CartService(CartRepository c,ProductRepository p,UserRepository u){carts=c;products=p;users=u;}
 private Cart cart(String email){return carts.findByUserEmail(email).orElseGet(()->{Cart c=new Cart();User u=users.findByEmail(email).orElseThrow(()->new ResourceNotFoundException("User not found"));c.setUser(u);return carts.save(c);});}
 @Transactional public Cart add(String email,CartItemRequest r){Cart c=cart(email);Product p=products.findById(r.getProductId()).orElseThrow(()->new ResourceNotFoundException("Product not found"));if(!p.isActive())throw new BadRequestException("Product is inactive");CartItem item=c.getItems().stream().filter(i->i.getProduct().getId().equals(p.getId())).findFirst().orElse(null);int q=r.getQuantity()+(item==null?0:item.getQuantity());if(q>p.getStockQuantity())throw new BadRequestException("Insufficient stock");if(item==null){item=new CartItem();item.setCart(c);item.setProduct(p);c.getItems().add(item);}item.setQuantity(q);return carts.save(c);}
 public Cart get(String email){return cart(email);}
 @Transactional public Cart update(String email,Long pid,int qty){Cart c=cart(email);CartItem i=c.getItems().stream().filter(x->x.getProduct().getId().equals(pid)).findFirst().orElseThrow(()->new ResourceNotFoundException("Cart item not found"));if(qty<1||qty>i.getProduct().getStockQuantity())throw new BadRequestException("Invalid quantity");i.setQuantity(qty);return carts.save(c);}
 @Transactional public Cart remove(String email,Long pid){Cart c=cart(email);c.getItems().removeIf(i->i.getProduct().getId().equals(pid));return carts.save(c);}
 @Transactional public void clear(String email){Cart c=cart(email);c.getItems().clear();carts.save(c);}
}
