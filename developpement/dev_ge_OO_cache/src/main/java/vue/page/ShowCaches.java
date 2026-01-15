package vue.page;

import modele.Cache;
import requete.RequeteGeOOCache;
import vue.Refreshable;
import vue.SelectionDropdown;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.sql.SQLException;

public class ShowCaches extends JPanel implements Refreshable {

    private RequeteGeOOCache requeteGeOOCache;
    private SelectionDropdown selectionDropdown;

    private JLabel nomCache;
    private JTextArea descriptionTextuelle;
    private JTextArea descriptionTechnique;
    private JTextArea rubriqueLibre;

    private JLabel typeLabel;
    private JLabel statutLabel;
    private JLabel locLabel;

    public ShowCaches(RequeteGeOOCache requeteGeOOCache, SelectionDropdown selectionDropdown) throws SQLException {
        super();


        this.requeteGeOOCache = requeteGeOOCache;
        this.selectionDropdown = selectionDropdown;


        this.setBackground(Color.WHITE);

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));  // comme un flow layout mais en mode vertical

        this.setBorder(new EmptyBorder(40, 30, 0 ,30));

        Font font = new Font("Consolas", Font.PLAIN, 24);

        JPanel haut = new JPanel();
        haut.setMaximumSize(new Dimension(Integer.MAX_VALUE, 80));  // limiter la hauteur
        haut.setLayout(new BorderLayout());

        nomCache = new JLabel("Cache n°X", SwingConstants.CENTER);
        nomCache.setFont(font);
        haut.add(nomCache, BorderLayout.CENTER);



        JPanel milieu = new JPanel();
        milieu.setLayout(new GridLayout(1, 3, 20, 0)); // 1 ligne, 3 colonnes, espace de 20px entre chauee colonne
        milieu.setBackground(Color.WHITE);

        descriptionTextuelle = new JTextArea("Description Textuelle", 10, 20);
        descriptionTextuelle.setFont(font);
        descriptionTechnique = new JTextArea("Description Technique", 10, 20);
        descriptionTechnique.setFont(font);

        rubriqueLibre = new JTextArea("Rubrique Libre", 10, 20);
        rubriqueLibre.setFont(font);

        descriptionTextuelle.setEditable(false);  // pour que ça soit juste pour l'affichage du texte
        descriptionTechnique.setEditable(false);  // pour que ça soit juste pour l'affichage du texte
        rubriqueLibre.setEditable(false);  // pour que ça soit juste pour l'affichage du texte

        JPanel textuellePanel = new JPanel();  // pour les bordures
        textuellePanel.setFont(font);
        JPanel techniquePanel = new JPanel();  // pour les bordures
        techniquePanel.setFont(font);
        JPanel librePanel = new JPanel();  // pour les bordures
        librePanel.setFont(font);

        textuellePanel.setLayout(new BorderLayout());
        techniquePanel.setLayout(new BorderLayout());
        librePanel.setLayout(new BorderLayout());

        textuellePanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        techniquePanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        librePanel.setBorder(new EmptyBorder(10, 10, 10, 10));

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


        milieu.add(textuellePanel);
        milieu.add(techniquePanel);
        milieu.add(librePanel);


        JPanel bas = new JPanel();
        bas.setBackground(Color.WHITE);

        JPanel typePanel = new JPanel();
        JPanel statutPanel = new JPanel();
        JPanel locPanel = new JPanel();

        typePanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));  // limiter la hauteur
        statutPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));  // limiter la hauteur
        locPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));  // limiter la hauteur

        typeLabel = new JLabel("TYPE");
        typeLabel.setFont(font);
        statutLabel = new JLabel("STATUT");
        statutLabel.setFont(font);
        locLabel = new JLabel("LOCALISATION");
        locLabel.setFont(font);

        typeLabel.setBorder(new EmptyBorder(10, 10, 10, 10));
        statutLabel.setBorder(new EmptyBorder(10, 10, 10, 10));
        locLabel.setBorder(new EmptyBorder(10, 10, 10, 10));

        typePanel.add(typeLabel);
        statutPanel.add(statutLabel);
        locPanel.add(locLabel);

        bas.add(typePanel);
        bas.add(Box.createHorizontalStrut(50));  // ecart
        bas.add(statutPanel);
        bas.add(Box.createHorizontalStrut(50));  // ecart
        bas.add(locPanel);


        this.add(haut);
        this.add(Box.createVerticalStrut(40));  // ecart
        this.add(milieu);
        this.add(Box.createVerticalStrut(40));  // ecart
        this.add(bas);


        this.setVisible(true);
    }

    @Override
    public void refreshData() {
        Cache cache = (Cache) selectionDropdown.getElementSelect("Cache");

        nomCache.setText("Cache n°" + cache.getNumero());

        descriptionTextuelle.setText(cache.getDescriptionTextuelle());
        descriptionTechnique.setText(cache.getDescriptionTechnique());
        rubriqueLibre.setText(cache.getRubriqueLibre());
        locLabel.setText("Localisation : " + cache.getInformationsGeolocalisation());
        typeLabel.setText("Type : " + requeteGeOOCache.getTypeCache(cache));
        statutLabel.setText("Statut : " + requeteGeOOCache.getStatutCache(cache));

    }
}