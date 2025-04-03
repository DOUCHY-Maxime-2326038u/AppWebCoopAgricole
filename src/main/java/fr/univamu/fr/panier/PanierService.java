package fr.univamu.fr.panier;

import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import java.util.ArrayList;

public class PanierService {

    protected PanierRepositoryInterface panierRepo;
    protected ProductRepositoryInterface productRepo;

    public PanierService(PanierRepositoryInterface panierRepo, ProductRepositoryInterface productRepo) {
        this.panierRepo = panierRepo;
        this.productRepo = productRepo;
    }

    public String getAllPaniersJSON() {
        ArrayList<Panier> allPaniers = panierRepo.getAllPaniers();

        String result = null;
        try (Jsonb jsonb = JsonbBuilder.create()) {
            result = jsonb.toJson(allPaniers);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

        return result;
    }

    public String getPanierJSON(String name) {
        String result = null;
        Panier myPanier = panierRepo.getPanier(name);

        if (myPanier != null) {
            try (Jsonb jsonb = JsonbBuilder.create()) {
                result = jsonb.toJson(myPanier);
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
        return result;
    }

    public boolean addPanier(Panier panier) {
        return panierRepo.addPanier(panier);
    }

    public boolean deletePanier(String Name) {
        return panierRepo.deletePanier(Name);
    }

    public boolean orderPanier(String NamePanier, int orderQuantity) {
        Panier panier = panierRepo.getPanier(NamePanier);

        if (panier == null) {
            return false;
        }

        if (panier.getQuantity() < orderQuantity) {
            return false;
        }

        panier.removeQuantity(orderQuantity);
        return panierRepo.updatePanier(panier.getName(), panier.getQuantity(), panier.getPrice(), panier.getProducts(), panier.getLastUpdate());
    }

    public Panier getPanier(String NamePanier) {
        return panierRepo.getPanier(NamePanier);
    }

    public boolean addProductsToPanier(String NamePanier, String produitName, int Quantity) {
        Product produit = productRepo.getProduct(produitName);
        if (produit == null) {
            return false;
        }

        Panier panier = panierRepo.getPanier(NamePanier);
        if (panier == null) {
            return false;
        }

        panier.addProducts(produitName, Quantity);
        return panierRepo.updatePanier(panier.getName(), panier.getQuantity(), panier.getPrice(), panier.getProducts(), panier.getLastUpdate());
    }

    public boolean removeProductsFromPanier(String NamePanier, String produitName, int Quantity) {
        Panier panier = panierRepo.getPanier(NamePanier);
        if (panier == null) {
            return false;
        }

        if (!panier.getProducts().containsKey(produitName) || panier.getProducts().get(produitName) < Quantity) {
            return false;
        }

        panier.removeProducts(produitName, Quantity);
        return panierRepo.updatePanier(panier.getName(), panier.getQuantity(), panier.getPrice(), panier.getProducts(), panier.getLastUpdate());
    }
}
