package testsUnitaire;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import modele.Cache;
import modele.Log;

import java.util.Date;

public class TestUnitaireCacheLog {
    public static void main(String[] args) {
        // TESTS DE L'ASSOCIATION "possède" entre Utilisateur et Log

        // Tests avec contexte de persistance
        try {
            Cache c1 = null;
            Cache c2 = null;
            Log l1 = null;

            System.out.println("-> Création du EMF");
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("ge_OO_cache_PU");
            System.out.println("-> EMF Créé !");

            System.out.println("-> Création du EM");
            EntityManager em = emf.createEntityManager();
            System.out.println("-> EM Créé !");

            final EntityTransaction et = em.getTransaction();
            try{
                et.begin();

                l1 = new Log("test",true , 3, new Date());
                em.persist(l1);

                c1 = new Cache("test", "test", "test", "test");
                em.persist(c1);

                c2 = new Cache("esssai", "boite boite", "Nord est 3 pas", "test");
                em.persist(c2);


                System.out.println(l1);
                System.out.println(c1);
                System.out.println(c2);

                System.out.println("l1 devient un log de c1");

                l1.setCache(c1);

                System.out.println(c1.getLogs());
                System.out.println(c2.getLogs());

                em.persist(l1);
                em.persist(c1);
                em.persist(c2);

                et.commit();

            } catch (Exception ex) {
                System.out.println("exception: " + ex);
                System.out.println("rollback");
                et.rollback();
            }

        } catch (Exception e) {
            System.out.println("Erreur : " + e);
        }
    }
}
