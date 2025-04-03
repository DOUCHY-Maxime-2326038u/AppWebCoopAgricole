package fr.univamu.iut.coopagricole.apiuser;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;

/**
 * Ressource REST pour la gestion des utilisateurs
 * (point d'accès de l'API REST)
 */
@Path("/users")
public class UserResource {

    /**
     * Service utilisé pour accéder aux données des utilisateurs
     */
    private UserService service;

    /**
     * Constructeur par défaut
     */
    public UserResource(){}

    /**
     * Constructeur avec injection de dépendances
     * @param userRepo Répository d'accès aux données des utilisateurs
     */
    @Inject
    public UserResource(UserRepositoryInterface userRepo){
        this.service = new UserService(userRepo);
    }

    /**
     * Constructeur permettant d'initialiser le service avec un mock
     * @param service Service utilisateur à utiliser
     */
    public UserResource(UserService service){
        this.service = service;
    }

    /**
     * Endpoint récupérant tous les utilisateurs au format JSON
     * @return Liste des utilisateurs au format JSON
     */
    @GET
    @Produces("application/json")
    public String getAllUsers() {
        return service.getAllUsersJSON();
    }

    /**
     * Endpoint récupérant un utilisateur spécifique par son ID
     * @param id Identifiant de l'utilisateur
     * @return Données de l'utilisateur au format JSON
     * @throws NotFoundException si l'utilisateur n'existe pas
     */
    @GET
    @Path("{id}")
    @Produces("application/json")
    public String getUser(@PathParam("id") String id) {
        String result = service.getUserJSON(id);
        if(result == null)
            throw new NotFoundException("Utilisateur non trouvé");
        return result;
    }

    /**
     * Endpoint mettant à jour les informations d'un utilisateur
     * @param id Identifiant de l'utilisateur à mettre à jour
     * @param user Nouvelles données de l'utilisateur
     * @return Réponse HTTP indiquant le succès de l'opération
     * @throws NotFoundException si l'utilisateur n'existe pas
     */
    @PUT
    @Path("{id}")
    @Consumes("application/json")
    public Response updateUser(@PathParam("id") String id, User user) {
        if(!service.updateUser(id, user))
            throw new NotFoundException("Utilisateur non trouvé");
        return Response.ok("Mise à jour réussie").build();
    }
}