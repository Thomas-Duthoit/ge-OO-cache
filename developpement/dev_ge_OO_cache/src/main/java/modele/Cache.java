package modele;

import jakarta.persistence.*;

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

    @OneToOne(cascade={
            CascadeType.PERSIST,
            CascadeType.REMOVE})
    @JoinColumn(name="ID_Type")
    private TypeCache typeCache; //Type de cache unique : cache traditionnelle, cache jeu de piste (étape), ...

    @OneToOne(cascade={
            CascadeType.PERSIST,
            CascadeType.REMOVE})
    @JoinColumn(name="ID_Statut")
    private StatutCache statutCache; // Etat du cache : activée, en cours, d'activation, fermée, suspendue

    /**
     *               Constructeurs
     */

    // Constructeur par défaut : dans notre cas il ne sert qu'à prendre l'espace mémoire
    public Cache(){
    }

    // Constructeur par données par :
    //        descriptionTextuelle, descriptionTechnique, informationsGeolocalisation, rubriqueLibre, typeCache, statutCache
    public Cache(String descriptionTextuelle, String descriptionTechnique, String informationsGeolocalisation, String rubriqueLibre, TypeCache typeCache, StatutCache statutCache){
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

        if (typeCache != null){
            this.typeCache = typeCache;
        } else {
            this.typeCache = null; //TODO : Mettre un typeCache de type Non détaillé
        }

        if(statutCache != null){
            this.statutCache = statutCache;
        }else {
            this.statutCache = null; //TODO : Mettre un typeCache de type Non finalisé
        }
    }

    /**
     *              toString
     */
    @Override
    public String toString() {
        return "Cache{" +
                "numero=" + numero +
                ", descriptionTextuelle='" + descriptionTextuelle + '\'' +
                ", descriptionTechnique='" + descriptionTechnique + '\'' +
                ", informationsGeolocalisation='" + informationsGeolocalisation + '\'' +
                ", rubriqueLibre='" + rubriqueLibre + '\'' +
                ", typeCache=" + typeCache +
                ", statutCache=" + statutCache +
                '}';
    }

    /**
     *            Méthodes de la classe Cache
     */


    /**
     *  Méthode de modification du statut de cache
     */
    public void setStatutCache(StatutCache statutCache) {
        if(statutCache != null)
            this.statutCache = statutCache;
    }
}

