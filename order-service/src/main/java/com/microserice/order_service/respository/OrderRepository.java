package com.microserice.order_service.respository;

import com.microserice.order_service.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order ,Long> {

}
