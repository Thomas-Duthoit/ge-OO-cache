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

/**
 * Classe ShowReseau
 * Une classe correspondant à une page de l'application
 * Elle permet l'affichage de la liste des reseaux de cache dont l'utilisateur est propriétaire ou associé à
 */
public class ShowReseauForCachePart extends ShowReseauMere implements Refreshable{

    //Constructeurs par défaut
    public ShowReseauForCachePart(RequeteGeOOCache requeteGeOOCache, Utilisateur utilisateur, JPanel mainPanel, CardLayout cl, SelectionDropdown selectionDropdown, ComboBoxGeneral comboBoxGeneral) throws SQLException {
        super(requeteGeOOCache, utilisateur, mainPanel, cl,  selectionDropdown, comboBoxGeneral);
    }

    @Override
    public void actionClick(ReseauCache reseauCache) {
        selectionDropdown.addElementSelect("Action", "Affichage de la liste des caches");
        cardLayout.show(mainPanel, "Affichage de la liste des caches");
        refreshDataView(); //Permet d'activer la méthode refreshData de la vue d'affichage de la liste des caches
        comboBoxGeneral.refreshComboBoxAction();
        comboBoxGeneral.refreshComboBoxReseau();
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
        rightLabel.setText("( Nbr de caches : " + requeteGeOOCache.getStatNbCaches(reseauCache) + " )");
        rightLabel.setForeground(Color.BLACK);
        rightLabel.setFont(new Font("Consolas", Font.PLAIN, 15));

        panel.add(leftLabel, BorderLayout.WEST);
        panel.add(rightLabel, BorderLayout.EAST);

        return panel;
    }
}