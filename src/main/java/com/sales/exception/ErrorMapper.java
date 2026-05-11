package com.sales.exception;

import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class ErrorMapper implements ExceptionMapper<Exception> {

    @Override
    public Response toResponse(Exception exception) {
        int status = 500;
        String message = "Đã xảy ra lỗi hệ thống!";

        // Nếu là lỗi do chúng ta chủ động ném ra (ví dụ: hết hàng, không tìm thấy ID)
        if (exception instanceof WebApplicationException) {
            status = ((WebApplicationException) exception).getResponse().getStatus();
            message = exception.getMessage();
        }
        // Xử lý các lỗi khác như vi phạm ràng buộc dữ liệu SQL
        else if (exception.getCause() != null && exception.getCause().getMessage().contains("ConstraintViolation")) {
            status = 409;
            message = "Dữ liệu bị trùng lặp hoặc vi phạm ràng buộc hệ thống.";
        }

        ErrorMessage error = new ErrorMessage(message, status);
        return Response.status(status).entity(error).build();
    }
}