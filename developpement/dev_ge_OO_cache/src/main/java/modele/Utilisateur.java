package modele;

public class Utilisateur {

    private int id;  // "clé primaire" avec la contrainte d'unicité pour l'entité
    private String pseudo;  // Pseudo d'un utilisateur
    private String mdp;  // mot de passe de l'utilisateur
    private boolean admin;  // utilisateur administrateur ?

    public Utilisateur(String pseudo, String mdp, boolean admin) {
        if (pseudo != null) {
            this.pseudo = pseudo;
        } else {
            this.pseudo = "PSEUDO PAR DEFAUT";
        }

        if (mdp != null) {
            this.mdp = mdp;
        } else {
            this.mdp = "MDP PAR DEFAUT";
        }

        this.admin = admin;
    }
}
