package dripvora.fashion.payment.service;
import org.springframework.stereotype.Service; import java.util.UUID; import dripvora.fashion.exception.*; import dripvora.fashion.order.entity.Order; import dripvora.fashion.order.repository.OrderRepository; import dripvora.fashion.payment.dto.PaymentRequest; import dripvora.fashion.payment.entity.Payment; import dripvora.fashion.payment.repository.PaymentRepository;
@Service public class PaymentService{
 private final PaymentRepository payments; private final OrderRepository orders;
 public PaymentService(PaymentRepository p,OrderRepository o){payments=p;orders=o;}
 public Payment create(String email,PaymentRequest r){Order o=orders.findByIdAndUserEmail(r.getOrderId(),email).orElseThrow(()->new ResourceNotFoundException("Order not found"));if(!o.getPaymentStatus().equals("PENDING"))throw new BadRequestException("Payment is already processed");Payment p=new Payment();p.setOrder(o);p.setAmount(o.getTotalAmount());p.setStatus("PAID");p.setTransactionId("TXN-"+UUID.randomUUID().toString().replace("-","").substring(0,16));o.setPaymentStatus("PAID");o.setOrderStatus("CONFIRMED");orders.save(o);return payments.save(p);}
}
