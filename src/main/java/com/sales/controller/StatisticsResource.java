package com.sales.controller;

import com.sales.dto.StatisticsDTO;
import com.sales.service.OrderService;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/api/statistics")
@Produces(MediaType.APPLICATION_JSON)
public class StatisticsResource {

    @Inject
    OrderService orderService;

    @GET
    public StatisticsDTO getStats() {
        return orderService.getStatistics();
    }
}