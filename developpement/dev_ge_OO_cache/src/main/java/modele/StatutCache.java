package modele;

import jakarta.persistence.*;

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
    @Column(name = "ID_Statut")
    private int id; //"clé primaire" avec la contrainte d'unicité pour l'entité

    @Column(
            name = "Texte",
            length = 50
    )
    private String texte; //Texte décrivant le statut

    /**
     *              Constructeurs
     */

    //Constructeurs par défaut : dans notre cas il ne sert qu'à prendre l'espace mémoire
    public StatutCache(){

    }

    //Constructeurs par données de texte
    public StatutCache(String texte){
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
}
