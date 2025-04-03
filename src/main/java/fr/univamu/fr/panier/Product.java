package fr.univamu.fr.panier;

import jakarta.json.bind.annotation.JsonbCreator;
import jakarta.json.bind.annotation.JsonbProperty;

public class Product {

    protected String name;
    protected String type;

    @JsonbCreator
    public Product(@JsonbProperty("name") String name,
                   @JsonbProperty("type") String type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
