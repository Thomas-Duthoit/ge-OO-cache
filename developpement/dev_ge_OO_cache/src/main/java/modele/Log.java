package modele;

import jakarta.persistence.*;

import java.util.Date;

/**
 * classe Log
 * Représente une visite pour un cache
 */
@Entity(name = "Log")
@Table(
        name = "Log"
)
public class Log {

    /**
     *              ATTRIBUTS Log
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_Log", unique = true)
    private int id;  // "clé primaire" avec la contrainte d'unicité pour l'entité

    @Column(name = "commentaire", nullable = false, length = 300)  // non nulle, max 300 caractères
    private String commentaire;

    private boolean trouver;  // l'utilisateur a trouvé ou non la cache

    private int note;  // note de l'utilisateur pour la cache

    @Column(name = "date", nullable = false)
    private Date date;  // date du log

    // TODO: image à faire avec une url selon le temps restant



    /**
     *              ASSOCIATIONS Log
     */
    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "fk_Utilisateur_Id")
    private Utilisateur proprietaire;  // association "possède" entre Utilisateur et Log

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "fk_Cache_Id")
    private Cache enregistrer;  // association "enregistrer" entre Cache et Log


    /**
     *              CONSTRUCTEURS Log
     */
    public Log() {  // par défaut
        commentaire = ""; // vide par défaut
        date = new Date();
    }

    /**
     * Constructeur par données pour le Log
     * @param commentaire commentaire laissé par l'utilisateur pour le log
     * @param trouver la cache a été trouvée
     * @param note note de la cache
     * @param date date du log
     */
    public Log(String commentaire, boolean trouver, int note, Date date) {
        this();  // appel du constructeur par défaut pour l'initialisation des variables par défaut

        if (commentaire != null) {
            this.commentaire = commentaire;
        }

        this.trouver = trouver;
        this.note = note;

        if (date != null) {
            this.date = date;
        }
    }

    /**
     * TOSTRING Log
     * @return la chaine de caractère représentant les informations du log
     */
    /*@Override
    public String toString() {
        return "Log{" +
                "id=" + id +
                ", commentaire='" + commentaire + '\'' +
                ", trouver=" + trouver +
                ", note=" + note +
                ", date=" + date +
                ", proprietaire='" + proprietaire + '\'' +
                ", cache='" + enregistrer + '\'' +
                '}';
    }*/
    @Override
    public String toString() {
        return "Log " + id;
    }



    /**
     *              METHODES ASSOCIATIONS Log
     *.
     * (Utilisées pour gérer les associations entre plusieurs classes)
     */

    /**
     * Méthode : setProprietaire
     * ---------------------------------
     * Définit l'utilisateur comme le propriétaire de ce log
     *
     * @param utilisateur l'utilisateur qui a réalisé le log actuel
     * @return affectation réussie
     */
    public boolean setProprietaire(Utilisateur utilisateur) {
        if (utilisateur != null) {
            this.proprietaire = utilisateur;
            return utilisateur.ajouterLogPossede(this);
        }
        return false;
    }

    /**
     * Méthode : setCache
     * ---------------------------------
     * Définit la cache concernée par ce log
     *
     * @param cache la cache concernée par le log
     * @return affectation réussie
     */
    public boolean setCache(Cache cache) {
        if (cache != null) {
            this.enregistrer = cache;
            return cache.ajouterLog(this);
        }
        return false;
    }

    /**
     *          GETTER et SETTER
     */
    public int getId() {
        return id;
    }

    public boolean isTrouver() {
        return trouver;
    }

    public Date getDate() {
        return date;
    }

    public int getNote() {
        return note;
    }

    public String getCommentaire() {
        return commentaire;
    }
}
