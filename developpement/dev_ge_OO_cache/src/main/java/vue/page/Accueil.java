package vue.page;

import vue.FenetrePrincipal;
import vue.Refreshable;
import vue.SelectionDropdown;
import vue.dropdown.ComboBoxGeneral;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;

/**
 * Classe Accueil
 * Une classe correspondant à une page de l'application
 * correspond à la page d'accueil d'une application
 */
public class Accueil extends JPanel {
    private FenetrePrincipal fenetrePrincipal;
    private SelectionDropdown sel;
    private CardLayout cl;
    private JPanel mainPanel;
    private ComboBoxGeneral cb;

    //Constructeur par données
    public Accueil(FenetrePrincipal fenetrePrincipal, SelectionDropdown sel, CardLayout cl, JPanel mainPanel, ComboBoxGeneral cb) throws SQLException {
        super();
        this.fenetrePrincipal = fenetrePrincipal;
        this.sel = sel;
        this.cl = cl;
        this.mainPanel = mainPanel;
        this.cb = cb;

        this.setLayout(new BorderLayout());
        this.setBackground(Color.WHITE);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.WHITE);

        //Image
        try {
            BufferedImage img = ImageIO.read(getClass().getResourceAsStream("/logo.png"));
            Image scaledImg = img.getScaledInstance(800, 400, Image.SCALE_SMOOTH);
            JLabel imageLabel = new JLabel(new ImageIcon(scaledImg));
            imageLabel.setAlignmentX(CENTER_ALIGNMENT);
            panel.add(imageLabel, BorderLayout.CENTER);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        JPanel boutonsHaut = new JPanel();
        boutonsHaut.setBackground(Color.WHITE);

        String[] choixHaut = {
                "Affichage des réseaux",
                "Affichage de la liste des caches",
                "Affichage des statistiques",
                "Affichage des logs",
        };

        for (String nav : choixHaut) {
            boutonsHaut.add(createButtonNav(nav));
        }

        Font fontTitre = new Font(null, Font.BOLD, 32);

        JLabel titreAffichage = new JLabel("> Affichage");
        titreAffichage.setAlignmentX(CENTER_ALIGNMENT);
        titreAffichage.setFont(fontTitre);

        panel.add(titreAffichage);
        panel.add(boutonsHaut);

        JPanel boutonsMilieu = new JPanel();
        boutonsMilieu.setBackground(Color.WHITE);

        String[] choixMilieu = {
                "Créer un réseau",
                "Créer une cache",
                "Créer un type",
                "Modifier le statut d'une cache",
        };

        for (String nav : choixMilieu) {
            boutonsMilieu.add(createButtonNav(nav));
        }


        JLabel titreGestion = new JLabel("> Gestion");
        titreGestion.setAlignmentX(CENTER_ALIGNMENT);
        titreGestion.setFont(fontTitre);

        panel.add(titreGestion);
        panel.add(boutonsMilieu);

        JPanel boutonsBas = new JPanel();
        boutonsBas.setBackground(Color.WHITE);

        String[] choixBas = {
                "Créer un utilisateur",
                "Associer un utilisateur",
        };

        for (String nav : choixBas) {
            boutonsBas.add(createButtonNav(nav));
        }

        JLabel titreUsers = new JLabel("> Utilisateurs");
        titreUsers.setAlignmentX(CENTER_ALIGNMENT);
        titreUsers.setFont(fontTitre);

        panel.add(titreUsers);
        panel.add(boutonsBas);

        this.add(panel, BorderLayout.NORTH);

        //Bouton de déconnexion

        this.add(createButtonDeconnexion(), BorderLayout.SOUTH);

        this.setVisible(true);
    }

    /**
     *          METHODES
     */

    /**
     * Méthode : createButtonDeconnexion
     * ------
     * permet de gérer la création du bouton de déconnexion
     * @return JPanel contenant le bouton de déconnexion
     */
    public JPanel createButtonDeconnexion(){
        JPanel panelDown = new JPanel();
        panelDown.setBackground(Color.WHITE);
        panelDown.setLayout(new BorderLayout());

        JPanel panelRight = new JPanel();
        panelRight.setBackground(Color.WHITE);
        panelRight.setLayout(new BorderLayout());

        //Ajout d'un bouton de deconnexion
//        JButton buttonDeconnexion = new JButton("> Deconnexion !");
//        buttonDeconnexion.setBackground(Color.decode("#c8d400"));
//        buttonDeconnexion.setPreferredSize(new Dimension(250, 45));
//        buttonDeconnexion.setMaximumSize(new Dimension(250, 45));
//        buttonDeconnexion.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
//        buttonDeconnexion.setFont(new Font("Consolas", Font.BOLD, 20));
//
//        buttonDeconnexion.addActionListener(new ActionDeconnexionListener());

        // panelRight.add(buttonDeconnexion, BorderLayout.CENTER);
        panelRight.add(Box.createRigidArea(new Dimension(50, 0)), BorderLayout.EAST);
        panelRight.add(Box.createRigidArea(new Dimension(0, 50)), BorderLayout.SOUTH);

        panelDown.add(panelRight, BorderLayout.EAST);

        return panelDown;
    }

    /**
     * Méthode : createButtonNav
     * ------
     * permet de gérer la création d'un bouton de navigation
     * @param nav le nom de la vue
     * @return JButton du bouton
     */
    public JButton createButtonNav(String nav){

        JButton btn = new JButton(nav);
        btn.setBackground(Color.decode("#c8d400"));
        btn.setBorder(new EmptyBorder(25, 25, 25, 25));
        btn.setFont(new Font("Consolas", Font.PLAIN, 24));
        btn.addActionListener(new ActionNavListener(nav));
        return btn;

    }

    /**
     * Methode : RefreshDataView
     * -------
     * Cette méthode a été proposé par l'IA
     * -------
     * permet de trouver quelle vue est actuellement la vue courante du cardLayout et d'activer la méthode refreshData si implémentée dans la classe
     */
    public void refreshDataView(){
        //On refresh les valeurs pour la vue courante
        //Dans le cas où il s'agit d'une vue nécessitant des valeurs dans les dropdowns
        Component c = Arrays.stream(mainPanel.getComponents())
                .filter(comp -> comp.isVisible())
                .findFirst()
                .orElse(null);

        if (c instanceof Refreshable) {
            ((Refreshable) c).refreshData();
        }
    }

    /**
     *              LISTENER
     */

    // classe interne à la vue car elle y est spécifique
    //Listener qui s'activer au click sur le bouton déconnexion.
    //Effectue la déconnexion de l'utilisateur et le retour à l'affichage de connexion
//    public class ActionDeconnexionListener implements ActionListener {
//        @Override
//        public void actionPerformed(ActionEvent e) {
//            fenetrePrincipal.deconnexionMainPanel();
//        }
//    }
//
    public class ActionNavListener implements ActionListener {

        private String nav;

        public ActionNavListener(String nav) {
            this.nav = nav;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            cl.show(mainPanel, nav);
            refreshDataView();
            sel.addElementSelect("Action", nav);
            cb.refreshComboBoxAction();
        }
    }

}
