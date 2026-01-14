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

public class ShowReseauForCreation extends ShowReseauMere implements Refreshable {
    //Constructeurs par défaut
    public ShowReseauForCreation(RequeteGeOOCache requeteGeOOCache, Utilisateur utilisateur, JPanel mainPanel, CardLayout cl, SelectionDropdown selectionDropdown, ComboBoxGeneral comboBoxGeneral) throws SQLException {
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
        cardLayout.show(mainPanel, "Créer une cache");
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