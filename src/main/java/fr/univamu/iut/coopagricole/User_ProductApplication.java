package fr.univamu.iut.coopagricole;


import fr.univamu.iut.coopagricole.apiproduct.ProductRepositoryDb;
import fr.univamu.iut.coopagricole.apiproduct.ProductRepositoryInterface;
import fr.univamu.iut.coopagricole.apiuser.UserRepositoryInterface;
import fr.univamu.iut.coopagricole.apiuser.UserRepositoryMariadb;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Disposes;
import jakarta.enterprise.inject.Produces;
import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;


@ApplicationPath("/api")
@ApplicationScoped
public class User_ProductApplication extends Application {

    /**
     * Méthode appelée par l'API CDI pour injecter la connection à la base de données au moment de la création
     * de la ressource
     * @return un objet implémentant l'interface BookRepositoryInterface utilisée
     *          pour accéder aux données des livres, voire les modifier
     */
    @Produces
    private UserRepositoryInterface openDbConnectionUser(){
        UserRepositoryMariadb db = null;

        try{
            db = new UserRepositoryMariadb("jdbc:mariadb://mysql-quentinfournieriut.alwaysdata.net/quentinfournieriut_agricole", "395478_agricole", "lesPetitsPaniersFruites");
        }
        catch (Exception e){
            System.err.println(e.getMessage());
        }
        return db;
    }

    @Produces
    private ProductRepositoryInterface openDbConnectionProduct(){
        ProductRepositoryDb db = null;

        try{
            db = new ProductRepositoryDb("jdbc:mariadb://mysql-quentinfournieriut.alwaysdata.net/quentinfournieriut_agricole", "395478_agricole", "lesPetitsPaniersFruites");
        }
        catch (Exception e){
            System.err.println(e.getMessage());
        }
        return db;
    }


    private void closeDbConnectionUser(@Disposes ProductRepositoryInterface productRepo) {
        productRepo.close();

    }
    private void closeDbConnectionProduit(@Disposes UserRepositoryInterface userRepo){
        userRepo.close();
    }

}
