package requete;

import jakarta.persistence.*;
import modele.*;

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
     *              METHODES RequeteGeOOCache
     *              (Pour la classe StatutCache)
     */



    /**
     *              METHODES RequeteGeOOCache
     *              (Pour la classe TypeCache)
     */
}
