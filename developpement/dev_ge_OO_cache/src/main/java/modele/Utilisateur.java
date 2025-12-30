package modele;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;


/**
 * classe Utilisateur
 * Contient toutes les informations relatives à un compte
 */
@Entity(name = "Utilisateur")
@Table(
        name = "Utilisateur"
)
public class Utilisateur {

    /**
     *              ATTRIBUTS Utilisateur
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_Utilisateur", unique = true)
    private int id;  // "clé primaire" avec la contrainte d'unicité pour l'entité

    @Column(name = "PSEUDO", nullable = false, length = 20, unique = true)  // non nulle, max 20 caractères, unique
    private String pseudo;  // Pseudo d'un utilisateur

    @Column(name = "MDP", nullable = false, length = 20)  // non nulle, max 20 caractères
    private String mdp;  // mot de passe de l'utilisateur

    private boolean admin;  // utilisateur administrateur ?



    /**
     *              ASSOCIATIONS Utilisateur
     */
    @ManyToMany(mappedBy = "utilisateurs", cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    private List<ReseauCache> accede;  // Association "accède" entre Utililsateur et ReseauCache

    @OneToMany(mappedBy = "proprietaire", cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    private List<ReseauCache> possede;  // Association "possède" entre Utililsateur et ReseauCache

    @OneToMany(mappedBy = "proprietaire", cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    private List<Log> possedeLog;  // Association "ppossède" entre Utililsateur et Log


    /**
     *              CONSTRUCTEURS Utilisateur
     */
    public Utilisateur() {  // Par défaut
        this.pseudo = "";
        this.mdp = "";
        this.accede = new ArrayList<>();
        this.possede = new ArrayList<>();
        this.possedeLog = new ArrayList<>();
    }

    /**
     * Constructeur par données pour l'Utilisateur
     * @param pseudo pseudo de l'utilisateur
     * @param mdp mot de passe de l'utilisateur
     * @param admin true=admin, false=utilisateur simple
     */
    public Utilisateur(String pseudo, String mdp, boolean admin) {
        this();  // appel du constructeur par défaut pour l'initialisation des variables par défaut
        if (pseudo != null) {
            this.pseudo = pseudo;
        }
        if (mdp != null) {
            this.mdp = mdp;
        }
        this.admin = admin;
    }

    /**
     * TOSTRING Utilisateur
     * @return la chaine de caractère représentant les informations de l'utilisateur
     */
    /*
    @Override
    public String toString() {
        return "Utilisateur{" +
                "id=" + id +
                ", pseudo='" + pseudo + '\'' +
                ", mdp='" + mdp + '\'' +
                ", admin=" + admin +
                '}';
    }*/
    @Override
    public String toString() {
        return pseudo;
    }

    /**
     *              METHODES ASSOCIATIONS Utilisateur
     *.
     * (Utilisées pour gérer les associations entre plusieurs classes)
     */

    /**
     * méthode : ajouterAccesReseau
     * ----------------------------
     * Ajoute l'accès au réseau pour l'utilisateur courant
     * /!\ Comme l'utilisateur n'est pas propriétaire de l'association, on doit faire la manipulation depuis l'instance du
     *     reseau et cette instance appelera cette méthode
     *
     * @param reseauCache le réseau à accéder
     * @return affectation réussie ou non
     */
    protected boolean ajouterAccesReseau(ReseauCache reseauCache) {
        if (reseauCache != null) {
            this.accede.add(reseauCache);
            return true;
        }
        return false;
    }

    /**
     * méthode : ajouterReseauPossede
     * ------------------------------
     * Définit le réseau comme appartenant à l'utilisateur courant
     * /!\ Comme l'utilisateur n'est pas propriétaire de l'association, on doit faire la manipulation depuis l'instance du
     *     reseau et cette instance appelera cette méthode
     *
     * @param reseauCache le réseau qui nous appartient
     * @return affectation réussie ou non
     */
    protected boolean ajouterReseauPossede(ReseauCache reseauCache) {
        if (reseauCache != null) {
            this.possede.add(reseauCache);
            return true;
        }
        return false;
    }

    /**
     * méthode : ajouterLogPossede
     * ---------------------------
     * Définit le log comme appartenant à l'utilisateur courant
     * /!\ Comme l'utilisateur n'est pas propriétaire de l'association, on doit faire la manipulation depuis l'instance du
     *     log et cette instance appelera cette méthode
     *
     * @param log le réseau qui nous appartient
     * @return affectation réussie ou non
     */
    protected boolean ajouterLogPossede(Log log) {
        if (log != null) {
            this.possedeLog.add(log);
            return true;
        }
        return false;
    }


    /**
     *              METHODES GETTERS/SETTERS Utilisateur
     */


    public int getId() {
        return id;
    }

    public boolean isAdmin() {
        return admin;
    }

    public String getPseudo() {
        return pseudo;
    }
}
