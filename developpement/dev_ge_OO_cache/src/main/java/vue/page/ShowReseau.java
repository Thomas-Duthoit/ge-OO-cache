package vue.page;

import modele.ReseauCache;
import modele.Utilisateur;
import requete.RequeteGeOOCache;
import vue.SelectionDropdown;
import vue.dropdown.ComboBoxGeneral;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

public class ShowReseau extends ShowReseauMere {
    public ShowReseau(RequeteGeOOCache requeteGeOOCache, Utilisateur utilisateur, JPanel mainPanel, CardLayout cl, SelectionDropdown selectionDropdown, ComboBoxGeneral comboBoxGeneral) throws SQLException {
        super(requeteGeOOCache, utilisateur, mainPanel, cl, selectionDropdown, comboBoxGeneral);
    }

    @Override
    public void actionClick(ReseauCache reseauCache){
        cardLayout.show(mainPanel, "Affichage gestion utilisateur");
        refreshDataView(); //Permet d'activer la méthode refreshData de la vue d'affichage de la liste des caches
    }

    @Override
    public JPanel createPanelRenderer(ReseauCache reseauCache){
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        JLabel leftLabel = new JLabel();
        leftLabel.setText(reseauCache.getNom());
        leftLabel.setForeground(Color.BLACK);
        leftLabel.setFont(new Font("Consolas", Font.BOLD, 15));

        JLabel rightLabel = new JLabel();
        rightLabel.setText("( Nbr utilisateur : " + requeteGeOOCache.getStatNbUtilisateurs(reseauCache) + " | Nbr de caches : " + requeteGeOOCache.getStatNbCaches(reseauCache) + " )");
        rightLabel.setForeground(Color.BLACK);
        rightLabel.setFont(new Font("Consolas", Font.PLAIN, 15));

        panel.add(leftLabel, BorderLayout.WEST);
        panel.add(rightLabel, BorderLayout.EAST);

        return panel;
    }

}
