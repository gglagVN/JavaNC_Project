package com.sales.controller;

import com.sales.entity.User;
import com.sales.entity.Customer;
import io.quarkus.elytron.security.common.BcryptUtil;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;

import java.util.Map;

@Path("/api/auth")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AuthResource {

    @Inject
    EntityManager entityManager; // Dùng để gọi lệnh merge thay cho persist

    @Context
    SecurityContext securityContext;

    /**
     * API Đăng ký tài khoản mới: Tạo User và Customer đồng bộ ID
     */
    @POST
    @Path("/register")
    @Transactional
    public Response register(User user) {
        // 1. Kiểm tra xem username đã tồn tại chưa
        if (User.find("username", user.username).firstResult() != null) {
            return Response.status(409).entity("Tên đăng nhập đã tồn tại!").build();
        }

        try {
            // 2. Mã hóa mật khẩu và thiết lập Role mặc định
            user.password = BcryptUtil.bcryptHash(user.password);
            if (user.role == null) {
                user.role = "user";
            }

            // 3. Lưu User trước để Hibernate sinh ra ID
            user.persist();

            // 4. Tạo bản ghi Customer tương ứng
            Customer customer = new Customer();
            customer.id = user.id; // Quan trọng: Dùng chung ID với User để thanh toán không lỗi
            customer.name = user.username; // Gán tạm tên là username

            // 5. Sử dụng merge để tránh lỗi "detached entity passed to persist"
            // Lệnh này sẽ tự động INSERT nếu ID chưa tồn tại
            entityManager.merge(customer);

            return Response.status(201).entity(user).build();
        } catch (Exception e) {
            return Response.status(500).entity("Lỗi đăng ký: " + e.getMessage()).build();
        }
    }

    /**
     * API lấy thông tin người dùng hiện tại (Lấy từ SecurityContext)
     */
    @GET
    @Path("/me")
    @RolesAllowed({"user", "admin"})
    public User getCurrentUser() {
        String username = securityContext.getUserPrincipal().getName();
        return User.find("username", username).firstResult();
    }
}