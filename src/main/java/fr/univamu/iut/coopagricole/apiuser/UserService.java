package fr.univamu.iut.coopagricole.apiuser;

import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;

import java.util.ArrayList;


/**
 * Classe utilisée pour récupérer les informations nécessaires à la ressource
 * (permet de dissocier ressource et mode d'éccès aux données)
 */
public class UserService {

    /**
     * Objet permettant d'accéder au dépôt où sont stockées les informations sur les utilisateurs
     */
    protected UserRepositoryInterface userRepo ;

    /**
     * Constructeur permettant d'injecter l'accès aux données
     * @param bookRepo objet implémentant l'interface d'accès aux données
     */
    public  UserService( UserRepositoryInterface bookRepo) {
        this.userRepo = bookRepo;
    }


    public boolean updateUser(String id, User user) {
        return userRepo.updateUser(id, user.name, user.mail, user.pwd);
    }

    /**
     * Méthode retournant les informations (sans mail et mot de passe) sur les utilisateurs au format JSON
     * @return une chaîne de caractère contenant les informations au format JSON
     */
    public String getAllUsersJSON(){

        ArrayList<User> allUsers = userRepo.getAllUsers();
        for( User currentUser : allUsers ){// on supprime les informations sur les mots de passe et les mails

            currentUser.setMail("");
            currentUser.setPwd("");
        }
        // création du json et conversion de la liste des utilisateurs
        String result = null;
        try( Jsonb jsonb = JsonbBuilder.create()){
            result = jsonb.toJson(allUsers);
        }
        catch (Exception e){
            System.err.println( e.getMessage() );
        }
        return result;
    }

    /**
     * Méthode retournant au format JSON les informations sur un utilisateur recherché
     * @param id l'identifiant de l'utilisateur recherché
     * @return une chaîne de caractère contenant les informations au format JSON
     */
    public String getUserJSON( String id ){
        String result = null;
        User myUser = userRepo.getUser(id);

        if( myUser != null ) { // si l'utilisateur a été trouvé

            try (Jsonb jsonb = JsonbBuilder.create()) { // création du json et conversion de l'utilisateur

                result = jsonb.toJson(myUser);
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
        return result;
    }
}
