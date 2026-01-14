package vue.page;

import modele.ReseauCache;
import modele.Utilisateur;
import requete.RequeteGeOOCache;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class CreateType extends JPanel {
    private RequeteGeOOCache requeteGeOOCache;
    private JTextField inputNameType;

    public CreateType(RequeteGeOOCache requeteGeOOCache) throws SQLException {
        super();

        this.requeteGeOOCache = requeteGeOOCache;

        this.setLayout(new BorderLayout());
        this.setBackground(Color.WHITE);

        Font font = new Font("Consolas", Font.PLAIN, 24);

        inputNameType = new JFormattedTextField();
        inputNameType.setFont(font);

        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setBackground(Color.WHITE);

        formPanel.add(creerChamp("Nom du Type ", inputNameType));

        this.add(formPanel, BorderLayout.CENTER);

        this.add(Box.createHorizontalStrut(50), BorderLayout.WEST);  // espace à gauche
        this.add(Box.createHorizontalStrut(50), BorderLayout.EAST);  // espace à droite
        this.add(Box.createVerticalStrut(50), BorderLayout.NORTH);  // espace en haut


        JButton btnCreer = new JButton("> Créer !");
        btnCreer.setBackground(Color.decode("#c8d400"));
        btnCreer.setPreferredSize(new Dimension(180, 45));
        btnCreer.setMaximumSize(new Dimension(180, 45));
        btnCreer.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        btnCreer.setFont(font);
        
        btnCreer.addActionListener(new CreerTypeActionListener(requeteGeOOCache, inputNameType));

        JPanel panelBtn = new JPanel();
        panelBtn.setLayout(new BorderLayout());
        panelBtn.setBorder(BorderFactory.createEmptyBorder(0, 0, 30, 30));  // bordure en bas et à droite
        panelBtn.setBackground(Color.WHITE);


        panelBtn.add(btnCreer, BorderLayout.EAST);  // à droite

        this.add(panelBtn, BorderLayout.SOUTH);  // le panel du bouton est en bas


        this.setVisible(true);
    }


    /**
     *          METHODE : creation des éléments de la vue
     */

    /**
     * méthode : creerChamp
     * -------
     * méthode pour créer une ligne, avec un label et une input de texte
     * réalisée à l'aide d'IA générative pour comprendre le fonctionnement des bordures pour un effet de "padding"
     * @param texteLabel : texte à mettre avant l'input
     * @param input : champ de valeur d'entrée
     * @return JPanel du design adéquat
     */
    private JPanel creerChamp(String texteLabel, JComponent input) {
        JPanel champ = new JPanel(new BorderLayout());
        champ.setBackground(new Color(230, 230, 230)); // fond gris du champ

        // padding pour le champ
        champ.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
        champ.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));

        // on crée le texte
        JLabel label = new JLabel(texteLabel);
        label.setFont(new Font("Consolas", Font.PLAIN, 24));
        label.setOpaque(true);
        label.setBackground(new Color(230, 230, 230)); // même couleur que le champ

        // applique une taille et une bordure au texte
        label.setBorder(BorderFactory.createEmptyBorder(6, 10, 6, 10));
        label.setPreferredSize(new Dimension(200, 30));

        // on applique une taille et une bordure à l'input
        input.setBorder(BorderFactory.createEmptyBorder(6, 10, 6, 10));
        input.setPreferredSize(new Dimension(200, 30));

        champ.add(label, BorderLayout.WEST);  // à gauche
        champ.add(input, BorderLayout.CENTER);  // au centre (prend la place restantee)

        return champ;
    }

    /**
     *              LISTENER
     */

    // classe interne à la vue car elle y est spécifique
    // permet de gérer la création du type au moment du click sur le bouton
    class CreerTypeActionListener implements ActionListener {

        private JTextField inputNameType;  // références vers les champs d'onput de la vue

        private RequeteGeOOCache req;

        public CreerTypeActionListener(RequeteGeOOCache req, JTextField inputNameType) {
            this.req = req;  // on récupère l'instance pour les requêtes
            this.inputNameType = inputNameType;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (inputNameType.getText().isEmpty()) {  // le champ type est vide on ne fait rien
                return;  // on ne fait rien
            }

            if (req.creerTypeCache(inputNameType.getText())) {
                inputNameType.setText("");
                System.out.println("Type créé !");
            }

        }
    }
}