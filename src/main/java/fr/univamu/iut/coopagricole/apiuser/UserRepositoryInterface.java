package fr.univamu.iut.coopagricole.apiuser;

import java.util.ArrayList;

/**
 * Interface d'accès aux données des utilisateurs
 */
public interface UserRepositoryInterface {

    /**
     * Méthode fermant le dépôt où sont stockées les informations sur les utilisateurs
     */
    public void close();

    /**
     * Retourne l'utilisateur correspondant à l'identifiant spécifié
     * @param id identifiant de l'utilisateur recherché
     * @return un objet User représentant l'utilisateur trouvé, ou null si non trouvé
     */
    public User getUser(String id);

    /**
     * Retourne la liste complète de tous les utilisateurs
     * @return une liste d'objets User
     */
    public ArrayList<User> getAllUsers();

    /**
     * Met à jour les informations d'un utilisateur existant
     * @param id Identifiant de l'utilisateur à modifier
     * @param name Nouveau nom de l'utilisateur
     * @param mail Nouvel email de l'utilisateur
     * @param pwd Nouveau mot de passe de l'utilisateur
     * @return true si la mise à jour a réussi, false sinon
     */
    public boolean updateUser(String id, String name, String mail, String pwd);
}