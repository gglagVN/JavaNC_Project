package com.sales.dto;

import java.util.List;

/**
 * DTO nhận dữ liệu khi tạo đơn hàng mới
 */
public class OrderRequestDTO {

    public Long customerId;        // ID của khách hàng mua hàng
    public List<OrderItemDTO> items; // Danh sách các sản phẩm và số lượng

    public OrderRequestDTO() {}
}