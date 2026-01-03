package vue.page;

import modele.Cache;
import modele.StatutCache;
import requete.RequeteGeOOCache;
import vue.SelectionDropdown;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class UpdateStatutCache extends JPanel {

    private RequeteGeOOCache requeteGeOOCache;
    private SelectionDropdown selectionDropdown;

    private JComboBox<StatutCache> statutCacheCombo;
    private JLabel nomCache;

    public UpdateStatutCache(RequeteGeOOCache requeteGeOOCache, SelectionDropdown selectionDropdown) throws SQLException {
        super();
        this.setLayout(new BorderLayout());
        this.setBackground(Color.WHITE);

        this.requeteGeOOCache = requeteGeOOCache;
        this.selectionDropdown = selectionDropdown;

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
        btnCreer.addActionListener(new UpdateStatutActionListener(requeteGeOOCache, statutCacheCombo, selectionDropdown));

        JPanel panelBtn = new JPanel();
        panelBtn.setLayout(new BorderLayout());
        panelBtn.setBorder(BorderFactory.createEmptyBorder(0, 0, 30, 30));  // bordure en bas et à droite
        panelBtn.setBackground(Color.WHITE);


        panelBtn.add(btnCreer, BorderLayout.EAST);  // à droite

        this.add(panelBtn, BorderLayout.SOUTH);  // le panel du bouton est en bas


        this.setVisible(true);
    }


    // classe interne à la vue car elle y est spécifique
    class UpdateStatutActionListener implements ActionListener {

        private SelectionDropdown sel;
        private JComboBox<StatutCache> statut;
        private RequeteGeOOCache req;

        public UpdateStatutActionListener(RequeteGeOOCache req, JComboBox<StatutCache> statut, SelectionDropdown sel) {
            this.req = req;  // on récupère l'instance pour les requêtes
            this.sel = sel;
            this.statut = statut;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("Cache : " + sel.getElementSelect("Cache"));
            System.out.println("Statut : " + statut.getSelectedItem());

            req.updateStatutCache(
                    ((Cache) sel.getElementSelect("Cache")).getNumero(),
                    ((StatutCache) statut.getSelectedItem()).getId());
        }
    }
}

