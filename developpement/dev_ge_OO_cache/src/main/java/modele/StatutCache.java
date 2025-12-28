package modele;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * classe Statut
 * Permet de détailler l'état unique du cache : activée, en cours d'activation, fermée, suspendue
 */
@Entity
@Table(name = "Statut")
public class StatutCache {
    /**
     *              Variables
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_Statut", unique = true, nullable = false)
    private int id; //"clé primaire" avec la contrainte d'unicité pour l'entité

    @Column(name = "Texte", length = 50, unique = true )
    private String texte; //Texte décrivant le statut

    @OneToMany(mappedBy = "statutCache", cascade={CascadeType.PERSIST, CascadeType.REMOVE })
    private List<Cache> caches;

    /**
     *              Constructeurs de STATUTCACHE
     */

    public StatutCache(){   //par défaut
        this.caches = new ArrayList<>();
    }

    /**
     * Contructeur par données du StatutCache
     * @param texte
     */
    public StatutCache(String texte){
        this();
        if (texte != null){
            this.texte = texte;
        }else {
            this.texte = "Pas de précision sur le texte";
        }
    }

    /**
     * TOSTRING StatutCache
     * @return la chaine de caractère représentant les informations du cache
     */
    @Override
    public String toString() {
        return "StatutCache{" +
                "id=" + id +
                ", texte='" + texte + '\'' +
                ", caches=" + caches +
                '}';
    }

    /**
     * TOSTRINGCACHE StatutCache
     * @return la chaine de caractère représentant les informations du cache
     * Utilisé dans l'appel toString de la Classe Cache pour éviter les erreurs de boucle
     */
    public String toStringCache() {
        return "Statut{" +
                "id=" + id +
                ", texte='" + texte + '\'' +
                '}';
    }

    /**
     *          GET et SETTER de StatutCache
     */
    public List<Cache> getCaches() {
        return caches;
    }

    public int getId() {
        return id;
    }

    /**
     *        EQUALS et HASHCODE de statutCache
     */
    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        StatutCache that = (StatutCache) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    /**
     *          METHODES de la classe TYPECACHE
     */

    /**
     * Méthode : addCacheToList
     * ----------------------------
     * Ajoute le cache dans le Statut courant
     * /!\ Comme le statut du cache n'est pas propriétaire de l'association, on doit faire la manipulation depuis l'instance du
     *     cache et cette instance appelera cette méthode
     * @param cache le cache à ajouter
     * @return affectation réussie ou non (l'affectation peut ne pas réussir si le cache existe déjà dans la liste de caches)
     */
    public boolean addCacheToList(Cache cache){
        if (cache != null) {
            if (caches.contains(cache)) {
                return false;
            }
            caches.add(cache);
            return true;
        }
        return false;
    }
}
