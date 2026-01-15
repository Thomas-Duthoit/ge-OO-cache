package data;


import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import modele.*;
import requete.*;

import java.util.Date;

/**
 * Classe à lancer (psvm) pour créer un jeu de données pour l'application
 */


public class CreerDataApplication {
    public static void main(String[] args) {
        RequeteGeOOCache req = new RequeteGeOOCache();
        EntityManager em = req.getEm();
        EntityTransaction et = em.getTransaction();
        try {

            et.begin();

            Utilisateur alice = new Utilisateur("Alice", "1234", true);
            Utilisateur bob = new Utilisateur("Bob", "5678", true);
            Utilisateur charlie = new Utilisateur("Charlie", "mdp", true);

            em.persist(alice);
            em.persist(bob);
            em.persist(charlie);

            et.commit();

        } catch (Exception e) {
            System.out.println("ERREUR : " + e);
            et.rollback();
        }
        et = em.getTransaction();

        try {
            et.begin();

            req.creerTypeCache("Traditionnelle");
            req.creerTypeCache("Jeu de piste (étape)");
            req.creerTypeCache("Objet");
            req.creerTypeCache("Objet voyageur");
            et.commit();

        } catch (Exception e) {
            System.out.println("ERREUR : " + e);
            et.rollback();
        }
        et = em.getTransaction();
        try {
            et.begin();

            StatutCache sc1 = new StatutCache("Activée");
            StatutCache sc2 = new StatutCache("En cours d'activation");
            StatutCache sc3 = new StatutCache("Fermée");
            StatutCache sc4 = new StatutCache("suspendue");

            em.persist(sc1);
            em.persist(sc2);
            em.persist(sc3);
            em.persist(sc4);
            et.commit();

        } catch (Exception e) {
            System.out.println("ERREUR : " + e);
            et.rollback();
        }
        et = em.getTransaction();
        try {

            et.begin();

            req.creerReseau("Mon premier réseau", em.find(Utilisateur.class, 1));
            req.creerReseau("Réseau stylé !", em.find(Utilisateur.class, 1));
            req.creerReseau("Mon premier réseau", em.find(Utilisateur.class, 2));
            req.creerReseau("Hello, cache world !", em.find(Utilisateur.class, 3));
            et.commit();

        } catch (Exception e) {
            System.out.println("ERREUR : " + e);
            et.rollback();
        }
        et = em.getTransaction();
        try {

            et.begin();

            // Les données des classes / logs ont été générées par IA

            // Réseau 1
            req.creerCache(
                    "Cache du vieux chêne",
                    "Magnétique, accessible sans escalade",
                    "Regarde du côté nord du tronc",
                    "48.8566,2.3522",
                    em.find(TypeCache.class, 1),
                    em.find(StatutCache.class, 1),
                    em.find(ReseauCache.class, 1)
            );

            req.creerCache(
                    "Étape 1 – Le pont",
                    "Micro cache sous le garde-corps",
                    "Note le chiffre gravé sur la plaque",
                    "48.8570,2.3510",
                    em.find(TypeCache.class, 2),
                    em.find(StatutCache.class, 1),
                    em.find(ReseauCache.class, 1)
            );

            // Réseau 2
            req.creerCache(
                    "Objet voyageur : le canard",
                    "Boîte étanche classique",
                    "Merci de faire voyager l’objet !",
                    "48.8600,2.3500",
                    em.find(TypeCache.class, 4),
                    em.find(StatutCache.class, 1),
                    em.find(ReseauCache.class, 2)
            );

            req.creerCache(
                    "Cache urbaine discrète",
                    "Magnétique, attention aux passants",
                    "Soyez discrets 👀",
                    "48.8585,2.3498",
                    em.find(TypeCache.class, 1),
                    em.find(StatutCache.class, 2),
                    em.find(ReseauCache.class, 2)
            );

            // Réseau 3
            req.creerCache(
                    "Cache abandonnée",
                    "Boîte endommagée",
                    "Cache temporairement fermée",
                    "48.8610,2.3470",
                    em.find(TypeCache.class, 1),
                    em.find(StatutCache.class, 3),
                    em.find(ReseauCache.class, 3)
            );

            req.creerCache(
                    "Objet mystérieux",
                    "Petite boîte plastique",
                    "Contient un objet étrange",
                    "48.8622,2.3465",
                    em.find(TypeCache.class, 3),
                    em.find(StatutCache.class, 1),
                    em.find(ReseauCache.class, 3)
            );

            // Réseau 4
            req.creerCache(
                    "Étape finale – Le belvédère",
                    "Cache camouflée sous les pierres",
                    "Vue magnifique au coucher du soleil",
                    "48.8640,2.3450",
                    em.find(TypeCache.class, 2),
                    em.find(StatutCache.class, 1),
                    em.find(ReseauCache.class, 4)
            );

            req.creerCache(
                    "Cache suspendue",
                    "Accès difficile après intempéries",
                    "Reviendra bientôt",
                    "48.8650,2.3440",
                    em.find(TypeCache.class, 1),
                    em.find(StatutCache.class, 4),
                    em.find(ReseauCache.class, 4)
            );
            et.commit();

        } catch (Exception e) {
            System.out.println("ERREUR : " + e);
            et.rollback();
        }
        et = em.getTransaction();
        try {

            et.begin();

            // Logs cache 1
            Log log1 = new Log("C'était sympa", true, 5, new Date(126, 0, 3));
            em.persist(log1);
            log1.setProprietaire(em.find(Utilisateur.class, 2)); // Bob
            log1.setCache(em.find(Cache.class, 1));

            Log log2 = new Log("Facile à trouver", true, 4, new Date(126, 0, 5));
            em.persist(log2);
            log2.setProprietaire(em.find(Utilisateur.class, 3)); // Charlie
            log2.setCache(em.find(Cache.class, 1));

            // Logs cache 2
            Log log3 = new Log("Bonne étape, bien cachée", true, 5, new Date(126, 0, 10));
            em.persist(log3);
            log3.setProprietaire(em.find(Utilisateur.class, 1)); // Alice
            log3.setCache(em.find(Cache.class, 2));

            Log log4 = new Log("Un peu galéré mais sympa", true, 3, new Date(126, 0, 12));
            em.persist(log4);
            log4.setProprietaire(em.find(Utilisateur.class, 2));
            log4.setCache(em.find(Cache.class, 2));

            // Logs cache 3
            Log log5 = new Log("Objet bien protégé", true, 4, new Date(126, 0, 15));
            em.persist(log5);
            log5.setProprietaire(em.find(Utilisateur.class, 3));
            log5.setCache(em.find(Cache.class, 3));

            // Logs cache 4
            Log log6 = new Log("Trop de monde autour", false, 2, new Date(126, 0, 18));
            em.persist(log6);
            log6.setProprietaire(em.find(Utilisateur.class, 1));
            log6.setCache(em.find(Cache.class, 4));

            // Logs cache 6
            Log log7 = new Log("Objet intriguant 👀", true, 5, new Date(126, 0, 20));
            em.persist(log7);
            log7.setProprietaire(em.find(Utilisateur.class, 2));
            log7.setCache(em.find(Cache.class, 6));

            // Logs cache 8
            Log log8 = new Log("Cache difficile mais récompensante", true, 4, new Date(126, 0, 22));
            em.persist(log8);
            log8.setProprietaire(em.find(Utilisateur.class, 3));
            log8.setCache(em.find(Cache.class, 8));

            et.commit();



        } catch (Exception e) {
            System.out.println("ERREUR : " + e);
            et.rollback();
        }
    }
}
