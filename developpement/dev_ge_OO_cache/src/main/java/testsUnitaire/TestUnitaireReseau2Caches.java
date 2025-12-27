package testsUnitaire;

import jakarta.persistence.*;
import modele.ReseauCache;
import modele.Cache;

import java.util.Date;

public class TestUnitaireReseau2Caches {
    public static void main(String[] args) {
        // TESTS DE L'ASSOCIATION "possède" entre Utilisateur et Log

        // Tests avec contexte de persistance
        try {
            Cache c1 = null;
            Cache c2 = null;
            ReseauCache rc1 = null;

            System.out.println("-> Création du EMF");
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("ge_OO_cache_PU");
            System.out.println("-> EMF Créé !");

            System.out.println("-> Création du EM");
            EntityManager em = emf.createEntityManager();
            System.out.println("-> EM Créé !");

            final EntityTransaction et = em.getTransaction();
            try{
                et.begin();

                rc1 = new ReseauCache("test");
                em.persist(rc1);

                c1 = new Cache("test", "test", "test", "test");
                em.persist(c1);

                c2 = new Cache("esssai", "boite boite", "Nord est 3 pas", "test");
                em.persist(c2);


                System.out.println(rc1);
                System.out.println(c1);
                System.out.println(c2);

                System.out.println("c1 devient un cache de rc1");

                c1.setReseau(rc1);

                System.out.println(rc1);
                System.out.println(c1);
                System.out.println(c2);

                System.out.println("c2 devient un cache de rc1");

                c2.setReseau(rc1);

                System.out.println(rc1);
                System.out.println(c1);
                System.out.println(c2);

                em.persist(rc1);
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
