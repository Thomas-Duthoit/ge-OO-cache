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

    //Constructeurs par données
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

        this.username = createTextField("Nom d'utilisateur");
        this.password = createPasswordField("Mot de passe");
        JButton loginButton = createButton();

        //LABEL pour le texte d'erreur
        this.errorLabel = new JLabel("Erreur sur le nom d'utilisateur ou le mot de passe, l'application va se fermer");
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

    /**
     *          METHODES : création des différents éléments à afficher
     */

    /**
     * Méthode : createLogo
     * ------
     * permet de récupérer l'image dans les fichiers et de l'afficher correctement
     * @return JLabel contenant l'image
     */

    //méthode pour récupérer l'image et créer le design autour de celle ci
    public JLabel createLogo(){
        try {
            BufferedImage img = ImageIO.read(getClass().getResourceAsStream("/logo.png")); // Récupère l'image à sa position
            Image scaledImg = img.getScaledInstance(600, 300, Image.SCALE_SMOOTH); // Rajoute une dimension à l'image
            JLabel imageLabel = new JLabel(new ImageIcon(scaledImg)); // Crée le label avec l'image
            imageLabel.setHorizontalAlignment(JLabel.CENTER);
            return imageLabel;
        } catch (IOException e) {
            //Dans le cas où l'image est introuvable
            return new JLabel("Ge-OO-cache", JLabel.CENTER);
        }
    }

    /**
     * Méthode : createTextField
     * -------
     * Le design a été réalisé à l'aide de l'IA générative afin de comprendre le fonctionnement d'un design plus précis sur un TextField
     * ------
     * permet de créer un JTextField selon une valeur de placeholder
     * @param placeholder : texte qui sera afficher par défaut sur le JTextField
     * @return JTextField pour l'input d'utilisateur
     */
    public JTextField createTextField(String placeholder){
        JTextField field = new JTextField(placeholder);

        //Ce listener permet d'ajouter l'effet de placeholder dans le JTextField
        //Permet également d'enregistrer la nouvelle valeur dans un attribut
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
        field.setFont(new Font("Arial", Font.PLAIN, 14));
        field.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 2, true));
        return field;
    }

    /**
     * Méthode : createPasswordField
     * -------
     * Le design a été réalisé à l'aide de l'IA générative afin de comprendre le fonctionnement d'un design plus précis sur un TextField
     * ------
     * permet de créer un JPasswordField selon une valeur de placeholder
     * @param placeholder : texte qui sera afficher par défaut sur le JPasswordField
     * @return JPasswordField pour l'input de mot de passe
     */
    //méthode pour créer le passwordField (input de password)
    private JPasswordField createPasswordField(String placeholder) {
        JPasswordField field = new JPasswordField(placeholder);

        //Ce listener permet d'ajouter l'effet de placeholder dans le JPasswordField
        //Permet également d'enregistrer la nouvelle valeur dans un attribut
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
        field.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 2, true));
        return field;
    }

    /**
     * Méthode : createButton
     * -------
     * Le design a été réalisé à l'aide de l'IA générative afin de comprendre le fonctionnement d'un design plus précis sur un TextField
     * ------
     * permet de créer un Button avec le design approprié
     * @return JButton le bouton créer
     */
    private JButton createButton() {
        JButton button = new JButton("> Connexion");
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setBackground(Color.decode("#c8d400"));
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setPreferredSize(new Dimension(180, 45));
        button.setMaximumSize(new Dimension(180, 45));
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        return button;
    }

    /**
     *              LISTENER
     */

    // classe interne à la vue car elle y est spécifique
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

                // solution proposée par l'IA pour fermer la fenêtre après un certain délai
                Timer closeTimer = new Timer(5000, evt -> {  // on crée un timer swing pour ne pas bloquer le thread UI de swing
                    Window window = SwingUtilities.getWindowAncestor(Login.this);  // on récupère l'instance de la fenêtre
                    if (window != null) {
                        window.dispose(); // on ferme la fenêtre
                    }
                });

                closeTimer.setRepeats(false); // une seule fois
                closeTimer.start();  // lancer le timer

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
}
