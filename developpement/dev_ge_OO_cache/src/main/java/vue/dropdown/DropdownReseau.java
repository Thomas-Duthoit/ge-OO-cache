package vue.dropdown;

import modele.ReseauCache;
import modele.Utilisateur;
import requete.RequeteGeOOCache;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DropdownReseau extends JPanel {
    private RequeteGeOOCache requeteGeOOCache;
    private CardLayout cl;
    private JPanel dynamicArea;
    private JPanel mainPanel;
    private JComboBox<Object> cb;
    private String actionPrec;
    private Utilisateur user;

    public DropdownReseau(RequeteGeOOCache requeteGeOOCache, JPanel mainPanel, CardLayout cl, String actionPrec, Utilisateur user) throws SQLException {
        this.requeteGeOOCache = requeteGeOOCache;
        this.mainPanel = mainPanel;
        this.cl = cl;
        this.actionPrec = actionPrec;
        this.user = user;
        System.out.println(this.user);

        //Mise en forme
        this.setLayout(new FlowLayout(FlowLayout.LEFT));
        //Les différentes valeurs a attribué pour le dropdown
        List<ReseauCache> reseauxCache = this.requeteGeOOCache.getReseauxUtilisateur(this.user);
        System.out.println(reseauxCache);
        DefaultComboBoxModel<Object> cbModel = new DefaultComboBoxModel<>();
        cbModel.addElement("Choix d'un réseau");

        for (ReseauCache rc : reseauxCache){
            cbModel.addElement(rc);
        }

        //Création du dropdown
        this.cb = new JComboBox<>(cbModel);

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
            Object choixSelectionne = cb.getSelectedItem();
            System.out.println("Action selectionné : " + choixSelectionne);

            //Ajout d'autres dropdowns si nécessaire selon le choix
            dynamicArea.removeAll();

            try {
                if (choixSelectionne instanceof ReseauCache) {
                    if("Modifier le statut d'une cache".equals(actionPrec) || "Affichage de la liste des caches".equals(actionPrec)){
                        dynamicArea.add(new DropdownCache(this.requeteGeOOCache, this.mainPanel, this.cl, actionPrec));
                    }
                }

            }catch (SQLException ex) {
                System.out.println("ERREUR : Dropdown : " + ex.getMessage());
            }
            if("Modifier le statut d'une cache".equals(actionPrec)){
                System.out.println("Pas de nouveau affichage");
                cl.show(mainPanel, "Choix de l'interface");
            }else if("Affichage de la liste des caches".equals(actionPrec)){
                cl.show(mainPanel, "Liste des caches");
            }else {
                cl.show(mainPanel, actionPrec);
            }
            refresh();
        }
    }

    public ReseauCache getReseauCacheSelectionne(){
        return (ReseauCache) cb.getSelectedItem();
    }
}
