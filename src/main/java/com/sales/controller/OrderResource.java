package com.sales.controller;

import com.sales.dto.OrderRequestDTO;
import com.sales.entity.Order;
import com.sales.service.OrderService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

@Path("/api/orders")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class OrderResource {

    @Inject
    OrderService orderService;

    @POST
    public Response createOrder(OrderRequestDTO request) {
        try {
            Order order = orderService.createOrder(request);
            return Response.status(Response.Status.CREATED).entity(order).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @GET
    public List<Order> getAllOrders() {
        return orderService.findAll();
    }

    @GET
    @Path("/{id}")
    public Response getOrderDetails(@PathParam("id") Long id) {
        Order order = orderService.findById(id);
        if (order == null) return Response.status(Response.Status.NOT_FOUND).build();
        return Response.ok(order).build();
    }
}