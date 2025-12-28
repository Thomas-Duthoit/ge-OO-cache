package testsUnitaire;

import jakarta.persistence.*;
import modele.*;
import requete.RequeteGeOOCache;

import java.util.List;


public class TestUnitairesRequetesThomas {
    public static void main(String[] args) {
        try {

            Utilisateur u1 = null, u2 = null;

            ReseauCache r1 = null, r2 = null, r3 = null;

            RequeteGeOOCache req = new RequeteGeOOCache();
            EntityManager em = req.getEm();
            EntityTransaction et = em.getTransaction();

            et.begin();

            em.persist(u1 = new Utilisateur("Alice", "1234", true));
            em.persist(u2 = new Utilisateur("Bob", "5678", false));

            em.persist(r1 = new ReseauCache("Mon réseau trop chouette !"));
            em.persist(r2 = new ReseauCache("Réseau stylé !"));
            em.persist(r3 = new ReseauCache("TEST ;)"));

            r1.setProprietaire(u1);
            r2.setProprietaire(u1);
            r3.setProprietaire(u1);

            et.commit();

            em.close();


            int id = req.autoriserConnexionApp("Alice", "1234");
            em = req.getEm();
            u1 = em.find(Utilisateur.class, id);
            em.close();
            System.out.println("Résultat de la demande de connection : '" + id + "'  -  utilisateur : " + u1);

            System.out.println("Réseau de l'utilisateur connecté : ");
            List<ReseauCache> reseaux = req.getReseauxUtilisateur(u1);
            System.out.println(reseaux);

            req.creerReseau("Réseau créé", u1);
            System.out.println("Réseau de l'utilisateur connecté (après ajout) : ");
            reseaux = req.getReseauxUtilisateur(u1);
            System.out.println(reseaux);


            System.out.println("création d'un utilisateur : 'Charlie', '1111'");
            req.creerUtilisateur("Charlie", "1111");

            System.out.println("Liste des utilisateurs : ");
            System.out.println(req.getListeUtilisateurs());

            System.out.println("Réseau de Charlie : ");
            em = req.getEm();
            u2 = em.find(Utilisateur.class, 3);
            em.close();

            System.out.println(u2);
            req.ajouterAccesReseau(r3, u2);
            System.out.println(req.getReseauxAvecAccesUtilisateur(u2));

            // TODO: tester filtrage et recherche de logs

            System.out.println("Statistiques de R1 : ");
            System.out.println("Propriétaire : " + req.getStatProprietaire(r1));

            // TODO: tester l'affichage de stat pour le nombre de caches
            // TODO: tester l'affichage de stat pour le nombre de logs





        } catch (Exception e) {
            System.out.println("Erreur : " + e);
        }
    }
}
