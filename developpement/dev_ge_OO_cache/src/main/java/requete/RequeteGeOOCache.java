package requete;

import jakarta.persistence.*;
import modele.*;
import modele.Cache;

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
         *              METHODES RequeteGeOOCache
         *              (Pour la classe ReseauCache)
         */



        /**
         *              METHODES RequeteGeOOCache
         *              (Pour la classe Log)
         */



        /**
         *              METHODES RequeteGeOOCache
         *              (Pour la classe Cache)
         */

        /**
         * méthode : getCachesByReseauCacheId
         * ----------------------------
         * récupère la liste des différents caches selon l'id du reseau de cache
         * @param reseau le réseau de cache
         * @return liste des Caches
         */
        public List<Cache> getCachesByReseauCacheId(ReseauCache reseau){
            List<Cache> caches = new ArrayList<Cache>();
            final EntityManager em = this.getEm();
            String strQuery = "Select c from Cache c where c.appartient = :reseau";
            Query query = em.createQuery(strQuery);
            query.setParameter("reseau", reseau);
            caches = query.getResultList();
            return caches;
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
         * méthode : updateStatutCache
         * ----------------------------
         * modifie le statut du cache
         * @param numero l'identifiant du réseau de cache
         * @param statutCacheId l'identifiant du nouveau statut du cache
         * @return boolean indiquant la réussite de l'update
         */
        public boolean updateStatutCache(int numero, int statutCacheId) {
            final EntityManager em = this.getEm();
            String strQuery = "Select c from Cache c where c.numero = :numero";
            Query query = em.createQuery(strQuery);
            query.setParameter("numero", numero);
            Cache cache = (Cache)query.getSingleResult();

            StatutCache statutCache = getStatutCacheById(statutCacheId);

            final EntityTransaction et = em.getTransaction();
            try{
                et.begin();
                cache.setStatutCache(statutCache);
                et.commit();
            }catch(Exception ex){
                et.rollback();
                return false;
            }
            em.close();
            return true;

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
            String strQuery = "Select s from StatutCache s";
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
