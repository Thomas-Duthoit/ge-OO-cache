package vue.dropdown;

import modele.Cache;
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

public class ComboBoxGeneral extends JPanel {
    //Attributs
    private CardLayout cl;
    private JPanel mainPanel;
    private RequeteGeOOCache requeteGeOOCache;
    private Utilisateur user;
    private SelectionDropdown selectionDropdown;

    private int modifVue;

    //Les différents comboBox pour l'affichage
    private JComboBox<String> comboBoxAction;
    private JComboBox<Object> comboBoxReseauCache;
    private JComboBox<Object> comboBoxCache;
    private JComboBox<Object> comboBoxUtilisateur;

    public ComboBoxGeneral(RequeteGeOOCache requeteGeOOCache, JPanel mainPanel, CardLayout cl, Utilisateur user, SelectionDropdown selectionDropdown) throws SQLException {
        super();

        //Initialisation des attributs
        this.requeteGeOOCache = requeteGeOOCache;
        this.cl = cl;
        this.mainPanel = mainPanel;
        this.user = user;
        this.selectionDropdown = selectionDropdown;
        this.modifVue = 0;

        //Mise en forme
        this.setLayout(new FlowLayout(FlowLayout.LEFT));

        //JComboBox pour l'action
        this.comboBoxAction = getComboBoxAction();
        this.comboBoxAction.setVisible(true);

        this.comboBoxUtilisateur = getComboBoxUtilisateur();
        this.comboBoxUtilisateur.setVisible(false);

        this.comboBoxReseauCache = getComboBoxReseauCache();
        this.comboBoxReseauCache.setVisible(false);

        this.comboBoxCache = new JComboBox<>();
        this.comboBoxCache.addActionListener(new ChoixCacheListener());
        this.comboBoxCache.setVisible(false);
        this.comboBoxCache.setBackground(Color.LIGHT_GRAY);

        this.add(this.comboBoxAction);
        this.add(this.comboBoxReseauCache);
        this.add(this.comboBoxUtilisateur);
        this.add(this.comboBoxCache);

        this.setVisible(true);

    }

    public JComboBox<String> getComboBoxAction() {
        //Les différentes valeurs a attribué pour le dropdown
        String[] choix = {
                "Choix de l'interface",
                "Associer un utilisateur",
                "Affichage des réseaux",
                "Affichage de la liste des caches",
                "Afficher les statistiques",
                "Afficher les loggins",
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

    public JComboBox<Object> getComboBoxUtilisateur() {
        //Les différentes valeurs a attribué pour le dropdown
        //Création du dropdown
        JComboBox<Object> comboBox = new JComboBox<>();

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
        comboBox.setModel(cbModel);

        comboBox.setSelectedIndex(0); //Valeur par défaut au niveau des choix
        comboBox.setBackground(Color.LIGHT_GRAY);

        //Au niveau des changements de vue par rapport au choix
        comboBox.addActionListener(new ChoixUtilisateurListener());
        return comboBox;
    }

    public JComboBox<Object> getComboBoxReseauCache() {
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

        //Création du dropdown
        JComboBox<Object> comboBox = new JComboBox<>();
        comboBox.setModel(cbModel);

        comboBox.setSelectedIndex(0); //Valeur par défaut au niveau des choix
        comboBox.setBackground(Color.LIGHT_GRAY);

        //Au niveau des changements de vue par rapport au choix
        comboBox.addActionListener(new ChoixReseauListener());
        //Note : L'affichage du JComboBox dans l'application dépend du toString de la classe concernée
        return comboBox;
    }

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

    public void refresh() {
        revalidate();
        repaint();
    }

    //Cette méthode a été proposé par l'IA
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

    public class ChoixActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JComboBox<String> cb = (JComboBox<String>) e.getSource();
            String choixActionSelectionnee =  cb.getSelectedItem().toString();
            System.out.println("Choix de l'action effectuée : " + choixActionSelectionnee);

            comboBoxReseauCache.setVisible(false);
            comboBoxCache.setVisible(false);
            comboBoxUtilisateur.setVisible(false);

            if (modifVue == 0) {
                comboBoxUtilisateur.setSelectedIndex(0);
                comboBoxReseauCache.setSelectedIndex(0);

                selectionDropdown.supprAllElementSelect();
            }

            if(choixActionSelectionnee.equals("Choix de l'interface")){
                cl.show(mainPanel, "Choix de l'interface");
            }
            else {
                // Affichage de certains dropdown
                selectionDropdown.addElementSelect("Action", choixActionSelectionnee);
                if ("Associer un utilisateur".equals(choixActionSelectionnee)) {
                    comboBoxUtilisateur.setVisible(true);
                }
                if ("Afficher les statistiques".equals(choixActionSelectionnee) || "Affichage de la liste des caches".equals(choixActionSelectionnee) || "Créer une cache".equals(choixActionSelectionnee) || "Modifier le statut d'une cache".equals(choixActionSelectionnee)) {
                    comboBoxReseauCache.setVisible(true);
                }

                // Affichage de la page adéquate
                if ("Affichage de la liste des caches".equals(choixActionSelectionnee) || "Créer une cache".equals(choixActionSelectionnee) || "Modifier le statut d'une cache".equals(choixActionSelectionnee) || "Associer un utilisateur".equals(choixActionSelectionnee) || "Afficher les statistiques".equals(choixActionSelectionnee)) {
                    System.out.println("Pas de changement de page à effectuer pour le moment");
                    cl.show(mainPanel, "Choix de l'interface");
                } else {
                    cl.show(mainPanel, choixActionSelectionnee);
                }

            }
            modifVue = 0;
            refresh();
            refreshDataView();
        }
    }

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
                cl.show(mainPanel, "Choix de l'interface");
            }
            refresh();
        }
    }

    public class ChoixReseauListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JComboBox<Object> cb = (JComboBox<Object>) e.getSource();

            comboBoxCache.setVisible(false);
            if(cb.getSelectedItem() instanceof ReseauCache){
                ReseauCache reseauCache = (ReseauCache) cb.getSelectedItem();
                System.out.println("Choix du réseau effectué : " + reseauCache);
                selectionDropdown.addElementSelect("Reseau", reseauCache);

                String actionPrec = comboBoxAction.getSelectedItem().toString();

                if ("Modifier le statut d'une cache".equals(actionPrec) || "Affichage de la liste des caches".equals(actionPrec)) {
                    System.out.println("test affichage cache");
                    comboBoxCache.setModel(getDefaultComboBoxModelCache(reseauCache));
                    comboBoxCache.setVisible(true);
                }

                if ("Modifier le statut d'une cache".equals(actionPrec)) {
                    System.out.println("Pas de nouveau affichage");
                    cl.show(mainPanel, "Choix de l'interface");
                } else if ("Affichage de la liste des caches".equals(actionPrec)) {
                    cl.show(mainPanel, "Liste des caches");
                } else {
                    cl.show(mainPanel, actionPrec);
                }
                refreshDataView();
            }else{
                selectionDropdown.supprElementSelect("Reseau");
                cl.show(mainPanel, "Choix de l'interface");
            }
            refresh();
        }
    }

    public class ChoixCacheListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JComboBox<String> cb = (JComboBox<String>) e.getSource();
            if(cb.getSelectedItem() instanceof Cache) {
                selectionDropdown.addElementSelect("Cache", cb.getSelectedItem());
                System.out.println("Choix des caches : " + cb.getSelectedItem());

                cl.show(mainPanel, comboBoxAction.getSelectedItem().toString());
                refreshDataView();
            }
            else{
                selectionDropdown.supprElementSelect("Cache");
                if("modifier le statut d'une cache".equals(cb.getSelectedItem())) {
                    cl.show(mainPanel, "Choix de l'interface");
                }
                else{
                    cl.show(mainPanel, "Liste des caches");
                }

            }
            refresh();
        }
    }

    /**
     *          REFRESH
     */
    public void refreshComboBoxUtilisateur(){
        comboBoxUtilisateur.setSelectedItem(selectionDropdown.getElementSelect("Utilisateur"));
    }

    public void refreshComboBoxReseau(){
        comboBoxReseauCache.setSelectedItem(selectionDropdown.getElementSelect("Reseau"));
        comboBoxCache.setModel(getDefaultComboBoxModelCache((ReseauCache) selectionDropdown.getElementSelect("Reseau")));
    }

    public void refreshComboBoxAction(){
        this.modifVue = 1;
        comboBoxAction.setSelectedItem(selectionDropdown.getElementSelect("Action"));
    }

    public JComboBox<Object> getComboBoxCache() {
        return comboBoxCache;
    }
}
