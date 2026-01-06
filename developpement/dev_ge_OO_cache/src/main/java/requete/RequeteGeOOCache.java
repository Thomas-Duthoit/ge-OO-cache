package requete;

import jakarta.persistence.*;
import modele.*;
import modele.Cache;

import java.lang.reflect.Type;
import java.util.ArrayList;
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
            return -1;  // pas admin -> on est pas connecté
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
     *
     *
     */
    public List<Utilisateur> getListeUtilisateursFromReseau(ReseauCache reseauCache){
        EntityManager em = emFactory.createEntityManager();
        String strQuery = "SELECT u FROM Utilisateur u where :reseauCache MEMBER OF u.accede";
        Query query = em.createQuery(strQuery);
        query.setParameter("reseauCache", reseauCache);
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
     * méthode: getReseauAvecProprietaireEtNom
     * ---------------------------------------
     * Récupère le reseau dont l'utilisateur est le propriétaire et le nom correspond
     *
     * @param utilisateur le propriétaire du réseau
     * @param nom le nom du réseau
     * @return le reseauCache recherché
     */
    public ReseauCache getReseauAvecProprietaireEtNom(Utilisateur utilisateur, String nom) {
        EntityManager em = emFactory.createEntityManager();

        String strQuery = "SELECT r FROM ReseauCache r JOIN r.proprietaire u WHERE u = :utilisateur and r.nom = :nom";

        Query query = em.createQuery(strQuery);
        query.setParameter("utilisateur", utilisateur);
        query.setParameter("nom", nom);
        ReseauCache res = (ReseauCache) query.getSingleResult();
        em.close();
        return res;
    }

    /**
     * méthode: checkAssociationReseauUtilisateurExist
     * ---------------------------------------
     * vérifie que l'association reseau_utilisateur existe ou non
     *
     * @param utilisateur l'utilisateur cible de l'association
     * @param reseauCache le reseau cible de l'association
     * @return boolean : l'association existe ou non
     */
    public boolean checkAssociationReseauUtilisateurExist(Utilisateur utilisateur, ReseauCache reseauCache) {
        EntityManager em = emFactory.createEntityManager();

        String strQuery = "SELECT r FROM ReseauCache r JOIN r.utilisateurs u WHERE u = :utilisateur and r = :reseauCache";

        Query query = em.createQuery(strQuery);
        query.setParameter("utilisateur", utilisateur);
        query.setParameter("reseauCache", reseauCache );
        List<ReseauCache> res =  query.getResultList();
        em.close();
        if(res.isEmpty()){
            return false;
        }else{
            return true;
        }
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

    public boolean deleteAccessReseau(Utilisateur utilisateur, ReseauCache reseauCache) {
        EntityManager em = emFactory.createEntityManager();
        EntityTransaction et = em.getTransaction();
        try {
            et.begin();

            utilisateur = em.merge(utilisateur);  // on réattache l'utilisateur à l'EM pour éviter les erreurs de LAZY init
            reseauCache = em.merge(reseauCache);  // on réattache le réseau à l'EM pour éviter les erreurs de LAZY init

            if(!(reseauCache.supprAccesUtilisateur(utilisateur))){
                et.rollback();
                return false;
            };

            et.commit();  // application des MàJ

        } catch (Exception e) {
            et.rollback();
            System.out.println("ERREUR ajouterAccesReseau : " + e);
            return false;
        } finally {
            em.close();
        }



        return true;
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

        String strQuery = "SELECT r FROM ReseauCache r JOIN r.utilisateurs u WHERE u = :utilisateur";

        Query query = em.createQuery(strQuery);
        query.setParameter("utilisateur", utilisateur);
        List<ReseauCache> res = query.getResultList();
        em.close();
        return res;
    }


    /**
     * méthode: getStatProprietaire
     * ----------------------------
     * Renvoie l'utilisateur propriétaire d'un réseau pour l'affichage des statistiques
     *
     * @param reseauCache le réseau dont on veut le propriétaire
     * @return l'utilisateur propriétaire du réseau
     */
    public Utilisateur getStatProprietaire(ReseauCache reseauCache) {
        EntityManager em = emFactory.createEntityManager();

        String strQuery = "SELECT u FROM Utilisateur u JOIN u.possede r WHERE r = :reseau";

        Query query = em.createQuery(strQuery);
        query.setParameter("reseau", reseauCache);
        List<Utilisateur> res = query.getResultList();
        em.close();
        return res.getFirst();
    }

    /**
     * méthode: getStatNbCaches
     * ----------------------------
     * Renvoie le nombre de caches d'un réseau pour l'affichage des statistiques
     *
     * @param reseauCache le réseau dont on veut le nombre de caches
     * @return le nombre de caches du réseau
     */
    public Long getStatNbCaches(ReseauCache reseauCache) {
        EntityManager em = emFactory.createEntityManager();

        String strQuery = "SELECT COUNT(c) FROM Cache c JOIN c.appartient r WHERE r = :reseau";

        Query query = em.createQuery(strQuery);
        query.setParameter("reseau", reseauCache);
        List<Long> res = query.getResultList();
        em.close();
        return res.getFirst();
    }

    /**
     * méthode: getStatNbCaches
     * ----------------------------
     * Renvoie le nombre d'utilisateurs qui accèdent au réseau pour l'affichage des statistiques
     *
     * @param reseauCache le réseau dont on veut le nombre d'utilisateurs
     * @return le nombre de caches du réseau
     */
    public Long getStatNbUtilisateurs(ReseauCache reseauCache) {
        EntityManager em = emFactory.createEntityManager();

        String strQuery = "SELECT COUNT(u) FROM Utilisateur u JOIN u.accede r WHERE r = :reseau";

        Query query = em.createQuery(strQuery);
        query.setParameter("reseau", reseauCache);
        List<Long> res = query.getResultList();
        em.close();
        return res.getFirst();
    }

    /**
     * méthode: getStatNbLogs
     * ----------------------------
     * Renvoie le nombre de logs d'un réseau pour l'affichage des statistiques
     *
     * @param reseauCache le réseau dont on veut le nombre de logs
     * @return le nombre de logs du réseau
     */
    public Long getStatNbLogs(ReseauCache reseauCache) {
        EntityManager em = emFactory.createEntityManager();

        String strQuery = "SELECT COUNT(l) FROM Log l JOIN l.enregistrer c JOIN c.appartient r WHERE r = :reseau";

        Query query = em.createQuery(strQuery);
        query.setParameter("reseau", reseauCache);
        List<Long> res = query.getResultList();
        em.close();
        return res.getFirst();
    }

    /**
     * méthode: getStatNbTrouve
     * ----------------------------
     * Renvoie le nombre de logs avec une cache trouvée d'un réseau pour l'affichage des statistiques
     *
     * @param reseauCache le réseau dont on veut le nombre de logs avec une cache trouvée
     * @return le nombre de logs avec une cache trouvée du réseau
     */
    public Long getStatNbTrouve(ReseauCache reseauCache) {
        EntityManager em = emFactory.createEntityManager();

        String strQuery = "SELECT COUNT(l) FROM Log l JOIN l.enregistrer c JOIN c.appartient r WHERE r = :reseau AND l.trouver = TRUE";

        Query query = em.createQuery(strQuery);
        query.setParameter("reseau", reseauCache);
        List<Long> res = query.getResultList();
        em.close();
        return res.getFirst();
    }

    /**
     * méthode: getStatNbPasTrouve
     * ----------------------------
     * Renvoie le nombre de logs avec une cache non trouvée d'un réseau pour l'affichage des statistiques
     *
     * @param reseauCache le réseau dont on veut le nombre de logs avec une cache non trouvée
     * @return le nombre de logs avec une cache non trouvée du réseau
     */
    public Long getStatNbPasTrouve(ReseauCache reseauCache) {
        EntityManager em = emFactory.createEntityManager();

        String strQuery = "SELECT COUNT(l) FROM Log l JOIN l.enregistrer c JOIN c.appartient r WHERE r = :reseau AND l.trouver = FALSE";

        Query query = em.createQuery(strQuery);
        query.setParameter("reseau", reseauCache);
        List<Long> res = query.getResultList();
        em.close();
        return res.getFirst();
    }

    /**
     * méthode: getStatPourcentageTrouve
     * ----------------------------
     * Renvoie le %age de logs dont la cache a été trouvée par rapport au nombre de logs total
     *
     * @param reseauCache le réseau dont on veut le %age de trouvaille
     * @return le %age de trouvaille du réseau
     */
    public float getStatPourcentageTrouve(ReseauCache reseauCache) {
        return (float) getStatNbTrouve(reseauCache) / getStatNbLogs(reseauCache);
    }




    /**
     *              METHODES RequeteGeOOCache
     *              (Pour la classe Log)
     */

    /**
     * méthode: getLogs
     * ----------------
     * Récupère la liste des logs dont la cache concernée appartient à l'utilisateur propCache
     *
     * @param propCache propriétaire des caches concernées par les logs
     * @param filtreReseau (possiblement null) filtre de réseau de cache pour les logs
     * @param filtreCache (possiblement null) filtre de cache pour les logs
     * @return la liste des logs dont la cache appartient à l'utilisateur, et qui correspondent au filtre
     */
    public List<Log> getLogs(Utilisateur propCache, ReseauCache filtreReseau, Cache filtreCache) {
        EntityManager em = emFactory.createEntityManager();

        if(propCache != null){
            propCache = em.merge(propCache);
        }
        if(filtreReseau != null) {
            filtreReseau = em.merge(filtreReseau);
        }
        if(filtreCache != null) {
            filtreCache = em.merge(filtreCache);
        }

        String strQuery;
        Query query;

        if (filtreCache != null) {
            strQuery = "SELECT l FROM Log l JOIN l.enregistrer c WHERE c = :filtreCache";
            query = em.createQuery(strQuery);
            query.setParameter("filtreCache", filtreCache);

        } else if (filtreReseau != null) {
            strQuery = "SELECT l FROM Log l JOIN l.enregistrer c JOIN c.appartient r WHERE r = :filtreReseau";
            query = em.createQuery(strQuery);
            query.setParameter("filtreReseau", filtreReseau);
        } else {
            strQuery = "SELECT l FROM Log l JOIN l.enregistrer c JOIN c.appartient r JOIN r.proprietaire u WHERE u = :propCache";
            query = em.createQuery(strQuery);
            query.setParameter("propCache", propCache);
        }

        List<Log> res = query.getResultList();
        em.close();
        return res;
    }

    /**
     *              METHODES RequeteGeOOCache
     *              (Pour la classe Cache)
     */

    /**
     * méthode : getCachesByReseauCacheId
     * ----------------------------
     * récupère la liste des différents caches selon le réseau de cache
     * @param reseau le réseau de cache
     * @return liste des Caches
     */
    public List<Cache> getCachesByReseauCache(ReseauCache reseau){
        List<Cache> caches = new ArrayList<Cache>();
        final EntityManager em = this.getEm();
        String strQuery = "Select c from Cache c where c.appartient = :reseau";
        Query query = em.createQuery(strQuery);
        query.setParameter("reseau", reseau);
        caches = query.getResultList();
        return caches;
    }

    /**
     * méthode : updateStatutCache
     * ----------------------------
     * modifie le statut du cache
     * @param cache la cache
     * @param statutCache le statut cache à changer
     * @return boolean indiquant la réussite de l'update
     */
    public boolean updateStatutCache(Cache cache, StatutCache statutCache) {
        final EntityManager em = this.getEm();

        cache = em.merge(cache);
        statutCache = em.merge(statutCache);

        final EntityTransaction et = em.getTransaction();
        try {
            et.begin();
            cache.setStatutCache(statutCache);
            et.commit();
        } catch (Exception ex) {
            et.rollback();
            System.out.println("ERREUR updateStatutCache : " + ex);
            return false;
        }
        em.close();
        return true;

    }
    /**
     * méthode : getDataCachesById
     * ----------------------------
     * récupère les informations sur un cache selon le numéro/identifiant indiqué
     * @param numero l'identifiant du réseau de cache
     * @return liste des Caches
     */
    public Cache getDataCachesById(int numero){
        final EntityManager em = this.getEm();
        String strQuery = "Select c from Cache c where c.numero = :numero";
        Query query = em.createQuery(strQuery);
        query.setParameter("numero", numero);
        return (Cache) query.getSingleResult();
    }

    /**
     * méthode : creerCache
     * ----------------------------
     * crée une cache
     *
     * @param text description textuelle
     * @param tech description technique
     * @param libre rubrique libre
     * @param loc localisation GPS
     * @param type type de la cache
     * @param statut statut de la cache
     * @param reseau réseau de la cache
     * @return création effectuée
     */
    public boolean creerCache(String text, String tech, String libre, String loc, TypeCache type, StatutCache statut, ReseauCache reseau) {
        EntityManager em = emFactory.createEntityManager();
        EntityTransaction et = em.getTransaction();
        try {
            et.begin();

            type = em.merge(type);  // on réattache à l'EM pour éviter les erreurs de LAZY init
            statut = em.merge(statut);  // on réattache à l'EM pour éviter les erreurs de LAZY init
            reseau = em.merge(reseau);  // on réattache à l'EM pour éviter les erreurs de LAZY init

            Cache cache = new Cache(text, tech, loc, libre);

            em.persist(cache);

            if (!cache.addTypeCache(type)) {
                // echec du addTypeCache
                et.rollback();
                return false;
            }
            if (!cache.setStatutCache(statut)) {
                // echec du setStatutCache
                et.rollback();
                return false;
            }
            if (!cache.setReseau(reseau)) {
                // echec du setReseau
                et.rollback();
                return false;
            }

            et.commit();  // application des MàJ

        } catch (Exception e) {
            et.rollback();
            System.out.println("ERREUR creerCache : " + e);
            return false;
        } finally {
            em.close();
        }

        return true;  // on est arrivé là sans retourner false -> création effectuée
    }


/**
 *              METHODES RequeteGeOOCache
 *              (Pour la classe StatutCache)
 */

/**
 * méthode : getStatutCache
 * ----------------------------
 * donne la liste des statuts possibles pour un cache
 * @return la liste des statuts
 */
public List<StatutCache> getStatutCache(){
    List<StatutCache> statuts = new ArrayList<StatutCache>();
    final EntityManager em = this.getEm();
    String strQuery = "Select s from StatutCache s";
    Query query = em.createQuery(strQuery);
    statuts = query.getResultList();
    return statuts;
}

/**
 * méthode : getStatutCacheById
 * ----------------------------
 * donne le statut cache correspondant à l'Id donné
 * @param id identifiant du StatutCache cherché
 * @return le statut cache correspondant
 */
public StatutCache getStatutCacheById(int id){
    final EntityManager em = this.getEm();

    String strQuery = "Select s from StatutCache s where s.id = :id";
    Query query = em.createQuery(strQuery);
    query.setParameter("id", id);
    StatutCache statutCache = (StatutCache) query.getSingleResult();
    return statutCache;
}

/**
 *              METHODES RequeteGeOOCache
 *              (Pour la classe TypeCache)
 */

/**
 * méthode : getTypeCache
 * ----------------------------
 * donne la liste des types possibles pour un cache
 * @return la liste des types
 */
public List<TypeCache> getTypeCache(){
    List<TypeCache> types = new ArrayList<TypeCache>();
    final EntityManager em = this.getEm();
    String strQuery = "Select t from TypeCache t";
    Query query = em.createQuery(strQuery);
    types = query.getResultList();
    return types;
}

/**
 *             TESTS
 */

    /*public static void main(String[] args) {
        RequeteGeOOCache r = new  RequeteGeOOCache();
        System.out.println("Début des tests");
        //List<Cache> caches = r.getCachesByReseauCacheId();
        System.out.println(caches);
        List<StatutCache> statuts = r.getStatutCache();
        System.out.println(statuts);
        //List<Cache> result = r.updateStatutCache(2, statuts.getFirst());
        System.out.println(statuts.getFirst());
        Cache c = r.getDataCachesById(2);
        System.out.println(c);
        boolean result = r.updateStatutCache(2, statuts.getFirst().getId());
        c = r.getDataCachesById(2);
        System.out.println(c);
        System.out.println(result);
        System.out.println(caches);
    }*/
}
