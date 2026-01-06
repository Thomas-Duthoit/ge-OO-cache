package modele;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * classe ReseauCache
 * Sert à regrouper des caches en un réseau nommé, auquel certains utilisateurs on accès
 */
@Entity(name = "ReseauCache")
@Table(
        name = "ReseauCache"
)
public class ReseauCache {

    /**
     *              ATTRIBUTS ReseauCache
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_ReseauCache", unique = true)
    private int id;  // "clé primaire" avec la contrainte d'unicité pour l'entité

    @Column(name = "nom", nullable = false, length = 100)  // non nulle, max 100 caractères
    private String nom;  // nom du réseau de cache




    /**
     *              ASSOCIATIONS ReseauCache
     */
    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(  // Table de jointure entre les deux entités car c'est une association many to many
            name = "Utilisateur_reseau",
            joinColumns = @JoinColumn(name = "idReseau"),
            inverseJoinColumns = @JoinColumn(name = "idUtilisateur")
    )
    private List<Utilisateur> utilisateurs;  // association "accède" entre Utilisateur et ReseauCache

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "idProprietaire")
    private Utilisateur proprietaire;  // association "possède" entre Utilisateur et ReseauCache

    @OneToMany(mappedBy = "appartient", cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    private List<Cache> caches;  // association "appartient" entre Cache et ReseauCache



    /**
     *              CONSTRUCTEURS ReseauCache
     */
    public ReseauCache() {  // Par défaut
        nom = "";
        utilisateurs = new ArrayList<>();
        caches = new ArrayList<>();
    }

    /**
     * Constructeur par données pour ReseauCache
     * @param nom le nom du réseau
     */
    public ReseauCache(String nom) {
        this();  // appel du constructeur par défaut pour l'initialisation des variables par défaut
        if (nom != null) {
            this.nom = nom;
        }
    }

    /**
     * TOSTRING ReseauCache
     * @return la chaine de caractère représentant les informations du réseau
     */
    /*
    @Override
    public String toString() {
        return "ReseauCache{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", proprietaire='" + proprietaire + '\'' +
                '}';
    }*/
    @Override
    public String toString() {
        return nom;
    }




    /**
     *              METHODES ASSOCIATIONS ReseauCache
     *.
     * (Utilisées pour gérer les associations entre plusieurs classes)
     */

    /**
     * Méthode : ajouterAccesUtilisateur
     * ---------------------------------
     * Ajoute l'accès au réseau pour l'utilisateur courant
     *
     * @param utilisateur l'utilisateur qui aura accès au réseau courant
     * @return l'ajout a réussi
     */
    public boolean ajouterAccesUtilisateur(Utilisateur utilisateur) {
        if (utilisateur != null) {
            this.utilisateurs.add(utilisateur);
            return utilisateur.ajouterAccesReseau(this);
        }
        return false;
    }

    public boolean supprAccesUtilisateur(Utilisateur utilisateur) {
        if (utilisateur != null) {
            if(this.utilisateurs.contains(utilisateur)){
                this.utilisateurs.remove(utilisateur);
                return utilisateur.supprAccesReseau(this);
            }
            return false;
        }
        return false;
    }

    /**
     * Méthode : setProprietaire
     * ---------------------------------
     * Définit l'utilisateur comme étant le propriétaire du réseau actuel
     * /!\ On ne doit pas changer le propriétaire du réseau
     *
     * @param utilisateur l'utilisateur qui deviendra propriétaire
     * @return l'affectation a été réussie
     */
    public boolean setProprietaire(Utilisateur utilisateur) {
        // TODO: check si on a déjà un propriétaire pour éviter les bugs
        if (utilisateur != null) {
            this.proprietaire = utilisateur;
            return utilisateur.ajouterReseauPossede(this);
        }
        return false;
    }

    /**
     * Méthode : addCache
     * ------------------
     * Ajoute une cache au réseau de cache actuel
     * /!\ Comme le réseau n'est pas propriétaire de l'association, on doit faire la manipulation depuis l'instance du
     *     cache et cette instance appelera cette méthode
     *
     * @param cache la cache à ajouter
     * @return ajout réussi
     */
    protected boolean addCache(Cache cache) {
        if (cache != null) {
            this.caches.add(cache);
            return true;
        }
        return false;
    }


    /**
     *              METHODES GETTERS/SETTERS ReseauCache
     */


    public int getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    /**
     *              METHODES EQUALS / HASHCODE
     */
    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        ReseauCache that = (ReseauCache) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
