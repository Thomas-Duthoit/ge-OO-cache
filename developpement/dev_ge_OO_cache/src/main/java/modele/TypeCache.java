package modele;

import jakarta.persistence.*;

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
    @Column(name = "ID_Type")
    private int id; //"clé primaire" avec la contrainte d'unicité pour l'entité

    @Column(
            name = "Texte",
            length = 50
    )
    private String texte; //Texte décrivant le type

    /**
     *              Constructeurs
     */

    //Constructeurs par défaut : dans notre cas il ne sert qu'à prendre l'espace mémoire
    public TypeCache(){

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
     *              toString
     */
    @Override
    public String toString() {
        return "Type{" +
                "id=" + id +
                ", texte='" + texte + '\'' +
                '}';
    }

}
