package modele;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity(name = "ReseauCache")
@Table(
        name = "RESEAU_CACHE"
)
public class ReseauCache {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", unique = true)
    private int id;  // "clé primaire" avec la contrainte d'unicité pour l'entité

    private String nom;




    // Associations
    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(
            name = "UTILISATEUR_RESEAU_CACHE",
            joinColumns = @JoinColumn(name = "RESEAU_CACHE_ID"),
            inverseJoinColumns = @JoinColumn(name = "UTILISATEUR_ID")
    )
    private List<Utilisateur> utilisateurs;  // association "accède" entre Utilisateur et ReseauCache


    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "PROPRIETAIRE_ID")
    private Utilisateur proprietaire;  // association "possède" entre Utilisateur et ReseauCache


    @OneToMany(mappedBy = "appartient", cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    private List<Cache> caches;  // association "appartient" entre Cache et ReseauCache



    public ReseauCache() {
        nom = "";
        utilisateurs = new ArrayList<>();
        caches = new ArrayList<>();
    }

    public ReseauCache(String nom) {
        this();
        if (nom != null) {
            this.nom = nom;
        }
    }

    @Override
    public String toString() {
        return "ReseauCache{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", proprietaire='" + proprietaire + '\'' +
                ", nombre d'utilisateurs : " + utilisateurs.size() +
                ", nombre de caches : " + caches.size() +
                '}';
    }



    // méthodes pour les associations:
    public void ajouterAccesUtilisateur(Utilisateur u) {
        if (u != null) {
            this.utilisateurs.add(u);
            u.ajouterAccesReseau(this);
        }
    }

    public void setProprietaire(Utilisateur u) {
        if (u != null) {
            this.proprietaire = u;
            u.ajouterReseauPossede(this);
        }
    }


    protected void addCache(Cache cache) {
        if (cache != null) {
            this.caches.add(cache);
        }
    }
}
