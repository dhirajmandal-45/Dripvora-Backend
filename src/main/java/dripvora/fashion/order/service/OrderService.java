package dripvora.fashion.order.service;
import org.springframework.stereotype.Service; import org.springframework.transaction.annotation.Transactional; import java.math.BigDecimal; import java.util.*; import java.util.UUID;
import dripvora.fashion.cart.entity.*; import dripvora.fashion.cart.repository.CartRepository; import dripvora.fashion.exception.*; import dripvora.fashion.order.dto.CheckoutRequest; import dripvora.fashion.order.entity.*; import dripvora.fashion.order.repository.OrderRepository;
@Service public class OrderService{
 private final OrderRepository orders; private final CartRepository carts;
 public OrderService(OrderRepository o,CartRepository c){orders=o;carts=c;}
 @Transactional public Order checkout(String email,CheckoutRequest r){
  Cart c=carts.findByUserEmail(email).orElseThrow(()->new BadRequestException("Cart is empty"));
  if(c.getItems().isEmpty())throw new BadRequestException("Cart is empty");
  Order o=new Order();o.setUser(c.getUser());o.setOrderNumber("DV-"+UUID.randomUUID().toString().substring(0,8).toUpperCase());o.setShippingAddress(r.getShippingAddress().trim());
  BigDecimal subtotal=BigDecimal.ZERO;
  for(CartItem ci:c.getItems()){if(ci.getQuantity()>ci.getProduct().getStockQuantity())throw new BadRequestException("Insufficient stock for "+ci.getProduct().getName());OrderItem oi=new OrderItem();oi.setOrder(o);oi.setProduct(ci.getProduct());oi.setProductName(ci.getProduct().getName());oi.setPrice(ci.getProduct().getPrice());oi.setQuantity(ci.getQuantity());BigDecimal line=ci.getProduct().getPrice().multiply(BigDecimal.valueOf(ci.getQuantity()));oi.setSubtotal(line);o.getItems().add(oi);subtotal=subtotal.add(line);}
  o.setSubtotal(subtotal);o.setTotalAmount(subtotal);Order saved=orders.save(o);
  for(CartItem ci:c.getItems()){ci.getProduct().setStockQuantity(ci.getProduct().getStockQuantity()-ci.getQuantity());}
  c.getItems().clear();carts.save(c);return saved;
 }
 public List<Order> mine(String email){return orders.findByUserEmailOrderByCreatedAtDesc(email);}
 public Order mineOne(String email,Long id){return orders.findByIdAndUserEmail(id,email).orElseThrow(()->new ResourceNotFoundException("Order not found"));}
 public List<Order> all(){return orders.findAll();}
 @Transactional public Order status(Long id,String status){Order o=orders.findById(id).orElseThrow(()->new ResourceNotFoundException("Order not found"));Set<String> allowed=Set.of("PENDING","CONFIRMED","PROCESSING","SHIPPED","OUT_FOR_DELIVERY","DELIVERED","CANCELLED");if(!allowed.contains(status))throw new BadRequestException("Invalid order status");o.setOrderStatus(status);return orders.save(o);}
}
