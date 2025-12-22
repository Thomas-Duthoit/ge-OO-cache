package modele;

import jakarta.persistence.*;

@Entity
@Table(name = "Cache")
public class Cache {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int numero;

    @Column(
            name = "descriptionTextuelle",
            length = 100
    )
    private String descriptionTextuelle;
    private String descriptionTechnique;
    private String rubriqueLibre;
    private Type TypeCache;
    private Statut StatutCache;

}

