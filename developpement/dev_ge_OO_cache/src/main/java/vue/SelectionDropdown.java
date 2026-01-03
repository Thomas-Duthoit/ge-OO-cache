package vue;

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Class selectionDropdown
 * Cette classe va permettre de rassembler (dans une classe partagé par toutes les vues) les informations sélectionnées dans chaque dropdown disponible
 */
//Dans la suite, le paramètre key représente le niveau de la dropdown soit "Actions", "Reseau", "Cache", "Log" et "Utilisateur"

//L'idée de la création d'une "mémoire partagée" a été présenté par l'IA
public class SelectionDropdown {
    //Attributs
    private final Map<String, Object> elementsSelec;
    //Mettre la variable en final permet d'éviter de la mettre en paramètres de chaque création d'instances
    //En effet la référence restera la même pour chaque class créant une instance de la class SelectionDropdown
    private FenetrePrincipal fenetrePrincipal;

    //Constructeur par défaut
    public SelectionDropdown(FenetrePrincipal fenetrePrincipal) {
        this.elementsSelec = new HashMap<>();
        this.fenetrePrincipal = fenetrePrincipal;
    }

    /**
     *              METHODES
     */

    /**
     * Méthode : addElementSelect
     * -------
     * ajouter une valeur dans la Map
     * @param key : clé de la valeur à ajouter
     * @param value : valeur à ajouter
     */
    public void addElementSelect(String key, Object value){
        if (key != null && value != null) {
            this.elementsSelec.remove(key); //Si la clé existe déjà dans la liste
            this.elementsSelec.put(key,value);
            this.fenetrePrincipal.refreshMainPanel();
        }
    }

    /**
     * Méthode : supprElementSelect
     * ------
     * supprimer une valeur dans la Map selon l'élément selectionné
     * @param key : clé de la valeur a supprimé
     */
    public void supprElementSelect(String key){
        if(key != null){
            this.elementsSelec.remove(key);
            this.fenetrePrincipal.refreshMainPanel();
        }
    }

    /**
     * Méthode : supprAllElementSelect
     * -----
     * permet de supprimer tous les éléments sélectionné de la Map
     */
    public void supprAllElementSelect(){
        this.elementsSelec.clear();
        this.fenetrePrincipal.refreshMainPanel();
    }

    /**
     * Méthode : getAllElementsSelect
     * -----
     * permet de récupérer tous les éléments de la Map
     */
    public Map<String, Object> getAllElementsSelec() {
        return this.elementsSelec;
    }

    /**
     * Méthode : getElementsSelect
     * -----
     * permet de récupérer une valeur d'un élément spécifique
     * @param key : clé correspondant à l'élément à récupérer
     */
    public Object getElementSelect(String key){
        return this.elementsSelec.get(key);
    }

    /**
     *              TOSTRING
     */

    @Override
    public String toString() {
        return "SelectionDropdown{" +
                "elementsSelec=" + elementsSelec +
                ", fenetrePrincipal=" + fenetrePrincipal +
                '}';
    }
}
