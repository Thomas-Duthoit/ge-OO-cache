package modele;

/**
 * classe Type
 * Permet de détailler le type unique du cache : cache traditionnelle, cache jeu de piste, cache objet, ...
 */

//@Entity
//@Table(name = "Type")
public class Type {
    /**
     *              Variables
     */
    private int id; //"clé primaire" avec la contrainte d'unicité pour l'entité

    private String texte; //Texte décrivant le type

    /**
     *              Constructeurs
     */

    //Constructeurs par défaut : dans notre cas il ne sert qu'à prendre l'espace mémoire
    public Type(){

    }

    //Constructeurs par données de texte
    public Type(String texte){
        this();
        if (texte != null){
            this.texte = texte;
        }else {
            this.texte = "Pas de précision sur le texte";
        }
    }

    /**
     *              toString
     */
    @Override
    public String toString() {
        return "Type{" +
                "id=" + id +
                ", texte='" + texte + '\'' +
                '}';
    }

    /**
     *             Zone de tests de la classe
     */
    public static void main(String[] args) {
        Type t = new Type("test de la classe Type");
        System.out.println(t);
    }
}
