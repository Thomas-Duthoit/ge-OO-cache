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

public class CreateCache extends JPanel implements Refreshable {

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

        Font font = new Font("Consolas", Font.PLAIN, 24);


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
        descriptionTextuelle.setFont(font);
        descriptionTechnique = new JTextArea(10, 20);
        descriptionTechnique.setFont(font);
        rubriqueLibre = new JTextArea(10, 20);
        rubriqueLibre.setFont(font);

        textuellePanel.add(descriptionTextuelle, BorderLayout.CENTER);
        JLabel lab1 = new JLabel("Description textuelle");
        lab1.setFont(font);
        textuellePanel.add(lab1, BorderLayout.NORTH);
        techniquePanel.add(descriptionTechnique, BorderLayout.CENTER);
        JLabel lab2 = new JLabel("Description technique");
        lab2.setFont(font);
        techniquePanel.add(lab2, BorderLayout.NORTH);
        librePanel.add(rubriqueLibre, BorderLayout.CENTER);
        JLabel lab3 = new JLabel("Rubrique libre");
        lab3.setFont(font);
        librePanel.add(lab3, BorderLayout.NORTH);

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

        this.statutCacheCombo.setFont(font);
        this.statutCacheCombo.setBackground(Color.decode("#e6e6e6"));
        this.typeCacheCombo.setFont(font);
        this.typeCacheCombo.setBackground(Color.decode("#e6e6e6"));

        typePanel.setLayout(new BorderLayout());
        JLabel lab4 = new JLabel("Type cache : ");
        lab4.setFont(font);
        typePanel.add(lab4, BorderLayout.WEST);
        typePanel.add(typeCacheCombo, BorderLayout.EAST);

        statutPanel.setLayout(new BorderLayout());
        JLabel lab5 =new JLabel("Statut cache : ");
        lab5.setFont(font);
        statutPanel.add(lab5, BorderLayout.WEST);
        statutPanel.add(statutCacheCombo, BorderLayout.EAST);


        JPanel locPanel = new JPanel();
        JLabel lab6 = new JLabel("Coordonnées GPS : ");
        lab6.setFont(font);
        locPanel.add(lab6);
        localisation = new JTextField();
        localisation.setFont(font);
        localisation.setPreferredSize(new Dimension(300, 40));
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
        btnCreer.setPreferredSize(new Dimension(180, 45));
        btnCreer.setMaximumSize(new Dimension(180, 45));
        btnCreer.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        btnCreer.setFont(font);

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

    @Override
    public void refreshData() {
        System.out.println("Refresh de CreateCache");
        statutCacheCombo.setModel(
                new DefaultComboBoxModel<>(
                        req.getStatutCache().toArray(new StatutCache[0])  // recuperer la lsite des sttus en BDD et en faire un combobox
                )
        );

        typeCacheCombo.setModel(
                new DefaultComboBoxModel<>(
                        req.getTypeCache().toArray(new TypeCache[0])  // recuperer la lsite des types en BDD et en faire un combobox
                )
        );

        revalidate();
        repaint();
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
