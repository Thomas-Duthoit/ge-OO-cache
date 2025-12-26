package testsUnitaire;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import modele.StatutCache;

public class TestUnitaireStatut {
    public static void main(String[] args) {
        final EntityManagerFactory emf = Persistence.createEntityManagerFactory("ge_OO_cache_PU");
        final EntityManager em = emf.createEntityManager();
        try{
            final EntityTransaction et = em.getTransaction();
            try{
                et.begin();
                StatutCache s1 = new StatutCache("activée");
                StatutCache s2 = new StatutCache("en cours d'activation");
                StatutCache s3 = new StatutCache("fermée");
                StatutCache s4 = new StatutCache("suspendue");
                System.out.println(s1);
                System.out.println(s2);
                System.out.println(s3);
                System.out.println(s4);
                em.persist(s1);
                em.persist(s2);
                em.persist(s3);
                em.persist(s4);
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
