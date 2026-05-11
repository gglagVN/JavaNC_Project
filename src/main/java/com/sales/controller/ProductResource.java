package com.sales.controller;

import com.sales.entity.Product;
import com.sales.service.ProductService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

@Path("/api/products")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ProductResource {

    @Inject
    ProductService productService;

    @GET
    public List<Product> getAll() {
        return productService.findAll();
    }

    @GET
    @Path("/{id}")
    public Response getById(@PathParam("id") Long id) {
        Product product = productService.findById(id);
        if (product == null) return Response.status(Response.Status.NOT_FOUND).build();
        return Response.ok(product).build();
    }

    @POST
    public Response create(Product product) {
        productService.save(product);
        return Response.status(Response.Status.CREATED).entity(product).build();
    }

    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") Long id, Product product) {
        productService.update(id, product);
        return Response.ok(product).build();
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        productService.delete(id);
        return Response.noContent().build();
    }

    @GET
    @Path("/search")
    public List<Product> search(@QueryParam("name") String name) {
        return productService.searchByName(name);
    }
}