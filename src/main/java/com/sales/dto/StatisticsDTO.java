package com.sales.dto;

import java.util.List;
import java.util.Map;

/**
 * DTO chứa thông tin báo cáo thống kê hệ thống
 */
public class StatisticsDTO {

    public Double totalRevenue;      // Tổng doanh thu
    public Long totalOrders;         // Tổng số lượng đơn hàng
    public List<Map<String, Object>> topProducts; // Danh sách sản phẩm bán chạy nhất

    public StatisticsDTO() {}

    public StatisticsDTO(Double totalRevenue, Long totalOrders) {
        this.totalRevenue = totalRevenue;
        this.totalOrders = totalOrders;
    }
}