package modele;

/**
 * classe Statut
 * Permet de détailler l'état unique du cache : activée, en cours d'activation, fermée, suspendue
 */
//@Entity
//@Table(name = "Statut")
public class Statut {
    /**
     *              Variables
     */
    private int id; //"clé primaire" avec la contrainte d'unicité pour l'entité
    private String texte; //Texte décrivant le statut

    /**
     *              Constructeurs
     */

    //Constructeurs par défaut : dans notre cas il ne sert qu'à prendre l'espace mémoire
    public Statut(){

    }

    //Constructeurs par données de texte
    public Statut(String texte){
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
        return "Statut{" +
                "id=" + id +
                ", texte='" + texte + '\'' +
                '}';
    }

    /**
     *             Zone de tests de la classe
     */

    public static void main(String[] args) {
        Statut s = new Statut("test de la classe Statut");
        System.out.println(s);
    }
}
