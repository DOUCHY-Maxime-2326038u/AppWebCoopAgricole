package fr.univamu.iut.coopagricole.apiproduct;

import java.io.Closeable;
import java.sql.*;
import java.util.ArrayList;


public class ProductRepositoryDb implements ProductRepositoryInterface, Closeable {

    /**
     * Session pour la base de données
     */
    protected Connection dbConnection ;


    /**
     * Constructeur de la classe
     * @param infoConnection chaîne de caractères avec les informations de connexion
     * @param user chaîne de caractères contenant l'identifiant de connexion pour la base de données
     * @param pwd chaîne de caractères contenant le mot de passe
     */
    public ProductRepositoryDb(String infoConnection, String user, String pwd ) throws SQLException, ClassNotFoundException {
        Class.forName("org.mariadb.jdbc.Driver");
        dbConnection = DriverManager.getConnection( infoConnection, user, pwd ) ;
    }

    @Override
    public void close() {
        try{
            dbConnection.close();
        }
        catch(SQLException e){
            System.err.println(e.getMessage());
        }
    }

    @Override
    public Product getProduct(String name) {

        Product selectedProduct = null;
        String query = "SELECT * FROM Produit WHERE nom=?";
        // construction et exécution de la requête
        try ( PreparedStatement ps = dbConnection.prepareStatement(query) ){
            ps.setString(1, name);
            ResultSet result = ps.executeQuery();// exécution
            if( result.next() )
            {
                String type = result.getString("type"); // récupération du tuple résultat
                selectedProduct = new Product( name,type); // création de l'objet Produit
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return selectedProduct;
    }


    @Override
    public ArrayList<Product> getAllProducts() {
        ArrayList<Product> listProducts ;
        String query = "SELECT * FROM Produit";
        try ( PreparedStatement ps = dbConnection.prepareStatement(query) ){ // construction et exécution de la requête
            ResultSet result = ps.executeQuery(); // exécution
            listProducts = new ArrayList<>();
            while ( result.next() )// récupération du tuple résultat
            {
                String name = result.getString("nom");
                String type = result.getString("type");

                Product currentProduct = new Product( name,type);
                listProducts.add(currentProduct);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return listProducts;
    }

    @Override
    public boolean updateProduct(String name, String type) {
        String query = "UPDATE Product SET type=?  where nom=?";
        int nbRowModified = 0;

        // construction et exécution de la requète
        try ( PreparedStatement ps = dbConnection.prepareStatement(query) ){
            ps.setString(1, type);
            ps.setString(2, name);
            // exécution
            nbRowModified = ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return ( nbRowModified != 0 );
    }


}
