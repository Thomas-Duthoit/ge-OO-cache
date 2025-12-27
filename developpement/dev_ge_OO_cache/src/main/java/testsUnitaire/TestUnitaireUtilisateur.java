package testsUnitaire;


import jakarta.persistence.*;
import modele.*;

public class TestUnitaireUtilisateur {
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
