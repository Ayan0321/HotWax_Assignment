package com.ecommerce.order_management.repository;

import com.ecommerce.order_management.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface OrderItemRepository extends JpaRepository<OrderItem, Integer> {

    List<OrderItem> findByOrder_OrderId(Integer orderId);

    Optional<OrderItem> findByOrder_OrderIdAndProduct_ProductId(
            Integer orderId,
            Integer productId);


}
