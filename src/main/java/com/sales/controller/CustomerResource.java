package com.sales.controller;

import com.sales.entity.Customer;
import com.sales.service.CustomerService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

@Path("/api/customers")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CustomerResource {

    @Inject
    CustomerService customerService;

    @GET
    public List<Customer> getAll() {
        return customerService.findAll();
    }

    @POST
    public Response create(Customer customer) {
        customerService.save(customer);
        return Response.status(Response.Status.CREATED).entity(customer).build();
    }

    @GET
    @Path("/search")
    public List<Customer> search(@QueryParam("query") String query) {
        return customerService.search(query);
    }
}