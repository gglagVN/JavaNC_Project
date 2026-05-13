package com.sales.controller;

import com.sales.dto.StatisticsDTO;
import com.sales.entity.Order; // Quan trọng: Phải import Entity Order
import com.sales.service.OrderService;
import jakarta.annotation.security.RolesAllowed; // Thêm import này
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response; // Thêm import này
import java.util.List; // Thêm import này

@Path("/api/statistics")
@Produces(MediaType.APPLICATION_JSON)
public class StatisticsResource {

    @Inject
    OrderService orderService;

    @GET
    @RolesAllowed("admin") // Chỉ Admin mới xem được thống kê tổng quát
    public StatisticsDTO getStats() {
        return orderService.getStatistics();
    }

    @GET
    @Path("/monthly")
    @RolesAllowed("admin")
    public Response getMonthlyRevenue() {
        // Sử dụng EntityManager từ lớp Order (Panache) để truy vấn HQL/JPQL
        List<Object[]> results = Order.getEntityManager()
                .createQuery("SELECT MONTH(o.orderDate), YEAR(o.orderDate), SUM(o.totalAmount) " +
                        "FROM Order o " +
                        "GROUP BY YEAR(o.orderDate), MONTH(o.orderDate) " +
                        "ORDER BY YEAR(o.orderDate) DESC, MONTH(o.orderDate) DESC", Object[].class)
                .getResultList();

        return Response.ok(results).build();
    }
}