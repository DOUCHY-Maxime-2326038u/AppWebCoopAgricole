package fr.univamu.fr.panier;

import java.util.*;

public interface PanierRepositoryInterface {

    void close();

    Panier getPanier(String name);

    ArrayList<Panier> getAllPaniers();

    boolean updatePanier(String name, int quantity, float price, Map<String, Integer> products, String lastUpdate);

    boolean addPanier(Panier panier);

    boolean deletePanier(String name);
}