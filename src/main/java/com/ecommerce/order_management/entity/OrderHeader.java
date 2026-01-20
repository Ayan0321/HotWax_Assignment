package com.ecommerce.order_management.entity;



import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "order_header")
public class OrderHeader {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer orderId;

    private LocalDate orderDate;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "shipping_contact_mech_id")
    private ContactMech shippingContact;

    @ManyToOne
    @JoinColumn(name = "billing_contact_mech_id")
    private ContactMech billingContact;

    // ===== Getters & Setters =====
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

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public ContactMech getShippingContact() {
        return shippingContact;
    }

    public void setShippingContact(ContactMech shippingContact) {
        this.shippingContact = shippingContact;
    }

    public ContactMech getBillingContact() {
        return billingContact;
    }

    public void setBillingContact(ContactMech billingContact) {
        this.billingContact = billingContact;
    }
}
