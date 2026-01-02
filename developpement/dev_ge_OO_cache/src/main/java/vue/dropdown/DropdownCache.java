package vue.dropdown;

import modele.Cache;
import modele.ReseauCache;
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

public class DropdownCache extends JPanel{
    private RequeteGeOOCache requeteGeOOCache;
    private CardLayout cl;
    private JPanel mainPanel;
    private JComboBox<Object> cb;
    private String actionPrec;
    private SelectionDropdown selectionDropdown;

    public DropdownCache(RequeteGeOOCache requeteGeOOCache, JPanel mainPanel, CardLayout cl, String actionPrec, ReseauCache reseauCache, SelectionDropdown selectionDropdown) throws SQLException {
        this.requeteGeOOCache = requeteGeOOCache;
        this.mainPanel = mainPanel;
        this.cl = cl;
        this.actionPrec = actionPrec;
        this.selectionDropdown = selectionDropdown;

        //Mise en forme
        this.setLayout(new FlowLayout(FlowLayout.LEFT));

        //Création du dropdown
        this.cb = new JComboBox<>();

        //Les différentes valeurs a attribué pour le dropdown
        //Récupère la liste des caches selon le reseauCache
        List<Cache> caches = this.requeteGeOOCache.getCachesByReseauCache(reseauCache);

        //Crée le modèle pour le JComboBoc
        DefaultComboBoxModel<Object> model = new DefaultComboBoxModel<>();

        model.addElement("Choix des caches");

        for (Cache cache : caches) {
            model.addElement(cache);
        }
        this.cb.setModel(model);

        cb.setSelectedIndex(0); //Valeur par défaut au niveau des choix
        cb.setBackground(Color.LIGHT_GRAY);
        cb.addActionListener(new ChoixActionListener(this.mainPanel, this.cl, this.requeteGeOOCache));

        this.add(cb);
        this.setVisible(true);

    }

    public void refresh() {
        revalidate();
        repaint();
    }

    public class  ChoixActionListener implements ActionListener {
        private JPanel mainPanel;
        private CardLayout cl;
        private RequeteGeOOCache requeteGeOOCache;

        public ChoixActionListener(JPanel mainPanel, CardLayout cl, RequeteGeOOCache requeteGeOOCache) {
            this.mainPanel = mainPanel;
            this.cl = cl;
            this.requeteGeOOCache = requeteGeOOCache;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            JComboBox<String> cb = (JComboBox<String>) e.getSource();
            String choixSelectionne = cb.getSelectedItem().toString();

            if (!"Choix des caches".equals(choixSelectionne)) {
                selectionDropdown.addElementSelect("Cache", cb.getSelectedItem());
                System.out.println("Choix des caches : " + choixSelectionne);
                System.out.println(actionPrec);
                cl.show(mainPanel, actionPrec);
                refreshDataView();

            }else{
                selectionDropdown.supprElementSelect("Cache");
                cl.show(mainPanel, "Choix de l'interface");
            }
            refresh();
        }
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

}
