package com.ecommerce.order_management.repository;

import com.ecommerce.order_management.entity.ContactMech;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ContactMechRepository extends JpaRepository<ContactMech, Integer> {

    List<ContactMech> findByCustomerId(Integer customerId);

}
