package fr.univamu.fr.panier;

import java.io.Closeable;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PanierRepositoryMariadb implements PanierRepositoryInterface, Closeable {

    protected Connection dbConnection;

    public PanierRepositoryMariadb(String connectionInfo, String user, String password) throws SQLException, ClassNotFoundException {
        Class.forName("org.mariadb.jdbc.Driver");
        dbConnection = DriverManager.getConnection(connectionInfo, user, password);
    }

    @Override
    public void close() {
        try {
            dbConnection.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public Panier getPanier(String name) {
        Panier selectedPanier = null;
        String query = "SELECT * FROM Panier WHERE name=?";
        String productQuery = "SELECT * FROM Composant_Panier WHERE nom_panier=?";

        try (PreparedStatement ps = dbConnection.prepareStatement(query)) {
            ps.setString(1, name);
            ResultSet result = ps.executeQuery();

            try (PreparedStatement productStatement = dbConnection.prepareStatement(productQuery)) {
                productStatement.setString(1, name);
                ResultSet productResultSet = productStatement.executeQuery();

                if (result.next()) {
                    int quantity = result.getInt("quantity");
                    float price = result.getFloat("price");
                    String updateDate = result.getString("maj");
                    Map<String, Integer> products = new HashMap<>();

                    while (productResultSet.next()) {
                        String productName = productResultSet.getString("name_products");
                        int productQuantity = productResultSet.getInt("quantite_produit");
                        products.put(productName, productQuantity);
                    }

                    selectedPanier = new Panier(name, quantity, price, products, updateDate);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return selectedPanier;
    }

    @Override
    public ArrayList<Panier> getAllPaniers() {
        ArrayList<Panier> panierList = new ArrayList<>();
        String query = "SELECT * FROM Panier";
        String productQuery = "SELECT * FROM Composant_Panier WHERE nom_panier=?";

        try (PreparedStatement ps = dbConnection.prepareStatement(query)) {
            ResultSet result = ps.executeQuery();

            while (result.next()) {
                String name = result.getString("name");
                int quantity = result.getInt("quantity");
                float price = result.getFloat("price");
                String updateDate = result.getString("maj");
                Map<String, Integer> products = new HashMap<>();

                try (PreparedStatement productStatement = dbConnection.prepareStatement(productQuery)) {
                    productStatement.setString(1, name);
                    ResultSet productResultSet = productStatement.executeQuery();

                    while (productResultSet.next()) {
                        String productName = productResultSet.getString("name_products");
                        int productQuantity = productResultSet.getInt("quantite_produit");
                        products.put(productName, productQuantity);
                    }
                }

                Panier currentPanier = new Panier(name, quantity, price, products, updateDate);
                panierList.add(currentPanier);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return panierList;
    }

    @Override
    public boolean updatePanier(String name, int quantity, float price, Map<String, Integer> products, String date) {
        String panierQuery = "UPDATE Panier SET quantity=?, price=?, maj=? WHERE name=?";
        String deleteProductsQuery = "DELETE FROM Composant_Panier WHERE nom_panier=?";
        String insertProductsQuery = "INSERT INTO Composant_Panier (nom_panier, nom_produit, quantite_produit) VALUES (?, ?, ?)";
        int rowsAffected = 0;

        try {
            dbConnection.setAutoCommit(false);

            try (PreparedStatement ps = dbConnection.prepareStatement(panierQuery)) {
                ps.setInt(1, quantity);
                ps.setFloat(2, price);
                ps.setString(3, date);
                ps.setString(4, name);
                rowsAffected = ps.executeUpdate();
            }

            try (PreparedStatement psDelete = dbConnection.prepareStatement(deleteProductsQuery)) {
                psDelete.setString(1, name);
                psDelete.executeUpdate();
            }

            try (PreparedStatement psInsert = dbConnection.prepareStatement(insertProductsQuery)) {
                for (Map.Entry<String, Integer> entry : products.entrySet()) {
                    psInsert.setString(1, name);
                    psInsert.setString(2, entry.getKey());
                    psInsert.setInt(3, entry.getValue());
                    psInsert.executeUpdate();
                }
            }

            dbConnection.commit();
            dbConnection.setAutoCommit(true);
        } catch (SQLException e) {
            try {
                dbConnection.rollback();
            } catch (SQLException rollbackEx) {
                rollbackEx.printStackTrace();
            }
            throw new RuntimeException(e);
        }
        return (rowsAffected != 0);
    }

    @Override
    public boolean addPanier(Panier panier) {
        String query = "INSERT INTO Panier (name, quantity, price) VALUES (?, ?, ?)";

        try (PreparedStatement ps = dbConnection.prepareStatement(query)) {
            ps.setString(1, panier.getName());
            ps.setInt(2, panier.getQuantity());
            ps.setFloat(3, panier.getPrice());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean deletePanier(String name) {
        String query = "DELETE FROM Panier WHERE name = ?";

        try (PreparedStatement ps = dbConnection.prepareStatement(query)) {
            ps.setString(1, name);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}