package vue;

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Class selectionDropdown
 * Cette classe va permettre de rassembler (dans une classe partagé par toutes les vues) les informations sélectionnées dans chaque dropdown disponible
 */
//Dans la suite, le paramètre key représente le niveau de la dropdown soit "Actions", "ReseauCache", "Cache", "Log" et "Utilisateur"

//L'idée de la création d'une "mémoire partagée" a été présenté par l'IA
public class SelectionDropdown {
    //Variables
    private final Map<String, Object> elementsSelec;
    //Mettre la variable en final permet d'éviter de la mettre en paramètres de chaque création d'instances
    //En effet la référence restera la même pour chaque class créant une instance de la class SelectionDropdown
    private FenetrePrincipal fenetrePrincipal;

    //Constructeur par défaut
    public SelectionDropdown(FenetrePrincipal fenetrePrincipal) {
        this.elementsSelec = new HashMap<>();
        this.fenetrePrincipal = fenetrePrincipal;
    }

    //Méthodes d'ajout de valeur dans la Map
    public void addElementSelect(String key, Object value){
        if (key != null && value != null) {
            this.elementsSelec.remove(key); //Si la clé existe déjà dans la liste
            this.elementsSelec.put(key,value);
            this.fenetrePrincipal.refreshMainPanel();
        }
    }

    //Méthodes de suppression de valeur dans la Map dans le cas où la dropdown passe à "Choix de ..."
    public void supprElementSelect(String key){
        if(key != null){
            this.elementsSelec.remove(key);
            this.fenetrePrincipal.refreshMainPanel();
        }
    }

    //Méthodes de suppression de toutes les valeurs dans la Map dans la première dropdown change
    public void supprAllElementSelect(){
        this.elementsSelec.clear();
        this.fenetrePrincipal.refreshMainPanel();
    }

    //Méthodes de récupération de la Map
    public Map<String, Object> getAllElementsSelec() {
        return this.elementsSelec;
    }

    //Méthode de récupération d'un unique élément de la Map
    public Object getElementSelect(String key){
        return this.elementsSelec.get(key);
    }

}
