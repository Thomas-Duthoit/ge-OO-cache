package modele;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;


// TODO: Rajouter une longueur max pour les champs "string"
// TODO: utiliser les noms de colonnes du MDP



@Entity(name = "Utilisateur")
@Table(
        name = "UTILISATEUR"
)
public class Utilisateur {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", unique = true)
    private int id;  // "clé primaire" avec la contrainte d'unicité pour l'entité

    @Column(name = "PSEUDO", nullable = false)  // non nulle
    private String pseudo;  // Pseudo d'un utilisateur
    @Column(name = "MDP", nullable = false)  // non nulle
    private String mdp;  // mot de passe de l'utilisateur
    private boolean admin;  // utilisateur administrateur ?



    // Associations:
    @ManyToMany(mappedBy = "utilisateurs", cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    private List<ReseauCache> accede;

    public List<ReseauCache> getAccede() {
        return accede;
    }


    public Utilisateur() {
        this.pseudo = "";
        this.mdp = "";
        this.accede = new ArrayList<>();
    }

    public Utilisateur(String pseudo, String mdp, boolean admin) {
        this();
        if (pseudo != null) {
            this.pseudo = pseudo;
        }
        if (mdp != null) {
            this.mdp = mdp;
        }
        this.admin = admin;
    }

    @Override
    public String toString() {
        return "Utilisateur{" +
                "id=" + id +
                ", pseudo='" + pseudo + '\'' +
                ", mdp='" + mdp + '\'' +
                ", admin=" + admin +
                ", réseaux accessibles : " + accede.size()  +
                '}';
    }



    // méthodes pour les associations:
    protected void ajouterAccesReseau(ReseauCache r) {
        if (r != null) {
            this.accede.add(r);
        }
    }



    public static void main(String[] args) {
        // TESTS DE LA CLASSE "Utilisateur"

        // Tests avec contexte de persistance
        try {

            Utilisateur u1 = null, u2 = null;


            System.out.println("-> Création du EMF");
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("ge_OO_cache_PU");
            System.out.println("-> EMF Créé !");

            System.out.println("-> Création du EM");
            EntityManager em = emf.createEntityManager();
            System.out.println("-> EM Créé !");

            final EntityTransaction et = em.getTransaction();
            try{
                et.begin();

                System.out.println("-> Persistace des Utilisateurs");
                // Création d'utilisateurs
                u1 = new Utilisateur("Alice", "1234", true);
                em.persist(u1);

                u2 = new Utilisateur("Bob", "5678", false);
                em.persist(u2);

                et.commit();

                System.out.println("-> Persistace des Utilisateurs OK !");

            } catch (Exception ex) {
                System.out.println("exception: " + ex);
                System.out.println("rollback");
                et.rollback();
            }


            System.out.println(u1);
            System.out.println(u2);

            System.out.println("-> Recherche d'un utilisateur avec l'ID 1 : ");

            System.out.println(em.find(Utilisateur.class, 1));

            System.out.println("-> Suppression puis recherche de l'utilisateur avec l'ID 1 : ");

            em.remove(em.find(Utilisateur.class, 1));
            System.out.println("-> ... supprimé !");
            System.out.println(em.find(Utilisateur.class, 1));

        } catch (Exception e) {
            System.out.println("Erreur : " + e);
        }
    }
}
