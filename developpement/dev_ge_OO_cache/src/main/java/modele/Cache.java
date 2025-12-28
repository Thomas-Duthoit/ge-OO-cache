package modele;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe Cache :
 * décrit la géocache ou le cache caché à trouver
 */
@Entity
@Table(name = "Cache")
public class Cache {
    /**
     *                Attributs Cache
     */

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "NUMERO", unique = true, nullable = false)
    private int numero; //"clé primaire" avec la contrainte d'unicité pour l'entité
    //Le numéro est une caractéristique unique demandé par le sujet

    //Variables permettant la description du cache
    @Column(name = "descriptionTextuelle", length = 300)
    private String descriptionTextuelle; // Une description textuelle

    @Column(name = "descriptionTechnique", length = 300)
    private String descriptionTechnique; // Une description plus technique

    @Column(name = "informationsGeolocalisation", length = 100)
    private String informationsGeolocalisation; // regroupent les informations pour la localiser

    @Column(name = "rubriqueLibre", length = 300)
    private String rubriqueLibre; // Une rubrique libre pour détailler le contenu et la forme de la cache


    /**
     *               Association Cache
     */

    @ManyToOne(cascade={CascadeType.PERSIST, CascadeType.REMOVE })
    @JoinColumn(name = "fk_Cache_Type")
    private TypeCache typeCache; // Association "caractérisé par" entre Cache et TypeCache

    @ManyToOne(cascade={CascadeType.PERSIST, CascadeType.REMOVE })
    @JoinColumn(name = "fk_Cache_Statut")
    private StatutCache statutCache; // Association "état" entre Cache et StatutCache

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "fk_ReseauCache_Id")
    private ReseauCache appartient;  // association "appartient" entre Cache et ReseauCache

    @OneToMany(mappedBy = "enregistrer", cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    private List<Log> logs;  // association "enregistrer" entre Cache et Log

    /**
     *               Constructeurs Cache
     */
    public Cache(){ //par Défaut
        this.logs = new ArrayList<>();
        this.typeCache = null;
        this.statutCache = null;
        this.appartient = null;
    }

    /**
     * TOSTRING Cache
     * @return la chaine de caractère représentant les informations du cache
     */
    @Override
    public String toString() {
        String texte = "Cache{" +
                "numero=" + numero +
                ", descriptionTextuelle='" + descriptionTextuelle + '\'' +
                ", descriptionTechnique='" + descriptionTechnique + '\'' +
                ", informationsGeolocalisation='" + informationsGeolocalisation + '\'' +
                ", rubriqueLibre='" + rubriqueLibre + '\'';
        if(this.typeCache != null)
            texte += ", typeCache=" + typeCache.toStringCache();
        if(this.statutCache != null)
            texte += ", statutCache=" + statutCache.toStringCache();
        if(this.logs != null)
            texte += ", logs=" + logs.size();
        texte += '}';
        return texte;
    }

    /**
     * Constructeur par données du Cache
     * @param descriptionTextuelle une description textuelle du cache
     * @param descriptionTechnique une description technique du cache
     * @param informationsGeolocalisation des informations géographiques sur la position du cache
     * @param rubriqueLibre des informations supplémentaires libres
     */
    public Cache(String descriptionTextuelle, String descriptionTechnique, String informationsGeolocalisation, String rubriqueLibre){
        this();

        if(descriptionTextuelle != null) {
            this.descriptionTextuelle = descriptionTextuelle;
        }else {
            this.descriptionTextuelle = "Aucune description textuelle n'a été inscrite";
        }

        if (descriptionTechnique != null) {
            this.descriptionTechnique = descriptionTechnique;
        }else {
            this.descriptionTechnique = "Aucune description technique n'a été inscrite";
        }

        if (informationsGeolocalisation != null){
            this.informationsGeolocalisation = informationsGeolocalisation;
        }else{
            this.informationsGeolocalisation = "Aucune informations de geolocalisation n'a été inscrite";
        }

        if  (rubriqueLibre != null){
            this.rubriqueLibre = rubriqueLibre;
        }else {
            this.rubriqueLibre = "Aucune informations supplémentaires n'a été ajouté";
        }

        this.statutCache = null;
        this.typeCache = null;
    }

    /**
     *          GETTER et SETTER
     */
    public List<Log> getLogs() {
        return logs;
    }

    /**
     *              METHODES ASSOCIATIONS CACHE
     *.
     * (Utilisées pour gérer les associations entre plusieurs classes)
     */

    /**
     * méthode : setStatutCache
     * ----------------------------
     * Ajoute le statut du cache courant
     * Cette méthode sera aussi utiliser pour la modification de statut de cache par la suite
     * @param statutCache le statut du cache à ajouter
     * @return affectation réussie ou non
     */
    public boolean setStatutCache(StatutCache statutCache) {
        if(statutCache != null) {
            if(this.statutCache != null)
                this.statutCache.getCaches().remove(this);

            boolean result = statutCache.addCacheToList(this);
            if (result)
                this.statutCache = statutCache;
            return result;
        }
        return false;
    }

    /**
     * méthode : addTypeCache
     * ----------------------------
     * Ajoute le type du cache courant
     * /!\ On ne doit pas changer le type du cache
     * @param typeCache le type du cache à ajouter
     * @return affectation réussie ou non
     */
    public boolean addTypeCache(TypeCache typeCache) {
        if(typeCache != null) {
            if(this.typeCache != null)
                return false;
            boolean result = typeCache.addCacheToList(this);
            if (result)
                this.typeCache = typeCache;
            return result;
        }
        return false;
    }

    /**
     * méthode : setReseau
     * ----------------------------
     * sélectionne le réseau propriétaire du cache
     * @param reseauCache le reseau du cache à attribuer
     * @return affectation réussie ou non
     */
    public boolean setReseau(ReseauCache reseauCache) {
        if(reseauCache != null) {
            this.appartient = reseauCache;
            reseauCache.addCache(this);
            return true;
        }
        return false;
    }

    /**
     * méthode : ajouterLog
     * ----------------------------
     * ajoute un log à la liste des logs
     * /!\ Comme le cache n'est pas propriétaire de l'association, on doit faire la manipulation depuis l'instance du
     *      log et cette instance appelera cette méthode
     * @param log le log à ajouter
     * @return affectation réussie ou non
     */
    protected boolean ajouterLog(Log log) {
        if(log != null) {
            this.logs.add(log);
            return true;
        }
        return false;
    }
}

