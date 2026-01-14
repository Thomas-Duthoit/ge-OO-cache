package vue.page;

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
 * Classe ShowReseauMere
 * Classe mère différentes vues de l'application
 * Permet de gérer les comportements communs entre les différentes vues affichant la liste des réseaux
 */
public abstract class ShowReseauMere extends JPanel implements Refreshable{
    protected RequeteGeOOCache requeteGeOOCache;
    protected Utilisateur utilisateur;
    protected JPanel mainPanel;
    protected CardLayout cardLayout;
    protected SelectionDropdown selectionDropdown;
    protected JList<ReseauCache> listReseaux;
    protected ComboBoxGeneral comboBoxGeneral;

    //Constructeurs par données
    public ShowReseauMere(RequeteGeOOCache requeteGeOOCache, Utilisateur utilisateur, JPanel mainPanel, CardLayout cl, SelectionDropdown selectionDropdown, ComboBoxGeneral comboBoxGeneral) throws SQLException {
        super();
        //Attributs
        this.requeteGeOOCache = requeteGeOOCache;
        this.utilisateur = utilisateur;
        this.mainPanel = mainPanel;
        this.cardLayout = cl;
        this.selectionDropdown = selectionDropdown;
        this.comboBoxGeneral = comboBoxGeneral;
        this.listReseaux = new JList<>();

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
        this.listReseaux = new JList<>();
        this.listReseaux.addMouseListener(new MouseReseauListener());

        //Design JList
        this.listReseaux.setFont(new Font("consolas", Font.BOLD, 15));
        this.listReseaux.setCellRenderer(new listReseauRenderer());

        JScrollPane scrollPaneListReseaux = new JScrollPane(this.listReseaux);
        scrollPaneListReseaux.setFont(new Font("consolas", Font.BOLD, 15));

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
     * Méthode : createDefaultModelListReseauCache
     * ------
     * permet de créer le modèle de la JList des réseaux de cache
     * @return DefaultListModel<ReseauCache> un modèle de jList de réseau de cache
     */
    public DefaultListModel<ReseauCache> createDefaultModelListReseauCache(){
        //Récupération des valeurs pour la liste
        List<ReseauCache> reseaux = this.requeteGeOOCache.getReseauxUtilisateur(utilisateur);
        System.out.println(reseaux);

        //Attribution des valeurs dans la JList
        JList<ReseauCache> listReseaux = new JList<>();
        DefaultListModel<ReseauCache> listReseauxModel = new DefaultListModel<>();
        for (ReseauCache reseau : reseaux){
            listReseauxModel.addElement(reseau);
        }
        return listReseauxModel;
    }

    /**
     *                  RENDERER
     */

    // classe interne à la vue car elle y est spécifique
    // Permet de modifier le rendu de la JList selon si l'élément subit le focus ou non
    public class listReseauRenderer implements ListCellRenderer<ReseauCache>{
        @Override
        public Component getListCellRendererComponent(JList<? extends ReseauCache> list, ReseauCache value, int index, boolean isSelected, boolean cellHasFocus) {
            JPanel panel = createPanelRenderer(value);

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

    /**
     *              LISTENER
     */

    // classe interne à la vue car elle y est spécifique
    // Permet de réagir au double click sur une ligne de la JList
    public class MouseReseauListener implements MouseListener {
        @Override
        public void mouseClicked(MouseEvent e) {
            if(e.getClickCount() == 2){
                JList reseaux = (JList) e.getSource();
                ReseauCache selected =  (ReseauCache) reseaux.getSelectedValue();
                if (selected != null) {
                    selectionDropdown.addElementSelect("Reseau", selected);
                    actionClick(selected);
                }
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
     *              METHODES ABSTRAITE
     */

    /**
     * Méthode : createPanelRenderer
     * -----------------
     * le design des lignes de la JList
     * @param reseau : reseau correspondant à celui de la ligne courante
     * @return JPanel correspondant au design adéquat
     */
    public abstract JPanel createPanelRenderer(ReseauCache reseau);

    /**
     * Méthode : actionClick
     * -----------------
     * action effectué au moment du double clique sur une vue
     * @param reseauCache : reseau correspondant à celui de la ligne cliquée
     */
    public abstract void actionClick(ReseauCache reseauCache);

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

    /**
     * Methode : RefreshData
     * -------
     * Permet de refresh les données quand on revient sur cette vue ou quand l'utilisateur est modifié dans la dropdown
     * Modifie la comboBox avec l'utilisateur choisit
     */
    @Override
    public void refreshData() {
        //Récupère l'utilisateur sélectionné dans la dropdown
        this.listReseaux.setModel(createDefaultModelListReseauCache());
        revalidate();
        repaint();
    }
}