package com.sales.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;

@Entity
@Table(name = "order_items")
public class OrderItem extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    // Nhiều chi tiết đơn hàng thuộc về một đơn hàng cha
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    @JsonIgnore // Ngăn vòng lặp vô tận khi chuyển dữ liệu sang định dạng JSON
    public Order order;

    // Một chi tiết đơn hàng tương ứng với một sản phẩm
    @ManyToOne
    @JoinColumn(name = "product_id")
    public Product product;

    public Integer quantity;

    @Column(name = "price_at_purchase")
    public Double priceAtPurchase; // Lưu lại giá tại thời điểm mua để làm báo cáo chính xác
}