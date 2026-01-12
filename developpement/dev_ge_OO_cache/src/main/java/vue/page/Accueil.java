package vue.page;

import vue.FenetrePrincipal;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.sql.SQLException;

/**
 * Classe Accueil
 * Une classe correspondant à une page de l'application
 * correspond à la page d'accueil d'une application
 */
public class Accueil extends JPanel {
    private FenetrePrincipal fenetrePrincipal;

    //Constructeur par données
    public Accueil(FenetrePrincipal fenetrePrincipal) throws SQLException {
        super();
        this.fenetrePrincipal = fenetrePrincipal;

        this.setLayout(new BorderLayout());
        this.setBackground(Color.WHITE);

        //Image
        try {
            BufferedImage img = ImageIO.read(getClass().getResourceAsStream("/logo.png"));
            Image scaledImg = img.getScaledInstance(600, 300, Image.SCALE_SMOOTH);
            JLabel imageLabel = new JLabel(new ImageIcon(scaledImg));
            this.add(imageLabel, BorderLayout.CENTER);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

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
        JButton buttonDeconnexion = new JButton("> Deconnexion !");
        buttonDeconnexion.setBackground(Color.decode("#c8d400"));
        buttonDeconnexion.setPreferredSize(new Dimension(180, 45));
        buttonDeconnexion.setMaximumSize(new Dimension(180, 45));
        buttonDeconnexion.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        buttonDeconnexion.addActionListener(new ActionDeconnexionListener());

        panelRight.add(buttonDeconnexion, BorderLayout.CENTER);
        panelRight.add(Box.createRigidArea(new Dimension(50, 0)), BorderLayout.EAST);
        panelRight.add(Box.createRigidArea(new Dimension(0, 50)), BorderLayout.SOUTH);

        panelDown.add(panelRight, BorderLayout.EAST);

        return panelDown;
    }

    /**
     *              LISTENER
     */

    // classe interne à la vue car elle y est spécifique
    //Listener qui s'activer au click sur le bouton déconnexion.
    //Effectue la déconnexion de l'utilisateur et le retour à l'affichage de connexion
    public class ActionDeconnexionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            fenetrePrincipal.deconnexionMainPanel();
        }
    }

}
