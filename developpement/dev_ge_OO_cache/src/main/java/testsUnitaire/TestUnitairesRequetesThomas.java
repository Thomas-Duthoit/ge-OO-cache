package testsUnitaire;

import jakarta.persistence.*;
import modele.*;
import requete.RequeteGeOOCache;


public class TestUnitairesRequetesThomas {
    public static void main(String[] args) {
        try {

            Utilisateur u1 = null, u2 = null;

            RequeteGeOOCache req = new RequeteGeOOCache();
            EntityManager em = req.getEm();
            EntityTransaction et = em.getTransaction();

            et.begin();

            em.persist(new Utilisateur("Alice", "1234", true));
            em.persist(new Utilisateur("Bob", "5678", false));

            et.commit();

            em.close();


            System.out.println(u1);
            System.out.println(u2);


            int id = req.allowConnectionToApp("Alice", "1234");
            em = req.getEm();
            u1 = em.find(Utilisateur.class, id);
            em.close();
            System.out.println("Résultat de la demande de connection : '" + id + "'  -  utilisateur : " + u1);






        } catch (Exception e) {
            System.out.println("Erreur : " + e);
        }
    }
}
