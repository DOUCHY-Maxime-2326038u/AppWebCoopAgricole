package fr.univamu.fr.panier;

import jakarta.json.bind.annotation.JsonbCreator;
import jakarta.json.bind.annotation.JsonbProperty;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Panier {

    private String name;
    private int quantity;
    private float price;
    private final Map<String, Integer> products;
    private String lastUpdate;

    public Panier() {
        this.products = new HashMap<>();
    }

    @JsonbCreator
    public Panier(@JsonbProperty("nom") String name,
                  @JsonbProperty("quantite") int quantity,
                  @JsonbProperty("prix") float price,
                  @JsonbProperty("produits") Map<String, Integer> products,
                  @JsonbProperty("derniere maj") String lastUpdate) {
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.products = products != null ? products : new HashMap<>();
        updateLastUpdate();
    }

    public void addQuantity(int quantity) {
        this.quantity += quantity;
        updateLastUpdate();
    }

    public void removeQuantity(int quantity) {
        this.quantity -= quantity;
        updateLastUpdate();
    }

    public void setPrice(float price) {
        this.price = price;
        updateLastUpdate();
    }

    public void addProducts(String product, int quantity) {
        this.products.put(product, this.products.getOrDefault(product, 0) + quantity);
        updateLastUpdate();
    }

    public void removeProducts(String product, int quantity) {
        if (this.products.containsKey(product)) {
            int newQuantity = this.products.get(product) - quantity;
            if (newQuantity > 0) {
                this.products.put(product, newQuantity);
            } else {
                this.products.remove(product);
            }
            updateLastUpdate();
        }
    }

    public String getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(String lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    private void updateLastUpdate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        this.lastUpdate = sdf.format(new Date());
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public float getPrice() {
        return price;
    }

    public Map<String, Integer> getProducts() {
        return this.products;
    }

    @Override
    public String toString() {
        return "Panier{" +
                "name='" + name + '\'' +
                ", quantity=" + quantity +
                ", price=" + price +
                ", products=" + products +
                ", lastUpdate='" + lastUpdate + '\'' +
                '}';
    }

    public String getMaj() {
        return lastUpdate;
    }

    // Setter pour la date de mise à jour (on reçoit une chaîne au format "yyyy-MM-dd")
    public void setMaj(String lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    // Mise à jour de la date de mise à jour (format "yyyy-MM-dd")
    private void updatelastUpdate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        this.lastUpdate = sdf.format(new Date()); // Formatage de la date actuelle
    }
}