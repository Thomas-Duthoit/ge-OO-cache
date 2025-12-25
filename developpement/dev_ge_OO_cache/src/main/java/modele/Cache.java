package modele;

//import jakarta.persistence.*;

/**
 * Classe Cache :
 * décrit la géocache ou le cache caché à trouver
 */
//@Entity
//@Table(name = "Cache")
public class Cache {
    /**
     *                  Variables
     */

    //@Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    private int numero; //"clé primaire" avec la contrainte d'unicité pour l'entité
    //Le numéro est une caractéristique unique demandé par le sujet

    //@Column(
    //        name = "descriptionTextuelle",
    //        length = 100
    //)
    //Variables permettant la description du cache
    private String descriptionTextuelle; // Une description textuelle
    private String descriptionTechnique; // Une description plus technique
    private String informationsGeolocalisation; // regroupent les informations pour la localiser
    private String rubriqueLibre; // Une rubrique libre pour détailler le contenu et la forme de la cache
    private Type typeCache; //Type de cache unique : cache traditionnelle, cache jeu de piste (étape), ...
    private Statut statutCache; // Etat du cache : activée, en cours, d'activation, fermée, suspendue

    private static int NUMERO_CACHE = 0; //TODO : à retirer quand on passera à la persistance des données
    /**
     *               Constructeurs
     */

    // Constructeur par défaut : dans notre cas il ne sert qu'à prendre l'espace mémoire
    public Cache(){
        //TODO : à retirer quand on passera à la persistance des données
        this.numero = NUMERO_CACHE;
        NUMERO_CACHE++;
    }

    // Constructeur par données par :
    //        descriptionTextuelle, descriptionTechnique, informationsGeolocalisation, rubriqueLibre, typeCache, statutCache
    public Cache(String descriptionTextuelle, String descriptionTechnique, String informationsGeolocalisation, String rubriqueLibre, Type typeCache, Statut statutCache){
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

        if (this.typeCache != null){
            this.typeCache = typeCache;
        } else {
            this.typeCache = null; //TODO : Mettre un typeCache de type Non détaillé
        }

        if(this.statutCache != null){
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
     *             Zone de tests de la classe
     */
    public static void main(String[] args) {
        Type t = new Type("test de la classe Cache avec un Type");
        System.out.println(t);
        Statut s = new Statut("test de la classe Cache avec un Statut");
        System.out.println(s);
        Cache c = new Cache("test Textuelle", "test Technique", "test informations Geo", "test rubrique", t, s);
        System.out.println(c);
        Cache c2 = new Cache("Test", null, null, null, null, null);
        System.out.println(c2);
    }
}

