package com.ecommerce.order_management.dto;

import java.time.LocalDate;
import java.util.List;


public class OrderResponse {

    private Integer orderId;
    private LocalDate orderDate;
    private Integer customerId;
    private Integer shippingContactMechId;
    private Integer billingContactMechId;

    private List<OrderItemResponse> items;

    // ===== getters & setters =====
    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public Integer getShippingContactMechId() {
        return shippingContactMechId;
    }

    public void setShippingContactMechId(Integer shippingContactMechId) {
        this.shippingContactMechId = shippingContactMechId;
    }

    public Integer getBillingContactMechId() {
        return billingContactMechId;
    }

    public void setBillingContactMechId(Integer billingContactMechId) {
        this.billingContactMechId = billingContactMechId;
    }

    public List<OrderItemResponse> getItems() {
        return items;
    }

    public void setItems(List<OrderItemResponse> items) {
        this.items = items;
    }
}
