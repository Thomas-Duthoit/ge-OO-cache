package vue.page;

import modele.Cache;
import modele.Log;
import modele.ReseauCache;
import modele.Utilisateur;
import requete.RequeteGeOOCache;
import vue.Refreshable;
import vue.SelectionDropdown;
import vue.dropdown.ComboBoxGeneral;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

/**
 * Classe ShowLoggings
 * Une classe correspondant à une page de l'application
 * Elle permet l'affichage de la liste des logs
 */
public class ShowLoggings extends JPanel implements Refreshable {
    private RequeteGeOOCache requeteGeOOCache;
    private Utilisateur utilisateur;

    // Liste des logs - est en attribut de la classe car elle sera modifié au fur et à mesure du filtrage
    private JList<Log> listeLogs;

    // ComboBox des reseaux pour le filtrage - en Object car il y a String et Reseau
    private JComboBox<Object> comboBoxFiltreReseau;

    // ComboBox des caches pour le filtrage - en Object car il y a String et Cache
    private JComboBox<Object> comboBoxFiltreCache;

    //Pour modifier la vue
    private CardLayout cl;
    private JPanel mainPanel;
    private ComboBoxGeneral comboBoxGeneral;

    //Constructeur par données
    public ShowLoggings(RequeteGeOOCache requeteGeOOCache, Utilisateur utilisateur, SelectionDropdown selectionDropdown, CardLayout cl, JPanel mainPanel, ComboBoxGeneral comboBoxGeneral) throws SQLException {
        super();
        this.requeteGeOOCache = requeteGeOOCache;
        this.utilisateur = utilisateur;
        this.cl = cl;
        this.mainPanel = mainPanel;
        this.comboBoxGeneral = comboBoxGeneral;

        this.setLayout(new BorderLayout());
        this.setBackground(Color.WHITE);

        // Par défaut les filtres des reseaux et des caches sont vides
        this.listeLogs = new JList<>();

        //Ajout d'un listener pour afficher le log quand on clique dessus
        this.listeLogs.addMouseListener(new MouseLogListener(selectionDropdown));

        //Ajout d'un Renderer pour le design sur celui qui a le focus
        this.listeLogs.setCellRenderer(new rendererJListLogs());

        this.listeLogs.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        this.listeLogs.setMaximumSize(new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));

        //Ajout de la barre de défilement de la JList
        JScrollPane scrollPaneListLog = new JScrollPane(listeLogs);
        scrollPaneListLog.setFont(new Font("consolas", Font.BOLD, 20));

        //Création de la partie filtre
        //1. Partie filtre Reseau
        this.comboBoxFiltreReseau = new JComboBox<>();
        //Ajout d'un listener pour l'écoute de la modification du choix de la comboBox
        this.comboBoxFiltreReseau.addActionListener(new ActionComboBoxReseauListener());

        Font font = new Font("Consolas", Font.PLAIN, 16);

        this.comboBoxFiltreReseau.setFont(font);
        this.comboBoxFiltreReseau.setBackground(Color.decode("#e6e6e6"));

        //2. Partie filtre Cache
        //Pour l'instant elle n'existe pas car dépend du choix de Reseau
        this.comboBoxFiltreCache = new JComboBox<>();

        this.comboBoxFiltreCache.setFont(font);
        this.comboBoxFiltreCache.setBackground(Color.decode("#e6e6e6"));

        //Ajout d'un listener pour l'écoute de la modification du choix de la comboBox
        this.comboBoxFiltreReseau.addActionListener(new ActionComboBoxCacheListener());

        //Mise en place des comboBoxFiltre dans un Panel
        JPanel FilterPanel = createFilterPanel();

        JPanel dataPanel = new JPanel();
        dataPanel.setBackground(Color.WHITE);
        dataPanel.setLayout(new BoxLayout(dataPanel, BoxLayout.Y_AXIS));

        dataPanel.add(FilterPanel, BorderLayout.NORTH);
        dataPanel.add(Box.createRigidArea(new Dimension(0, 50)));
        dataPanel.add(scrollPaneListLog, BorderLayout.CENTER);

        //Mise en place d'un cadre vide autour de dataPanel
        this.add(Box.createRigidArea(new Dimension(0, 50)), BorderLayout.NORTH);
        this.add(Box.createRigidArea(new Dimension(0, 50)), BorderLayout.SOUTH);
        this.add(Box.createRigidArea(new Dimension(50, 0)), BorderLayout.EAST);
        this.add(Box.createRigidArea(new Dimension(50, 0)), BorderLayout.WEST);
        this.add(dataPanel, BorderLayout.CENTER);
        this.setVisible(true);
    }

    /**
     *              METHODES pour les différents éléments de la vues
     */

    //Dans la suite, nous ne créons que les DefaultModel pour chaque élément regroupant un liste car ils seront modifiés au cours du temps selon les filtres
    //Donc on ne doit pas modifier directement l'instance sinon ce ne sera plus l'instance mise en place dans l'affichage qu'on modifiera

    /**
     * Méthode : createModelJListLog
     * -------
     * permet de créer le modèle par défaut de la JList des logs selon les valeurs de filtre et celui de l'utilisateur
     * @param utilisateur : utilisateur connecté à l'application
     * @param filtreReseau : ReseauCache choisit dans le filtre
     * @param filtreCache : Cache choisit dans le filtre
     * @return DefaultListModel<Log> le modèle par défaut de la JList des logs
     */
    public DefaultListModel<Log> createModelJListLog(Utilisateur utilisateur, ReseauCache filtreReseau, Cache filtreCache){
        /**
         * Les étapes de création de la JList
         * 1. Récupérer la liste des logs avec une requête
         * 2. Créer la JList
         */
        //Etape 1
        List<Log> listeLogs = this.requeteGeOOCache.getLogs(utilisateur, filtreReseau, filtreCache);

        //Etape 2
        DefaultListModel<Log> listeLogsModel = new DefaultListModel<>();
        for(Log log : listeLogs){
            listeLogsModel.addElement(log);
        }
        return listeLogsModel;
    }

    /**
     * Méthode : createModelJComboBoxReseau
     * ------
     * permet de créer le modèle par défaut de la JComboBox de ReseauCache
     * @param utilisateur : l'utilisateur connecté à l'application
     * @return DefaultComboBoxModel<Object> le modèle par défaut de la JComboBox des ReseauCache
     */
    public DefaultComboBoxModel<Object> createModelJComboBoxReseau(Utilisateur utilisateur) {
        /**
         * Les étapes de création de la JComboBox
         * 1. Récupérer les données
         * 2. Créer la JCombox
         */

        //Etape 1
        List<ReseauCache> listeReseauxCache = this.requeteGeOOCache.getReseauxUtilisateur(utilisateur);

        //Etape 2
        DefaultComboBoxModel<Object> comboBoxReseauModel = new DefaultComboBoxModel<>();
        comboBoxReseauModel.addElement("Filtrer par Réseau");
        for(ReseauCache reseauCache : listeReseauxCache){
            comboBoxReseauModel.addElement(reseauCache);
        }
        return comboBoxReseauModel;
    }

    /**
     * Méthode : createModelJComboBoxCache
     * ------
     * permet de créer le modèle par défaut de la JComboBox de Cache
     * @param reseauCache : reseauCache choisit dans le filtre de reseauCache
     * @return DefaultComboBoxModel<Object> le modèle par défaut de la JComboBox des Cache
     */
    public DefaultComboBoxModel<Object> createModelJComboBoxCache(ReseauCache reseauCache) {
        /**
         * Les étapes de création de la JComboBox
         * 1. Récupérer les données
         * 2. Créer la JCombox
         */

        //Etape 1
        List<Cache> listeCaches = this.requeteGeOOCache.getCachesByReseauCache(reseauCache);

        //Etape 2
        JComboBox<Object> comboBoxCache = new JComboBox<>();
        DefaultComboBoxModel<Object> comboBoxCacheModel = new DefaultComboBoxModel<>();
        comboBoxCacheModel.addElement("Filter par Cache");
        for(Cache cache : listeCaches){
            comboBoxCacheModel.addElement(cache);
        }
        return comboBoxCacheModel;
    }

    /**
     *              METHODES pour l'affichage
     */
    /**
     * Méthode : createFilterPanel
     * -------
     * permet de créer le panel de filtre regroupant les différents JComboBox, le Label et le bouton
     * @return JPanel correspondant à la partie filtre
     */
    public JPanel createFilterPanel(){
        // mainFilterPanel correspond au rassemblement de tous les éléments
        JPanel mainFilterPanel = new JPanel();
        mainFilterPanel.setLayout(new BorderLayout());

        // filterPanel correspond au rassemblement des éléments à gauche dans la partie filtre
        JPanel filterPanel = new JPanel();
        filterPanel.setLayout(new BoxLayout(filterPanel, BoxLayout.X_AXIS));
        filterPanel.setBackground(Color.LIGHT_GRAY);

        //Mise en place des tailles des différents ComboBox
        this.comboBoxFiltreReseau.setMaximumSize(new Dimension(200, 40));
        this.comboBoxFiltreCache.setMaximumSize(new Dimension(200, 40));

        //Création du texte avant les filtres :
        JLabel labelFiltre = new JLabel("Filtrer par : ");

        //Rassemblement des éléments
        filterPanel.add(labelFiltre);
        filterPanel.add(Box.createRigidArea(new Dimension(50, 0)));
        filterPanel.add(this.comboBoxFiltreReseau);
        filterPanel.add(Box.createRigidArea(new Dimension(50, 0)));
        filterPanel.add(this.comboBoxFiltreCache);

        //Cache comBoxFiltre
        this.comboBoxFiltreCache.setVisible(false);

        //Création du bouton pour effacer les filtres
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BorderLayout());

        JButton buttonEraseFilter = new JButton("Effacer les filtres");
        buttonEraseFilter.addActionListener(new ActionButtonListener());
        buttonEraseFilter.setBackground(Color.white);

        buttonPanel.add(buttonEraseFilter, BorderLayout.CENTER);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 10));
        buttonPanel.setBackground(Color.LIGHT_GRAY);

        mainFilterPanel.add(filterPanel, BorderLayout.WEST);
        mainFilterPanel.add(buttonPanel, BorderLayout.EAST);

        mainFilterPanel.setBackground(Color.LIGHT_GRAY);
        mainFilterPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));

        return mainFilterPanel;
    }

    /**
     *          REFRESHDATA
     */

    /**
     * Methode : RefreshData
     * -------
     * Permet de refresh les données quand on revient sur cette vue
     * Crée la JList initiale (quand aucun filtre) + réinitialise les filtres + cache le filtre de cache
     */
    @Override
    public void refreshData() {
        //Récupère l'utilisateur sélectionné dans la dropdown

        this.comboBoxFiltreReseau.setModel(createModelJComboBoxReseau(this.utilisateur));
        this.comboBoxFiltreCache.setModel(new DefaultComboBoxModel<>());

        this.comboBoxFiltreReseau.setSelectedIndex(0);

        this.comboBoxFiltreReseau.setVisible(true);
        this.comboBoxFiltreCache.setVisible(false);

        this.listeLogs.setModel(createModelJListLog(this.utilisateur, null, null));
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
     *              RENDERER
     */

    // classe interne à la vue car elle y est spécifique
    // Permet de modifier le rendu de la JList selon si l'élément subit le focus ou non
    public class rendererJListLogs implements ListCellRenderer<Log>{
        @Override
        public Component getListCellRendererComponent(JList<? extends Log> list, Log value, int index, boolean isSelected, boolean cellHasFocus) {
            JPanel panel = createAffichageRenderer(value);

            Color bg = isSelected
                    ? Color.decode("#dbdbd8")
                    : Color.WHITE;

            panel.setBackground(bg);

            // La boucle a été créé à l'aide de l'IA
            // Elle permet de faire en sorte que tous les éléments aient la couleur approprié selon si sélectionné ou non
            // Sans les éléments gardent leur background personnel
            for (Component c : panel.getComponents()) {
                c.setBackground(bg);
                if (c instanceof JPanel) {
                    for (Component cc : ((JPanel) c).getComponents()) {
                        cc.setBackground(bg);
                    }
                }
            }

            return panel;
        }
    }

    public JPanel createAffichageRenderer(Log log){
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BorderLayout());
        leftPanel.setBackground(Color.WHITE);

        JLabel leftLabel = new JLabel();
        leftLabel.setText("> Log " + log.getId());
        leftLabel.setForeground(Color.BLACK);
        leftLabel.setFont(new Font("Consolas", Font.BOLD, 25));

        leftPanel.add(leftLabel, BorderLayout.CENTER);
        leftPanel.add(Box.createRigidArea(new Dimension(60, 0)), BorderLayout.WEST);
        leftPanel.add(Box.createRigidArea(new Dimension(0, 20)), BorderLayout.NORTH);
        leftPanel.add(Box.createRigidArea(new Dimension(0, 20)), BorderLayout.SOUTH);

        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BorderLayout());
        rightPanel.setBackground(Color.WHITE);

        JLabel rightLabel = new JLabel();
        rightLabel.setText("( provenant du cache : " + log.getEnregistrer() + " | note : " + log.getNote() + "/5 )");
        rightLabel.setForeground(Color.BLACK);
        rightLabel.setFont(new Font("Consolas", Font.PLAIN, 25));

        rightPanel.add(rightLabel, BorderLayout.CENTER);
        rightPanel.add(Box.createRigidArea(new Dimension(20, 0)), BorderLayout.EAST);
        rightPanel.add(Box.createRigidArea(new Dimension(0, 10)), BorderLayout.NORTH);
        rightPanel.add(Box.createRigidArea(new Dimension(0, 10)), BorderLayout.SOUTH);

        panel.add(leftPanel, BorderLayout.WEST);
        panel.add(rightPanel, BorderLayout.EAST);

        return panel;
    }

    /**
     *              LISTENER
     */

    // classe interne à la vue car elle y est spécifique
    // Permet de réagir selon le choix effectué sur la JComboBox de reseauCache
    public class ActionComboBoxReseauListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            /**
             * Etapes quand changement de selected value dans comboBox :
             * 1. Récupérer le Reseau selectionné
             * 2. Vérifier que c'est un Reseau et non un String
             * 3. Si String, cacher comboBoxCache
             * 4. Sinon afficher comboBoxCache + attribuer les valeurs dans comboBoxCache
             * 5. Modifier la JList en adéquation avec le choix
             */
            ReseauCache reseauCache;

            if(comboBoxFiltreReseau.getSelectedItem() instanceof ReseauCache){
                reseauCache = (ReseauCache) comboBoxFiltreReseau.getSelectedItem();

                comboBoxFiltreCache.setModel(createModelJComboBoxCache(reseauCache));
                comboBoxFiltreCache.setVisible(true);
            }else{
                reseauCache = null;
                comboBoxFiltreCache.setVisible(false);
            }

            listeLogs.setModel(createModelJListLog(utilisateur, reseauCache, null));
        }
    }

    // classe interne à la vue car elle y est spécifique
    // Permet de réagir selon le choix effectué sur la JComboBox de Cache
    public class ActionComboBoxCacheListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            /**
             * Etapes quand changement de selected value dans comboBox :
             * 1. Récupérer le Cache selectionné
             * 2. Vérifier que c'est un Cache et non un String
             * 3. Si String, cacher comboBoxCache
             * 4. Sinon afficher comboBoxCache
             * 5. Modifier la JList en adéquation avec le choix
             */
            Cache cache;
            ReseauCache reseauCache;

            if(comboBoxFiltreCache.getSelectedItem() instanceof Cache){
                cache = (Cache) comboBoxFiltreCache.getSelectedItem();
            }else{
                cache = null;
            }

            if(comboBoxFiltreReseau.getSelectedItem() instanceof ReseauCache){
                reseauCache = (ReseauCache) comboBoxFiltreReseau.getSelectedItem();
            }else{
                reseauCache = null;
            }

            listeLogs.setModel(createModelJListLog(utilisateur, reseauCache, cache));
        }
    }

    // classe interne à la vue car elle y est spécifique
    // Permet de réagir à l'action effectué sur le bouton et donc d'effacer les filtres
    public class ActionButtonListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            /**
             * Effacer les filtres :
             * 1. Remet la comboBox Reseau à l'etat initial
             * 2. Cache la comboxBox Cache
             * 3. reinitialise la JList des logs
             */

            comboBoxFiltreReseau.setSelectedIndex(0);
            comboBoxFiltreCache.setVisible(false);
            listeLogs.setModel(createModelJListLog(utilisateur, null, null));
        }
    }

    // classe interne à la vue car elle y est spécifique
    // Permet de réagir à un double clic sur un élément de la JList des logs : change de vue pour celle du détails de la log
    public class MouseLogListener implements MouseListener {
        private SelectionDropdown selectionDropdown;

        public MouseLogListener(SelectionDropdown selectionDropdown){
            this.selectionDropdown = selectionDropdown;
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            if(e.getClickCount() == 2){
                System.out.println("Affichage des détails d'un login");
                JList log = (JList) e.getSource();
                Log selected =  (Log) log.getSelectedValue();
                System.out.println(selected);
                this.selectionDropdown.addElementSelect("Log", selected);

                System.out.println(this.selectionDropdown.getElementSelect("Log"));

                cl.show(mainPanel, "Afficher les logging détails");
                refreshDataView(); //Permet d'activer la méthode refreshData de la vue d'affichage des détails d'un log

                comboBoxGeneral.refreshComboBoxLog();
            }
        }

        @Override
        public void mousePressed(MouseEvent e) {
            //Non utilisé
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            //Non utilisé
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            //Non utilisé
        }

        @Override
        public void mouseExited(MouseEvent e) {
            //Non utilisé
        }
    }
}