package com.ecommerce.order_management.service;

import com.ecommerce.order_management.dto.*;
import com.ecommerce.order_management.entity.*;
import com.ecommerce.order_management.exception.ResourceNotFoundException;
import com.ecommerce.order_management.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;


@Service
public class OrderService {

    @Autowired
    private OrderHeaderRepository orderHeaderRepo;

    @Autowired
    private OrderItemRepository orderItemRepo;

    @Autowired
    private CustomerRepository customerRepo;

    @Autowired
    private ContactMechRepository contactMechRepo;

    @Autowired
    private ProductRepository productRepo;


    // CREATE ORDER

    public OrderResponse createOrder(OrderRequest dto) {

        // Fetch Customer
        Customer customer = customerRepo.findById(dto.getCustomerId())
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        // Fetch Shipping & Billing Address
        ContactMech shipping = contactMechRepo.findById(dto.getShippingContactMechId())
                .orElseThrow(() -> new RuntimeException("Shipping address not found"));

        ContactMech billing = contactMechRepo.findById(dto.getBillingContactMechId())
                .orElseThrow(() -> new RuntimeException("Billing address not found"));

        // Create OrderHeader
        OrderHeader order = new OrderHeader();
        order.setOrderDate(dto.getOrderDate());
        order.setCustomer(customer);
        order.setShippingContact(shipping);
        order.setBillingContact(billing);

        orderHeaderRepo.save(order);

        // Create OrderItems
        List<OrderItemResponse> itemResponses = new ArrayList<>();

        for (OrderItemRequest itemDto : dto.getItems()) {

            Product product = productRepo.findById(itemDto.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found"));

            OrderItem item = new OrderItem();
            item.setOrder(order);
            item.setProduct(product);
            item.setQuantity(itemDto.getQuantity());
            item.setStatus("CREATED");

            orderItemRepo.save(item);

            // Build Item Response
            OrderItemResponse itemResponse = new OrderItemResponse();
            itemResponse.setProductName(product.getProductName());
            itemResponse.setQuantity(item.getQuantity());
            itemResponse.setStatus(item.getStatus());

            itemResponses.add(itemResponse);
        }

        // Build OrderResponse
        return buildOrderResponse(order, itemResponses);
    }


    // GET ORDER BY ID

    public OrderResponse getOrderById(Integer orderId) {

        OrderHeader order = orderHeaderRepo.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        List<OrderItem> items =
                orderItemRepo.findByOrder_OrderId(orderId);

        List<OrderItemResponse> itemResponses = new ArrayList<>();

        for (OrderItem item : items) {
            OrderItemResponse response = new OrderItemResponse();
            response.setProductName(item.getProduct().getProductName());
            response.setQuantity(item.getQuantity());
            response.setStatus(item.getStatus());
            itemResponses.add(response);
        }

        return buildOrderResponse(order, itemResponses);
    }


    // DELETE ORDER

    public void deleteOrder(Integer orderId) {

        OrderHeader order = orderHeaderRepo.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        // Delete items first
        List<OrderItem> items =
                orderItemRepo.findByOrder_OrderId(orderId);
        orderItemRepo.deleteAll(items);

        // Delete order
        orderHeaderRepo.delete(order);
    }


    // COMMON RESPONSE BUILDER

    private OrderResponse buildOrderResponse(
            OrderHeader order,
            List<OrderItemResponse> items) {

        OrderResponse response = new OrderResponse();
        response.setOrderId(order.getOrderId());
        response.setOrderDate(order.getOrderDate());
        response.setCustomerId(order.getCustomer().getCustomerId());
        response.setShippingContactMechId(order.getShippingContact().getContactMechId());
        response.setBillingContactMechId(order.getBillingContact().getContactMechId());
        response.setItems(items);

        return response;
    }
    public void updateOrderItem(
            Integer orderId,
            Integer productId,
            Integer quantity) {

        OrderItem item = orderItemRepo
                .findByOrder_OrderIdAndProduct_ProductId(orderId, productId)
                .orElseThrow(() -> new ResourceNotFoundException("Order item not found"));

        item.setQuantity(quantity);
        orderItemRepo.save(item);
    }


// ADD ORDER ITEM

    public void addOrderItem(Integer orderId, AddOrderItemRequest request) {

        OrderHeader order = orderHeaderRepo.findById(orderId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Order not found"));

        Product product = productRepo.findById(request.getProductId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Product not found"));

        OrderItem item = new OrderItem();
        item.setOrder(order);
        item.setProduct(product);
        item.setQuantity(request.getQuantity());
        item.setStatus("CREATED");

        orderItemRepo.save(item);
    }



// DELETE ORDER ITEM

    public void deleteOrderItem(Integer orderId, Integer productId) {

        OrderItem item = orderItemRepo
                .findByOrder_OrderIdAndProduct_ProductId(orderId, productId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Order item not found"));

        orderItemRepo.delete(item);
    }

}

