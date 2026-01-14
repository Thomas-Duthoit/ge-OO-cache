package vue.page;

import requete.RequeteGeOOCache;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class CreateUser extends JPanel {

    private JTextField inputPseudo;  // champ d'input pour le pseudo du futur utilisateur
    private JTextField inputMdp;  // champ d'input pour le mot de passe

    private RequeteGeOOCache req;

    public CreateUser(RequeteGeOOCache requeteGeOOCache) throws SQLException {
        super();


        this.req = requeteGeOOCache;  // on récupère l'instance pour les requêtes


        this.setLayout(new BorderLayout());
        this.setBackground(Color.WHITE);

        inputPseudo = new JFormattedTextField();
        inputMdp = new JFormattedTextField();


        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setBackground(Color.WHITE);

        formPanel.add(creerChamp("pseudo", inputPseudo));

        formPanel.add(Box.createVerticalStrut(15));

        formPanel.add(creerChamp("mot de passe", inputMdp));

        this.add(formPanel, BorderLayout.CENTER);

        this.add(Box.createHorizontalStrut(50), BorderLayout.WEST);  // espace à gauceS
        this.add(Box.createHorizontalStrut(50), BorderLayout.EAST);  // espace à droite
        this.add(Box.createVerticalStrut(50), BorderLayout.NORTH);  // espace en haut


        JButton btnCreer = new JButton("> Créer");

        btnCreer.setBackground(Color.decode("#c8d400"));
        btnCreer.setPreferredSize(new Dimension(180, 45));
        btnCreer.setMaximumSize(new Dimension(180, 45));
        btnCreer.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        
        btnCreer.addActionListener(new CreerUtilisateurActionListener(req, inputPseudo, inputMdp));

        JPanel panelBtn = new JPanel();
        panelBtn.setLayout(new BorderLayout());
        panelBtn.setBorder(BorderFactory.createEmptyBorder(0, 0, 30, 30));  // bordure en bas et à droite
        panelBtn.setBackground(Color.WHITE);


        panelBtn.add(btnCreer, BorderLayout.EAST);  // à droite

        this.add(panelBtn, BorderLayout.SOUTH);  // le panel du bouton est en bas


        this.setVisible(true);
    }


    // méthode pour créer une ligne, avec un label et une input de texte
    // réalisée à l'aide d'IA générative pour comprendre le fonctionnement des bordures pour un effet de "padding"
    private JPanel creerChamp(String texteLabel, JComponent input) {
        JPanel champ = new JPanel(new BorderLayout());
        champ.setBackground(new Color(230, 230, 230)); // fond gris du champ

        // padding pour le champ
        champ.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
        champ.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));

        // on crée le texte
        JLabel label = new JLabel(texteLabel);
        label.setOpaque(true);
        label.setBackground(new Color(230, 230, 230)); // même couleur que le champ

        // applique une taille et une bordure au texte
        label.setBorder(BorderFactory.createEmptyBorder(6, 10, 6, 10));
        label.setPreferredSize(new Dimension(120, 30));

        // on applique une taille et une bordure à l'input
        input.setBorder(BorderFactory.createEmptyBorder(6, 10, 6, 10));
        input.setPreferredSize(new Dimension(200, 30));

        champ.add(label, BorderLayout.WEST);  // à gauche
        champ.add(input, BorderLayout.CENTER);  // au centre (prend la place restantee)

        return champ;
    }






    // classe interne à la vue car elle y est spécifique
    class CreerUtilisateurActionListener implements ActionListener {

        private JTextField inputPseudo;  // références vers les champs d'onput de la vue
        private JTextField inputMdp;

        private RequeteGeOOCache req;

        public CreerUtilisateurActionListener(RequeteGeOOCache req, JTextField inputPseudo, JTextField inputMdp) {
            this.req = req;  // on récupère l'instance pour les requêtes
            this.inputPseudo = inputPseudo;
            this.inputMdp = inputMdp;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (inputPseudo.getText().isEmpty() || inputMdp.getText().isEmpty()) {  // un des champs est vide
                return;  // on ne fait rien
            }

            System.out.println("Création de l'utilisateur : " + inputPseudo.getText() + "  -  " + inputMdp.getText());
            if (req.creerUtilisateur(inputPseudo.getText(), inputMdp.getText())) {
                System.out.println("Création réussie");
                // vide les inputs
                inputPseudo.setText("");
                inputMdp.setText("");
            } else {
                System.out.println("Création échouée");
            }
        }
    }
}
