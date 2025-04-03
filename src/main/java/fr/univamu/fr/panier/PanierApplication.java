package fr.univamu.fr.panier;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Disposes;
import jakarta.enterprise.inject.Produces;
import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;

@ApplicationPath("/api")
@ApplicationScoped
public class PanierApplication extends Application {

    @Produces
    private PanierRepositoryInterface openDbConnection(){
        PanierRepositoryMariadb db = null;

        try{
            db = new PanierRepositoryMariadb("jdbc:mariadb://mysql-basededonnee.alwaysdata.net/basededonnee_agricolea", "378414", "mdpagricole");
        }
        catch (Exception e){
            System.err.println(e.getMessage());
        }
        return db;
    }

    private void closeDbConnection(@Disposes PanierRepositoryInterface panierRepo ) {
        panierRepo.close();
    }

    @Produces
    private ProductRepositoryInterface connectProductApi(){
        return new ProductRepositoryAPI("http://localhost:8080/product-1.0-SNAPSHOT");
    }
}