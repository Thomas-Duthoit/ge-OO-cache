package testsUnitaire;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import modele.Log;

import java.util.Date;

public class TestUnitaireLog {
    public static void main(String[] args) {
        // TESTS DE LA CLASSE "Log"

        // Tests avec contexte de persistance
        try {

            Log l1 = null, l2 = null;


            System.out.println("-> Création du EMF");
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("ge_OO_cache_PU");
            System.out.println("-> EMF Créé !");

            System.out.println("-> Création du EM");
            EntityManager em = emf.createEntityManager();
            System.out.println("-> EM Créé !");

            final EntityTransaction et = em.getTransaction();
            try{
                et.begin();

                System.out.println("-> Persistace des Logs");
                // Création d'utilisateurs
                l1 = new Log("trop cool !", true, 5, new Date(2025, 12, 26));
                em.persist(l1);

                l2 = new Log("pas trouvé ...", false, 1, new Date());
                em.persist(l2);

                et.commit();

                System.out.println("-> Persistace des Logs OK !");

            } catch (Exception ex) {
                System.out.println("exception: " + ex);
                System.out.println("rollback");
                et.rollback();
            }


            System.out.println(l1);
            System.out.println(l2);

            System.out.println("-> Recherche d'un Log avec l'ID 1 : ");

            System.out.println(em.find(Log.class, 1));

            System.out.println("-> Suppression puis recherche du log avec l'ID 1 : ");

            em.remove(em.find(Log.class, 1));
            System.out.println("-> ... supprimé !");
            System.out.println(em.find(Log.class, 1));

        } catch (Exception e) {
            System.out.println("Erreur : " + e);
        }
    }
}
