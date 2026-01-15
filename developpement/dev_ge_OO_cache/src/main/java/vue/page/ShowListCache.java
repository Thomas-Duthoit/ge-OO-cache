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
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

public class ShowListCache extends JPanel implements Refreshable {

    private RequeteGeOOCache requeteGeOOCache;
    private SelectionDropdown selectionDropdown;

    //Pour modifier la vue
    private CardLayout cl;
    private JPanel mainPanel;
    private ComboBoxGeneral comboBoxGeneral;

    // la liste des caches car si on change de réseau on veut que ça change
    private JList<Cache> listeCaches;

    public ShowListCache(RequeteGeOOCache requeteGeOOCache, Utilisateur utilisateur, SelectionDropdown selectionDropdown, CardLayout cl, JPanel mainPanel, ComboBoxGeneral comboBoxGeneral) throws SQLException {
        super();


        this.requeteGeOOCache = requeteGeOOCache;
        this.selectionDropdown = selectionDropdown;
        this.cl = cl;
        this.mainPanel = mainPanel;
        this.comboBoxGeneral = comboBoxGeneral;




        this.setLayout(new BorderLayout());
        this.setBackground(Color.WHITE);
        this.setBorder(new EmptyBorder(50, 100, 50, 100));



        this.listeCaches = new JList<>();
        this.listeCaches.setModel(createModelJListCache(null));

        this.listeCaches.setCellRenderer(new CacheListCellRenderer());

        this.listeCaches.addMouseListener(new ShowListCache.MouseCacheListener(selectionDropdown));


        JPanel bordure = new JPanel(new BorderLayout());
        bordure.setBorder(new EmptyBorder(2, 2, 2, 2));

        JScrollPane scrollPane = new JScrollPane(listeCaches);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());

        bordure.add(scrollPane, BorderLayout.CENTER);


        this.add(bordure);


        this.setVisible(true);
    }

    @Override
    public void refreshData() {
        ReseauCache reseauCache = (ReseauCache) selectionDropdown.getElementSelect("Reseau");
        System.out.println("Reseau : " + reseauCache);
        this.listeCaches.setModel(createModelJListCache(reseauCache));


        revalidate();
        repaint();
    }


    /**
     * Méthode : createModelJListCache
     * -------
     * permet de créer le modèle par défaut de la JList des caches selon le réseau
     * @param reseau : utilisateur connecté à l'application
     * @return DefaultListModel<Cache> le modèle par défaut de la JList des caches
     */
    public DefaultListModel<Cache> createModelJListCache(ReseauCache reseau){
        /**
         * Les étapes de création de la JList
         * 1. Récupérer la liste des caches avec une requête
         * 2. Créer la JList
         */
        //Etape 1
        List<Cache> listeCaches = this.requeteGeOOCache.getCachesByReseauCache(reseau);

        //Etape 2
        JList<Cache> cacheJList = new JList<>();
        DefaultListModel<Cache> listeCacheModel = new DefaultListModel<>();
        for(Cache cache : listeCaches){
            listeCacheModel.addElement(cache);
        }
        return listeCacheModel;
    }

    // classe interne à la vue car elle y est spécifique
    // Permet de réagir à un double clic sur un élément de la JList des caches : change de vue pour celle du détails du cache
    public class MouseCacheListener implements MouseListener {
        private SelectionDropdown selectionDropdown;

        public MouseCacheListener(SelectionDropdown selectionDropdown){
            this.selectionDropdown = selectionDropdown;
        }


        // utilisée pour le changement des vues
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

        @Override
        public void mouseClicked(MouseEvent e) {
            if(e.getClickCount() == 2){
                System.out.println("Passage vers le détail d'une cache");
                JList caches = (JList) e.getSource();
                System.out.println(caches);
                Cache selected =  (Cache) caches.getSelectedValue();
                System.out.println(selected);
                this.selectionDropdown.addElementSelect("Cache", selected);

                System.out.println(this.selectionDropdown.getElementSelect("Cache"));

                cl.show(mainPanel, "détails caches");
                refreshDataView(); //Permet d'activer la méthode refreshData de la vue d'affichage des détails d'une cache

                comboBoxGeneral.refreshComboBoxCache();
            }
        }

        @Override
        public void mousePressed(MouseEvent e) {

        }

        @Override
        public void mouseReleased(MouseEvent e) {

        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }
    }

    public class CacheListCellRenderer extends DefaultListCellRenderer {

        @Override
        public Component getListCellRendererComponent(
                JList<?> list,
                Object value,
                int index,
                boolean isSelected,
                boolean cellHasFocus) {

            JLabel label = (JLabel) super.getListCellRendererComponent(
                    list, value, index, isSelected, cellHasFocus);

            if (value instanceof Cache) {
                Cache cache = (Cache) value;

                label.setText(cache.toString());

                label.setFont(new Font("Consolas", Font.BOLD, 24));

                label.setBackground(Color.WHITE);
                label.setForeground(Color.DARK_GRAY);

                label.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
            }

            return label;
        }
    }

}