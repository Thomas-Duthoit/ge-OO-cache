package vue.page;

import modele.Log;
import modele.ReseauCache;
import modele.Utilisateur;
import requete.RequeteGeOOCache;
import vue.Refreshable;
import vue.SelectionDropdown;
import vue.dropdown.ComboBoxGeneral;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

/**
 * Classe ShowReseau
 * Une classe correspondant à une page de l'application
 * Elle permet l'affichage de la liste des reseaux de cache dont l'utilisateur est propriétaire ou associé à
 */
public class ShowReseau extends JPanel {
    //Attributs
    private RequeteGeOOCache requeteGeOOCache;
    private Utilisateur utilisateur;
    private JPanel mainPanel;
    private CardLayout cardLayout;
    private SelectionDropdown selectionDropdown;
    private ComboBoxGeneral comboBoxGeneral;

    //Constructeurs par défaut
    public ShowReseau(RequeteGeOOCache requeteGeOOCache, Utilisateur utilisateur, JPanel mainPanel, CardLayout cl, SelectionDropdown selectionDropdown, ComboBoxGeneral comboBoxGeneral) throws SQLException {
        super();
        //Attributs
        this.requeteGeOOCache = requeteGeOOCache;
        this.utilisateur = utilisateur;
        this.mainPanel = mainPanel;
        this.cardLayout = cl;
        this.selectionDropdown = selectionDropdown;
        this.comboBoxGeneral = comboBoxGeneral;

        this.setLayout(new BorderLayout());
        this.setBackground(Color.WHITE);

        this.add(getMainPanel(), BorderLayout.CENTER);
        this.add(Box.createHorizontalStrut(50), BorderLayout.WEST);  // espace à gauche
        this.add(Box.createHorizontalStrut(50), BorderLayout.EAST);  // espace à droite
        this.add(Box.createVerticalStrut(50), BorderLayout.NORTH);  // espace en haut
        this.add(Box.createVerticalStrut(50), BorderLayout.SOUTH);  // espace en bas

        this.setVisible(true);
    }

    /**
     *              MéTHODES : création des éléments à afficher
     */

    /**
     * Méthode getMainPanel
     * ----
     * Permet de créer le Panel avec la JList des ReseauCache
     * @return JPanel avec la liste des réseaux de cache
     */
    public JPanel getMainPanel() {
        //Creation de la JList
        JList<ReseauCache> listReseaux = createListReseauCache();
        JScrollPane scrollPaneListReseaux = new JScrollPane(listReseaux);
        scrollPaneListReseaux.setFont(new Font("consolas", Font.BOLD, 20));

        //Design
        JPanel listPanel = new JPanel();
        listPanel.setBackground(Color.WHITE);
        listPanel.setLayout(new BorderLayout());
        listPanel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        listPanel.add(scrollPaneListReseaux, BorderLayout.CENTER);

        listPanel.setMinimumSize(new Dimension(500, 500));
        return listPanel;
    }

    /**
     * Méthode : createListReseauCache
     * ------
     * permet de créer la JList des réseaux de cache
     * @return JList<ReseauCache> une jList de réseau de cache
     */
    public JList<ReseauCache> createListReseauCache(){
        //Récupération des valeurs pour la liste
        List<ReseauCache> reseaux = this.requeteGeOOCache.getReseauxUtilisateur(utilisateur);
        System.out.println(reseaux);

        //Attribution des valeurs dans la JList
        JList<ReseauCache> listReseaux = new JList<>();
        DefaultListModel<ReseauCache> listReseauxModel = new DefaultListModel<>();
        for (ReseauCache reseau : reseaux){
            listReseauxModel.addElement(reseau);
        }
        listReseaux.setModel(listReseauxModel);

        listReseaux.addMouseListener(new MouseReseauListener());

        //Design JList
        listReseaux.setFont(new Font("consolas", Font.BOLD, 15));
        listReseaux.setCellRenderer(new listReseauRenderer());

        return listReseaux;
    }

    /**
     *                  RENDERER
     */

    // classe interne à la vue car elle y est spécifique
    // Permet de modifier le rendu de la JList selon si l'élément subit le focus ou non
    public class listReseauRenderer implements ListCellRenderer<ReseauCache>{
        @Override
        public Component getListCellRendererComponent(JList<? extends ReseauCache> list, ReseauCache value, int index, boolean isSelected, boolean cellHasFocus) {
            JLabel label = new JLabel();
            int emp = 0;
            label.setText(value.getNom());

            if (cellHasFocus) {
                label.setForeground(Color.BLACK);
                label.setOpaque(true);
                label.setBackground(Color.decode("#dbdbd8"));
            } else {
                label.setForeground(Color.BLACK);
            }

            label.setFont(new Font("consolas", Font.BOLD, 30));
            return label;
        }
    }

    public class MouseReseauListener implements MouseListener {
        @Override
        public void mouseClicked(MouseEvent e) {
            if(e.getClickCount() == 2){
                JList reseaux = (JList) e.getSource();
                ReseauCache selected =  (ReseauCache) reseaux.getSelectedValue();
                selectionDropdown.addElementSelect("Reseau", selected);
                selectionDropdown.addElementSelect("Action", "Affichage de la liste des caches");
                cardLayout.show(mainPanel, "Affichage de la liste des caches");
                refreshDataView(); //Permet d'activer la méthode refreshData de la vue d'affichage de la liste des caches
                comboBoxGeneral.refreshComboBoxReseau();
                comboBoxGeneral.refreshComboBoxAction();
                comboBoxGeneral.getComboBoxCache().setVisible(true);
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

    /**
     *          REFRESHDATA
     */

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
}