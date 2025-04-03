package fr.univamu.iut.coopagricole.apiuser;


public class User {

    /** Identifiant unique de l'utilisateur */
    protected String id;

    /** Nom de l'utilisateur */
    protected String name;

    /** Adresse email de l'utilisateur */
    protected String mail;

    /** Mot de passe de l'utilisateur */
    protected String pwd;

    /**
     * Constructeur par défaut créant un utilisateur vide.
     */
    public User(){
    }

    /**
     * Constructeur initialisant toutes les propriétés de l'utilisateur.
     *
     * @param id Identifiant unique de l'utilisateur
     * @param name Nom de l'utilisateur
     * @param mail Adresse email de l'utilisateur
     * @param pwd Mot de passe de l'utilisateur
     */
    public User(String id, String name, String mail, String pwd) {
        this.id = id;
        this.name = name;
        this.mail = mail;
        this.pwd = pwd;
    }

    /**
     * Retourne l'identifiant unique de l'utilisateur.
     * @return L'identifiant de l'utilisateur
     */
    public String getId() {
        return id;
    }

    /**
     * Retourne le nom de l'utilisateur.
     * @return Le nom de l'utilisateur
     */
    public String getName() {
        return name;
    }

    /**
     * Retourne l'adresse email de l'utilisateur.
     * @return L'email de l'utilisateur
     */
    public String getMail() {
        return mail;
    }

    /**
     * Retourne le mot de passe de l'utilisateur.
     * @return Le mot de passe de l'utilisateur
     */
    public String getPwd() {
        return pwd;
    }

    /**
     * Définit l'identifiant unique de l'utilisateur.
     * @param id Le nouvel identifiant
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Définit le nom de l'utilisateur.
     * @param name Le nouveau nom
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Définit l'adresse email de l'utilisateur.
     * @param mail La nouvelle adresse email
     */
    public void setMail(String mail) {
        this.mail = mail;
    }

    /**
     * Définit le mot de passe de l'utilisateur.
     * @param pwd Le nouveau mot de passe
     */
    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    @Override
    public String toString() {
        return "Utilisateur{" +
                "id='" + id + '\'' +
                ", nom='" + name + '\'' +
                ", mail='" + mail + '\'' +
                ", pwd=" + pwd +
                '}';
    }
}