package vue.page;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import modele.Utilisateur;
import requete.RequeteGeOOCache;
import vue.FenetrePrincipal;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.sql.SQLException;

/**
 * Classe Login
 * Première vue afficher lors de l'ouverture de l'application
 * Permet de se connecter à l'application
 */

public class Login extends JPanel{
    private FenetrePrincipal fenetrePrincipal;
    private JTextField username;
    private JPasswordField password;
    private JLabel errorLabel;

    public Login(FenetrePrincipal fenetrePrincipal) throws SQLException {
        super();
        this.fenetrePrincipal = fenetrePrincipal;
        this.setLayout(new BorderLayout());
        this.setBackground(Color.WHITE);

        //Interface de connexion

        //Img : LOGO
        JLabel logoLabel = createLogo();
        this.add(logoLabel, BorderLayout.NORTH);

        //FORMULAIRE
        JPanel loginPanel = new JPanel();
        loginPanel.setBackground(Color.WHITE);
        loginPanel.setLayout(new BoxLayout(loginPanel, BoxLayout.Y_AXIS));
        loginPanel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));

        this.username = createTextField("Nom d'utilisateur");
        this.password = createPasswordField("Mot de passe");
        JButton loginButton = createButton();

        //LABEL pour le texte d'erreur
        this.errorLabel = new JLabel("Erreur sur le nom d'utilisateur ou le mot de passe");
        errorLabel.setForeground(Color.RED);
        errorLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        errorLabel.setVisible(false);



        loginButton.addActionListener(new MyButtonListener(loginPanel, this.username, this.password));

        loginPanel.add(errorLabel);
        loginPanel.add(Box.createVerticalStrut(10));
        loginPanel.add(username);
        loginPanel.add(Box.createVerticalStrut(20));
        loginPanel.add(password);
        loginPanel.add(Box.createVerticalStrut(30));
        loginPanel.add(loginButton);
        this.add(loginPanel, BorderLayout.CENTER);

        this.setVisible(true);
    }

    //Listener qui s'activer au click sur le bouton connexion.
    //Vérifie puis effectue une réaction selon le résultat
    public class  MyButtonListener implements ActionListener{
        private JPanel panel;
        private JTextField username;
        private JPasswordField password;

        public MyButtonListener(JPanel panel, JTextField username, JPasswordField password) {
            this.panel = panel;
            this.username = username;
            this.password = password;
        }

        public void actionPerformed(ActionEvent e){
            RequeteGeOOCache requeteGeOOCache = fenetrePrincipal.getRequeteGeOOCache();
            int id = requeteGeOOCache.autoriserConnexionApp(this.username.getText(), new String(this.password.getPassword()));
            if (id == -1){
                System.out.println("Erreur de connexion");
                errorLabel.setVisible(true);
                revalidate();
                repaint();
                return;
            }
            System.out.println("Connexion correcte");
            EntityManager em = requeteGeOOCache.getEm();
            Utilisateur u1 = em.find(Utilisateur.class, id);
            em.close();
            System.out.println("Résultat de la demande de connection : '" + id + "'  -  utilisateur : " + u1);
            fenetrePrincipal.loginValider(u1);
        }
    }

    //méthode pour récupérer l'image et créer le design autour de celle ci
    public JLabel createLogo(){
        try {
            BufferedImage img = ImageIO.read(getClass().getResourceAsStream("/logo.png"));
            Image scaledImg = img.getScaledInstance(600, 300, Image.SCALE_SMOOTH);
            JLabel imageLabel = new JLabel(new ImageIcon(scaledImg));
            imageLabel.setHorizontalAlignment(JLabel.CENTER);
            return imageLabel;
        } catch (IOException e) {
            return new JLabel("Ge-OO-cache", JLabel.CENTER);
        }
    }

    //méthode pour créer le TextField (input de texte)
    // le design a été réalisé à l'aide de l'IA générative afin de comprend le fonctionnement d'un design plus précis sur un TextField
    public JTextField createTextField(String placeholder){
        JTextField field = new JTextField(placeholder);

        //Ajoute un listener pour ajouter l'effet de placeholder
        // + récupère l'information sur le nom d'utilisateur
        field.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if(field.getText().equals(placeholder)){
                    field.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if(field.getText().isEmpty()){
                    field.setText(placeholder);
                }
            }
        });

        field.setMaximumSize(new Dimension(350, 45));
        field.setPreferredSize(new Dimension(350, 45));
        field.setHorizontalAlignment(JTextField.CENTER);
        field.setFont(new Font("Arial", Font.PLAIN, 14));
        field.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1, true));
        return field;
    }

    //méthode pour créer le passwordField (input de password)
    private JPasswordField createPasswordField(String placeholder) {
        JPasswordField field = new JPasswordField(placeholder);

        //Récupère l'information sur le password quand perd focus
        field.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if(field.getText().equals(placeholder)){
                    field.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if(field.getText().isEmpty()){
                    field.setText(placeholder);
                }
            }
        });

        field.setMaximumSize(new Dimension(350, 45));
        field.setHorizontalAlignment(JTextField.CENTER);
        field.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1, true));
        return field;
    }

    //Méthode pour créer le design du Button
    // le design a été réalisé à l'aide de l'IA générative afin de comprend le fonctionnement d'un design plus précis sur un TextField
    private JButton createButton() {
        JButton button = new JButton("> Connexion");
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setBackground(new Color(200, 220, 0)); // vert-jaune
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setPreferredSize(new Dimension(180, 45));
        button.setMaximumSize(new Dimension(180, 45));
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        return button;
    }
}
