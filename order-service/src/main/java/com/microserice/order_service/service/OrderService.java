package com.microserice.order_service.service;

import com.microserice.order_service.dto.Request.OrderItemRequest;
import com.microserice.order_service.dto.Request.OrderRequest;
import com.microserice.order_service.dto.Response.OrderItemResponse;
import com.microserice.order_service.dto.Response.OrderResponse;
import com.microserice.order_service.entity.Order;
import com.microserice.order_service.entity.OrderItem;
import com.microserice.order_service.enums.OrderStatus;
import com.microserice.order_service.exception.OrderNotFoundException;
import com.microserice.order_service.respository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.aspectj.weaver.ast.Or;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService {

    private final OrderRepository orderRepository;

    @Transactional
    public OrderResponse createOrder(OrderRequest request){
        Order order = new Order();
        order.setUserId(request.userId());
        order.setStatus(OrderStatus.PENDING);
        order.setCreatedAt(LocalDateTime.now());

        BigDecimal totalAmount = BigDecimal.ZERO;

        for (OrderItemRequest itemRequest: request.items()){
            OrderItem item = new OrderItem();
            item.setProductId(itemRequest.productId());
            item.setQuantity(itemRequest.quantity());
            item.setUnitPrice(itemRequest.unitPrice());
            BigDecimal lineTotal = itemRequest.unitPrice()
                    .multiply(BigDecimal.valueOf(itemRequest.quantity()));
            item.setLineTotal(lineTotal);
            order.addItem(item);

            totalAmount =totalAmount.add(lineTotal);

        }
        order.setTotalAmount(totalAmount);

        Order savedOrder = orderRepository.save(order);
        return mapToResponse(savedOrder);


    }
    public List<OrderResponse> getAllOrders(){
        return orderRepository.findAll()
                .stream().
                map(this::mapToResponse)
                .toList();
    }
    public OrderResponse getOrderById(Long id ){
        Order order = orderRepository.findById(id)
                .orElseThrow(()->
                        new OrderNotFoundException("Order not found with order id :"+id));
        return mapToResponse(order);
    }
    @Transactional
    public OrderResponse updateOrderStatus(Long id , OrderStatus status){
        Order order = orderRepository.findById(id)
                .orElseThrow(()-> new OrderNotFoundException("Order not found with order id :"+id));
        order.setStatus(status);
        return mapToResponse(order);
    }

    public OrderResponse mapToResponse(Order order){
        List<OrderItemResponse> itemResponses = order.getItems()
                .stream()
                .map(item -> new OrderItemResponse(
                        item.getProductId(),
                        item.getQuantity(),
                        item.getUnitPrice(),
                        item.getLineTotal()
                )).toList();
        return new OrderResponse(
                order.getId(),
                order.getUserId(),
                itemResponses,
                order.getTotalAmount(),
                order.getStatus(),
                order.getCreatedAt()
        );
     }
}
