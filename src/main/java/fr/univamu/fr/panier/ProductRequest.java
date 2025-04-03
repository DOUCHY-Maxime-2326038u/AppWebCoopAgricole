package fr.univamu.fr.panier;

public class ProductRequest {

    private String produit;
    private int quantity;

    public ProductRequest() {}

    public ProductRequest(String produit, int quantity) {
        this.produit = produit;
        this.quantity = quantity;
    }

    public String getProduit() {
        return produit;
    }

    public void setProduit(String produit) {
        this.produit = produit;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
