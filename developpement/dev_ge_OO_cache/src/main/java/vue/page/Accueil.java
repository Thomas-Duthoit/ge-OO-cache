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
    //Attributs
    private FenetrePrincipal fenetrePrincipal;

    //Constructeur par défaut
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
        JPanel panel = new JPanel();
        panel.setBackground(Color.WHITE);
        panel.setLayout(new BorderLayout());

        //Ajout d'un bouton de deconnexion
        JButton buttonDeconnexion = new JButton("> Deconnexion");
        buttonDeconnexion.setBackground(Color.WHITE);

        buttonDeconnexion.addActionListener(new ActionDeconnexionListener());

        panel.add(buttonDeconnexion, BorderLayout.EAST);
        return panel;
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
