package testsUnitaire;


import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import modele.Log;
import modele.ReseauCache;
import modele.Utilisateur;

import java.util.Date;

public class TestUnitaireUtilisateurLog {
    public static void main(String[] args) {
        // TESTS DE L'ASSOCIATION "possède" entre Utilisateur et Log

        // Tests avec contexte de persistance
        try {
            Utilisateur u1 = null;
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

                u1 = new Utilisateur("Alice", "1234", true);
                em.persist(u1);

                l1 = new Log("stylé !", true, 5, new Date());
                em.persist(l1);

                l2 = new Log("trop nul ...", false, 1, new Date());
                em.persist(l2);

                et.commit();

            } catch (Exception ex) {
                System.out.println("exception: " + ex);
                System.out.println("rollback");
                et.rollback();
            }


            System.out.println(u1);
            System.out.println(l1);
            System.out.println(l2);

            System.out.println("U1 devient le proprietaire de L1");

            l1.setProprietaire(u1);

            System.out.println(u1);
            System.out.println(l1);
            System.out.println(l2);

            System.out.println("U1 devient le proprietaire de L2");

            l2.setProprietaire(u1);

            System.out.println(u1);
            System.out.println(l1);
            System.out.println(l2);

        } catch (Exception e) {
            System.out.println("Erreur : " + e);
        }
    }
}
