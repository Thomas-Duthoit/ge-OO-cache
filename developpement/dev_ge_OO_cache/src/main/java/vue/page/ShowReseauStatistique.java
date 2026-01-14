package vue.page;

import modele.ReseauCache;
import modele.Utilisateur;
import requete.RequeteGeOOCache;
import vue.Refreshable;
import vue.SelectionDropdown;
import vue.dropdown.ComboBoxGeneral;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

public class ShowReseauStatistique extends ShowReseauMere implements Refreshable {
    //Constructeurs par défaut
    public ShowReseauStatistique(RequeteGeOOCache requeteGeOOCache, Utilisateur utilisateur, JPanel mainPanel, CardLayout cl, SelectionDropdown selectionDropdown, ComboBoxGeneral comboBoxGeneral) throws SQLException {
        super(requeteGeOOCache, utilisateur, mainPanel, cl,  selectionDropdown, comboBoxGeneral);
    }

    /**
     * actionClick
     * ------------------
     * permet de passer à l'affichage de la liste des caches au moment d'un double click sur une des lignes de la JList
     * @param reseauCache : le reseauCache sélectionné
     */

    @Override
    public void actionClick(ReseauCache reseauCache) {
        cardLayout.show(mainPanel, "Afficher les statistiques");
        refreshDataView(); //Permet d'activer la méthode refreshData de la vue d'affichage de la liste des caches
        comboBoxGeneral.refreshComboBoxReseau();
    }

    /**
     * méthode : createPanelRenderer
     * -----------------------
     * permet de régler l'affichage des différents éléments de la JList
     * @param reseauCache : reseauCache de la ligne courante
     * @return JPanel correspondant au design de la ligne
     */
    @Override
    public JPanel createPanelRenderer(ReseauCache reseauCache){
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BorderLayout());
        leftPanel.setBackground(Color.WHITE);

        JLabel leftLabel = new JLabel();
        leftLabel.setText(" > " + reseauCache.getNom());
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
        rightLabel.setText("( Nbr de caches : " + requeteGeOOCache.getStatNbCaches(reseauCache) + " )");
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

}
