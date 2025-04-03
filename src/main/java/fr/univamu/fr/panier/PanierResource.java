package fr.univamu.fr.panier;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;

@Path("/paniers")
@ApplicationScoped
public class PanierResource {

    private PanierService service;

    public PanierResource(){}

    public @Inject PanierResource(PanierRepositoryInterface panierRepo, ProductRepositoryInterface productRepo){
        this.service = new PanierService(panierRepo, productRepo);
    }

    public PanierResource(PanierService service) {
        this.service = service;
    }

    @GET
    @Produces("application/json")
    public String getAllPaniers() {
        return service.getAllPaniersJSON();
    }

    @GET
    @Path("{name}")
    @Produces("application/json")
    public String getPanier(@PathParam("name") String name) {
        String result = service.getPanierJSON(name);
        if (result == null) throw new NotFoundException();
        return result;
    }

    @DELETE
    @Path("{name}")
    public Response deletePanier(@PathParam("name") String name) {
        boolean success = service.deletePanier(name);
        if (success) {
            return Response.status(Response.Status.NO_CONTENT).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("Panier not found").build();
        }
    }

    @POST
    @Path("/order/{name}")
    @Consumes("application/json")
    public Response orderPanier(@PathParam("name") String PanierName, OrderRequest request) {
        boolean success = service.orderPanier(PanierName, request.quantity);
        if (success) {
            return Response.status(Response.Status.OK).entity("Order processed successfully").build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).entity("Insufficient quantity or Panier not available").build();
        }
    }

    @POST
    @Path("/add")
    @Consumes("application/json")
    public Response addPanier(Panier panier) {
        if (panier.getName().isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST).entity("The Panier must have a name and products").build();
        }
        boolean success = service.addPanier(panier);
        if (success) {
            return Response.status(Response.Status.CREATED).entity("Panier added successfully").build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).entity("Failed to add Panier").build();
        }
    }

    @POST
    @Path("/add/{name}")
    @Consumes("application/json")
    public Response addProduct(@PathParam("name") String PanierName, ProductRequest productRequest) {
        String productName = productRequest.getProduit();
        int quantity = productRequest.getQuantity();
        boolean success = service.addProductsToPanier(PanierName, productName, quantity);
        if (success) {
            return Response.status(Response.Status.OK).entity("Product successfully added to Panier").build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("Product or Panier does not exist").build();
        }
    }

    @POST
    @Path("/remove/{name}")
    @Consumes("application/json")
    public Response removeProduct(@PathParam("name") String PanierName, ProductRequest productRequest) {
        String productName = productRequest.getProduit();
        int quantity = productRequest.getQuantity();
        boolean success = service.removeProductsFromPanier(PanierName, productName, quantity);
        if (success) {
            return Response.status(Response.Status.OK).entity("Product successfully removed from Panier").build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).entity("Error removing product from Panier").build();
        }
    }
}