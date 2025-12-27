package modele;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * classe Type
 * Permet de détailler le type unique du cache : cache traditionnelle, cache jeu de piste, cache objet, ...
 */

@Entity
@Table(name = "Type")
public class TypeCache {
    /**
     *              Variables
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_Type", unique = true, nullable = false)
    private int id; //"clé primaire" avec la contrainte d'unicité pour l'entité

    @Column(
            name = "Texte",
            length = 50
    )
    private String texte; //Texte décrivant le type

    @OneToMany(mappedBy = "typeCache", cascade={CascadeType.PERSIST, CascadeType.REMOVE })
    private List<Cache> caches;
    /**
     *              Constructeurs
     */

    //Constructeurs par défaut : dans notre cas il ne sert qu'à prendre l'espace mémoire
    public TypeCache(){
        this.caches = new ArrayList<>();
    }

    //Constructeurs par données de texte
    public TypeCache(String texte){
        this();
        if (texte != null){
            this.texte = texte;
        }else {
            this.texte = "Pas de précision sur le texte";
        }
    }

    /**
     * TOSTRING TypeCache
     * @return la chaine de caractère représentant les informations du cache
     */
    @Override
    public String toString() {
        return "TypeCache{" +
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
     *          GET et SETTER de TypeCache
     */
    public List<Cache> getCaches() {
        return caches;
    }

    /**
     *        EQUALS and HASHCODE de TypeCache
     */
    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        TypeCache typeCache = (TypeCache) o;
        return id == typeCache.id;
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
     * Ajoute le cache dans le Type courant
     * /!\ Comme le statut du cache n'est pas propriétaire de l'association, on doit faire la manipulation depuis l'instance du
     *       cache et cette instance appelera cette méthode
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
