package fr.univamu.iut.coopagricole.apiproduct;

import jakarta.json.bind.annotation.JsonbCreator;
import jakarta.json.bind.annotation.JsonbProperty;


public class Product {

    protected String name;
    protected String type;

    @JsonbCreator
    public Product(@JsonbProperty("name") String name, @JsonbProperty("type") String type) {
        this.name = name;
        this.type = type;
    }

    public String getNom() {
        return name;
    }

    public void setNom(String nom) {
        this.name = nom;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
