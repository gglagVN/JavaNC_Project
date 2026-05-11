package com.sales.dto;

/**
 * DTO đại diện cho một dòng sản phẩm trong yêu cầu đặt hàng
 */
public class OrderItemDTO {

    public Long productId;    // Chỉ cần ID sản phẩm
    public Integer quantity;  // Số lượng khách mua

    // Constructors (Tùy chọn cho việc khởi tạo nhanh)
    public OrderItemDTO() {}

    public OrderItemDTO(Long productId, Integer quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }
}