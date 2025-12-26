package testsUnitaire;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import modele.Cache;
import modele.StatutCache;
import modele.TypeCache;

public class TestUnitaireCache {
    public static void main(String[] args) {
        final EntityManagerFactory emf = Persistence.createEntityManagerFactory("ge_OO_cache_PU");
        final EntityManager em = emf.createEntityManager();
        try{
            final EntityTransaction et = em.getTransaction();
            try{
                et.begin();
                TypeCache t1 = new TypeCache("cache traditionnelle");
                TypeCache t2 = new TypeCache("cache caché");
                StatutCache s1 = new StatutCache("activée");
                StatutCache s2 = new StatutCache("en cours d'activation");

                em.persist(t1);
                em.persist(t2);
                em.persist(s1);
                em.persist(s2);

                Cache c1 = new Cache("cache caché", "boite blanche", "derrière vous", "oui oui");
                Cache c2 = new Cache("test", "test", "test", "test");
                Cache c3 = new Cache("essai", "essai", "essai", "essai");
                c1.addTypeCache(t1);
                c1.addStatutCache(s1);

                c2.addTypeCache(t1);
                c2.addStatutCache(s2);

                c3.addStatutCache(s1);
                c3.addTypeCache(t1);
                System.out.println(c3);
                c3.addTypeCache(t2);
                System.out.println(c3);
                c3.addStatutCache(s2);
                System.out.println(c3);
                em.persist(c1);
                em.persist(c2);
                em.persist(c3);
                et.commit();


            } catch (Exception ex) {
                System.out.println("exception: " + ex);
                System.out.println("rollback");
                et.rollback();
            } finally {
                if(em != null && em.isOpen()){
                    em.close();
                }
                if(emf != null && emf.isOpen()){
                    emf.close();
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
