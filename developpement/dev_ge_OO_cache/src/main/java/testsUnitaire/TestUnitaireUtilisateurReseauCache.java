package testsUnitaire;


import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import modele.*;

public class TestUnitaireUtilisateurReseauCache {
    public static void main(String[] args) {
        // TESTS DE L'ASSOCIATION "accède" entre Utilisateur et ReseauCache

        // Tests avec contexte de persistance
        try {

            ReseauCache r1 = null, r2 = null;
            Utilisateur u1 = null;


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

                r1 = new ReseauCache("Réseau de cache trop stylé");
                em.persist(r1);

                r2 = new ReseauCache("Réseau niveau expert");
                em.persist(r2);

                et.commit();

            } catch (Exception ex) {
                System.out.println("exception: " + ex);
                System.out.println("rollback");
                et.rollback();
            }


            System.out.println(u1);
            System.out.println(r1);
            System.out.println(r2);

            System.out.println("ajout de l'accès à R1 pour U1");

            r1.ajouterAccesUtilisateur(u1);

            System.out.println(u1);
            System.out.println(r1);
            System.out.println(r2);

        } catch (Exception e) {
            System.out.println("Erreur : " + e);
        }
    }
}
