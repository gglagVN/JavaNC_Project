package com.sales.controller;

import com.sales.entity.User;
import io.quarkus.elytron.security.common.BcryptUtil;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;

@Path("/api/auth")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AuthResource {

    @Context
    SecurityContext securityContext;

    /**
     * API Đăng ký tài khoản mới
     */
    @POST
    @Path("/register")
    @Transactional
    public Response register(User user) {
        // Kiểm tra xem username đã tồn tại chưa để tránh lỗi DB
        if (User.find("username", user.username).firstResult() != null) {
            return Response.status(409).entity("Tên đăng nhập đã tồn tại!").build();
        }

        // Mã hóa mật khẩu bằng Bcrypt trước khi lưu
        user.password = BcryptUtil.bcryptHash(user.password);
        if (user.role == null) user.role = "user";

        user.persist();
        return Response.status(201).entity(user).build();
    }

    /**
     * API lấy thông tin người dùng hiện tại (Lấy từ Basic Auth)
     * Giúp Frontend biết ID của người đang đăng nhập
     */
    @GET
    @Path("/me")
    @RolesAllowed({"user", "admin"}) // Chỉ cho phép người đã đăng nhập gọi API này
    public User getCurrentUser() {
        // Lấy username từ "thẻ thông hành" (SecurityContext)
        String username = securityContext.getUserPrincipal().getName();

        // Tìm và trả về toàn bộ thông tin User (bao gồm ID)
        return User.find("username", username).firstResult();
    }
}