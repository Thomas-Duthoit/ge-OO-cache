package vue.page;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import modele.ReseauCache;
import modele.Utilisateur;
import requete.RequeteGeOOCache;
import vue.dropdown.DropdownReseau;
import vue.SelectionDropdown;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.sql.SQLException;

public class CreateCache extends JPanel {

    private RequeteGeOOCache req;

    // champs pour les informations de la cache:
    private JTextArea descriptionTextuelle;
    private JTextArea descriptionTechnique;
    private JTextArea rubriqueLibre;

    public CreateCache(RequeteGeOOCache requeteGeOOCache, SelectionDropdown selectionDropdown) throws SQLException {
        super();


        this.req = requeteGeOOCache;


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


        this.add(haut, BorderLayout.NORTH);

        this.setVisible(true);
    }

}
