package com.ecommerce.order_management.repository;

import com.ecommerce.order_management.entity.OrderHeader;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderHeaderRepository extends JpaRepository<OrderHeader, Integer> {
}

