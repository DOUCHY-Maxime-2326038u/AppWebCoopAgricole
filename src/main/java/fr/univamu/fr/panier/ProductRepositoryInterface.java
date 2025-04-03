package fr.univamu.fr.panier;

import java.util.*;

public interface ProductRepositoryInterface {
 public void close();

 public Product getProduct(String name);

 public ArrayList<Product> getAllProducts();

 public boolean updateProduct(String name, String type);
}
