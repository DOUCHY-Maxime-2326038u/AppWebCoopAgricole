package fr.univamu.iut.coopagricole.apiuser;

import java.io.Closeable;
import java.sql.*;
import java.util.ArrayList;

/**
 * Classe permettant d'accèder aux utilisateurs stockés dans une base de données Mariadb
 */
public class UserRepositoryMariadb   implements UserRepositoryInterface, Closeable {

    /**
     * Session pour la base de données
     */
    protected Connection dbConnection ;

    /**
     * Constructeur de la classe
     * @param infoConnection chaîne de caractères avec les informations de connexion
     * @param user chaîne de caractères contenant l'identifiant de connexion à la base de données
     * @param pwd chaîne de caractères contenant le mot de passe
     */
    public UserRepositoryMariadb(String infoConnection, String user, String pwd ) throws SQLException, ClassNotFoundException {
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
    public User getUser(String id) {

        User selectedUser = null;
        String query = "SELECT * FROM User WHERE id=?";

        try ( PreparedStatement ps = dbConnection.prepareStatement(query) ){// construction et exécution de la requête

            ps.setString(1, id);
            ResultSet result = ps.executeQuery();// exécution

            // récupération du tuple résultat
            if( result.next() )
            {
                String name = result.getString("name");
                String mail = result.getString("mail");
                String pwd = result.getString("pwd");

                selectedUser = new User(id, name, mail,pwd);// création et initialisation de l'objet User
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return selectedUser;
    }


    @Override
    public ArrayList<User> getAllUsers() {
        ArrayList<User> listUsers ;

        String query = "SELECT * FROM User";

        try ( PreparedStatement ps = dbConnection.prepareStatement(query) ){// construction et exécution de la requête

            ResultSet result = ps.executeQuery();// exécution
            listUsers = new ArrayList<>();

            while ( result.next() )// récupération du tuple résultat
            {
                String id = result.getString("id");
                String name = result.getString("name");
                String mail = result.getString("mail");
                String pwd = result.getString("pwd");

                User currentUser = new User(id, name, mail,pwd);// création du User
                listUsers.add(currentUser);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return listUsers;
    }


    @Override
    public boolean updateUser(String id, String name, String mail, String pwd) {
        String query = "UPDATE User SET name=?, mail=?, mail=?  where id=?";
        int nbRowModified = 0;

        try ( PreparedStatement ps = dbConnection.prepareStatement(query) ){// construction et exécution de la requête
            ps.setString(1, name);
            ps.setString(2, mail);
            ps.setString(3, pwd );
            ps.setString(4, id);

            nbRowModified = ps.executeUpdate(); // exécution
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return ( nbRowModified != 0 );
    }
}
