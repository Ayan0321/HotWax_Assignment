package com.ecommerce.order_management.repository;

import com.ecommerce.order_management.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Integer> {
}
