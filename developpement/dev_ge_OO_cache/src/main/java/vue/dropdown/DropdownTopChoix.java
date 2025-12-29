package vue.dropdown;

import modele.Utilisateur;
import requete.RequeteGeOOCache;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class DropdownTopChoix extends JPanel {
    private CardLayout cl;
    private JPanel mainPanel;
    private JPanel dynamicArea;
    private RequeteGeOOCache requeteGeOOCache;
    private JComboBox<String> cb;
    private Utilisateur user;

    public DropdownTopChoix(RequeteGeOOCache requeteGeOOCache, JPanel mainPanel, CardLayout cl, Utilisateur user) throws SQLException {
        super();

        //Initialisation des attributs
        this.requeteGeOOCache = requeteGeOOCache;
        this.cl = cl;
        this.mainPanel = mainPanel;
        this.user = user;

        //Mise en forme
        this.setLayout(new FlowLayout(FlowLayout.LEFT));
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

            try {
                if ("Associer un utilisateur".equals(choixSelectionne)) {
                    dynamicArea.add(new DropdownUtilisateur(this.requeteGeOOCache, this.mainPanel, this.cl, choixSelectionne));
                }
                if ("Afficher les statistiques".equals(choixSelectionne) ||"Affichage de la liste des caches".equals(choixSelectionne) || "Créer une cache".equals(choixSelectionne) || "Modifier le statut d'une cache".equals(choixSelectionne)) {
                    dynamicArea.add(new DropdownReseau(this.requeteGeOOCache, this.mainPanel, this.cl, choixSelectionne, user));
                }
                if("Afficher les loggins".equals(choixSelectionne)) {
                    dynamicArea.add(new DropdownLoggings(this.requeteGeOOCache, this.mainPanel, this.cl, choixSelectionne));
                }
            }catch (SQLException ex) {
                System.out.println("ERREUR : Dropdown : " + ex.getMessage());
            }
            refresh();

            if("Affichage de la liste des caches".equals(choixSelectionne) || "Créer une cache".equals(choixSelectionne) || "Modifier le statut d'une cache".equals(choixSelectionne) || "Associer un utilisateur".equals(choixSelectionne) || "Afficher les statistiques".equals(choixSelectionne)) {
                System.out.println("Pas de changement de page à effectuer pour le moment");
                cl.show(mainPanel, "Choix de l'interface");
            }else {
                cl.show(mainPanel, choixSelectionne);
            }
        }
    }

}
