package testsUnitaire;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import modele.ReseauCache;

public class TestUnitaireReseauCache {
    public static void main(String[] args) {
        // TESTS DE LA CLASSE "ReseauCache"

        // Tests avec contexte de persistance
        try {

            ReseauCache r1 = null, r2 = null;


            System.out.println("-> Création du EMF");
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("ge_OO_cache_PU");
            System.out.println("-> EMF Créé !");

            System.out.println("-> Création du EM");
            EntityManager em = emf.createEntityManager();
            System.out.println("-> EM Créé !");

            final EntityTransaction et = em.getTransaction();
            try{
                et.begin();

                System.out.println("-> Persistace des ReseauCaches");
                // Création d'utilisateurs
                r1 = new ReseauCache("Réseau de cache trop stylé");
                em.persist(r1);

                r2 = new ReseauCache("Réseau niveau expert");
                em.persist(r2);

                et.commit();

                System.out.println("-> Persistace des ReseauCaches OK !");

            } catch (Exception ex) {
                System.out.println("exception: " + ex);
                System.out.println("rollback");
                et.rollback();
            }


            System.out.println(r1);
            System.out.println(r2);

            System.out.println("-> Recherche d'un ReseauCache avec l'ID 1 : ");

            System.out.println(em.find(ReseauCache.class, 1));

            System.out.println("-> Suppression puis recherche du ReseauCache avec l'ID 1 : ");

            em.remove(em.find(ReseauCache.class, 1));
            System.out.println("-> ... supprimé !");
            System.out.println(em.find(ReseauCache.class, 1));

        } catch (Exception e) {
            System.out.println("Erreur : " + e);
        }
    }
}
