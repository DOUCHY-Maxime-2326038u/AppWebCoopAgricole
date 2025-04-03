package fr.univamu.fr.panier;

import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.ArrayList;
import java.util.Arrays;

public class ProductRepositoryAPI implements ProductRepositoryInterface {

    String url;

    public ProductRepositoryAPI(String url){
        this.url = url;
    }

    @Override
    public void close() {}

    @Override
    public Product getProduct(String name) {
        Product myProduct = null;
        Client client = ClientBuilder.newClient();
        WebTarget productResource  = client.target(url);
        WebTarget productEndpoint = productResource.path("products/" + name);
        Response response = productEndpoint.request(MediaType.APPLICATION_JSON).get();

        if( response.getStatus() == 200)
            myProduct = response.readEntity(Product.class);

        client.close();
        return myProduct;
    }

    @Override
    public ArrayList<Product> getAllProducts() {
        ArrayList<Product> productList = new ArrayList<>();
        Client client = ClientBuilder.newClient();
        WebTarget productResource  = client.target(url);
        WebTarget productEndpoint = productResource.path("products");
        Response response = productEndpoint.request(MediaType.APPLICATION_JSON).get();

        if( response.getStatus() == 200)
            productList = new ArrayList<>(Arrays.asList(response.readEntity(Product[].class)));

        client.close();
        return productList;
    }

    @Override
    public boolean updateProduct(String name, String type) {
        Client client = ClientBuilder.newClient();
        WebTarget productResource  = client.target(url);
        WebTarget productEndpoint = productResource.path("products/" + name);

        Product updatedProduct = new Product(name, type);
        Response response = productEndpoint.request(MediaType.APPLICATION_JSON)
                .put(Entity.entity(updatedProduct, MediaType.APPLICATION_JSON));

        client.close();
        return response.getStatus() == 200;
    }
}
