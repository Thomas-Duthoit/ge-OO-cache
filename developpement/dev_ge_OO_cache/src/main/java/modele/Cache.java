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
     *                  Variables
     */

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "NUMERO")
    private int numero; //"clé primaire" avec la contrainte d'unicité pour l'entité
    //Le numéro est une caractéristique unique demandé par le sujet


    //Variables permettant la description du cache
    @Column(
            name = "descriptionTextuelle",
            length = 100
    )
    private String descriptionTextuelle; // Une description textuelle

    @Column(
            name = "descriptionTechnique",
            length = 100
    )
    private String descriptionTechnique; // Une description plus technique

    @Column(
            name = "informationsGeolocalisation",
            length = 100
    )
    private String informationsGeolocalisation; // regroupent les informations pour la localiser

    @Column(
            name = "rubriqueLibre",
            length = 100
    )
    private String rubriqueLibre; // Une rubrique libre pour détailler le contenu et la forme de la cache

    @ManyToOne(cascade={CascadeType.PERSIST, CascadeType.REMOVE })
    @JoinColumn(
            name = "fk_Cache_Type"
    )
    private TypeCache typeCache; //Type de cache unique : cache traditionnelle, cache jeu de piste (étape), ...

    @ManyToOne(cascade={CascadeType.PERSIST, CascadeType.REMOVE })
    @JoinColumn(
            name = "fk_Cache_Statut"
    )
    private StatutCache statutCache; // Etat du cache : activée, en cours, d'activation, fermée, suspendue

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "fk_ReseauCache_Id")
    private ReseauCache appartient;  // association "appartient" entre Cache et ReseauCache

    @OneToMany(mappedBy = "enregistrer", cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    private List<Log> logs;  // association "enregistrer" entre Cache et Log

    /**
     *               Constructeurs
     */

    // Constructeur par défaut : dans notre cas il ne sert qu'à prendre l'espace mémoire
    public Cache(){
        logs = new ArrayList<>();
    }

    // Constructeur par données par :
    //        descriptionTextuelle, descriptionTechnique, informationsGeolocalisation, rubriqueLibre, typeCache, statutCache
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
     *              toString
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
     *            Méthodes de la classe Cache
     */


    /**
     *  Méthode de modification du statut de cache
     */
    public boolean setStatutCache(StatutCache statutCache) {
        if(statutCache != null) {
            this.statutCache = statutCache;
            return true;
        }
        return false;
    }

    /**
     *  Méthode d'ajout de l'association StatutCache et Cache
     */

    public boolean addStatutCache(StatutCache statutCache) {
        if(statutCache != null) {
            if(this.statutCache != null)
                this.statutCache.getCaches().remove(this);
            this.statutCache = statutCache;
            statutCache.getCaches().add(this);
            return true;
        }
        return false;
    }

    /**
     *  Méthode d'ajout de l'association StatutCache et Cache
     */

    public boolean addTypeCache(TypeCache typeCache) {
        if(typeCache != null) {
            if(this.typeCache != null)
                this.typeCache.getCaches().remove(this);
            this.typeCache = typeCache;
            typeCache.getCaches().add(this);
            return true;
        }
        return false;
    }

    /**
     *  Méthode de selection d'un reseau propriétaire
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
     *  Méthode d'ajout d'un log aux logs enregistrés pour la cache
     */

    protected boolean ajouterLog(Log log) {
        if(log != null) {
            this.logs.add(log);
            return true;
        }
        return false;
    }
}

