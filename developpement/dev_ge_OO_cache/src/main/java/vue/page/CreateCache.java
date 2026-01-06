package vue.page;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import modele.ReseauCache;
import modele.StatutCache;
import modele.TypeCache;
import modele.Utilisateur;
import requete.RequeteGeOOCache;
import vue.Refreshable;
import requete.RequeteGeOOCache;
import vue.SelectionDropdown;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Type;
import java.sql.SQLException;

public class CreateCache extends JPanel {

    private RequeteGeOOCache req;

    // champs pour les informations de la cache:
    private JTextArea descriptionTextuelle;
    private JTextArea descriptionTechnique;
    private JTextArea rubriqueLibre;
    private JComboBox<TypeCache> typeCacheCombo;
    private JComboBox<StatutCache> statutCacheCombo;
    private JTextField localisation;
    private SelectionDropdown selectionDropdown;

    public CreateCache(RequeteGeOOCache requeteGeOOCache, SelectionDropdown selectionDropdown) throws SQLException {
        super();


        this.req = requeteGeOOCache;
        this.selectionDropdown = selectionDropdown;


        this.setLayout(new BorderLayout());
        this.setBackground(Color.WHITE);


        JPanel haut = new JPanel();  // pour les trois text areas
        haut.setLayout(new BorderLayout());
        haut.setBackground(Color.WHITE);

        JPanel textuellePanel = new JPanel();  // pour les bordures
        JPanel techniquePanel = new JPanel();  // pour les bordures
        JPanel librePanel = new JPanel();  // pour les bordures

        textuellePanel.setLayout(new BorderLayout());
        techniquePanel.setLayout(new BorderLayout());
        librePanel.setLayout(new BorderLayout());

        textuellePanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        techniquePanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        librePanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        descriptionTextuelle = new JTextArea(10, 20);
        descriptionTechnique = new JTextArea(10, 20);
        rubriqueLibre = new JTextArea(10, 20);

        textuellePanel.add(descriptionTextuelle, BorderLayout.CENTER);
        textuellePanel.add(new JLabel("Description textuelle"), BorderLayout.NORTH);
        techniquePanel.add(descriptionTechnique, BorderLayout.CENTER);
        techniquePanel.add(new JLabel("Description technique"), BorderLayout.NORTH);
        librePanel.add(rubriqueLibre, BorderLayout.CENTER);
        librePanel.add(new JLabel("Rubrique libre"), BorderLayout.NORTH);

        JPanel milieu = new JPanel();  // on le laisse en FlowLayout pour avoir les text area comme on veut
        milieu.setBackground(Color.WHITE);

        milieu.add(textuellePanel);
        milieu.add(techniquePanel);
        milieu.add(librePanel);
        haut.add(milieu, BorderLayout.CENTER);
        haut.add(Box.createVerticalStrut(40), BorderLayout.NORTH);  // pour rajouter de l'espace en haut


        JPanel typePanel = new JPanel();
        JPanel statutPanel = new JPanel();

        statutCacheCombo = new JComboBox<>(
                new DefaultComboBoxModel<>(
                        req.getStatutCache().toArray(new StatutCache[0])  // recuperer la lsite des sttus en BDD et en faire un combobox
                )
        );
        typeCacheCombo = new JComboBox<>(
                new DefaultComboBoxModel<>(
                        req.getTypeCache().toArray(new TypeCache[0])  // recuperer la lsite des types en BDD et en faire un combobox
                )
        );

        typePanel.setLayout(new BorderLayout());
        typePanel.add(new JLabel("Type cache : "), BorderLayout.WEST);
        typePanel.add(typeCacheCombo, BorderLayout.EAST);

        statutPanel.setLayout(new BorderLayout());
        statutPanel.add(new JLabel("Statut cache : "), BorderLayout.WEST);
        statutPanel.add(statutCacheCombo, BorderLayout.EAST);


        JPanel locPanel = new JPanel();
        locPanel.add(new JLabel("Coordonnées GPS : "));
        localisation = new JTextField();
        localisation.setPreferredSize(new Dimension(100, 20));
        locPanel.add(localisation);


        JPanel comboPanel = new JPanel();
        comboPanel.setBackground(Color.WHITE);
        comboPanel.add(typePanel);
        comboPanel.add(statutPanel);
        comboPanel.add(locPanel);

        this.add(haut, BorderLayout.NORTH);
        this.add(comboPanel, BorderLayout.CENTER);

        JButton btnCreer = new JButton("> Créer");
        btnCreer.setBackground(Color.decode("#c8d400"));
        btnCreer.addActionListener(new CreerCacheActionListener(
                this.req,
                descriptionTextuelle,
                descriptionTechnique,
                rubriqueLibre,
                typeCacheCombo,
                statutCacheCombo,
                localisation,
                selectionDropdown
        ));

        JPanel panelBtn = new JPanel();
        panelBtn.setLayout(new BorderLayout());
        panelBtn.setBorder(BorderFactory.createEmptyBorder(0, 0, 30, 30));  // bordure en bas et à droite
        panelBtn.setBackground(Color.WHITE);

        panelBtn.add(btnCreer, BorderLayout.EAST);  // à droite

        this.add(panelBtn, BorderLayout.SOUTH);  // le panel du bouton est en bas

        this.setVisible(true);
    }




    // classe interne car utile juste ici
    class CreerCacheActionListener implements ActionListener {

        private JTextArea descriptionTextuelle;
        private JTextArea descriptionTechnique;
        private JTextArea rubriqueLibre;
        private JComboBox<TypeCache> typeCacheCombo;
        private JComboBox<StatutCache> statutCacheCombo;
        private JTextField localisation;
        private SelectionDropdown sel;

        private RequeteGeOOCache req;

        public CreerCacheActionListener(RequeteGeOOCache req, JTextArea txt, JTextArea tech, JTextArea libre, JComboBox<TypeCache> type, JComboBox<StatutCache> statut, JTextField localisation, SelectionDropdown sel) {
            this.req = req;  // on récupère l'instance pour les requêtes
            this.descriptionTextuelle = txt;
            this.descriptionTechnique = tech;
            this.rubriqueLibre = libre;
            this.typeCacheCombo = type;
            this.statutCacheCombo = statut;
            this.localisation = localisation;
            this.sel = sel;
        }

        @Override
        public void actionPerformed(ActionEvent e) {

            System.out.println("Type : " + this.typeCacheCombo.getSelectedItem());
            System.out.println("Statut : " + this.statutCacheCombo.getSelectedItem());
            System.out.println("Reseau : " + sel.getElementSelect("Reseau"));

            if (
            req.creerCache(
                    this.descriptionTextuelle.getText(),
                    this.descriptionTechnique.getText(),
                    this.rubriqueLibre.getText(),
                    this.localisation.getText(),
                    (TypeCache) this.typeCacheCombo.getSelectedItem(),
                    (StatutCache) this.statutCacheCombo.getSelectedItem(),
                    (ReseauCache) sel.getElementSelect("Reseau")
            )
            ) {
                // création réussie -> on vide les inputs
                System.out.println("Cache créée");
                this.descriptionTextuelle.setText("");
                this.descriptionTechnique.setText("");
                this.rubriqueLibre.setText("");
                this.localisation.setText("");
            }
        }
    }
}
