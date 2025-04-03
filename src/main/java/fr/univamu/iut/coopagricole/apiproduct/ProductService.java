package fr.univamu.iut.coopagricole.apiproduct;

import fr.univamu.iut.coopagricole.apiproduct.Product;
import fr.univamu.iut.coopagricole.apiproduct.ProductRepositoryInterface;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;

import java.util.ArrayList;

public class ProductService {

    /**
     * Objet permettant d'accéder au dépôt où sont stockées les informations
     */
    protected ProductRepositoryInterface productRepo ;

    /**
     * Constructeur permettant d'injecter l'accès aux données
     * @param productRepo objet implémentant l'interface d'accès aux données
     */
    public  ProductService( ProductRepositoryInterface productRepo) {
        this.productRepo = productRepo;
    }

    public boolean updateProduct(String name, Product product) {
        return productRepo.updateProduct(name, product.type);
    }

    /**
     * Méthode retournant les informations sur les utilisateurs
     * @return la chaîne contenant les informations
     */
    public String getAllProductsJSON(){

        ArrayList<Product> allProducts = productRepo.getAllProducts();
        String result = null;
        try( Jsonb jsonb = JsonbBuilder.create()){ // création du json
            result = jsonb.toJson(allProducts);
        }
        catch (Exception e){
            System.err.println( e.getMessage() );
        }
        return result;
    }

    /**
     * Méthode retournant  les informations sur l'utilisateur qu'on recherche
     * @param id l'identifiant de l'utilisateur recherché
     * @return une chaîne de caractère contenant les informations au format JSON
     */
    public String getProductJSON( String id ){
        String result = null;
       Product myProduct = productRepo.getProduct(id);
        if( myProduct != null ) { // si le produit a été trouvé
            try (Jsonb jsonb = JsonbBuilder.create()) { // création du json
                result = jsonb.toJson(myProduct);
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
        return result;
    }
}
