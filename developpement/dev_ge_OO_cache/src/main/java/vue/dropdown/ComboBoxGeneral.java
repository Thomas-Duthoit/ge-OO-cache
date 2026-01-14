package vue.dropdown;

import modele.Cache;
import modele.Log;
import modele.ReseauCache;
import modele.Utilisateur;
import requete.RequeteGeOOCache;
import vue.Refreshable;
import vue.SelectionDropdown;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

/**
 * Classe ComboBoxGeneral
 * La classe correspondant à la vue en haut de l'application
 * Elle permet de gérer l'affichage des JComboBox et des valeurs de chacune
 */
public class ComboBoxGeneral extends JPanel {
    private CardLayout cl;
    private JPanel mainPanel;
    private RequeteGeOOCache requeteGeOOCache;
    private Utilisateur user;
    private SelectionDropdown selectionDropdown;

    private int modifVue; //Permet d'indiquer au comBox Action si c'est une modification de vue par une page ou non

    //Les différents comboBox pour l'affichage
    private JComboBox<String> comboBoxAction;
    private JComboBox<Object> comboBoxReseauCache;
    private JComboBox<Object> comboBoxCache;
    private JComboBox<Object> comboBoxUtilisateur;
    private JComboBox<Object> comboBoxLog;

    //Constructeur par données
    public ComboBoxGeneral(RequeteGeOOCache requeteGeOOCache, JPanel mainPanel, CardLayout cl, Utilisateur user, SelectionDropdown selectionDropdown) throws SQLException {
        super();

        //Initialisation des attributs
        this.requeteGeOOCache = requeteGeOOCache;
        this.cl = cl;
        this.mainPanel = mainPanel;
        this.user = user;
        this.selectionDropdown = selectionDropdown;
        this.modifVue = 0;

        DefaultComboBoxModel<Object> model = new DefaultComboBoxModel<>();
        model.addElement("test");

        //Mise en forme
        this.setLayout(new FlowLayout(FlowLayout.LEFT));

        //JComboBox pour l'action
        this.comboBoxAction = getComboBoxAction();
        this.comboBoxAction.setVisible(true);

        this.comboBoxUtilisateur = new JComboBox<>();
        this.comboBoxUtilisateur.setBackground(Color.LIGHT_GRAY);
        this.comboBoxUtilisateur.setModel(model);
        //Au niveau des changements de vue par rapport au choix
        this.comboBoxUtilisateur.addActionListener(new ChoixUtilisateurListener());
        this.comboBoxUtilisateur.setVisible(false);

        this.comboBoxReseauCache = new JComboBox<>();
        this.comboBoxReseauCache.setBackground(Color.LIGHT_GRAY);
        this.comboBoxReseauCache.setModel(model);
        this.comboBoxReseauCache.addActionListener(new ChoixReseauListener());
        this.comboBoxReseauCache.setVisible(false);

        this.comboBoxLog = new JComboBox<>();
        this.comboBoxLog.setModel(getDefaultModelComboBoxLog());
        this.comboBoxLog.setBackground(Color.LIGHT_GRAY);
        this.comboBoxLog.addActionListener(new ChoixLogListener());
        this.comboBoxLog.setVisible(false);

        this.comboBoxCache = new JComboBox<>();
        this.comboBoxCache.setBackground(Color.LIGHT_GRAY);
        this.comboBoxCache.addActionListener(new ChoixCacheListener());
        this.comboBoxCache.setVisible(false);

        this.add(this.comboBoxAction);
        this.add(this.comboBoxLog);
        this.add(this.comboBoxReseauCache);
        this.add(this.comboBoxUtilisateur);
        this.add(this.comboBoxCache);

        this.setVisible(true);

        //Dimension des tailles de comboBox
        Dimension comboBoxDimension = new Dimension(220, 40);
        Dimension comboBoxDimensionLarge = new Dimension(320, 40);
        this.comboBoxAction.setPreferredSize(comboBoxDimensionLarge);
        this.comboBoxUtilisateur.setPreferredSize(comboBoxDimension);
        this.comboBoxLog.setPreferredSize(comboBoxDimension);
        this.comboBoxCache.setPreferredSize(comboBoxDimension);
        this.comboBoxReseauCache.setPreferredSize(comboBoxDimension);

        //Taille des textes de comboBox
        Font font = new Font("Consolas", Font.PLAIN, 16);

        this.comboBoxAction.setFont(font);
        this.comboBoxUtilisateur.setFont(font);
        this.comboBoxReseauCache.setFont(font);
        this.comboBoxLog.setFont(font);
        this.comboBoxCache.setFont(font);
    }

    /**
     *          METHODES : création des JComboBox ou des DefaultJComboBoxModel
     */

    /**
     * Méthode : getComboBoxAction
     * ------
     * permet de créer la comboBox correspondant au action disponible dans l'application
     * @return JComboBox<String> la JComboBox des actions
     */

    public JComboBox<String> getComboBoxAction() {
        //Les différentes valeurs a attribué pour le dropdown
        String[] choix = {
                "Accueil",
                "Associer un utilisateur",
                "Affichage des réseaux",
                "Affichage de la liste des caches",
                "Afficher les statistiques",
                "Afficher les logs",
                "Créer un réseau",
                "Créer une cache",
                "Créer un utilisateur",
                "Créer un type",
                "Modifier le statut d'une cache",
        };

        //Création du dropdown
        JComboBox<String> comboBox = new JComboBox<>(choix);
        comboBox.setSelectedIndex(0); //Valeur par défaut au niveau des choix
        comboBox.setBackground(Color.LIGHT_GRAY);

        //Au niveau des changements de vue par rapport au choix
        comboBox.addActionListener(new ChoixActionListener());

        return comboBox;
    }

    /**
     * getDefaultModelComboBoxUtilisateur
     * ------
     * permet de créer le modèle de la comboBox correspondant au utilisateur de l'application hors utilisateur connecté
     * @return DefaultComboBoxModel<Object> des utilisateurs
     */
    public DefaultComboBoxModel<Object> getDefaultModelComboBoxUtilisateur() {
        //Les différentes valeurs a attribué pour le dropdown

        //Création du modèle pour la comboBox
        DefaultComboBoxModel<Object> cbModel = new DefaultComboBoxModel<>();
        cbModel.addElement("Choix de l'utilisateur");
        //Récupère dans une requête de la liste des Utilisateurs
        List<Utilisateur> utilisateurs = this.requeteGeOOCache.getListeUtilisateurs();
        for(Utilisateur utilisateur : utilisateurs){
            if(!(user.equals(utilisateur))) {
                cbModel.addElement(utilisateur);
            }
        }
        return cbModel;
    }

    /**
     * getDefaultModelComboBoxReseauCache
     * ------
     * permet de créer le modèle de la comboBox correspondant au réseau cache de l'application dont l'utilisateur est propriétaire ou associé
     * @return DefaultComboBoxModel<Object> des reseauCache
     */
    public DefaultComboBoxModel<Object> getDefaultModelComboBoxReseauCache() {
        //Les différentes valeurs a attribué pour le dropdown
        //Récupération de la liste des réseauxCache dont l'utilisateur est propriétaire
        List<ReseauCache> reseauxCacheProp = this.requeteGeOOCache.getReseauxUtilisateur(user);

        //Récupération de la liste des réseauxCache dont l'utilisateur a accès
        List<ReseauCache> reseauxCacheAcces = this.requeteGeOOCache.getReseauxAvecAccesUtilisateur(user);

        //Création du modèle pour la ComboBox
        DefaultComboBoxModel<Object> cbModel = new DefaultComboBoxModel<>();
        cbModel.addElement("Choix d'un réseau");

        for (ReseauCache rc : reseauxCacheProp){
            cbModel.addElement(rc);
        }

        System.out.println("reseauxCacheProp " + reseauxCacheProp);
        System.out.println("reseauxCacheAcces " + reseauxCacheAcces);

        for (ReseauCache rc : reseauxCacheAcces){
            if (!(reseauxCacheProp.contains(rc))){
                cbModel.addElement(rc);
            }
        }
        return cbModel;
    }

    /**
     * getDefaultModelComboBoxLog()
     * ------
     * permet de créer le modèle de la comboBox correspondant au utilisateur de l'application hors utilisateur connecté
     * @return DefaultComboBoxModel<Object> des logs sur les différents caches de réseau dont l'utilisateur est propriétaire
     */
    public DefaultComboBoxModel<Object> getDefaultModelComboBoxLog() {
        List<Log> logs = this.requeteGeOOCache.getLogs(user, null, null);
        DefaultComboBoxModel<Object> cbModel = new DefaultComboBoxModel<>();
        cbModel.addElement("Choix du log");
        for (Log log : logs){
            cbModel.addElement(log);
        }
        return cbModel;
    }

    /**
     * getDefaultComboBoxModelCache
     * ------
     * permet de créer le modèle de la comboBox correspondant au cache de l'application selon le reseau choisit
     * @param reseauCache : le reseauCache selectionnée dans la comboBox de reseau de cache
     * @return DefaultComboBoxModel<Object> modèle pour la comboBox des caches
     */
    public DefaultComboBoxModel<Object> getDefaultComboBoxModelCache(ReseauCache reseauCache) {
        //Les différentes valeurs a attribué pour le dropdown
        //Récupère la liste des caches selon le reseauCache
        List<Cache> caches = this.requeteGeOOCache.getCachesByReseauCache(reseauCache);

        //Crée le modèle pour le JComboBoc
        DefaultComboBoxModel<Object> model = new DefaultComboBoxModel<>();

        model.addElement("Choix des caches");

        for (Cache cache : caches) {
            model.addElement(cache);
        }
        return model;
    }

    /**
     *          REFRESHDATA
     */

    /**
     * Méthode : refresh
     * ------
     * permet de refresh la page après les modifications effectuées aux différents éléments
     */
    public void refresh() {
        revalidate();
        repaint();
    }

    /**
     * Methode : RefreshDataView
     * -------
     * Cette méthode a été proposé par l'IA
     * -------
     * permet de trouver quelle vue est actuellement la vue courante du cardLayout et d'activer la méthode refreshData si implémentée dans la classe
     */
    public void refreshDataView(){
        //On refresh les valeurs pour la vue courante
        //Dans le cas où il s'agit d'une vue nécessitant des valeurs dans les dropdowns
        Component c = Arrays.stream(mainPanel.getComponents())
                .filter(comp -> comp.isVisible())
                .findFirst()
                .orElse(null);

        if (c instanceof Refreshable) {
            ((Refreshable) c).refreshData();
        }
    }

    /**
     *              LISTENER
     */

    // classe interne à la vue car elle y est spécifique
    // Permet de réagir selon le choix effectué sur la JComboBox d'Action
    //Permet de gérer l'affichage des autres ComboBox ou non (ReseauCache, Login, Utilisateur) et de choisir la page a affiché
    public class ChoixActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JComboBox<String> cb = (JComboBox<String>) e.getSource();
            String choixActionSelectionnee =  cb.getSelectedItem().toString();
            System.out.println("Choix de l'action effectuée : " + choixActionSelectionnee);

            comboBoxReseauCache.setVisible(false);
            comboBoxCache.setVisible(false);
            comboBoxUtilisateur.setVisible(false);
            comboBoxLog.setVisible(false);

            if (modifVue == 0) {
                comboBoxUtilisateur.setSelectedIndex(0);
                comboBoxReseauCache.setSelectedIndex(0);
                comboBoxLog.setSelectedIndex(0);

                selectionDropdown.supprAllElementSelect();
            }

            if("Accueil".equals(choixActionSelectionnee)){
                cl.show(mainPanel, "Accueil");
            }
            else {
                // Affichage de certains dropdown
                selectionDropdown.addElementSelect("Action", choixActionSelectionnee);
                if ("Associer un utilisateur".equals(choixActionSelectionnee)) {
                    comboBoxUtilisateur.setVisible(true);
                    comboBoxUtilisateur.setModel(getDefaultModelComboBoxUtilisateur());
                }
                if ("Afficher les statistiques".equals(choixActionSelectionnee) || "Affichage de la liste des caches".equals(choixActionSelectionnee) || "Créer une cache".equals(choixActionSelectionnee) || "Modifier le statut d'une cache".equals(choixActionSelectionnee)) {
                    comboBoxReseauCache.setVisible(true);
                    comboBoxReseauCache.setModel(getDefaultModelComboBoxReseauCache());
                }
                if("Afficher les logs".equals(choixActionSelectionnee)){
                    comboBoxLog.setVisible(true);
                    comboBoxLog.setModel(getDefaultModelComboBoxLog());
                }

                // Affichage de la page adéquate
                if ("Associer un utilisateur".equals(choixActionSelectionnee)) {
                    System.out.println("Pas de changement de page à effectuer pour le moment");
                    cl.show(mainPanel, "Accueil");
                }else if("Créer une cache".equals(choixActionSelectionnee) ){
                    cl.show(mainPanel, "Affichage des réseaux pour création");
                }
                else if ( "Affichage de la liste des caches".equals(choixActionSelectionnee) || "Modifier le statut d'une cache".equals(choixActionSelectionnee) ){
                    cl.show(mainPanel, "Affichage des réseaux pour cache");
                }else if("Afficher les statistiques".equals(choixActionSelectionnee)){
                    cl.show(mainPanel, "Afficher les réseaux pour statistique");
                }else {
                    cl.show(mainPanel, choixActionSelectionnee);
                }
            }

            modifVue = 0;
            refresh();
            refreshDataView();
        }
    }

    // classe interne à la vue car elle y est spécifique
    // Permet de réagir selon le choix effectué sur la JComboBox Utilisateur
    //Permet de gérer l'affichage soit de choisir la page a affiché
    public class ChoixUtilisateurListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JComboBox<Object> cb = (JComboBox<Object>) e.getSource();
            if(cb.getSelectedItem() instanceof Utilisateur){
                Utilisateur utilisateur = (Utilisateur) cb.getSelectedItem();
                System.out.println("Choix de l'utilisateur effectué : " + utilisateur);
                selectionDropdown.addElementSelect("Utilisateur", utilisateur);
                cl.show(mainPanel, comboBoxAction.getSelectedItem().toString());
                refreshDataView();
            }else{
                selectionDropdown.supprElementSelect("Utilisateur");
                cl.show(mainPanel, "Accueil");
            }
            refresh();
        }
    }

    // classe interne à la vue car elle y est spécifique
    // Permet de réagir selon le choix effectué sur la JComboBox Reseau
    //Permet de gérer l'affichage de la comboBox Cache et de choisir la page a affiché
    public class ChoixReseauListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JComboBox<Object> cb = (JComboBox<Object>) e.getSource();
            String actionPrec = comboBoxAction.getSelectedItem().toString();

            comboBoxCache.setVisible(false);
            if(cb.getSelectedItem() instanceof ReseauCache){
                ReseauCache reseauCache = (ReseauCache) cb.getSelectedItem();
                System.out.println("Choix du réseau effectué : " + reseauCache);
                selectionDropdown.addElementSelect("Reseau", reseauCache);

                if ("Modifier le statut d'une cache".equals(actionPrec) || "Affichage de la liste des caches".equals(actionPrec)) {
                    System.out.println("test affichage cache");
                    comboBoxCache.setModel(getDefaultComboBoxModelCache(reseauCache));
                    comboBoxCache.setVisible(true);
                }

                if ("Modifier le statut d'une cache".equals(actionPrec)) {
                    cl.show(mainPanel, "Affichage de la liste des caches");
                } else if ("Affichage de la liste des caches".equals(actionPrec)) {
                    cl.show(mainPanel, "Affichage de la liste des caches");
                } else {
                    cl.show(mainPanel, actionPrec);
                }
                refreshDataView();
            }else{
                selectionDropdown.supprElementSelect("Reseau");
                if("Afficher les statistiques".equals(actionPrec)){
                    cl.show(mainPanel, "Afficher les réseaux pour statistique");
                }else if("Créer une cache".equals(actionPrec)){
                    cl.show(mainPanel, "Affichage des réseaux pour création");
                } else{
                    cl.show(mainPanel, "Affichage des réseaux pour cache");
                }
            }
            refresh();
        }
    }

    // classe interne à la vue car elle y est spécifique
    // Permet de réagir selon le choix effectué sur la JComboBox Cache
    //Permet de gérer l'affichage soit de choisir la page a affiché
    public class ChoixCacheListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JComboBox<Object> cb = (JComboBox<Object>) e.getSource();
            if(cb.getSelectedItem() instanceof Cache) {
                selectionDropdown.addElementSelect("Cache", cb.getSelectedItem());
                System.out.println("Choix des caches : " + cb.getSelectedItem());

                if("Modifier le statut d'une cache".equals(comboBoxAction.getSelectedItem())) {
                    System.out.println("test Statut");
                    cl.show(mainPanel, "Modifier le statut d'une cache");
                }else {
                    cl.show(mainPanel, "détails caches");
                }
                refreshDataView();
            }
            else{
                System.out.println("Pas de cache choisit !");
                cl.show(mainPanel, "Affichage de la liste des caches");
                selectionDropdown.supprElementSelect("Cache");
            }
            refresh();
        }
    }

    // classe interne à la vue car elle y est spécifique
    // Permet de réagir selon le choix effectué sur la JComboBox Log
    //Permet de gérer l'affichage soit de choisir la page a affiché
    public class ChoixLogListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JComboBox<Object> cb = (JComboBox<Object>) e.getSource();

            if(cb.getSelectedItem() instanceof Log) {
                selectionDropdown.addElementSelect("Log", cb.getSelectedItem());
                System.out.println("Choix des logs : " + cb.getSelectedItem());

                cl.show(mainPanel, "Afficher les logging détails");
                refreshDataView();
            }
            else{
                selectionDropdown.supprElementSelect("Log");
                cl.show(mainPanel, "Afficher les logs");
            }
            System.out.println(cb.getSelectedItem().toString());
            refresh();
        }
    }

    /**
     *          REFRESH : SETTER
     */
    /**
     * refreshComboBoxUtilisateur
     * -------
     * setter qui vient modifier l'élément selectionné Utilisateur selon l'élément dans la mémoire partagée
     */
    public void refreshComboBoxUtilisateur(){
        comboBoxUtilisateur.setSelectedItem(selectionDropdown.getElementSelect("Utilisateur"));
    }

    /**
     * refreshComboBoxReseau
     * -------
     * setter qui vient modifier l'élément selectionné Reseau selon l'élément dans la mémoire partagée
     */
    public void refreshComboBoxReseau(){
        comboBoxReseauCache.setSelectedItem(selectionDropdown.getElementSelect("Reseau"));
        if(comboBoxAction.getSelectedItem().toString().equals("Afficher les statistiques") || comboBoxAction.getSelectedItem().toString().equals("Créer une cache")) {
            comboBoxCache.setVisible(false);
        } else {
            comboBoxCache.setModel(getDefaultComboBoxModelCache((ReseauCache) selectionDropdown.getElementSelect("Reseau")));
            comboBoxCache.setVisible(true);
        }
    }

    /**
     * refreshComboBoxAction
     * -------
     * setter qui vient modifier l'élément selectionné Action selon l'élément dans la mémoire partagée
     */
    public void refreshComboBoxAction(){
        this.modifVue = 1;
        comboBoxAction.setSelectedItem(selectionDropdown.getElementSelect("Action"));
    }

    /**
     * refreshComboBoxLog
     * -------
     * setter qui vient modifier l'élément selectionné Log selon l'élément dans la mémoire partagée
     */
    public void refreshComboBoxLog(){
        comboBoxLog.setSelectedItem(selectionDropdown.getElementSelect("Log"));
    }

    /**
     * refreshComboBoxCache
     * -------
     * setter qui vient modifier l'élément selectionné Cache selon l'élément dans la mémoire partagée
     */
    public void refreshComboBoxCache(){
        comboBoxCache.setSelectedItem(selectionDropdown.getElementSelect("Cache"));
    }
}
