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
                StatutCache s1 = new StatutCache("activée");
                Cache c1 = new Cache("cache caché", "boite blanche", "derrière vous", "oui oui", t1, s1);
                System.out.println(t1);
                System.out.println(s1);
                System.out.println(c1);
                em.persist(t1);
                em.persist(s1);
                em.persist(c1);
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
