package vue.page;

import modele.ReseauCache;
import modele.Utilisateur;
import requete.RequeteGeOOCache;
import vue.Refreshable;
import vue.SelectionDropdown;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.sql.SQLException;

public class ShowStatistic extends JPanel implements Refreshable {

    private RequeteGeOOCache requeteGeOOCache;
    private SelectionDropdown selectionDropdown;

    private ReseauCache reseauCache;
    private JLabel titre;
    private JLabel proprietaire;
    private JLabel nbCache;
    private JLabel nbUtilisateurs;
    private JLabel nbLogs;
    private JLabel nbTrouve;
    private JLabel nbNonTrouve;
    private JLabel pourcentage;

    private JPanel frame;

    public ShowStatistic(RequeteGeOOCache requeteGeOOCache, SelectionDropdown selectionDropdown) throws SQLException {
        super();

        this.requeteGeOOCache = requeteGeOOCache;
        this.selectionDropdown = selectionDropdown;

        this.setLayout(new BorderLayout());
        this.setBackground(Color.WHITE);

        this.setBorder(new EmptyBorder(50, 100, 50, 100));

        frame = new JPanel();
        frame.setLayout(new BorderLayout());  // solution trouvée sur internet pour avoir un FlowLayout vertical
        frame.setBorder(new EmptyBorder(10, 10, 10 ,10));

        JPanel panelTitre = new JPanel();
        panelTitre.setLayout(new BorderLayout());
        panelTitre.setBackground(Color.WHITE);
        titre = new JLabel("Quelques infos sur le réseau : Reseau X");
        titre.setFont(new Font(null, Font.PLAIN, 20));
        titre.setHorizontalAlignment(SwingConstants.CENTER);  // centré horizontalement

        panelTitre.add(titre, BorderLayout.NORTH);

        JPanel espace = new JPanel();
        espace.add(Box.createVerticalStrut(10));

        panelTitre.add(espace);

        frame.add(panelTitre, BorderLayout.NORTH);

        JPanel panelStats = new JPanel();
        panelStats.setLayout(new BoxLayout(panelStats, BoxLayout.Y_AXIS));
        panelStats.setAlignmentX(Component.LEFT_ALIGNMENT);  // pour que tout soit collé à gauche dans le panel
        panelStats.setBorder(new EmptyBorder(10, 10, 10 ,10));  // un peu depadding
        panelStats.setBackground(Color.WHITE);

        proprietaire = new JLabel("- Porprietaire : PROPRIETAIRE");
        proprietaire.setAlignmentX(Component.LEFT_ALIGNMENT);
        panelStats.add(proprietaire);

        nbCache = new JLabel("- Nombre de caches : nnn");
        nbCache.setAlignmentX(Component.LEFT_ALIGNMENT);
        panelStats.add(nbCache);

        nbUtilisateurs = new JLabel("- Nombre d'utilisateurs associés : nnn");
        nbUtilisateurs.setAlignmentX(Component.LEFT_ALIGNMENT);
        panelStats.add(nbUtilisateurs);

        nbLogs = new JLabel("- Nombre de visites/logs : nnn");
        nbLogs.setAlignmentX(Component.LEFT_ALIGNMENT);
        panelStats.add(nbLogs);

        nbTrouve = new JLabel("- Nombre de trouvailles : nnn");
        nbTrouve.setAlignmentX(Component.LEFT_ALIGNMENT);
        panelStats.add(nbTrouve);

        nbNonTrouve = new JLabel("- Nombre d'échec de trouvaille : nnn");
        nbNonTrouve.setAlignmentX(Component.LEFT_ALIGNMENT);
        panelStats.add(nbNonTrouve);

        pourcentage = new JLabel("- Pourcentage de trouvaille : ppp%");
        pourcentage.setAlignmentX(Component.LEFT_ALIGNMENT);
        panelStats.add(pourcentage);

        frame.add(panelStats, BorderLayout.CENTER);

        this.add(frame);

        this.setVisible(true);
    }

    @Override
    public void refreshData() {
        reseauCache = (ReseauCache) selectionDropdown.getElementSelect("Reseau");
        if(reseauCache != null) {
            System.out.println("Reseau : " + reseauCache);
            frame.setVisible(true);

            // TODO : update les textes

        } else {
            frame.setVisible(false);
        }
        revalidate();
        repaint();
    }
}