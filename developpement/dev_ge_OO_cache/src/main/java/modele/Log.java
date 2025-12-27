package modele;

import jakarta.persistence.*;

import java.util.Date;


@Entity(name = "Log")
@Table(
        name = "LOG"
)
public class Log {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", unique = true)
    private int id;  // "clé primaire" avec la contrainte d'unicité pour l'entité

    @Column(name = "COMMENTAIRE", nullable = false)
    private String commentaire;
    private boolean trouver;
    private int note;
    @Column(name = "DATE", nullable = false)
    private Date date;

    // image à faire avec une url selon le temps restant

    public Log() {
        commentaire = ""; // vide par défaut
        date = new Date();
    }

    public Log(String commentaire, boolean trouver, int note, Date date) {
        this();

        if (commentaire != null) {
            this.commentaire = commentaire;
        }

        this.trouver = trouver;
        this.note = note;

        if (date != null) {
            this.date = date;
        }
    }

    // Associations
    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "PROPRIETAIRE_ID")
    private Utilisateur proprietaire;  // association "possède" entre Utilisateur et Log

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "fk_Cache_Id")
    private Cache enregistrer;  // association "enregistrer" entre Cache et Log


    @Override
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
    }



    // méthodes pour les associations

    public boolean setProprietaire(Utilisateur u) {
        if (u != null) {
            this.proprietaire = u;
            return u.ajouterLogPossede(this);
        }
        return false;
    }

    public boolean setCache(Cache cache) {
        if (cache != null) {
            this.enregistrer = cache;
            return cache.ajouterLog(this);
        }
        return false;
    }
}
