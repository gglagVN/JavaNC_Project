package com.sales.controller;
import com.sales.entity.Customer;
import com.sales.dto.OrderRequestDTO;
import com.sales.entity.Order;
import com.sales.entity.User;
import com.sales.service.OrderService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import java.util.List;

@Path("/api/orders")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class OrderResource {

    @Inject
    OrderService orderService;

    @Context
    SecurityContext securityContext;

    @POST
    @RolesAllowed({"user", "admin"})
    public Response createOrder(OrderRequestDTO request) {
        try {
            Order order = orderService.createOrder(request);
            return Response.status(Response.Status.CREATED).entity(order).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    /**
     * API dành cho KHÁCH HÀNG: Chỉ lấy đơn hàng của chính mình
     */
    @GET
    @RolesAllowed({"user", "admin"})
    public Response getMyOrders() {
        try {
            String username = securityContext.getUserPrincipal().getName();

            // 1. Tìm User từ username (để lấy thông tin đăng nhập)
            User user = User.find("username", username).firstResult();
            if (user == null) return Response.status(Response.Status.NOT_FOUND).build();

            // 2. Tìm Customer tương ứng với User đó
            // (Giả định bạn có quan hệ giữa User và Customer, hoặc ID của chúng khớp nhau)
            Customer customer = Customer.findById(user.id);
            if (customer == null) return Response.ok(List.of()).build();

            // 3. ĐÂY LÀ DÒNG QUAN TRỌNG NHẤT:
            // Phải dùng "customer" vì đó là tên biến trong file Order.java bạn vừa gửi
            List<Order> orders = Order.find("customer", customer).list();

            return Response.ok(orders).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(500).entity(e.getMessage()).build();
        }
    }

    /**
     * API dành cho ADMIN: Xem toàn bộ đơn hàng của hệ thống
     */
    @GET
    @Path("/all")
    @RolesAllowed("admin")
    public List<Order> getAllOrders() {
        return orderService.findAll();
    }

    @GET
    @Path("/{id}")
    @RolesAllowed({"user", "admin"})
    public Response getOrderDetails(@PathParam("id") Long id) {
        Order order = orderService.findById(id);
        if (order == null) return Response.status(Response.Status.NOT_FOUND).build();
        return Response.ok(order).build();
    }
}