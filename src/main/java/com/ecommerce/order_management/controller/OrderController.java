package com.ecommerce.order_management.controller;

import com.ecommerce.order_management.dto.AddOrderItemRequest;
import com.ecommerce.order_management.dto.OrderRequest;
import com.ecommerce.order_management.dto.OrderResponse;
import com.ecommerce.order_management.dto.UpdateOrderItemRequest;
import com.ecommerce.order_management.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;


    // CREATE ORDER API

    @PostMapping
    public ResponseEntity<OrderResponse> createOrder(
            @RequestBody OrderRequest request) {

        OrderResponse response = orderService.createOrder(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

// UPDATE ORDER ITEM QUANTITY

    @PutMapping("/{orderId}/items/{productId}")
    public ResponseEntity<String> updateOrderItem(
            @PathVariable Integer orderId,
            @PathVariable Integer productId,
            @RequestBody UpdateOrderItemRequest request) {

        orderService.updateOrderItem(orderId, productId, request.getQuantity());
        return ResponseEntity.ok("Order item updated successfully");
    }


    // GET ORDER BY ID API

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResponse> getOrder(
            @PathVariable Integer orderId) {

        OrderResponse response = orderService.getOrderById(orderId);
        return ResponseEntity.ok(response);
    }



    // ADD ORDER ITEM

    @PostMapping("/{orderId}/items")
    public ResponseEntity<String> addOrderItem(
            @PathVariable Integer orderId,
            @RequestBody AddOrderItemRequest request) {

        orderService.addOrderItem(orderId, request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Order item added successfully");
    }


    // DELETE ORDER API

    @DeleteMapping("/{orderId}")
    public ResponseEntity<String> deleteOrder(
            @PathVariable Integer orderId) {

        orderService.deleteOrder(orderId);
        return ResponseEntity.ok("Order deleted successfully");
    }


    // DELETE ORDER ITEM

    @DeleteMapping("/{orderId}/items/{productId}")
    public ResponseEntity<String> deleteOrderItem(
            @PathVariable Integer orderId,
            @PathVariable Integer productId) {

        orderService.deleteOrderItem(orderId, productId);
        return ResponseEntity.ok("Order item deleted successfully");
    }

}

