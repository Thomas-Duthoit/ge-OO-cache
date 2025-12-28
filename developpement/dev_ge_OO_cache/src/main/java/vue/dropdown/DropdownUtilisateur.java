package vue.dropdown;

import requete.RequeteGeOOCache;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class DropdownUtilisateur extends JPanel {
    private RequeteGeOOCache requeteGeOOCache;
    private CardLayout cl;
    private JPanel dynamicArea;
    private JPanel mainPanel;
    private JComboBox<String> cb;
    private String actionPrec;

    public DropdownUtilisateur(RequeteGeOOCache requeteGeOOCache, JPanel mainPanel, CardLayout cl, String actionPrec) throws SQLException {
        this.requeteGeOOCache = requeteGeOOCache;
        this.mainPanel = mainPanel;
        this.cl = cl;
        this.actionPrec = actionPrec;

        //Mise en forme
        this.setLayout(new FlowLayout(FlowLayout.LEFT));
        //Les différentes valeurs a attribué pour le dropdown
        String[] choix = {
                "Choix des utilisateurs",
                "Utilisateur X"
        };

        //Création du dropdown
        this.cb = new JComboBox<>(choix);
        cb.setSelectedIndex(0); //Valeur par défaut au niveau des choix
        cb.setBackground(Color.LIGHT_GRAY);

        //Au niveau des changements de vue par rapport au choix
        cb.addActionListener(new ChoixActionListener(this.mainPanel, this.cl, this.requeteGeOOCache));

        this.add(cb);
        this.dynamicArea = new JPanel(new FlowLayout(FlowLayout.LEFT));
        this.add(dynamicArea);
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
            System.out.println("Action selectionné : " + choixSelectionne);

            //Ajout d'autres dropdowns si nécessaire selon le choix
            dynamicArea.removeAll();

            refresh();
            if (!choixSelectionne.equals("Choix d'un utilisateur")){
            //if(!"Associer un utilisateur".equals(actionPrec)) {
                cl.show(mainPanel, actionPrec);
            }else{
                cl.show(mainPanel, "Choix de l'interface");
            }
        }
    }

}
