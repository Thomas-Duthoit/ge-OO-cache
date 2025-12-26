package testsUnitaire;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import modele.TypeCache;

public class TestUnitaireType {
    public static void main(String[] args) {
        final EntityManagerFactory emf = Persistence.createEntityManagerFactory("ge_OO_cache_PU");
        final EntityManager em = emf.createEntityManager();
        try{
            final EntityTransaction et = em.getTransaction();
            try{
                et.begin();
                TypeCache t1 = new TypeCache("cache traditionnelle");
                TypeCache t2 = new TypeCache("cache jeu de piste (étape)");
                TypeCache t3 = new TypeCache("cache objet");
                TypeCache t4 = new TypeCache("cache objet voyageur");
                System.out.println(t1);
                System.out.println(t2);
                System.out.println(t3);
                System.out.println(t4);
                em.persist(t1);
                em.persist(t2);
                em.persist(t3);
                em.persist(t4);
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
