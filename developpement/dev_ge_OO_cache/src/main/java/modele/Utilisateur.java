package modele;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;


// TODO: Rajouter une longueur max pour les champs "string"
// TODO: utiliser les noms de colonnes du MDP



@Entity(name = "Utilisateur")
@Table(
        name = "UTILISATEUR"
)
public class Utilisateur {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", unique = true)
    private int id;  // "clé primaire" avec la contrainte d'unicité pour l'entité

    @Column(name = "PSEUDO", nullable = false)  // non nulle
    private String pseudo;  // Pseudo d'un utilisateur
    @Column(name = "MDP", nullable = false)  // non nulle
    private String mdp;  // mot de passe de l'utilisateur
    private boolean admin;  // utilisateur administrateur ?



    // Associations:
    @ManyToMany(mappedBy = "utilisateurs", cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    private List<ReseauCache> accede;


    @OneToMany(mappedBy = "proprietaire", cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    private List<ReseauCache> possede;

    @OneToMany(mappedBy = "proprietaire", cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    private List<Log> possedeLog;


    public Utilisateur() {
        this.pseudo = "";
        this.mdp = "";
        this.accede = new ArrayList<>();
        this.possede = new ArrayList<>();
        this.possedeLog = new ArrayList<>();
    }

    public Utilisateur(String pseudo, String mdp, boolean admin) {
        this();
        if (pseudo != null) {
            this.pseudo = pseudo;
        }
        if (mdp != null) {
            this.mdp = mdp;
        }
        this.admin = admin;
    }

    @Override
    public String toString() {
        return "Utilisateur{" +
                "id=" + id +
                ", pseudo='" + pseudo + '\'' +
                ", mdp='" + mdp + '\'' +
                ", admin=" + admin +
                ", réseaux accessibles : " + accede.size() +
                ", réseaux possédés : " + possede.size()  +
                ", logs possédés : " + possedeLog.size()  +
                '}';
    }



    // méthodes pour les associations:
    protected void ajouterAccesReseau(ReseauCache r) {
        if (r != null) {
            this.accede.add(r);
        }
    }

    protected void ajouterReseauPossede(ReseauCache r) {
        if (r != null) {
            this.possede.add(r);
        }
    }

    protected boolean ajouterLogPossede(Log l) {
        if (l != null) {
            this.possedeLog.add(l);
            return true;
        }
        return false;
    }
}
