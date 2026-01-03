package vue.page;

import modele.StatutCache;
import requete.RequeteGeOOCache;
import vue.SelectionDropdown;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

public class UpdateStatutCache extends JPanel {

    RequeteGeOOCache requeteGeOOCache;
    SelectionDropdown selectionDropdown;

    private JComboBox<StatutCache> statutCacheCombo;
    private JLabel nomCache;

    public UpdateStatutCache(RequeteGeOOCache requeteGeOOCache, SelectionDropdown selectionDropdown) throws SQLException {
        super();
        this.setLayout(new BorderLayout());
        this.setBackground(Color.WHITE);

        JPanel panelModif = new JPanel();
        panelModif.setLayout(new BoxLayout(panelModif, BoxLayout.Y_AXIS));
        panelModif.setBackground(Color.WHITE);

        JPanel panelNom = new JPanel();
        panelNom.setLayout(new BorderLayout());

        nomCache = new JLabel("Cache n° X");

        panelNom.add(nomCache, BorderLayout.CENTER);
        panelNom.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));

        panelModif.add(panelNom);




        panelModif.add(Box.createVerticalStrut(15));

        JPanel panelCombo = new JPanel();
        panelCombo.setLayout(new BorderLayout());

        statutCacheCombo = new JComboBox<>(
                new DefaultComboBoxModel<>(
                        requeteGeOOCache.getStatutCache().toArray(new StatutCache[0])  // recuperer la lsite des sttus en BDD et en faire un combobox
                )
        );

        panelCombo.add(statutCacheCombo, BorderLayout.CENTER);
        panelCombo.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));


        panelModif.add(panelCombo);

        this.add(panelModif, BorderLayout.CENTER);

        this.add(Box.createHorizontalStrut(50), BorderLayout.WEST);  // espace à gauceS
        this.add(Box.createHorizontalStrut(50), BorderLayout.EAST);  // espace à droite
        this.add(Box.createVerticalStrut(50), BorderLayout.NORTH);  // espace en haut


        JButton btnCreer = new JButton("> Modifier");
        btnCreer.setBackground(Color.decode("#c8d400"));

        JPanel panelBtn = new JPanel();
        panelBtn.setLayout(new BorderLayout());
        panelBtn.setBorder(BorderFactory.createEmptyBorder(0, 0, 30, 30));  // bordure en bas et à droite
        panelBtn.setBackground(Color.WHITE);


        panelBtn.add(btnCreer, BorderLayout.EAST);  // à droite

        this.add(panelBtn, BorderLayout.SOUTH);  // le panel du bouton est en bas


        this.setVisible(true);
    }

}

