package fr.univamu.iut.coopagricole.apiproduct;

import java.util.ArrayList;


public interface ProductRepositoryInterface {
   /**
    *  Méthode fermant le dépôt où sont stockées les informations
    */
    public void close();

    /**
     * Méthode retournant le produit dont la référence est en paramètre
     * @param name nom du produit
     * @return un objet Product représentant le produit
     */
    public Product getProduct(String name );

    /**
     * Méthode retournant la liste des produits
     * @return une liste d'objets produits
     */
    public ArrayList<Product> getAllProducts() ;


    public boolean updateProduct(  String name,  String type);



}
