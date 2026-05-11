package com.sales.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders") // "order" là từ khóa trùng với lệnh SQL nên dùng "orders"
public class Order extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    // Nhiều đơn hàng thuộc về một khách hàng
    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    public Customer customer;

    @Column(name = "order_date")
    public LocalDateTime orderDate;

    @Column(name = "total_amount")
    public Double totalAmount;

    // Quan hệ 1 đơn hàng - nhiều chi tiết đơn hàng
    // cascade = ALL: Lưu/Xóa đơn hàng sẽ tự động Lưu/Xóa các chi tiết kèm theo
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    public List<OrderItem> items = new ArrayList<>();

    /**
     * Helper method để thêm item vào đơn hàng.
     * Giúp đồng bộ hóa cả 2 phía của mối quan hệ.
     */
    public void addItem(OrderItem item) {
        items.add(item);
        item.order = this;
    }
}