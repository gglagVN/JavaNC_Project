package com.sales.controller;

import com.sales.entity.User;
import io.quarkus.elytron.security.common.BcryptUtil;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/api/auth")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AuthResource {

    @POST
    @Path("/register")
    @Transactional
    public Response register(User user) {
        // Mã hóa mật khẩu bằng Bcrypt trước khi lưu
        user.password = BcryptUtil.bcryptHash(user.password);
        if (user.role == null) user.role = "user";
        user.persist();
        return Response.status(201).entity("Đăng ký thành công!").build();
    }
}