package requete;

import jakarta.persistence.*;
import modele.*;

import java.util.List;

/**
 * Classe RequeteGeOOCache
 * responsable de toutes les requêtes et de la persistance
 */
public class RequeteGeOOCache {


    /**
     * Entity Manager Factory, gère les contextes de persistance
     */
    private EntityManagerFactory emFactory;

    /**
     * Constructeur par défaut
     */
    public RequeteGeOOCache() {
        // On initialise l'EMF pour utliser notre persistance unit
        emFactory = Persistence.createEntityManagerFactory("ge_OO_cache_PU");
    }

    /**
     *              METHODES RequeteGeOOCache
     */

    /**
     * méthode: getEm
     * --------------
     * Utilisée pour récupérer une instance d'EntityManager pour pouvoir ajouter des entités au contexte de persistance
     * directement à des fins de débug
     * /!\ attention, à ne plus utiliser une fois l'application finale développée, à utiliser uniquement dans un but de
     *     débug pour faire du em.persist() !
     *
     * @return une instance d'EntityManager liée au contexte de persistance de l'EMF
     */
    public EntityManager getEm() {
        return emFactory.createEntityManager();
    }

    /**
     *              METHODES RequeteGeOOCache
     *              (Pour la classe Utilisateur)
     */

    /**
     * méthode: autoriserConnexionApp
     * ------------------------------
     * à utiliser pour tenter une connexion pour utiliser l'application
     * si "-1" est renvoyée, la connexion est refusée
     *
     * @param pseudo le pseudo de l'utilisateur à connecter
     * @param mdp le mot de passe de l'utilisateur à connecter
     * @return -1 : connexion refusée, sinon l'id de l'utilisateur à connecter
     */
    public int autoriserConnexionApp(String pseudo, String mdp) {
        EntityManager em = emFactory.createEntityManager();
        String strQuery = "SELECT u FROM Utilisateur u WHERE u.pseudo = :pseudo AND u.mdp = :mdp";
        Query query = em.createQuery(strQuery);
        query.setParameter("pseudo", pseudo);
        query.setParameter("mdp", mdp);
        List<Utilisateur> res = query.getResultList();
        em.close();

        if (res.isEmpty()) {
            return -1;  // pas de résultat -> on est pas connecté
        } else if (res.getFirst().isAdmin()) {
            return res.getFirst().getId();  // on a eu un résultat -> on peut autoriser la connection et pouvoir retrouver l'utilisateur avec un find
        } else {
            return -1;  // pas admin -> on esr pas connecté
        }
    }

    /**
     * méthode: creerUtilisateur
     * -------------------------
     * Crée un nouvel utilisateur
     *
     * @param pseudo pseudo de l'utilisateur à créer
     * @param mdp mot de posse de l'utilisateur à créer
     * @return ajout effectué
     */
    public boolean creerUtilisateur(String pseudo, String mdp) {
        EntityManager em = emFactory.createEntityManager();
        EntityTransaction et = em.getTransaction();
        try {
            et.begin();

            Utilisateur utilisateur = new Utilisateur(pseudo, mdp, false);
            em.persist(utilisateur);

            et.commit();  // application des MàJ

        } catch (Exception e) {
            et.rollback();
            System.out.println("ERREUR creerUtilisateur : " + e);
            return false;
        } finally {
            em.close();
        }

        return true;  // on est arrivé là sans retourner false -> création effectuée
    }

    /**
     * méthode: getListeUtilisateurs
     * -------------------------
     *
     * @return la liste des utilisateurs de l'application sauvegardés en BDD
     */
    public List<Utilisateur> getListeUtilisateurs() {
        EntityManager em = emFactory.createEntityManager();
        String strQuery = "SELECT u FROM Utilisateur u";
        Query query = em.createQuery(strQuery);
        List<Utilisateur> res = query.getResultList();
        em.close();
        return res;
    }


    /**
     *              METHODES RequeteGeOOCache
     *              (Pour la classe ReseauCache)
     */

    /**
     * méthode: getReseauxUtilisateur
     * ------------------------------
     * Récupère la liste des réseaux qui appartiennent à l'utilisateur
     *
     * @param utilisateur l'utilisateur propriétaire des réseaux
     * @return la liste des réseaux qui lui appartiennent
     */
    public List<ReseauCache> getReseauxUtilisateur(Utilisateur utilisateur) {
        EntityManager em = emFactory.createEntityManager();
        String strQuery = "SELECT r FROM ReseauCache r WHERE r.proprietaire = :utilisateur";
        Query query = em.createQuery(strQuery);
        query.setParameter("utilisateur", utilisateur);
        List<ReseauCache> res = query.getResultList();
        em.close();
        return res;
    }

    /**
     * méthode: creerReseau
     * --------------------
     * Crée un réseau avec un nom et un propriétaire donné
     * @param nom
     * @param proprietaire le propriétaire du réseau à créer
     * @return ajout correctement effectué
     */
    public boolean creerReseau(String nom, Utilisateur proprietaire) {
        EntityManager em = emFactory.createEntityManager();
        EntityTransaction et = em.getTransaction();
        try {
            et.begin();

            proprietaire = em.merge(proprietaire);  // on réattache l'utilisateur à l'EM pour éviter les erreurs de LAZY init

            ReseauCache reseauCache = new ReseauCache(nom);
            em.persist(reseauCache);

            if (!reseauCache.setProprietaire(proprietaire)) {
                // echec du setProprietaire
                et.rollback();
                return false;
            }

            et.commit();  // application des MàJ

        } catch (Exception e) {
            et.rollback();
            System.out.println("ERREUR creerReseau : " + e);
            return false;
        } finally {
            em.close();
        }

        return true;  // on est arrivé là sans retourner false -> création effectuée
    }

    /**
     * méthode: ajouterAccesReseau
     * ---------------------------
     * Permet à un utilisateur d'obtenir l'accès au réseau
     *
     * @param reseau le réseau concerné
     * @param utilisateur l'utilisateur qui pourra accéder au réseau
     * @return association effectuée
     */
    public boolean ajouterAccesReseau(ReseauCache reseau, Utilisateur utilisateur) {
        EntityManager em = emFactory.createEntityManager();
        EntityTransaction et = em.getTransaction();
        try {
            et.begin();

            utilisateur = em.merge(utilisateur);  // on réattache l'utilisateur à l'EM pour éviter les erreurs de LAZY init
            reseau = em.merge(reseau);  // on réattache le réseau à l'EM pour éviter les erreurs de LAZY init

            if (!reseau.ajouterAccesUtilisateur(utilisateur)) {
                // echec du ajouterAccesUtilisateur
                et.rollback();
                return false;
            }

            et.commit();  // application des MàJ

        } catch (Exception e) {
            et.rollback();
            System.out.println("ERREUR ajouterAccesReseau : " + e);
            return false;
        } finally {
            em.close();
        }

        return true;  // on est arrivé là sans retourner false -> association effectuée
    }

    /**
     * méthode: getReseauxAvecAccesUtilisateur
     * ---------------------------------------
     * Récupère la liste des réseaux qui dont l'utilisateur a accès
     *
     * @param utilisateur l'utilisateur qui accède aux réseaux
     * @return la liste des réseaux auxquels il accède
     */
    public List<ReseauCache> getReseauxAvecAccesUtilisateur(Utilisateur utilisateur) {
        EntityManager em = emFactory.createEntityManager();

        utilisateur = em.merge(utilisateur);

        String strQuery = "SELECT r FROM ReseauCache r JOIN r.utilisateurs u WHERE u = :utilisateur";

        Query query = em.createQuery(strQuery);
        query.setParameter("utilisateur", utilisateur);
        List<ReseauCache> res = query.getResultList();
        em.close();
        return res;
    }



    /**
     *              METHODES RequeteGeOOCache
     *              (Pour la classe Log)
     */



    /**
     *              METHODES RequeteGeOOCache
     *              (Pour la classe Cache)
     */



    /**
     *              METHODES RequeteGeOOCache
     *              (Pour la classe StatutCache)
     */



    /**
     *              METHODES RequeteGeOOCache
     *              (Pour la classe TypeCache)
     */
}
